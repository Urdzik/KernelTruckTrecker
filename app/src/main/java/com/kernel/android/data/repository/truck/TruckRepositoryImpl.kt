package com.kernel.android.data.repository.truck

import com.kernel.android.data.key.FirestoreKeys
import com.kernel.android.data.mappers.DocumentTruckMapper
import com.kernel.android.data.repository.shared.TruckSharedPreferenceRepository
import com.kernel.android.data.sourse.firebase.firestore.TruckFirestoreSource
import com.kernel.android.data.worker.TruckWorkManagerLauncher
import com.kernel.android.domain.model.Fact
import com.kernel.android.domain.model.TruckModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TruckRepositoryImpl @Inject constructor(
    private val firestoreSource: TruckFirestoreSource,
    private val workManagerLauncher: TruckWorkManagerLauncher,
    private val sharedPreferencesRepository: TruckSharedPreferenceRepository
) : TruckRepository {

    @ExperimentalCoroutinesApi
    override fun getTrucks(): Flow<List<TruckModel>> = firestoreSource.getTrucks().map { snapshot ->
        snapshot.documents.map(DocumentTruckMapper::invoke)
    }

    override suspend fun getTruckInfo(id: String): TruckModel {
        val doc = firestoreSource.getTruckInfo(id) ?: throw IllegalStateException("Can't get a  truck")
        return DocumentTruckMapper.invoke(doc)
    }

    override suspend fun submitTruck(fact: Fact) {
        submitPhoto(fact)
        with(firestoreSource) {
            addFact(fact)
            updateTruck(fact.truckId, FirestoreKeys.IN_PROGRESS to false)
        }
        workManagerLauncher.invoke()
    }


    private fun submitPhoto(fact: Fact) =
        sharedPreferencesRepository.getPhotosUriSet().let { set ->
            val mutableSet = set.toMutableSet()
            fact.seals.mapNotNullTo(mutableSet) { it.uri }
            sharedPreferencesRepository.putPhotosUriSet(mutableSet)
        }
}

