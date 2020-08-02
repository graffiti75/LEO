package br.cericatto.leo.presenter.impl

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.cericatto.leo.MainApplication
import br.cericatto.leo.model.Repo
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

    override fun initDataSet() {
        val app: MainApplication = activity.application as MainApplication
        val page = app.page
        if (page == 1) {
            mService = activity.apiService
        }

        val observable = mService.getRepos()
        val subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val items = it?.items ?: emptyList()
                    if (items.isNotEmpty()) {
                        showData(items)
                        Timber.i("getRepos() -> $items")
                    } else {
                        app.loadedAllData = true
                    }
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
        mAdapter.updateAdapter(repos)
        Timber.d(repos.toString())
    }

    override fun showErrorMessage(error: String) {
        Timber.e(error)
    }
}