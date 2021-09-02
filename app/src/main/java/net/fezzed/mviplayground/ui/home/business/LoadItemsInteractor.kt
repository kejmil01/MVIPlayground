package net.fezzed.mviplayground.ui.home.business

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.fezzed.mviplayground.domain.SwapiRepository
import net.fezzed.mviplayground.udf.CurrentStateHolder
import net.fezzed.mviplayground.ui.home.model.ItemModel
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleResult
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleState
import org.reactivestreams.Subscription

class LoadItemsInteractor(
    private val stateHolder: CurrentStateHolder<SearchPeopleState>,
    private val api: SwapiRepository
) {

    private var lastApiCallSubscription: Subscription? = null

    fun loadItems(query: String): Flowable<SearchPeopleResult> {
        //Cancel previous subscription to stop potentiall ongoing call.
        lastApiCallSubscription?.cancel()
        if (query.isEmpty()) {
            //Do not fetch items on empty query
            return Flowable
                .just(SearchPeopleResult.ItemsSuccess(emptyList()))
        }

        return api
            .searchPeople(query)
            .subscribeOn(Schedulers.io())
            .map { response -> response.results }
            .toFlowable()
            .map<SearchPeopleResult> { itemList ->
                val itemModeList = itemList.map { item ->
                    ItemModel(item.name, item.birth_year, item.height)
                }
                SearchPeopleResult.ItemsSuccess(itemModeList)
            }
            .doOnSubscribe {
                lastApiCallSubscription = it
            }
    }
}