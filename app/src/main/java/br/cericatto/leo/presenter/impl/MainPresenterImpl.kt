package br.cericatto.leo.presenter.impl

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.cericatto.leo.MainApplication
import br.cericatto.leo.R
import br.cericatto.leo.model.Repo
import br.cericatto.leo.model.Search
import br.cericatto.leo.model.api.ApiService
import br.cericatto.leo.presenter.MainPresenter
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

    //--------------------------------------------------
    // Override Methods
    //--------------------------------------------------

    override fun initRecyclerView() {
        activity.apply {
            mAdapter = RepoAdapter(this)
            id_activity_main__recycler_view.adapter = mAdapter
            id_activity_main__recycler_view.layoutManager = LinearLayoutManager(this)
        }
    }

    fun initDataSet() {
        initDataSet(mQuery)
    }

    override fun initDataSet(query: String) {
        val app: MainApplication = activity.application as MainApplication
        val page = app.page
        if (page == 1) {
            mService = activity.apiService
        }

        val observable = mService.getRepos(query = query)
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    loadDataOnSuccess(it, query)
                },
                {
                    it.message?.let { errorMessage ->
                        showErrorMessage(errorMessage)
                    }
                },
                {
                    Timber.i("getRepos() -> At OnCompleted.")
                }
            )
        val composite = CompositeDisposable()
        composite.add(subscription)
    }

    override fun showData(repos: List<Repo>) {
        activity.apply {
            id_activity_main__loading.visibility = View.GONE
            id_activity_main__recycler_view.visibility = View.VISIBLE
        }
        mRepos = repos
        mAdapter.updateAdapter(repos)
        Timber.d(repos.toString())
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }

    override fun onCreateOptionsMenu(menu: Menu) {
        activity.apply {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.dashboard, menu)
            val searchItem = menu.findItem(R.id.action_search)
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            var searchView = setSearchView(searchItem, searchManager)
            searchViewListener(searchView, searchItem)
        }
    }

    override fun setSearchView(searchItem: MenuItem, searchManager: SearchManager): SearchView? {
        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem!!.actionView as SearchView
        }
        if (searchView != null) {
            searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        }
        return searchView
    }

    override fun searchViewListener(searchView: SearchView?, searchItem: MenuItem) {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                activity.apply {
                    id_activity_main__loading.visibility = View.VISIBLE
                    id_activity_main__default_text.visibility = View.GONE

                    if (!searchView?.isIconified) {
                        searchView?.isIconified = true
                    }
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

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private fun loadDataOnSuccess(search: Search?, query: String) {
        val items = search?.items ?: emptyList()
        if (items.isNotEmpty()) {
            showData(items)
            Timber.i("getRepos() -> $items")
        } else {
            activity.apply {
                id_activity_main__loading.visibility = View.GONE
                id_activity_main__default_text.visibility = View.VISIBLE

                val text = getString(R.string.retrofit_empty_repos, query)
                id_activity_main__default_text.text = text
            }
            val app: MainApplication = activity.application as MainApplication
            app.loadedAllData = true
        }
    }
}