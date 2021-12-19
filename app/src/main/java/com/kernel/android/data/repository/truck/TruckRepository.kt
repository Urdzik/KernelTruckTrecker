package com.kernel.android.data.repository.truck

import com.kernel.android.domain.model.Fact
import com.kernel.android.domain.model.TruckModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface TruckRepository {
    @ExperimentalCoroutinesApi
    fun getTrucks(): Flow<List<TruckModel>>
    suspend fun getTruckInfo(id: String): TruckModel
   suspend fun submitTruck(fact: Fact)
}

