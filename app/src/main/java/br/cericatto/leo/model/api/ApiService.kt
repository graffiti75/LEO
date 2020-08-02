package br.cericatto.leo.model.api

import br.cericatto.leo.model.Search
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search/repositories")
    fun getRepos(@Query("q") query: String = "android",
         @Query("per_page") perPage: Int = 30,
         @Query("page") page: Int = 1
    ): Observable<Search>
}