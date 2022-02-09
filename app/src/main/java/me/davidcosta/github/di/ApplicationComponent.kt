package me.davidcosta.github.di

import dagger.Component
import me.davidcosta.github.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}