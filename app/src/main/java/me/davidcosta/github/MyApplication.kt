package me.davidcosta.github

import android.app.Application
import me.davidcosta.github.di.ApplicationComponent
import me.davidcosta.github.di.DaggerApplicationComponent

class MyApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        this.applicationComponent =
            DaggerApplicationComponent
            .builder()
            .build()
    }
}