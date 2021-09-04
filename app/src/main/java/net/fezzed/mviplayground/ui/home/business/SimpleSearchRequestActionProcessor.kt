package net.fezzed.mviplayground.ui.home.business

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import net.fezzed.mviplayground.udf.ActionProcessor
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleAction
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleResult
import javax.inject.Inject

class SimpleSearchRequestActionProcessor @Inject constructor(
    private val loadItemsInteractor: LoadItemsInteractor
) : ActionProcessor<SearchPeopleAction.SearchRequestAction, SearchPeopleResult> {

    override fun process(action: SearchPeopleAction.SearchRequestAction): Observable<SearchPeopleResult> {
        if (action.text.isEmpty() || action.text.contains("error")) {
            return Observable.just(SearchPeopleResult.QueryErrorResult)
        }
        return loadItemsInteractor
            .loadItems(action.text)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .startWith(Single.just(SearchPeopleResult.ItemsInProgress(action.text)))
    }

}