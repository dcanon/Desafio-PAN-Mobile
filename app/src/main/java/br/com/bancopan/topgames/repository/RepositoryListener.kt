package br.com.bancopan.topgames.repository

import br.com.bancopan.topgames.repository.data.Game

interface RepositoryListener {

    fun onDataSuccess(games: List<Game>)

    fun onDataFailure()

    fun onAPISuccess()

    fun onAPIFailure()

}
