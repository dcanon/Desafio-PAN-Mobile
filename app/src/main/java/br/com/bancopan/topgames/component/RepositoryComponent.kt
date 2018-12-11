package br.com.bancopan.topgames.component

import br.com.bancopan.topgames.main.viewmodel.GameListViewModel
import br.com.bancopan.topgames.module.DatabaseModule
import br.com.bancopan.topgames.module.MainModule
import br.com.bancopan.topgames.module.RepositoryModule
import br.com.bancopan.topgames.module.RetrofitModule
import dagger.Component

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MainModule::class, RetrofitModule::class, DatabaseModule::class, RepositoryModule::class))
interface RepositoryComponent {
    fun inject(vmGameList: GameListViewModel)
}