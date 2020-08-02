package br.cericatto.leo.presenter.impl

import android.app.SearchManager
import android.content.Context
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.cericatto.leo.MainApplication
import br.cericatto.leo.R
import br.cericatto.leo.model.Repo
import br.cericatto.leo.model.Search
import br.cericatto.leo.model.api.ApiService
import br.cericatto.leo.presenter.MainPresenter
import br.cericatto.leo.presenter.extensions.scrollPagination
import br.cericatto.leo.presenter.extensions.setGone
import br.cericatto.leo.presenter.extensions.setVisible
import br.cericatto.leo.view.activity.MainActivity
import br.cericatto.leo.view.adapter.RepoAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val activity: MainActivity
): MainPresenter {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private lateinit var mAdapter: RepoAdapter
    private lateinit var mService : ApiService

    private lateinit var mRepos: List<Repo>
    private lateinit var mQuery: String

    private val mComposite = CompositeDisposable()

    //--------------------------------------------------
    // Override Methods
    //--------------------------------------------------

    /**
     * Data
     */

    override fun initRecyclerView() {
        activity.apply {
            val recyclerView = activity_main__recycler_view
            mAdapter = RepoAdapter(this)
            recyclerView.adapter = mAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.scrollPagination(MainApplication.page)
        }
    }

    override fun getRepos(query: String) {
        mService = activity.apiService
        val observable = mService.getRepos(query = query, page = MainApplication.page)
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    getReposOnSuccess(it, query)
                },
                {
                    getReposOnError(it)
                },
                {
                    Timber.i("getRepos() -> At OnCompleted.")
                }
            )
        mComposite.add(subscription)
    }

    override fun showData(repos: List<Repo>) {
        activity.apply {
            hideLoading()
            showRecyclerView()
        }
        mRepos = repos
        mAdapter.updateAdapter(repos)
        Timber.d(repos.toString())
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }

    /**
     * Menu
     */

    override fun onCreateOptionsMenu(menu: Menu) {
        activity.apply {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.dashboard, menu)
            val searchItem = menu.findItem(R.id.action_search)
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = setSearchView(searchItem, searchManager)
            searchViewListener(searchView, searchItem)
        }
    }

    override fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView? {
        val searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        return searchView
    }

    override fun searchViewListener(searchView: SearchView?, searchItem: MenuItem) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                activity.apply {
                    showLoading()
                    hideEmptyRecyclerView()

                    if (!searchView.isIconified) searchView.isIconified = true
                    searchItem.collapseActionView()

                    mQuery = query
                    getData(query)
                }
                return false
            }
            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }

    /**
     * Views
      */

    override fun showLoading() {
        activity.activity_main__loading.setVisible()
    }
    override fun hideLoading() {
        activity.activity_main__loading.setGone()
    }

    override fun showPaginationLoading() {
        activity.activity_main__pagination_loading.setVisible()
    }
    override fun hidePaginationLoading() {
        activity.activity_main__pagination_loading.setGone()
    }

    override fun showRecyclerView() {
        activity.activity_main__recycler_view.setVisible()
    }
    override fun hideRecyclerView() {
        activity.activity_main__recycler_view.setGone()
    }

    override fun showEmptyRecyclerView() {
        activity.activity_main__default_text.setVisible()
    }
    override fun hideEmptyRecyclerView() {
        activity.activity_main__default_text.setGone()
    }

    /**
     * RxJava
     */

    override fun dispose() {
        mComposite.dispose()
    }

    //--------------------------------------------------
    // Internal Methods
    //--------------------------------------------------

    fun getRepos() {
        getRepos(mQuery)
    }

    private fun getReposOnSuccess(search: Search?, query: String) {
        val items = search?.items ?: emptyList()
        if (items.isNotEmpty()) {
            val itemsLoaded = MainApplication.itemsLoaded
            MainApplication.itemsLoaded = itemsLoaded + search!!.items.size
            MainApplication.itemsTotal = search.total_count
            Log.i("leo", "========== itemsTotal: ${search.total_count}")

            hidePaginationLoading()

            showData(items)
            Timber.i("getRepos() -> $items")
        } else {
            activity.apply {
                hideLoading()
                showEmptyRecyclerView()

                val text = getString(R.string.retrofit_empty_repos, query)
                activity_main__default_text.text = text
            }
        }
    }

    private fun getReposOnError(throwable: Throwable) {
        throwable.message?.let { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }
}