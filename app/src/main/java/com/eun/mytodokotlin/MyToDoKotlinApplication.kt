package com.eun.mytodokotlin

import android.app.Application
import com.eun.mytodokotlin.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyToDoKotlinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO koin Trigger

startKoin {
    androidLogger(Level.ERROR)
    androidContext(this@MyToDoKotlinApplication)
    modules(appModule)
}
    }
}