package com.dicoding.tourismapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.tourismapp.core.ui.ViewModelFactory
import com.dicoding.tourismapp.detail.DetailTourismViewModel
import com.dicoding.tourismapp.favorite.FavoriteViewModel
import com.dicoding.tourismapp.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    // Inject object ini ke dalam Map (@IntoMap) -> ViewModelFactory
    // dengan HomeViewModel::class sebagai Key (@ViewModelKey)
    // dan HomeViewModel sebagai value (parameter pada fungsi).
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailTourismViewModel::class)
    abstract fun bindDetailTourismViewModel(viewModel: DetailTourismViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}