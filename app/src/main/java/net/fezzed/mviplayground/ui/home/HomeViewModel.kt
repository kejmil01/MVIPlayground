package net.fezzed.mviplayground.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import net.fezzed.mviplayground.domain.FetchHomeContentUseCase
import net.fezzed.mviplayground.ui.home.model.ItemModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    fetchHomeContentUseCase: FetchHomeContentUseCase
) : ViewModel() {

    val errorText = MutableLiveData<String>(null)
    val errorVisible = errorText.map { !it.isNullOrEmpty() }
    val loadingInProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val items: MutableLiveData<List<ItemModel>> = MutableLiveData(emptyList())

    init {
        loadingInProgress.value = true
        fetchHomeContentUseCase
            .fetchContent()
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
            }
    }
}