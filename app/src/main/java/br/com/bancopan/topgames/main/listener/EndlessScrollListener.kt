package br.com.bancopan.topgames.main.listener

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

abstract class EndlessScrollListener(private var visibleThreshold: Int) : RecyclerView.OnScrollListener() {
    private var previousTotalItemCount = 0
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as GridLayoutManager)
            .findFirstVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && firstVisibleItem + visibleItemCount + visibleThreshold >= totalItemCount) {
            loading = onLoadMore()
        }
    }

    abstract fun onLoadMore(): Boolean

}