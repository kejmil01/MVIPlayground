package net.fezzed.mviplayground.ui.home.udf

import android.os.Parcel
import android.os.Parcelable
import net.fezzed.mviplayground.ui.home.model.ItemModel

data class SearchPeopleViewState(
    val filterText: String,
    val itemList: List<ItemModel>,
    val inProgress: Boolean
) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(ItemModel)!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(filterText)
        parcel.writeTypedList(itemList)
        parcel.writeByte(if (inProgress) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchPeopleViewState> {

        val EMPTY = SearchPeopleViewState("", emptyList(), false)

        override fun createFromParcel(parcel: Parcel): SearchPeopleViewState {
            return SearchPeopleViewState(parcel)
        }

        override fun newArray(size: Int): Array<SearchPeopleViewState?> {
            return arrayOfNulls(size)
        }
    }
}