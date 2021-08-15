package net.fezzed.mviplayground.domain

import io.reactivex.rxjava3.core.Single
import net.fezzed.mviplayground.data.network.model.SearchResultModel
import javax.inject.Inject

class FetchHomeContentUseCase @Inject constructor(private val repository: SwapiRepository) {

	fun fetchContent(): Single<SearchResultModel> {
		return repository.fetchHomeContent()
	}
}