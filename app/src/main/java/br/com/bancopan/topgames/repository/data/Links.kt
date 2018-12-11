package br.com.bancopan.topgames.repository.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Links() : RealmObject(), Parcelable {

    @SerializedName("self")
    @Expose
    var self: String? = null
    @SerializedName("next")
    @Expose
    var next: String? = null

    constructor(parcel: Parcel) : this() {
        self = parcel.readString()
        next = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(self)
        parcel.writeString(next)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Links> {
        override fun createFromParcel(parcel: Parcel): Links {
            return Links(parcel)
        }

        override fun newArray(size: Int): Array<Links?> {
            return arrayOfNulls(size)
        }
    }

}
