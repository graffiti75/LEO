package br.cericatto.leo.model.api

import br.cericatto.leo.model.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("/user/repos")
    fun getRepos(
        @Header("Authorization") authorization: String,
        @Query("page") page: Int
    ): Observable<MutableList<Repo>>
}