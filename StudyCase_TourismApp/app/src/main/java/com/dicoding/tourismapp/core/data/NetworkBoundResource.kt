package com.dicoding.tourismapp.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse

import com.dicoding.tourismapp.core.utils.AppExecutors

abstract class NetworkBoundResource<ResultType, RequestType>(
    private val mExecutors: AppExecutors
) {
    // menampung hasil fetch data
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        // load data awal
        result.value = Resource.Loading(null)

        // sumber database
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        // melakukan mediasi antara data dari db dan api
        result.addSource(dbSource) { data ->
            // ilangin sumber db langsung
            result.removeSource(dbSource)

            // cek apakah harus fetch dari api atau tidak dengan data saat ini
            if (shouldFetch(data)) {
                // jika harus fetch, maka fetch dari api
                fetchFromNetwork(dbSource)
            } else {
                // jika tidak harus fetch, maka set hasil dari db
                result.addSource(dbSource) { newData ->
                    result.value = Resource.Success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
    protected abstract fun saveCallResult(data: RequestType)

    // template fetch data dari API
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        // menambahkan sumber db ke result buat menunggu hasil dari api
        result.addSource(dbSource) { newData ->
            result.value = Resource.Loading(newData)
        }

        // fetch data dari api
        result.addSource(apiResponse) { response ->
            // ilangin sumber dari api
            result.removeSource(apiResponse)

            // ilangin sumber dari db
            result.removeSource(dbSource)

            when (response) {

                is ApiResponse.Success ->
                    mExecutors.diskIO().execute {
                        // simpan hasil api ke db
                        saveCallResult(response.data)

                        // refresh data dari db dan set sumber data dari db
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.Success(newData)
                            }
                        }
                    }

                is ApiResponse.Empty -> mExecutors.mainThread().execute {

                    // refresh data dari db dan set sumber data dari db
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.Success(newData)
                    }
                }

                is ApiResponse.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.Error(response.errorMessage, newData)
                    }
                }

            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}