package com.kernel.android.data.worker

import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TruckWorkManagerLauncher @Inject constructor(
    private val workManager: WorkManager
) {

    operator fun invoke() {
        workManager.enqueue(buildRequest())
    }

    private fun buildRequest(): WorkRequest {
        return PeriodicWorkRequestBuilder<TruckDataWorkManager>(15, TimeUnit.MINUTES)
            .setConstraints(buildConstraints())
            .addTag(TruckDataWorkManager::class.java.name)
            .build()
    }

    private fun buildConstraints() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

}