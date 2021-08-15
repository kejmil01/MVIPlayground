package net.fezzed.mviplayground.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import net.fezzed.mviplayground.data.network.model.ResultModel
import net.fezzed.mviplayground.domain.FetchHomeContentUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	fetchHomeContentUseCase: FetchHomeContentUseCase
) : ViewModel() {

	val text = MutableLiveData("No data")
	val result: MutableList<ResultModel> = mutableListOf()
	val loadingInProgress: MutableLiveData<Boolean> = MutableLiveData(false)

	init {
		loadingInProgress.value = true
		fetchHomeContentUseCase
			.fetchContent()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { content, error ->
				content?.let {
					result.clear()
					result.addAll(content.results)
					text.value = content.results.toString()
				} ?: run {
					text.value = error.message
				}
				loadingInProgress.value = false
			}
	}
}