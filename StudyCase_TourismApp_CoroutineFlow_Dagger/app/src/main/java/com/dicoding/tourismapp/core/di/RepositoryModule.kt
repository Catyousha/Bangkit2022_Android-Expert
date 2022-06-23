package com.dicoding.tourismapp.core.di

import com.dicoding.tourismapp.core.data.TourismRepository
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
// module ini bersifat abstrak karena provider hanya menyediakan kelas abstrak saja
abstract class RepositoryModule {

    // kalau yang disediakan adalah abstrak, maka pakai anotasi @Binds
    @Binds
    abstract fun provide(tourismRepository: TourismRepository): ITourismRepository
}