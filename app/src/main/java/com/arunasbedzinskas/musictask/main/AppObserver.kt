package com.arunasbedzinskas.musictask.main

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.arunasbedzinskas.musictask.work.WorkScheduler

class AppObserver(private val workScheduler: WorkScheduler) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        Log.d(TAG, "App goes to background")
        workScheduler.scheduleOneTimeDatabaseWriteWorker()
    }

    companion object {

        private const val TAG = "AppObserver"
    }
}