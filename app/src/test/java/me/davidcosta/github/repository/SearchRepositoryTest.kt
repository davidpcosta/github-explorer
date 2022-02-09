package me.davidcosta.github.repository

import io.mockk.mockk
import io.mockk.spyk
import me.davidcosta.github.api.response.ItemResponse
import me.davidcosta.github.api.response.OwnerResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchRepositoryTest {

    private val searchRepository: SearchRepository = spyk(SearchRepository(mockk(relaxed = true)))

    @Test
    fun `transform item response to repository vo`() {
        val repositoryVO = searchRepository
            .transformToRepositoryVO(dummyItemResponseList)
            .first()

        assertEquals(1, repositoryVO.id)
        assertEquals("name", repositoryVO.name)
        assertEquals("fullName", repositoryVO.fullName)
        assertEquals("1.1K", repositoryVO.stargazersCount)
        assertEquals("1.2K", repositoryVO.forksCount)
        assertEquals("language", repositoryVO.language)
        assertEquals(2, repositoryVO.ownerVO.id)
        assertEquals("login", repositoryVO.ownerVO.login)
        assertEquals("avatar.jpg", repositoryVO.ownerVO.avatarUrl)
    }

    @Test
    fun `calculate pages when has next page`() {

        val pair = searchRepository.calculatePages(
            page = 1,
            perPage = 2,
            totalCount = 5
        )

        assertEquals(true, pair.first)
        assertEquals(2, pair.second)
    }

    @Test
    fun `calculate pages when does not have next page`() {

        val pair = searchRepository.calculatePages(
            page = 3,
            perPage = 2,
            totalCount = 5
        )

        assertEquals(false, pair.first)
        assertEquals(null, pair.second)
    }
    private val dummyItemResponseList =
        listOf(
            ItemResponse(
                id = 1,
                name = "name",
                fullName = "fullName",
                stargazersCount = 1100,
                forksCount = 1200,
                language = "language",
                owner = OwnerResponse(
                    id = 2,
                    login = "login",
                    avatarUrl = "avatar.jpg"
                )
            )
        )
}