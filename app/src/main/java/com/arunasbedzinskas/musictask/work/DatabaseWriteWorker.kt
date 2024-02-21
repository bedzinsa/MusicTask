package com.arunasbedzinskas.musictask.work

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.arunasbedzinskas.musictask.repository.SongsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseWriteWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val songsRepository: SongsRepository by inject()

    override suspend fun doWork(): Result {
        Log.d(TAG, "Starting work")
        return if (songsRepository.saveSongsToDatabase()) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo =
        ForegroundInfo(
            System.currentTimeMillis().toInt(),
            createNotification()
        )

    private fun createNotification() = Notification.Builder(applicationContext, "Generic")
        .setContentTitle("Saving")
        .setContentText("Saving songs to database...")
        .build()

    companion object {

        private const val TAG = "DatabaseWriteWorker"
    }
}