package com.kernel.android.data.worker

import android.content.Context
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kernel.android.data.sourse.firebase.firestore.TruckFirestoreSource
import com.kernel.android.data.sourse.firebase.storage.FirebaseStorageSource
import com.kernel.android.data.repository.shared.TruckSharedPreferenceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@HiltWorker
class TruckDataWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val sharedPreferencesRepository: TruckSharedPreferenceRepository,
    private val truckFirestoreSource: TruckFirestoreSource,
    private val firebaseStorageSource: FirebaseStorageSource
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        return runCatching {
            uploadPhotos()
            truckFirestoreSource.submitCashingData()
        }.onFailure(::logError)
            .fold({
                Result.success()
            }, {
                Result.retry()
            })
    }

    private suspend fun uploadPhotos() {
        val pendingPhotoSet = sharedPreferencesRepository.getPhotosUriSet().toMutableSet()

        supervisorScope {
            pendingPhotoSet.forEach { photoName ->
                launch(CoroutineExceptionHandler(::logError)) {
                    firebaseStorageSource.submitFile(photoName.toUri())
                    pendingPhotoSet.remove(photoName)
                }
            }
        }


        sharedPreferencesRepository.putPhotosUriSet(pendingPhotoSet)
    }


    private fun logError(
        throwable: Throwable,
        message: String = "Error submit cash data"
    ) {
        Timber.e(throwable, message)
    }

    private fun logError(
        coroutineContext: CoroutineContext?  = null,
        throwable: Throwable,
        message: String = "Error submit cash data in coroutines $coroutineContext"
    ) {
        Timber.e(throwable, message)
    }
}