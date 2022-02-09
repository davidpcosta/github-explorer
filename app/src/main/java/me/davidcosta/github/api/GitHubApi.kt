package me.davidcosta.github.api

import io.reactivex.rxjava3.core.Observable
import me.davidcosta.github.api.response.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun repositories(
        @Query("q") query: String = "language\\:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Observable<RepositoriesResponse>
}