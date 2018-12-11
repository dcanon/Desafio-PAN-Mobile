package br.com.bancopan.topgames.repository.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GameResponse {

    @SerializedName("_total")
    @Expose
    var total: Int? = null
    @SerializedName("_links")
    @Expose
    var links: Links? = null
    @SerializedName("top")
    @Expose
    var top: List<Top>? = null

}
