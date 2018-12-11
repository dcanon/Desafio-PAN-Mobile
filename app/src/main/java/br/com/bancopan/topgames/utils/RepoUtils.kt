package br.com.bancopan.topgames.utils

import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.repository.data.Top

import java.util.ArrayList

object RepoUtils {

    fun buildGameList(response: List<Top>): List<Game> {
        val games = ArrayList<Game>()

        for (top in response) {
            top.game!!.channels = top.channels
            top.game!!.viewers = top.viewers
            games.add(top.game!!)
        }

        return games

    }

}
