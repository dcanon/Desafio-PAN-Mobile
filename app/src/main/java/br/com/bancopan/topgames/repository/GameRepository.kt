package br.com.bancopan.topgames.repository

import br.com.bancopan.topgames.repository.api.Api
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.repository.data.GameResponse
import br.com.bancopan.topgames.repository.data.ServiceErrorModel
import br.com.bancopan.topgames.repository.db.GameDao
import br.com.bancopan.topgames.utils.Constants
import br.com.bancopan.topgames.utils.RepoUtils
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class GameRepository(val gameDao: GameDao, val api: Api) {
    var totalItems: Int = 0

    @Synchronized
    fun getTopGames(offset: Int, onResponse: (List<Game>) -> Unit, onFailure: (ServiceErrorModel) -> Unit,
                    limit: Int = Constants.API_LIMIT) {

        GlobalScope.launch(Dispatchers.Main) {

            try {
                val request = api.getTopGames(limit, offset)
                val response = request.await()
                applyOnModelResponse(onResponse, response)
            } catch (e: Exception) {
                getDataFromDb(e, onResponse, onFailure)
            }
        }

    }

    @Synchronized
    private fun applyOnModelResponse(onResponse: (List<Game>) -> Unit, response: GameResponse) {
        val games = RepoUtils.buildGameList(response.top!!)
        totalItems = response.total!!
        onResponse.invoke(games)
        storeGamesInDb(games)
    }

    @Synchronized
    private fun applyOnThrowableResponse(t: Throwable, onError: (ServiceErrorModel) -> Unit) {
        val error = ServiceErrorModel()
        when (t) {
            is HttpException -> {
                error.apply {
                    throwable = t
                    httpCode = t.code()
                    isConnectException = false
                    errorMessage = t.localizedMessage
                }
            }
            else -> {
                error.apply {
                    isConnectException = true
                    errorMessage = "unknown error"
                }
            }
        }
        onError.invoke(error)
    }

    private fun getDataFromDb(t: Throwable, onResponse: (List<Game>) -> Unit, onFailure: (ServiceErrorModel) -> Unit) {
        gameDao.findAll()
                .doOnError { applyOnThrowableResponse(t, onFailure) }
                .subscribe { result ->
                    if (result.isValid && result.isNotEmpty()) {
                        onResponse.invoke(Realm.getDefaultInstance().copyFromRealm(result))
                        Timber.d("Loaded ${result.size} games from Db")
                    } else {
                        applyOnThrowableResponse(t, onFailure)
                    }
                }
    }

    private fun storeGamesInDb(games: List<Game>) {
        gameDao.addAll(games)
    }

}
