package br.com.bancopan.topgames.module

import br.com.bancopan.topgames.repository.GameRepository
import br.com.bancopan.topgames.repository.api.Api
import br.com.bancopan.topgames.repository.db.GameDao
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesGameRepository(gameDao: GameDao, api: Api): GameRepository {
        return GameRepository(gameDao, api)
    }

}
