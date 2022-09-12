package com.artushock.itcentertest

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.artushock.itcentertest.di.AppModule
import com.artushock.itcentertest.di.ApplicationComponent
import com.artushock.itcentertest.di.DaggerApplicationComponent

class WeatherApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}

val Context.appComponent: ApplicationComponent
    get() = when (this) {
        is WeatherApplication -> appComponent
        else -> this.applicationContext.appComponent
    }

val Context.isConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }