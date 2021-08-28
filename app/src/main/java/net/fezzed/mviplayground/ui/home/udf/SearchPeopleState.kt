package net.fezzed.mviplayground.ui.home.udf

import android.os.Parcel
import android.os.Parcelable
import net.fezzed.mviplayground.udf.ProcessState

data class SearchPeopleState(
    val viewState: SearchPeopleViewState,
    val loadItemsProcessState: ProcessState = ProcessState.IDLE
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(viewState, flags)
        parcel.writeInt(loadItemsProcessState.toInt())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchPeopleState> {

        val EMPTY = SearchPeopleState(SearchPeopleViewState.EMPTY)

        override fun createFromParcel(parcel: Parcel): SearchPeopleState {

            val searchPeopleView: SearchPeopleViewState =
                parcel.readParcelable(SearchPeopleViewState::class.java.classLoader)!!
            val loadItemProcessState = ProcessState.fromInt(parcel.readInt())

            return SearchPeopleState(searchPeopleView, loadItemProcessState)
        }

        override fun newArray(size: Int): Array<SearchPeopleState?> {
            return arrayOfNulls(size)
        }
    }
}