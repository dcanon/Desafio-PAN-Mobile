package br.com.bancopan.topgames.main.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import br.com.bancopan.topgames.R
import br.com.bancopan.topgames.databinding.ActivityGamesBinding
import br.com.bancopan.topgames.main.adapter.GameAdapter
import br.com.bancopan.topgames.main.listener.EndlessScrollListener
import br.com.bancopan.topgames.main.listener.GameAdapterListener
import br.com.bancopan.topgames.main.viewmodel.GameListVM
import br.com.bancopan.topgames.repository.data.Game
import br.com.bancopan.topgames.utils.Constants
import timber.log.Timber

class GameListActivity : AppCompatActivity(), GameAdapterListener {
    private lateinit var viewModel: GameListVM
    private lateinit var binding: ActivityGamesBinding
    private var gameAdapter: GameAdapter = GameAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        // Configure Android DataBinding / ViewModel instance
        viewModel = ViewModelProviders.of(this).get(GameListVM::class.java)
        DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_games)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_games)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        //Configure UI Elements
        configureUI()

        // Configure Observers from ViewModel
        configureObservers()

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

    private fun configureObservers() {
        viewModel.serviceSuccess.observe(this, Observer { response ->
            response?.let { gameAdapter.addDataToList(response) }
        })

        viewModel.serviceFailure.observe(this, Observer { response ->
            response?.let {
                Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onItemClicked(view: View, Position: Int, game: Game) {
        GameDetailFragment
                .newInstance(game)
                .show(supportFragmentManager, GameDetailFragment::class.java.simpleName)
    }

}
