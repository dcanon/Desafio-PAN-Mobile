package br.com.bancopan.topgames

import android.app.Application
import br.com.bancopan.topgames.component.DaggerRepositoryComponent
import br.com.bancopan.topgames.component.RepositoryComponent
import br.com.bancopan.topgames.module.DatabaseModule
import br.com.bancopan.topgames.module.MainModule
import br.com.bancopan.topgames.module.RetrofitModule
import io.realm.Realm
import timber.log.Timber

class App : Application() {
    lateinit var repositoryComponent: RepositoryComponent

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
        Realm.init(this)
        instance = this

        repositoryComponent = DaggerRepositoryComponent.builder()
            .mainModule(MainModule(this))
            .retrofitModule(RetrofitModule())
            .databaseModule(DatabaseModule())
            .build()
    }

}
