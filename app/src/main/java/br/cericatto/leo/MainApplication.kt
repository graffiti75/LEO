package br.cericatto.leo

import android.app.Application
import br.cericatto.leo.presenter.log.LineNumberDebugTree
import br.cericatto.leo.presenter.log.ReleaseTree
import timber.log.Timber

open class MainApplication : Application() {
    var page: Int = 1
    var loadedAllData: Boolean = false

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(LineNumberDebugTree())
        else Timber.plant(ReleaseTree())
    }
}