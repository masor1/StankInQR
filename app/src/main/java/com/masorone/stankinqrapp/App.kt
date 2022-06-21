package com.masorone.stankinqrapp

import android.app.Application
import com.masorone.stankinqrapp.di.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }
}