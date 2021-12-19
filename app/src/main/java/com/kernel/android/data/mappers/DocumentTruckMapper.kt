package com.kernel.android.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.kernel.android.data.key.FirestoreKeys.DETAILS
import com.kernel.android.data.key.FirestoreKeys.DRIVER_DATA
import com.kernel.android.data.key.FirestoreKeys.IN_PROGRESS
import com.kernel.android.data.key.FirestoreKeys.LIFTING_CAPACITY
import com.kernel.android.data.key.FirestoreKeys.CAR_STATE_NUMBER
import com.kernel.android.data.key.FirestoreKeys.TRAILER_STATE_NUMBER
import com.kernel.android.data.key.FirestoreKeys.TRUCK_TYPE
import com.kernel.android.domain.model.TruckModel
import com.kernel.android.domain.model.TruckType

object DocumentTruckMapper {
    operator fun invoke(documentSnapshot: DocumentSnapshot): TruckModel =
        documentSnapshot.run {
            val carStateNumber = get(CAR_STATE_NUMBER) as? String ?: error(CAR_STATE_NUMBER)
            val driverData = get(DRIVER_DATA) as? String ?: error(DRIVER_DATA)
            val trailerStateNumber = get(TRAILER_STATE_NUMBER) as? String ?: error(TRAILER_STATE_NUMBER)
            val inProgress = get(IN_PROGRESS) as? Boolean ?: error(IN_PROGRESS)
            val details = get(DETAILS) as? List<String> ?: error(DETAILS)
            val liftingCapacity = get(LIFTING_CAPACITY) as? Number ?: error(LIFTING_CAPACITY)
            val truckType = get(TRUCK_TYPE) as? Number ?: error(TRUCK_TYPE)
            TruckModel(
                id = id,
                inProgress = inProgress,
                stateNumber = carStateNumber,
                driverData = driverData,
                details = details.joinToString(", "),
                liftingCapacity = liftingCapacity.toFloat(),
                truckType = truckType.toTruckEnum(),
                trailerNumber = trailerStateNumber,
            )
        }

    private fun error(valueName: String): Nothing {
        kotlin.error("Invalid $valueName")
    }

    private fun Number.toTruckEnum(): TruckType {
        return TruckType.values()[this.toInt()]
    }

}