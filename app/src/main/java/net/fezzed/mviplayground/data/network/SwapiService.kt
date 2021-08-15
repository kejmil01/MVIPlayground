package net.fezzed.mviplayground.data.network

import io.reactivex.rxjava3.core.Single
import net.fezzed.mviplayground.data.network.model.SearchResultModel
import retrofit2.http.GET

interface SwapiService {
	@GET("people/?search=a")
	fun getUsers(): Single<SearchResultModel>
}