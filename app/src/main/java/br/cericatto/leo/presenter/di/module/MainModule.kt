package br.cericatto.leo.presenter.di.module

import br.cericatto.leo.model.api.ApiService
import br.cericatto.leo.presenter.di.scope.PerActivity
import br.cericatto.leo.view.activity.MainActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule(private val mActivity: MainActivity) {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @PerActivity
    @Provides
    fun provideActivity(): MainActivity {
        return mActivity
    }
}