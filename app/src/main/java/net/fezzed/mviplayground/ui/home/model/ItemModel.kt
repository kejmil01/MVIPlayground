package net.fezzed.mviplayground.ui.home.model

import android.os.Parcel
import android.os.Parcelable

data class ItemModel(val fullName: String, val birthYear: String, val height: String) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(fullName)
		parcel.writeString(birthYear)
		parcel.writeString(height)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ItemModel> {
		override fun createFromParcel(parcel: Parcel): ItemModel {
			return ItemModel(parcel)
		}

		override fun newArray(size: Int): Array<ItemModel?> {
			return arrayOfNulls(size)
		}
	}
}