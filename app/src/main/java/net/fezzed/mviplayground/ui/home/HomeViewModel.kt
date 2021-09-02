package net.fezzed.mviplayground.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import net.fezzed.mviplayground.udf.ActionBinding
import net.fezzed.mviplayground.ui.home.business.OnTextChangedActionProcessor
import net.fezzed.mviplayground.ui.home.business.SearchRequestActionProcessor
import net.fezzed.mviplayground.ui.home.model.ItemModel
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleAction
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleReducerFactory
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleState
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleStore
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val searchPeopleStore: SearchPeopleStore,
    private val filterTextChangedActionProcessor: OnTextChangedActionProcessor,
    private val searchRequestActionProcessor: SearchRequestActionProcessor
) : ViewModel() {

    private var searchCompositeDisposable = CompositeDisposable()

    val query = MutableLiveData("")
    val loadingInProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val items: MutableLiveData<List<ItemModel>> = MutableLiveData(emptyList())

    val searchButtonEnabled = MutableLiveData(false)
    val noResultsMessageVisible = MutableLiveData(false)
    val errorVisible = MutableLiveData(false)

    init {
        initialize()
    }

    fun onSearchQueryChanged(text: CharSequence) {
        searchPeopleStore.onNextAction(
            SearchPeopleAction.FilterTextChangedAction(
                text.toString()
            )
        )
    }

    fun onSearchDoneAction(): Boolean {
        onSearchClick()
        return true
    }

    fun onSearchClick() {
        searchPeopleStore.onNextAction(
            SearchPeopleAction.SearchRequestAction(
                searchPeopleStore.currentState?.viewState?.filterText ?: ""
            )
        )
    }

    override fun onCleared() {
        searchCompositeDisposable.dispose()
        super.onCleared()
    }

    private fun initialize() {
        searchPeopleStore.initializeStream(
            listOf(
                ActionBinding(
                    SearchPeopleAction.FilterTextChangedAction::class,
                    filterTextChangedActionProcessor
                ), ActionBinding(
                    SearchPeopleAction.SearchRequestAction::class,
                    searchRequestActionProcessor
                )
            ),
            SearchPeopleReducerFactory.generateReducer(
                searchPeopleStore
            ),
            SearchPeopleState.EMPTY
        ).addTo(searchCompositeDisposable)

        searchPeopleStore
            .state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { state ->
                render(state)
            }
            .addTo(searchCompositeDisposable)
    }

    private fun render(state: SearchPeopleState) {
        query.value = state.viewState.filterText
        searchButtonEnabled.value = state.viewState.filterText.isNotEmpty()
        loadingInProgress.value = state.viewState.inProgress
        items.value = state.viewState.itemList
        noResultsMessageVisible.value = state.viewState.noResultsMessageVisible
        errorVisible.value = state.viewState.error
    }
}