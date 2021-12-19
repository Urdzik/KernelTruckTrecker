package com.kernel.android.domain.usecase

import com.kernel.android.data.repository.truck.TruckRepository
import javax.inject.Inject

class GetTruckInfoUseCase @Inject constructor(private val truckRepository: TruckRepository) {

    suspend operator fun invoke(id: String) =
        truckRepository.getTruckInfo(id)
}