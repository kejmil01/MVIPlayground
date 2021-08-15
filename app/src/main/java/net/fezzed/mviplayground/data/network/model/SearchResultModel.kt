package net.fezzed.mviplayground.data.network.model

import com.google.gson.annotations.SerializedName

data class SearchResultModel(
	@SerializedName("count") val count: Int,
	@SerializedName("next") val next: String,
	@SerializedName("previous") val previous: String,
	@SerializedName("results") val results: List<ResultModel>
)