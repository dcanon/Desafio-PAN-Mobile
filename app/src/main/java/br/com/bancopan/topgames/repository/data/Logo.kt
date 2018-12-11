package br.com.bancopan.topgames.repository.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Logo() : RealmObject(), Parcelable {
    @SerializedName("large")
    @Expose
    var large: String? = null
    @SerializedName("medium")
    @Expose
    var medium: String? = null
    @SerializedName("small")
    @Expose
    var small: String? = null
    @SerializedName("template")
    @Expose
    var template: String? = null

    constructor(parcel: Parcel) : this() {
        large = parcel.readString()
        medium = parcel.readString()
        small = parcel.readString()
        template = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(large)
        parcel.writeString(medium)
        parcel.writeString(small)
        parcel.writeString(template)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Logo> {
        override fun createFromParcel(parcel: Parcel): Logo {
            return Logo(parcel)
        }

        override fun newArray(size: Int): Array<Logo?> {
            return arrayOfNulls(size)
        }
    }

}
