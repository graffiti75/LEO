package br.cericatto.leo.presenter.di.component

import br.cericatto.leo.presenter.di.module.ApplicationModule
import dagger.Component
import retrofit2.Retrofit

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun exposeRetrofit(): Retrofit
}