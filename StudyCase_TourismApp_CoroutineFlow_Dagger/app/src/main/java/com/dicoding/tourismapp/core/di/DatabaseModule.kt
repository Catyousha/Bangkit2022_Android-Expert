package com.dicoding.tourismapp.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.tourismapp.core.data.source.local.room.TourismDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    // singleton berarti hanya ada 1 instance yang dibentuk dan berlaku untuk keseluruhan aplikasi
    @Singleton

    // setiap fungsi yang:
    // 1. dianotasikan @Inject
    // 2. memiliki parameter dengan tipe data yang sama dengan @Provides (TourismDatabase)
    // maka akan disediakan dependensinya dengan provider ini
    @Provides
    fun provideDatabase(context: Context): TourismDatabase = Room.databaseBuilder(
        context,
        TourismDatabase::class.java,
        "Tourism.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: TourismDatabase) = database.tourismDao()
}