package net.fezzed.mviplayground.data.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import net.fezzed.mviplayground.data.network.SwapiService
import net.fezzed.mviplayground.data.network.model.SearchResultModel
import net.fezzed.mviplayground.domain.SwapiRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SwapiRepositoryImpl @Inject constructor(
	private val swapiService: SwapiService
) : SwapiRepository {
	override fun fetchHomeContent(): Single<SearchResultModel> {
		return swapiService.getUsers()
			.subscribeOn(Schedulers.io())
			.delay(3, TimeUnit.SECONDS)
	}
}