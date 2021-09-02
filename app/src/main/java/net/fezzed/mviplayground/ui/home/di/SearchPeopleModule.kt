package net.fezzed.mviplayground.ui.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import net.fezzed.mviplayground.domain.SwapiRepository
import net.fezzed.mviplayground.ui.home.business.LoadItemsInteractor
import net.fezzed.mviplayground.ui.home.business.OnTextChangedActionProcessor
import net.fezzed.mviplayground.ui.home.business.SearchRequestActionProcessor
import net.fezzed.mviplayground.ui.home.udf.SearchPeopleStore

@Module
@InstallIn(ViewModelComponent::class)
class SearchPeopleModule {

    @Provides
    fun provideLoadItemsInteractor(
        store: SearchPeopleStore,
        api: SwapiRepository
    ): LoadItemsInteractor {
        return LoadItemsInteractor(store, api)
    }

    @Provides
    fun provideOnTextChangedActionProcessor(
    ): OnTextChangedActionProcessor {
        return OnTextChangedActionProcessor()
    }

    @Provides
    fun provideSearchRequestActionProcessor(
        store: SearchPeopleStore,
        interactor: LoadItemsInteractor
    ): SearchRequestActionProcessor {
        return SearchRequestActionProcessor(store, interactor)
    }
}