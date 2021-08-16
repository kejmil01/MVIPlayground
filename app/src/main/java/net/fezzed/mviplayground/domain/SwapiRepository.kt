package net.fezzed.mviplayground.domain

import io.reactivex.rxjava3.core.Single
import net.fezzed.mviplayground.data.network.model.SearchResultModel

interface SwapiRepository {
	fun searchPeople(query: String): Single<SearchResultModel>
}