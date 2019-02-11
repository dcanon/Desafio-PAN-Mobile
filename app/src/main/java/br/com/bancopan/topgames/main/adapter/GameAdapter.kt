package br.com.bancopan.topgames.main.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.bancopan.topgames.R
import br.com.bancopan.topgames.databinding.ItemGameBinding
import br.com.bancopan.topgames.main.BindingHolder
import br.com.bancopan.topgames.main.listener.AdapterEvents
import br.com.bancopan.topgames.main.viewmodel.TopVM
import br.com.bancopan.topgames.repository.data.Game

import java.util.ArrayList

class GameAdapter(private val listener: AdapterEvents<Game>) : RecyclerView.Adapter<BindingHolder>() {
    private val games = ArrayList<Game>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): BindingHolder {
        val binding = DataBindingUtil.inflate<ItemGameBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_game, viewGroup, false
        )
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, i: Int) {
        holder.binding.viewModel = TopVM(games[i])
        holder.binding.container.setOnClickListener {
            view -> listener.onItemClicked(view, i, games[i])
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun addDataToList(data: List<Game>) {
        val positionStart = itemCount
        games.addAll(data)
        notifyItemRangeInserted(positionStart, games.size)
    }

}
