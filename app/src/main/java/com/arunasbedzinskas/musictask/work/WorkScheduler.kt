package com.arunasbedzinskas.musictask.work

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager

class WorkScheduler(private val workManager: WorkManager) {

    fun scheduleOneTimeDatabaseWriteWorker() {
        val request = OneTimeWorkRequestBuilder<DatabaseWriteWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueue(request)
    }
}
