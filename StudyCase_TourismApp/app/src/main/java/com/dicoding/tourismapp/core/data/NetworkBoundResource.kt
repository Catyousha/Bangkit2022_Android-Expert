package com.dicoding.tourismapp.core.data

import android.annotation.SuppressLint
import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse
import com.dicoding.tourismapp.core.utils.AppExecutors
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
@Suppress("LeakingThis")
abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {
  // menampung hasil fetch data
  private val result = PublishSubject.create<Resource<ResultType>>()
  private val mCompositeDisposable = CompositeDisposable()

  init {
    val dbSource = loadFromDB()
    val db =
        dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { value ->
              dbSource.unsubscribeOn(Schedulers.io())
              if (shouldFetch(value)) {
                fetchFromNetwork()
              } else {
                result.onNext(Resource.Success(value))
              }
            }
    mCompositeDisposable.add(db)
  }

  protected open fun onFetchFailed() {}

  protected abstract fun loadFromDB(): Flowable<ResultType>
  protected abstract fun shouldFetch(data: ResultType?): Boolean
  protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>
  protected abstract fun saveCallResult(data: RequestType)

  // template fetch data dari API
  private fun fetchFromNetwork() {
    val apiResponse = createCall()
    result.onNext(Resource.Loading())

    val response =
        apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete { mCompositeDisposable.dispose() }
            .subscribe { resValue ->
              when (resValue) {
                is ApiResponse.Success -> {
                  saveCallResult(resValue.data)
                  val dbSource = loadFromDB()
                  dbSource
                      .subscribeOn(Schedulers.computation())
                      .observeOn(AndroidSchedulers.mainThread())
                      .take(1)
                      .subscribe {
                        dbSource.unsubscribeOn(Schedulers.computation())
                        result.onNext(Resource.Success(it))
                      }
                }
                is ApiResponse.Empty -> {
                  val dbSource = loadFromDB()
                  dbSource
                      .subscribeOn(Schedulers.computation())
                      .observeOn(AndroidSchedulers.mainThread())
                      .take(1)
                      .subscribe {
                        dbSource.unsubscribeOn(Schedulers.computation())
                        result.onNext(Resource.Success(it))
                      }
                }
                is ApiResponse.Error -> {
                  onFetchFailed()
                  result.onNext(Resource.Error(resValue.errorMessage, null))
                }
              }
            }
    mCompositeDisposable.add(response)
  }

  fun asFlowable(): Flowable<Resource<ResultType>> = result.toFlowable(BackpressureStrategy.BUFFER)
}
