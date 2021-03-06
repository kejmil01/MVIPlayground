package net.fezzed.mviplayground.domain

import io.reactivex.rxjava3.core.Single
import net.fezzed.mviplayground.data.network.model.SearchResultModel
import javax.inject.Inject

class FetchHomeContentUseCase @Inject constructor(private val repository: SwapiRepository) {

	fun searchPeople(query: String): Single<SearchResultModel> {
		return repository.searchPeople(query)
	}
}