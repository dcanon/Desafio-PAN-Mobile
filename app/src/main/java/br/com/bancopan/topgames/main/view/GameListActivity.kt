package br.com.bancopan.topgames.main.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import br.com.bancopan.topgames.R
import br.com.bancopan.topgames.databinding.ActivityGamesBinding
import br.com.bancopan.topgames.main.adapter.GameAdapter
import br.com.bancopan.topgames.main.listener.EndlessScrollListener
import br.com.bancopan.topgames.main.listener.GameAdapterListener
import br.com.bancopan.topgames.main.viewmodel.GameListViewModel
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.utils.Constants
import timber.log.Timber

class GameListActivity : AppCompatActivity(), GameAdapterListener {
    private var viewModel: GameListViewModel? = null
    private var binding: ActivityGamesBinding? = null
    private var gameAdapter: GameAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        // Configure Android DataBinding / ViewModel instance
        viewModel = ViewModelProviders.of(this).get(GameListViewModel::class.java)
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_games)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_games)
        binding!!.viewModel = viewModel
        binding!!.setLifecycleOwner(this)

        //Configure UI Elements
        configureUI()

        // Configure Observers from ViewModel
        configureObservers()

        // Provides first data
        viewModel!!.requestData()
    }

    private fun configureUI() {
        gameAdapter = GameAdapter(this)
        val layoutManager = GridLayoutManager(this, 3)

        binding!!.recyclerView.layoutManager = layoutManager
        binding!!.recyclerView.adapter = gameAdapter

        binding!!.recyclerView.addOnScrollListener(object : EndlessScrollListener(Constants.API_LIMIT) {
            override fun onLoadMore(): Boolean {
                if (viewModel!!.scrollHelper.enable) {
                    Timber.d("Requesting more data")
                    viewModel!!.requestData()
                }
                return true
            }
        })

    }

    private fun configureObservers() {
        viewModel!!.games.observe(this, Observer {
                games -> gameAdapter!!.addDataToList(games!!) }
        )
    }

    override fun onItemClicked(view: View, Position: Int, game: Game) {
        val fm = supportFragmentManager
        val frag = GameDetailFragment.newInstance(game)
        frag.show(fm, GameDetailFragment::class.java.simpleName)
    }

}
