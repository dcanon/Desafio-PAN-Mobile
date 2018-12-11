package br.com.bancopan.topgames.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import br.com.bancopan.topgames.App
import br.com.bancopan.topgames.main.EndlessScrollHelper
import br.com.bancopan.topgames.repository.GameRepository
import br.com.bancopan.topgames.repository.RepositoryListener
import br.com.bancopan.topgames.repository.data.Game

import javax.inject.Inject

import br.com.bancopan.topgames.utils.Constants.API_LIMIT
import timber.log.Timber

class GameListViewModel : ViewModel(), RepositoryListener {
    @Inject
    lateinit var repository: GameRepository
    var isLoading: ObservableBoolean
    var games: MutableLiveData<List<Game>>
    val scrollHelper: EndlessScrollHelper

    init {
        isLoading = ObservableBoolean()
        games = MutableLiveData()
        scrollHelper = EndlessScrollHelper(API_LIMIT)
        App.instance.repositoryComponent.inject(this)

        repository.listener = this
    }

    fun requestData() {
        repository.getTopGames(scrollHelper.nextOffset)
    }

    fun onRefresh() {
        isLoading.set(true)
        requestData()
    }

    override fun onDataSuccess(response: List<Game>) {
        games.postValue(response)
        isLoading.set(false)

        if (scrollHelper.totalPages == 1) {
            scrollHelper.updateTotalPage(repository!!.totalItems)
        }

        scrollHelper.updatePageIndex()
    }

    override fun onDataFailure() {
    }

    override fun onAPIFailure() {
        scrollHelper.enable = false
        Timber.d("onAPIFailure")
    }

    override fun onAPISuccess() {
        scrollHelper.enable = true
        Timber.d("onAPISuccess")
    }

}

