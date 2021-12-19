package com.kernel.android.domain.usecase

import com.kernel.android.data.repository.truck.TruckRepository
import com.kernel.android.domain.model.TruckModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrucksUseCase @Inject constructor(private val truckRepository: TruckRepository) {

    @ExperimentalCoroutinesApi
    operator fun invoke(): Flow<List<TruckModel>> =
        truckRepository.getTrucks()
}