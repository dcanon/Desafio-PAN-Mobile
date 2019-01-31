package br.com.bancopan.topgames.main.viewmodel

import android.arch.lifecycle.ViewModel
import br.com.bancopan.topgames.repository.data.Game

class TopVM(var game: Game) : ViewModel() {

    fun gameName(): String? {
        return game.name
    }

    fun imageUrl(): String? {
        return game.box?.large
    }


}
