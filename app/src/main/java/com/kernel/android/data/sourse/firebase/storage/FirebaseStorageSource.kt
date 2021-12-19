package com.kernel.android.data.sourse.firebase.storage

import android.net.Uri

interface FirebaseStorageSource {

   suspend fun submitFile(fileUri: Uri)
}
