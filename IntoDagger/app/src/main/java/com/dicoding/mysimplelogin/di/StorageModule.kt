package com.dicoding.mysimplelogin.di

import android.content.Context
import com.dicoding.mysimplelogin.SessionManager
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    // akan otomatis inject ini ke seluruh class yang membutuhkan SessionManager
    @Provides
    fun provideSessionManager(context: Context): SessionManager {
        return SessionManager(context)
    }
}