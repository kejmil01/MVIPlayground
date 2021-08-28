package net.fezzed.mviplayground.ui.home.business

import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import net.fezzed.mviplayground.udf.ContinuousActionProcessor
import net.fezzed.mviplayground.udf.CurrentStateHolder
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleAction
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleResult
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleState
import javax.inject.Inject

class SearchRequestActionProcessor @Inject constructor(
    private val stateHolder: CurrentStateHolder<SearchPeopleState>,
    private val loadItemsInteractor: LoadItemsInteractor
) : ContinuousActionProcessor<SearchPeopleAction.SearchRequestAction, SearchPeopleResult>() {

    init {
        initializeStream()
    }

    override fun process(action: SearchPeopleAction.SearchRequestAction): Observable<SearchPeopleResult> {
        if (action.text.isEmpty() || action.text.contains("error")) {
            return Observable.just(SearchPeopleResult.EmptyQueryErrorResult)
        }

        return super.process(action)
    }

    private fun initializeStream() {
        feedSubject
            .subscribeOn(Schedulers.io())
            .map { it.text }
            .distinctUntilChanged()
            .flatMap {
                resultSubject.onNext(SearchPeopleResult.ItemsInProgress(it))
                Observable.just(it)
            }
            .toFlowable(BackpressureStrategy.LATEST)
            .flatMap {
                loadItemsInteractor.loadItems(it)
            }
            .subscribe {
                resultSubject.onNext(it)
            }
    }
}