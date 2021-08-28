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
                                inProgress = false,
                                noResultsMessageVisible = result.items.isEmpty(),
                                error = false
                            ),
                            loadItemsProcessState = ProcessState.IDLE
                        )
                    }
                    is SearchPeopleResult.ItemsInProgress -> {
                        Log.d("UDF_FLOW", " ----- REDUCER SearchPeopleResult.ItemsInProgress -----")


                        previousState.copy(
                            viewState = previousState.viewState.copy(
                                filterText = result.filterText,
                                inProgress = true,
                                noResultsMessageVisible = false,
                                error = false
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
                    SearchPeopleResult.EmptyQueryErrorResult -> {
                        Log.d(
                            "UDF_FLOW",
                            " ----- REDUCER SearchPeopleResult.EmptyQueryErrorResult -----"
                        )

                        previousState.copy(
                            viewState = previousState.viewState.copy(
                                itemList = emptyList(),
                                inProgress = false,
                                noResultsMessageVisible = false,
                                error = true
                            ),
                            loadItemsProcessState = ProcessState.IDLE
                        )
                    }
                }
            }
        }

    }

}