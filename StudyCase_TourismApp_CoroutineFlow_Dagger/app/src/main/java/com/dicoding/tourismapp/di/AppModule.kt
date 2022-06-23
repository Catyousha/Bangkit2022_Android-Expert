package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    // param TourismInteractor akan dinjeksi oleh ITourismRepository dari CoreComponent
    @Binds
    abstract fun provideTourismUseCase(
        tourismInteractor: TourismInteractor
    ) :TourismUseCase
}