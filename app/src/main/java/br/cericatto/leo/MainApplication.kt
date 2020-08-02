package br.cericatto.leo

import android.app.Application
import br.cericatto.leo.presenter.di.component.ApplicationComponent
import br.cericatto.leo.presenter.di.component.DaggerApplicationComponent
import br.cericatto.leo.presenter.di.module.ApplicationModule
import br.cericatto.leo.presenter.log.LineNumberDebugTree
import br.cericatto.leo.presenter.log.ReleaseTree
import timber.log.Timber

open class MainApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
    var page: Int = 1
    var loadedAllData: Boolean = false

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initializeApplicationComponent()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(LineNumberDebugTree())
        else Timber.plant(ReleaseTree())
    }

    private fun initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this, AppConfiguration.BASE_URL))
            .build()
    }
}