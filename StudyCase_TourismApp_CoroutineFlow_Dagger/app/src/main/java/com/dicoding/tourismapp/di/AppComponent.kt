package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.CoreComponent
import com.dicoding.tourismapp.detail.DetailTourismActivity
import com.dicoding.tourismapp.favorite.FavoriteFragment
import com.dicoding.tourismapp.home.HomeFragment
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    // fungsi inject digunakan oleh activity untuk menginjeksi segala dependensinya
    // (requireActivity().application as MyApplication).appComponent.inject(this)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: DetailTourismActivity)
}