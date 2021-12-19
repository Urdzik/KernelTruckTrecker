package com.kernel.android.domain.usecase

import com.kernel.android.data.repository.truck.TruckRepository
import com.kernel.android.domain.model.Fact
import javax.inject.Inject

class SubmitTruckFactUseCase @Inject constructor(
    private val truckRepository: TruckRepository
) {
    suspend operator fun invoke(fact: Fact) = truckRepository.submitTruck(fact)
}