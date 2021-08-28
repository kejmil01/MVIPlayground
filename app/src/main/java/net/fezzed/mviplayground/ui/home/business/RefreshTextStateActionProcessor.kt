package net.fezzed.mviplayground.ui.home.business

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import net.fezzed.mviplayground.udf.ActionProcessor
import net.fezzed.mviplayground.udf.CurrentStateHolder
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleAction
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleResult
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleState
import javax.inject.Inject

class RefreshTextStateActionProcessor @Inject constructor(
    private val stateHolder: CurrentStateHolder<SearchPeopleState>
) : ActionProcessor<SearchPeopleAction.RefreshTextStateAction, SearchPeopleResult> {

    override fun process(action: SearchPeopleAction.RefreshTextStateAction): Observable<SearchPeopleResult> {
        stateHolder.currentState?.let {
            return Observable.just(SearchPeopleResult.NewTextInput(it.viewState.filterText))
        } ?: run {
            Log.e("RefreshActionProcessor", "Null currentState")
            return Observable.empty()
        }
    }

}