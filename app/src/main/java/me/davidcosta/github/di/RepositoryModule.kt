package me.davidcosta.github.di

import dagger.Module
import dagger.Provides
import me.davidcosta.github.api.GitHubApi
import me.davidcosta.github.repository.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RepositoryModule @Inject constructor(
    private val gitHubApi: GitHubApi
) {

    @Provides
    @Singleton
    fun provideSearchRepository():SearchRepository = SearchRepository(gitHubApi)
}