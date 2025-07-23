package com.edurda77.test.workmate

import android.app.Application
import com.edurda77.test.workmate.di.baseModule
import com.edurda77.test.workmate.di.repoModule
import com.edurda77.test.workmate.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                baseModule, viewModelModule, repoModule,
            )
        }
    }
}