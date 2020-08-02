package br.cericatto.leo.presenter

import android.app.SearchManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import br.cericatto.leo.model.Repo

interface MainPresenter {
    /**
     * Data
     */

    fun getData(query: String)
    fun initRecyclerView()
    fun getRepos(query: String)
    fun showData(repos: List<Repo>)
    fun showErrorMessage(error: String)

    /**
     * Menu
     */

    fun onCreateOptionsMenu(menu: Menu)
    fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView?
    fun searchViewListener(searchView: SearchView?, searchItem: MenuItem)

    /**
     * Views
     */

    fun showLoading()
    fun hideLoading()
    fun showPaginationLoading()
    fun hidePaginationLoading()
    fun showRecyclerView()
    fun hideRecyclerView()
    fun showEmptyRecyclerView()
    fun hideEmptyRecyclerView()

    /**
     * RxJava
     */

    fun dispose()
}