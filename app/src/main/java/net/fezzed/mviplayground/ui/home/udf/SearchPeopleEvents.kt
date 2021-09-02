package net.fezzed.mviplayground.ui.home.udf

import net.fezzed.mviplayground.ui.home.model.ItemModel

sealed class SearchPeopleAction {
    data class FilterTextChangedAction(val text: String) : SearchPeopleAction()
    data class SearchRequestAction(val text: String) : SearchPeopleAction()
}

sealed class SearchPeopleResult {
    data class NewTextInput(val filterText: String) : SearchPeopleResult()
    data class ItemsSuccess(val items: List<ItemModel>) : SearchPeopleResult()
    data class ItemsInProgress(val filterText: String) : SearchPeopleResult()
    sealed class ErrorResult : SearchPeopleResult()
    object QueryErrorResult : ErrorResult()
}

sealed class SearchPeopleOneTimeEvent {
    object ErrorWhenTryingToSearch : SearchPeopleOneTimeEvent()
}

