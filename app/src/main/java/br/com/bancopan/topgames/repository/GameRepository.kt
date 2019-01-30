package br.com.bancopan.topgames.repository

import android.annotation.SuppressLint
import br.com.bancopan.topgames.repository.api.Api
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.repository.data.GameResponse
import br.com.bancopan.topgames.repository.data.Top
import br.com.bancopan.topgames.repository.db.GameDao
import br.com.bancopan.topgames.utils.Constants
import br.com.bancopan.topgames.utils.RepoUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException

class GameRepository(val gameDao: GameDao, val api: Api) {
    var totalItems: Int = 0
//    var listener: RepositoryListener? = null

    @Deprecated("Ira ser desabilitado")
    fun getTopGames(offset: Int) {
//        serviceCall(offset)
    }
    /*
    @Synchronized
    fun <T> serviceCall(service: Observable<T>, onResponse: (T) -> Unit, onFailure: (ServiceErrorModel) -> Unit) {

        if (App.getInstance().hasInternet) {
            service.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { response -> applyOnModelResponse(response, service, onResponse, onFailure) },
                            { throwable -> applyOnThrowableResponse(throwable, service, onResponse, onFailure) }
                    ).addTo(disposables)

        } else {
            applyOnThrowableResponse(ConnectException("No Connection available"), service, onResponse, onFailure)
        }
    }*/


    fun serviceCall(offset: Int, onResponse: (List<Game>) -> Unit, onFailure: (String) -> Unit,
                    limit: Int = Constants.API_LIMIT) {

        GlobalScope.launch(Dispatchers.Main) {

            try {
                val request = api.getTopGames(limit, offset)
                val response = request.await()

                response.top?.apply {
                    val games = RepoUtils.buildGameList(response.top!!)
                    totalItems = response.total!!
                    onResponse.invoke(games)
                }

            } catch (e: Exception) {
                onFailure.invoke(e.localizedMessage)
            }
        }

    }

    @SuppressLint("CheckResult")
    private fun getDataFromDb() {
        gameDao.findAll()
                .doOnError {
//                    listener?.onDataFailure()
                }
                .subscribe {
                    if (it.isValid && it.isNotEmpty()) {
                        Timber.d("Loaded ${it.size} games from Db")
//                        listener?.onDataSuccess(Realm.getDefaultInstance().copyFromRealm(it))
                    }
                }
    }

    private fun storeGamesInDb(games: List<Game>) {
        gameDao.addAll(games)
    }

}
