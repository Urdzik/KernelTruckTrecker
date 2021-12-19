package com.kernel.android.data.sourse.firebase.firestore

import com.kernel.android.data.utils.toFlow
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kernel.android.data.key.FirestoreKeys
import com.kernel.android.domain.model.Fact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TruckFirestoreSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TruckFirestoreSource {

    @ExperimentalCoroutinesApi
    override fun getTrucks(): Flow<QuerySnapshot> = firestore.collection(FirestoreKeys.TRUCKS)
        .whereEqualTo(FirestoreKeys.IN_PROGRESS, true)
        .toFlow()


    override suspend fun getTruckInfo(id: String): DocumentSnapshot? =
        firestore.collection(FirestoreKeys.TRUCKS).document(id).get().await()


    override fun addFact(fact: Fact) {
        firestore.collection(FirestoreKeys.FACTS)
            .add(fact)
    }

    override fun updateTruck(id: String, vararg values: Pair<String, Any>) {
        firestore.document("${FirestoreKeys.TRUCKS}/$id")
            .update(hashMapOf(*values))
    }

    override suspend fun submitCashingData() {
            firestore.enableNetwork()
            firestore.waitForPendingWrites().await()
    }

}