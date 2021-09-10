package net.fezzed.mviplayground.ui.home.udf

import net.fezzed.mviplayground.udf.Store
import javax.inject.Inject

class SearchPeopleStore @Inject constructor() :
    Store<SearchPeopleState,
            SearchPeopleOneTimeEvent,
            SearchPeopleAction,
            SearchPeopleResult>()