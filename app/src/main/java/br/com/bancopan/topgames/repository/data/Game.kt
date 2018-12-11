package br.com.bancopan.topgames.repository.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Game() : RealmObject(), Parcelable {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Int? = null
    @SerializedName("_id")
    @Expose
    @PrimaryKey
    var id: Int? = null
    @SerializedName("giantbomb_id")
    @Expose
    var giantbombId: Int? = null
    @SerializedName("box")
    @Expose
    var box: Box? = null
    @SerializedName("logo")
    @Expose
    var logo: Logo? = null
    @SerializedName("localized_name")
    @Expose
    var localizedName: String? = null
    @SerializedName("locale")
    @Expose
    var locale: String? = null
    @Expose(serialize = false)
    var viewers: Int? = null
    @Expose(serialize = false)
    var channels: Int? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        popularity = parcel.readValue(Int::class.java.classLoader) as? Int
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        giantbombId = parcel.readValue(Int::class.java.classLoader) as? Int
        box = parcel.readParcelable(Box::class.java.classLoader)
        logo = parcel.readParcelable(Logo::class.java.classLoader)
        localizedName = parcel.readString()
        locale = parcel.readString()
        viewers = parcel.readValue(Int::class.java.classLoader) as? Int
        channels = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(popularity)
        parcel.writeValue(id)
        parcel.writeValue(giantbombId)
        parcel.writeParcelable(box, flags)
        parcel.writeParcelable(logo, flags)
        parcel.writeString(localizedName)
        parcel.writeString(locale)
        parcel.writeValue(viewers)
        parcel.writeValue(channels)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

    fun imageUrl() : String? {
        return box?.large
    }


}
