package com.kernel.android.data.sourse.firebase.firestore

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.kernel.android.domain.model.Fact
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface TruckFirestoreSource {

    @ExperimentalCoroutinesApi
    fun getTrucks(): Flow<QuerySnapshot>
    suspend fun getTruckInfo(id: String): DocumentSnapshot?
    fun addFact(fact: Fact)
    fun updateTruck(id: String, vararg values: Pair<String, Any>)
    suspend fun submitCashingData()
}
