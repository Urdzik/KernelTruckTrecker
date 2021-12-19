package com.kernel.android.data.firebase.storage

import android.net.Uri
import androidx.core.net.toFile
import com.google.firebase.storage.FirebaseStorage
import com.kernel.android.data.sourse.firebase.storage.FirebaseStorageSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseStorageSourceImpl @Inject constructor(
    private val storage: FirebaseStorage
) : FirebaseStorageSource {

    override suspend fun submitFile(fileUri: Uri) {
        storage.reference.child(fileUri.toString()).putFile(fileUri).await()
        fileUri.toFile().delete()
    }

}