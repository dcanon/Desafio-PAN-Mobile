package br.com.bancopan.topgames.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import br.com.bancopan.topgames.repository.db.GameDao
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesGameDao(): GameDao {
        return GameDao(Realm.getDefaultInstance())
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

}
