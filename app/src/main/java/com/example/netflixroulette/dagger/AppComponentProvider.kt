package com.example.netflixroulette.dagger

import android.app.Application
import android.content.Context
import com.example.netflixroulette.dagger.modules.DBModule

object AppComponentProvider {
    lateinit var appComponent: AppComponent

    fun provideAppComponent(context: Context) : AppComponent {
        if (this::appComponent.isInitialized.not()) {
            appComponent = DaggerAppComponent
                .builder()
                .initDBModule(DBModule(context))
                .build()

        }
        return appComponent
    }
}