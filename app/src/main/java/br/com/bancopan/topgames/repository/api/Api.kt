package br.com.bancopan.topgames.repository.api

import br.com.bancopan.topgames.repository.data.GameResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    /**
     * Gets games sorted by number of current viewers on Twitch, most popular first
     *
     * @param limit  Maximum number of objects to return. Default: 10. Maximum: 100.
     * @param offset Object offset for pagination of results. Default: 0.
     * @return
     */
    @GET("kraken/games/top")
    fun getTopGames(@Query("limit") limit: Int, @Query("offset") offset: Int): Deferred<GameResponse>

}
