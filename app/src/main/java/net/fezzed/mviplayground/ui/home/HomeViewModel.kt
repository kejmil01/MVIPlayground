package net.fezzed.mviplayground.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import net.fezzed.mviplayground.domain.FetchHomeContentUseCase
import net.fezzed.mviplayground.ui.home.model.ItemModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchHomeContentUseCase: FetchHomeContentUseCase
) : ViewModel() {

    private var searchDiposable: Disposable? = null
    private var query = ""

    val errorText = MutableLiveData<String>(null)
    val errorVisible = errorText.map { !it.isNullOrEmpty() }
    val loadingInProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val items: MutableLiveData<List<ItemModel>> = MutableLiveData(emptyList())

    fun onSearchChanged(text: CharSequence) {
        query = text.toString()
    }

    fun onSearchDoneAction(): Boolean {
        onSearchClick()
        return true
    }

    fun onSearchClick() {
        searchDiposable?.dispose()
        loadingInProgress.value = true
        fetchHomeContentUseCase
            .searchPeople(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { content, error ->
                content?.let {
                    errorText.value = ""

                    val itemModeList = content.results.map { item ->
                        ItemModel(item.name, item.birth_year, item.height)
                    }
                    items.value = itemModeList
                } ?: run {
                    errorText.value = error.message
                }
                loadingInProgress.value = false
            }.also { searchDiposable = it }
    }

    override fun onCleared() {
        searchDiposable?.dispose()
        super.onCleared()
    }
}