package net.fezzed.mviplayground.data.network

import io.reactivex.rxjava3.core.Single
import net.fezzed.mviplayground.data.network.model.SearchResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SwapiService {
    @GET("people/")
    fun searchPeople(
        @Query("search") query: String? = null
    ): Single<SearchResultModel>
}