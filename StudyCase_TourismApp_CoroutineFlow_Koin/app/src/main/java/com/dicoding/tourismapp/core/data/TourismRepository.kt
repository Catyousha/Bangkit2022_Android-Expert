package com.dicoding.tourismapp.core.data

import com.dicoding.tourismapp.core.data.source.local.LocalDataSource
import com.dicoding.tourismapp.core.data.source.remote.RemoteDataSource
import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse
import com.dicoding.tourismapp.core.data.source.remote.response.TourismResponse
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import com.dicoding.tourismapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TourismRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ITourismRepository {

  companion object {
    @Volatile private var instance: TourismRepository? = null

    fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource): TourismRepository =
        instance ?: synchronized(this) { instance ?: TourismRepository(remoteData, localData) }
  }

  override fun getAllTourism(): Flow<Resource<List<Tourism>>> =
      object : NetworkBoundResource<List<Tourism>, List<TourismResponse>>() {
            // load data dari db
            override fun loadFromDB(): Flow<List<Tourism>> {
              return localDataSource.getAllTourism().map { DataMapper.mapEntitiesToDomain(it) }
            }

            // kondisi untuk fetch data
            override fun shouldFetch(data: List<Tourism>?): Boolean {
              return (data == null || data.isEmpty())
            }

            // fetch data dari api
            override suspend fun createCall(): Flow<ApiResponse<List<TourismResponse>>> {
              return remoteDataSource.getAllTourism()
            }

            // simpan data dari api langsung ke db
            override suspend fun saveCallResult(data: List<TourismResponse>) {
              val tourismList = DataMapper.mapResponsesToEntities(data)
              localDataSource.insertTourism(tourismList)
            }
          }
          .asFlow()

  override fun getFavoriteTourism(): Flow<List<Tourism>> {
    return localDataSource.getFavoriteTourism().map { DataMapper.mapEntitiesToDomain(it) }
  }

  override fun setFavoriteTourism(tourism: Tourism, state: Boolean): Flow<Boolean> = flow {
    val tourismEntity = DataMapper.mapDomainToEntity(tourism)
    localDataSource.setFavoriteTourism(tourismEntity, state)
    emit(true)
  }
}
