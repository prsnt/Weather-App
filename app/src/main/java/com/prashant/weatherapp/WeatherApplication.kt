package com.prashant.weatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class WeatherApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}