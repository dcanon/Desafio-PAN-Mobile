package br.com.bancopan.topgames

import br.com.bancopan.topgames.repository.GameRepository
import br.com.bancopan.topgames.repository.api.Api
import br.com.bancopan.topgames.repository.db.GameDao
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class GameRepositoryTest {

    lateinit var gameRepository: GameRepository
    lateinit var api: Api
    lateinit var gameDao: GameDao

    @Before
    fun setup() {
        api = mock()
        gameDao = mock()
        gameRepository = GameRepository(gameDao, api)
        `when`(gameDao.findAll()).thenReturn(Flowable.empty())
    }

    @Test
    fun whenEmptyDb_ShouldReturnEmptyList() {
        gameDao.findAll()
            .test()
            .assertEmpty()
    }

}