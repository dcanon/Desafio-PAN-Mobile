package br.com.bancopan.topgames.module

import android.app.Application
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class MainModule(val application: Application) {

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return application
    }
}