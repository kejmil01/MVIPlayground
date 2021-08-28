package net.fezzed.mviplayground.ui.home.business

import io.reactivex.rxjava3.core.Observable
import net.fezzed.mviplayground.udf.ActionProcessor
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleAction
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleResult
import javax.inject.Inject

class OnTextChangedActionProcessor @Inject constructor(
) : ActionProcessor<SearchPeopleAction.FilterTextChangedAction, SearchPeopleResult> {

    override fun process(action: SearchPeopleAction.FilterTextChangedAction): Observable<SearchPeopleResult> {
        return Observable.just(SearchPeopleResult.NewTextInput(action.text))
    }
}