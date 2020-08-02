package br.cericatto.leo.model.api

import br.cericatto.leo.AppConfiguration
import br.cericatto.leo.model.Search
import br.cericatto.leo.model.User
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/repositories")
    fun getRepos(@Query("q") query: String = "android",
         @Query("per_page") perPage: Int = AppConfiguration.ITEMS_PER_PAGE,
         @Query("page") page: Int = AppConfiguration.PAGE
    ): Observable<Search>

    @GET("users/{user}")
    fun getUser(@Path("user") user: String): Call<User>
}