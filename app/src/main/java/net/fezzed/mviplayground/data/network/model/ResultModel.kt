package net.fezzed.mviplayground.data.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResultModel (

	@SerializedName("name") val name : String,
	@SerializedName("height") val height : String,
	@SerializedName("mass") val mass : String,
	@SerializedName("hair_color") val hair_color : String,
	@SerializedName("skin_color") val skin_color : String,
	@SerializedName("eye_color") val eye_color : String,
	@SerializedName("birth_year") val birth_year : String,
	@SerializedName("gender") val gender : String,
	@SerializedName("homeworld") val homeworld : String,
	@SerializedName("films") val films : List<String>,
	@SerializedName("species") val species : List<String>,
	@SerializedName("vehicles") val vehicles : List<String>,
	@SerializedName("starships") val starships : List<String>,
	@SerializedName("created") val created : String,
	@SerializedName("edited") val edited : String,
	@SerializedName("url") val url : String
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.createStringArrayList()!!,
		parcel.createStringArrayList()!!,
		parcel.createStringArrayList()!!,
		parcel.createStringArrayList()!!,
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readString()!!
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(name)
		parcel.writeString(height)
		parcel.writeString(mass)
		parcel.writeString(hair_color)
		parcel.writeString(skin_color)
		parcel.writeString(eye_color)
		parcel.writeString(birth_year)
		parcel.writeString(gender)
		parcel.writeString(homeworld)
		parcel.writeStringList(films)
		parcel.writeStringList(species)
		parcel.writeStringList(vehicles)
		parcel.writeStringList(starships)
		parcel.writeString(created)
		parcel.writeString(edited)
		parcel.writeString(url)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResultModel> {
		override fun createFromParcel(parcel: Parcel): ResultModel {
			return ResultModel(parcel)
		}

		override fun newArray(size: Int): Array<ResultModel?> {
			return arrayOfNulls(size)
		}
	}
}