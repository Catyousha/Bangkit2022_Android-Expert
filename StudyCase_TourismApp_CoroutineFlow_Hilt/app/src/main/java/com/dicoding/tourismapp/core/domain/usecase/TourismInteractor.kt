package com.dicoding.tourismapp.core.domain.usecase

import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TourismInteractor
@Inject constructor(
  private val tourismRepository: ITourismRepository
) : TourismUseCase {
  override fun getAllTourism(): Flow<Resource<List<Tourism>>> {
    return tourismRepository.getAllTourism()
  }

  override fun getFavoriteTourism(): Flow<List<Tourism>> {
    return tourismRepository.getFavoriteTourism()
  }

  override fun setFavoriteTourism(tourism: Tourism, state: Boolean): Flow<Boolean> {
    return tourismRepository.setFavoriteTourism(tourism, state)
  }
}
