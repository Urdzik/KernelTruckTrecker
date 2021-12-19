package com.kernel.android.di

import com.kernel.android.data.sourse.firebase.firestore.TruckFirestoreSource
import com.kernel.android.data.sourse.firebase.firestore.TruckFirestoreSourceImpl
import com.kernel.android.data.sourse.firebase.storage.FirebaseStorageSource
import com.kernel.android.data.firebase.storage.FirebaseStorageSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseSourceModule {

    @Binds
    abstract fun bindTruckFirestoreSource(truckFirestoreSourceImpl: TruckFirestoreSourceImpl): TruckFirestoreSource

    @Binds
    abstract fun bindTruckStorageSource(firebaseStorageSourceImpl: FirebaseStorageSourceImpl): FirebaseStorageSource
}

