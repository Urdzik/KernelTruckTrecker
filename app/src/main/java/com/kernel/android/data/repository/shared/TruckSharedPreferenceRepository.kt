package com.kernel.android.data.repository.shared

interface TruckSharedPreferenceRepository {

    fun getPhotosUriSet(): Set<String>
    fun putPhotosUriSet(set: MutableSet<String>)
}