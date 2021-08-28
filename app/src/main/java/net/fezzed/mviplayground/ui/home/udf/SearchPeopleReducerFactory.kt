package net.fezzed.mviplayground.ui.home.udf

import android.util.Log
import io.reactivex.rxjava3.functions.BiFunction
import net.fezzed.mviplayground.udf.ProcessState

class SearchPeopleReducerFactory {

	companion object {

		fun generateReducer(store: SearchPeopleStore): BiFunction<SearchPeopleState, SearchPeopleResult, SearchPeopleState> {
			return BiFunction { previousState: SearchPeopleState, result: SearchPeopleResult ->
				when (result) {
					is SearchPeopleResult.ItemsSuccess -> {
						Log.d(
							"UDF_FLOW",
							" ----- REDUCER SearchPeopleResult.ItemsSuccess -----"
						)

						previousState.copy(
							viewState = previousState.viewState.copy(
								itemList = result.items,
								inProgress = false
							),
							loadItemsProcessState = ProcessState.IDLE
						)
					}
					is SearchPeopleResult.ItemsInProgress -> {
						Log.d("UDF_FLOW", " ----- REDUCER SearchPeopleResult.ItemsInProgress -----")


						previousState.copy(
							viewState = previousState.viewState.copy(
								filterText = result.filterText,
								inProgress = true
							),
							loadItemsProcessState = ProcessState.IN_PROGRESS
						)
					}
					is SearchPeopleResult.NewTextInput -> {
						Log.d(
							"UDF_FLOW",
							" ----- REDUCER SearchPeopleResult.NewTextInput -----"
						)

						previousState.copy(
							viewState = previousState.viewState.copy(
								filterText = result.filterText
							)
						)
					}
					//TODO implement error handling
					SearchPeopleResult.EmptyQueryErrorResult -> previousState
				}
			}
		}

	}

}