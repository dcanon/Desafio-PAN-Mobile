package br.com.bancopan.topgames.repository

import android.annotation.SuppressLint
import br.com.bancopan.topgames.repository.api.Api
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.repository.db.GameDao
import br.com.bancopan.topgames.utils.Constants
import br.com.bancopan.topgames.utils.RepoUtils
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import timber.log.Timber

class GameRepository(val gameDao: GameDao, val api: Api) {
    var totalItems: Int = 0
    var listener: RepositoryListener? = null

    fun getTopGames(offset: Int) {
        getDataFromApi(Constants.API_LIMIT, offset)
    }

    private fun getDataFromApi(limit: Int, offset: Int) {
        GlobalScope.launch(Dispatchers.Main) {

            try {
                val request = api.getTopGames(limit, offset)
                val response = request.await()

                val games = RepoUtils.buildGameList(response.top!!)
                listener?.onDataSuccess(games)
                totalItems = response.total!!
                storeGamesInDb(games)

            } catch (e: Exception) {
                listener?.onAPIFailure()
                getDataFromDb()
            }
        }

    }

    @SuppressLint("CheckResult")
    private fun getDataFromDb() {
        gameDao.findAll()
                .doOnError {
                    listener?.onDataFailure()
                }
                .subscribe {
                    if (it.isValid && it.isNotEmpty()) {
                        Timber.d("Loaded ${it.size} games from Db")
                        listener?.onDataSuccess(Realm.getDefaultInstance().copyFromRealm(it))
                    }
                }
    }

    private fun storeGamesInDb(games: List<Game>) {
        gameDao.addAll(games)
    }

}
