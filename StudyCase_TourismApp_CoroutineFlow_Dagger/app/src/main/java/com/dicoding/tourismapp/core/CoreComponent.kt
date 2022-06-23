package com.dicoding.tourismapp.core

import android.content.Context
import com.dicoding.tourismapp.core.di.RepositoryModule
import com.dicoding.tourismapp.core.domain.repository.ITourismRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        // modul dapat menerima param dengan tipe data Context
        // dari activity atau fragment
        // dengan anotasi @BindsInstance
        fun create(@BindsInstance context: Context): CoreComponent
    }

    // pembuatan method provide dibutuhkan oleh TourismInteractor di AppModule
    fun provideRepository() : ITourismRepository
}