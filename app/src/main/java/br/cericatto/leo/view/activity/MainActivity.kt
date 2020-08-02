package br.cericatto.leo.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import br.cericatto.leo.R
import br.cericatto.leo.model.api.ApiService
import br.cericatto.leo.presenter.di.component.DaggerMainComponent
import br.cericatto.leo.presenter.di.module.MainModule
import br.cericatto.leo.presenter.extensions.checkIfHasNetwork
import br.cericatto.leo.presenter.extensions.showToast
import br.cericatto.leo.presenter.impl.MainPresenterImpl
import javax.inject.Inject

class MainActivity : BaseActivity() {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @Inject
    lateinit var presenter: MainPresenterImpl

    @Inject
    lateinit var apiService: ApiService

    //--------------------------------------------------
    // Base Activity
    //--------------------------------------------------

    override val contentView: Int
        get() = R.layout.activity_main

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        setContentView(contentView)
        super.onViewReady(savedInstanceState, intent)
    }

    @Suppress("DEPRECATION")
    override fun resolveDaggerDependency() {
        DaggerMainComponent.builder()
            .applicationComponent(applicationComponent)
            .mainModule(MainModule(this))
            .build().inject(this)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    //--------------------------------------------------
    // Activity Lifecycle
    //--------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomToolbar(false, getString(R.string.app_name))
    }

    //--------------------------------------------------
    // Menu
    //--------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        presenter.onCreateOptionsMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    fun getData(query: String) {
        if (checkIfHasNetwork()) {
            presenter.initRecyclerView()
            presenter.initDataSet(query)
        } else showToast(R.string.no_internet)
    }
}