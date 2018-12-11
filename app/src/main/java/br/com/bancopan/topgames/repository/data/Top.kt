package br.com.bancopan.topgames.repository.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Top : RealmObject() {
    @SerializedName("game")
    @Expose
    var game: Game? = null
    @SerializedName("viewers")
    @Expose
    var viewers: Int? = null
    @SerializedName("channels")
    @Expose
    var channels: Int? = null

}
