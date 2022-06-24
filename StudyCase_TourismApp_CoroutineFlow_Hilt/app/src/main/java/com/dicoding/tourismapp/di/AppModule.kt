package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    // param TourismInteractor akan dinjeksi oleh ITourismRepository dari CoreComponent
    @Binds
    @ViewModelScoped // cuma dibutuhkan oleh viewmodel
    abstract fun provideTourismUseCase(
        tourismInteractor: TourismInteractor
    ) :TourismUseCase
}