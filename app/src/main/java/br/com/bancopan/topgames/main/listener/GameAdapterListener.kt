package br.com.bancopan.topgames.main.listener

import android.view.View
import br.com.bancopan.topgames.repository.data.Game

interface GameAdapterListener {

    fun onItemClicked(view: View, Position: Int, game: Game)
}