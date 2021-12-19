package com.kernel.android.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TruckModel(
    val id: String = "",
    val inProgress: Boolean = true,
    val stateNumber: String = "",
    val driverData: String = "",
    val details: String = "",
    val liftingCapacity: Float = 0.0F,
    val truckType: TruckType = TruckType.TRUCK,
    val trailerNumber: String
): Parcelable


enum class TruckType(val type: String){
    TRUCK("Тягач"),
    SMALL_TRUCK("Фургон"),
    SMALL_TRUCK_WITH_TRAILER("Фургон з прицепом")
}
