package com.arunasbedzinskas.musictask.main

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.arunasbedzinskas.musictask.di.Koin
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initAppProcessListener()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)

            modules(Koin.modules())
        }
    }

    private fun initAppProcessListener() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(get<AppObserver>())
    }
}
