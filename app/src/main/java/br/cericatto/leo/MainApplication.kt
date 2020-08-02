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

    companion object {
        var page: Int = 0
        var itemsLoaded: Int = 0
        var itemsTotal: Int = 0
    }

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