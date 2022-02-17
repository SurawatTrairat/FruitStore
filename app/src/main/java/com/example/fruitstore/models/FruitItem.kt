package com.example.fruitstore.models

import android.os.Parcel
import android.os.Parcelable

data class FruitItem(
        var IMAGE:String = "",
        var NAME: String = "",
        var PRICE: Double = 0.0
):Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!, parcel.readString()!!, parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(IMAGE)
        parcel.writeString(NAME)
        parcel.writeDouble(PRICE)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FruitItem> {
        override fun createFromParcel(parcel: Parcel): FruitItem {
            return FruitItem(parcel)
        }

        override fun newArray(size: Int): Array<FruitItem?> {
            return arrayOfNulls(size)
        }
    }
}