package me.davidcosta.github.repository

import io.reactivex.rxjava3.core.Observable
import me.davidcosta.github.api.GitHubApi
import me.davidcosta.github.api.response.ItemResponse
import me.davidcosta.github.extensions.toShortNotation
import me.davidcosta.github.model.OwnerVO
import me.davidcosta.github.model.RepositoryVO
import javax.inject.Inject
import kotlin.math.roundToInt

class SearchRepository @Inject constructor(private val gitHubApi: GitHubApi) {

    private val perPage = 30
    private val gitHubMaxResults = 1_000

    fun searchRepositories(page: Int): Observable<Triple<Boolean, Int?, List<RepositoryVO>>> =
        gitHubApi
            .repositories(
                page = page,
                perPage = perPage
            )
            .map { response ->
                val pagination = calculatePages(page, perPage, response.totalCount)
                Triple(
                    pagination.first,
                    pagination.second,
                    transformToRepositoryVO(response.items)
                )
            }

    internal fun transformToRepositoryVO(itemResponseList: List<ItemResponse>): List<RepositoryVO> =
        itemResponseList
            .map { itemResponse ->
                RepositoryVO(
                    id = itemResponse.id,
                    name = itemResponse.name,
                    fullName = itemResponse.fullName,
                    stargazersCount = itemResponse.stargazersCount.toShortNotation(),
                    forksCount = itemResponse.forksCount.toShortNotation(),
                    language = itemResponse.language.orEmpty(),
                    ownerVO = OwnerVO(
                        id =  itemResponse.owner.id,
                        login = itemResponse.owner.login,
                        avatarUrl = itemResponse.owner.avatarUrl
                    )
                )
            }

    internal fun calculatePages(page: Int, perPage: Int, totalCount: Int): Pair<Boolean, Int?> {
        val totalCount = totalCount.takeIf { it < gitHubMaxResults } ?: gitHubMaxResults
        val totalPages = (totalCount / perPage.toDouble()).roundToInt()
        val hasNextPage = page < totalPages
        val nextPage = if (hasNextPage) page.inc() else null
        return Pair(hasNextPage, nextPage)
    }

}