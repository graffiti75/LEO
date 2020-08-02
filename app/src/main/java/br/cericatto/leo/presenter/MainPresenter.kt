package br.cericatto.leo.presenter

import br.cericatto.leo.model.Repo
import br.cericatto.leo.model.api.ApiService

interface MainPresenter {
    fun initRecyclerView()
    fun initDataSet()
    fun showData(repos: List<Repo>)
    fun showErrorMessage(error: String)
}