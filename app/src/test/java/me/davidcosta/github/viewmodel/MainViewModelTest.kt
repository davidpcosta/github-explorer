package me.davidcosta.github.viewmodel

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import me.davidcosta.github.repository.SearchRepository
import org.junit.Test

class MainViewModelTest {

    private val searchRepository = mockk<SearchRepository>(relaxed = true)
    private val mainViewModel = spyk(MainViewModel(searchRepository))

    @Test
    fun `load repositories when is not loading`() {
        mainViewModel.isLoading = false

        mainViewModel.loadRepositories()

        verify {
            searchRepository.searchRepositories(any())
        }
    }

    @Test
    fun `load repositories when is loading`() {
        mainViewModel.isLoading = true

        mainViewModel.loadRepositories()

        verify(exactly = 0) {
            searchRepository.searchRepositories(any())
        }
    }

    @Test
    fun `load more repositories when is not loading and has next page`() {
        mainViewModel.isLoading = false
        every { mainViewModel.hasNextPage } returns true

        mainViewModel.loadMoreRepositories()

        verify {
            searchRepository.searchRepositories(any())
        }
    }

    @Test
    fun `load more repositories when is loading and has next page`() {
        mainViewModel.isLoading = true
        every { mainViewModel.hasNextPage } returns true

        mainViewModel.loadMoreRepositories()

        verify (exactly = 0) {
            searchRepository.searchRepositories(any())
        }
    }

    @Test
    fun `load more repositories when is not loading and does not have next page`() {
        mainViewModel.isLoading = false
        every { mainViewModel.hasNextPage } returns false

        mainViewModel.loadMoreRepositories()

        verify (exactly = 0) {
            searchRepository.searchRepositories(any())
        }
    }

    @Test
    fun `load more repositories when is loading and does not have next page`() {
        mainViewModel.isLoading = true
        every { mainViewModel.hasNextPage } returns false

        mainViewModel.loadMoreRepositories()

        verify (exactly = 0) {
            searchRepository.searchRepositories(any())
        }
    }
}