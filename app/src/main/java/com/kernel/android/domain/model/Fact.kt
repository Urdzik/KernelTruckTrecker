package com.kernel.android.domain.model

data class Fact(
    val truckId: String,
    val seals: List<Seal> = emptyList()
)

