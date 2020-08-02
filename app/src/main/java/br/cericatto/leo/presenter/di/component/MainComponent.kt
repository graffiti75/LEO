package br.cericatto.leo.presenter.di.component

import br.cericatto.leo.presenter.di.module.MainModule
import br.cericatto.leo.presenter.di.scope.PerActivity
import br.cericatto.leo.view.activity.MainActivity
import dagger.Component

@PerActivity
@Component(modules = [MainModule::class], dependencies = [ApplicationComponent::class])
interface MainComponent {
    fun inject(target: MainActivity)
}