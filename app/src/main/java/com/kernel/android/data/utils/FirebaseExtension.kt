package com.kernel.android.data.utils

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun Query.toFlow(): Flow<QuerySnapshot> = callbackFlow {
    addSnapshotListener { snapshot, exception ->
        exception?.let(::error)
        snapshot?.let(::trySend)
    }.run {
        awaitClose { remove() }
    }
}

