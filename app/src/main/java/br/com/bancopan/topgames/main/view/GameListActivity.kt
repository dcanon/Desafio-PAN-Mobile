package br.com.bancopan.topgames.main.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import br.com.bancopan.topgames.R
import br.com.bancopan.topgames.core.CoreVMActivity
import br.com.bancopan.topgames.databinding.ActivityGamesBinding
import br.com.bancopan.topgames.main.adapter.GameAdapter
import br.com.bancopan.topgames.main.listener.AdapterEvents
import br.com.bancopan.topgames.main.listener.EndlessScrollListener
import br.com.bancopan.topgames.main.viewmodel.GameListVM
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.utils.Constants
import timber.log.Timber

class GameListActivity : CoreVMActivity<GameListVM, ActivityGamesBinding>(), AdapterEvents<Game> {

    private var gameAdapter: GameAdapter = GameAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure Android DataBinding / ViewModel instance
        setContentView(R.layout.activity_games, GameListVM::class.java)

        //Configure UI Elements
        configureUI()

        // Configure Observers from ViewModel
        initObserves()

        // Provides first data
        viewModel.requestData()
    }

    private fun configureUI() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = gameAdapter

        binding.recyclerView.addOnScrollListener(object : EndlessScrollListener(Constants.API_LIMIT) {
            override fun onLoadMore(): Boolean {
                if (viewModel.scrollHelper.enable) {
                    Timber.d("Requesting more data")
                    viewModel.requestData()
                }
                return true
            }
        })

    }

    private fun initObserves() {
        viewModel.serviceSuccess.observe(this, Observer { response ->
            response?.let { gameAdapter.addDataToList(response) }
        })

        viewModel.serviceFailure.observe(this, Observer { response ->
            response?.let {
                Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun onItemClicked(view: View, Position: Int, item: Game) {
        GameDetailFragment
                .newInstance(item)
                .show(supportFragmentManager, GameDetailFragment::class.java.simpleName)
    }

}
