package br.com.bancopan.topgames.repository.db

import br.com.bancopan.topgames.repository.data.Game
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import timber.log.Timber

class GameDao(val realm: Realm) {

    fun findAll() : Flowable<RealmResults<Game>> {
        return realm.where<Game>()
            .findAll()
            .asFlowable()
    }

    fun addAll(games: List<Game>) {
        val realmList = RealmList<Game>()
        realmList.addAll(games)

        try {
            realm.beginTransaction()
            realm.insertOrUpdate(realmList)
            realm.commitTransaction()
            Timber.d("Inserted ${games.size} games in Db")
        } catch (ex: Exception) {
            Timber.d(ex.localizedMessage)
            realm.cancelTransaction()
        }
    }

}