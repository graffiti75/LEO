package br.cericatto.leo.presenter

import android.app.SearchManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import br.cericatto.leo.model.Repo

interface MainPresenter {
    fun initRecyclerView()
    fun initDataSet(query: String)
    fun showData(repos: List<Repo>)
    fun showErrorMessage(error: String)

    fun onCreateOptionsMenu(menu: Menu)
    fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView?
    fun searchViewListener(searchView: SearchView?, searchItem: MenuItem)
}