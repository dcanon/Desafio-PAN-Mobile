package br.com.bancopan.topgames.main

class EndlessScrollHelper(limit: Int, total: Int = limit) {
    var currentPage = 1
    var limit = 1
    var totalPages = 1
    var enable: Boolean = true

    val isNextPageAvailable: Boolean
        get() = currentPage < totalPages

    val nextOffset: Int
        get() = (currentPage - 1) * limit + 1

    init {
        if (total >= limit) {
            this.totalPages = calculateTotalPages(limit, total)
            this.limit = limit
        }
    }

    fun updatePageIndex() {
        if (currentPage < totalPages) {
            currentPage += 1
        }
    }

    fun updateTotalPage(totalItems: Int) {
        if (totalItems >= limit) {
            this.totalPages = calculateTotalPages(limit, totalItems)
        }
    }

    private fun calculateTotalPages(limit: Int, total: Int): Int {
        var pages = total / limit
        if (total % limit > 0) {
            pages++
        }
        return pages
    }
}
