package br.com.bancopan.topgames.main.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.view.View
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
    var progressVisibility: ObservableInt
    var labelErrorVisibility: ObservableInt
    var games: MutableLiveData<List<Game>>
    var serviceFailure: MutableLiveData<Boolean>
    val scrollHelper: EndlessScrollHelper

    init {
        isLoading = ObservableBoolean()
        progressVisibility = ObservableInt()
        labelErrorVisibility = ObservableInt()
        games = MutableLiveData()
        serviceFailure = MutableLiveData()
        scrollHelper = EndlessScrollHelper(API_LIMIT)
        App.instance.repositoryComponent.inject(this)

        repository.listener = this
    }

    fun requestData() {
        repository.getTopGames(scrollHelper.nextOffset)
        progressVisibility.set(View.VISIBLE)
        labelErrorVisibility.set(View.GONE)
    }

    fun onRefresh() {
        isLoading.set(true)
        requestData()
    }

    override fun onDataSuccess(response: List<Game>) {
        games.postValue(response)
        configureUIForSuccess()

        if (scrollHelper.totalPages == 1) {
            scrollHelper.updateTotalPage(repository!!.totalItems)
        }

        scrollHelper.updatePageIndex()
    }

    override fun onDataFailure() {
        configureUIForError()
        Timber.d("Failed to load data from Db")
    }

    override fun onAPIFailure() {
        serviceFailure.postValue(false)
        scrollHelper.enable = false
        Timber.d("Failed to load data from cloud")
    }

    override fun onAPISuccess() {
        scrollHelper.enable = true
        Timber.d("onAPISuccess")
    }

    private fun configureUIForError() {
        progressVisibility.set(View.GONE)
        labelErrorVisibility.set(View.VISIBLE)
    }

    private fun configureUIForSuccess() {
        progressVisibility.set(View.GONE)
        labelErrorVisibility.set(View.GONE)
    }

}

