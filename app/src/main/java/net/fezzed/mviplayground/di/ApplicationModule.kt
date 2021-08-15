package net.fezzed.mviplayground.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.fezzed.mviplayground.data.network.SwapiServiceBuilder
import net.fezzed.mviplayground.data.repository.SwapiRepositoryImpl
import net.fezzed.mviplayground.domain.SwapiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

	@Provides
	@Singleton
	fun provideApiService() = SwapiServiceBuilder.build()

	@Provides
	fun provideSwapiRepository(repository: SwapiRepositoryImpl): SwapiRepository = repository
}