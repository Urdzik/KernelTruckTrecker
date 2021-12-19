package com.kernel.android.di

import com.kernel.android.data.repository.truck.TruckRepository
import com.kernel.android.data.repository.truck.TruckRepositoryImpl
import com.kernel.android.data.repository.shared.TruckSharedPreferenceRepository
import com.kernel.android.data.repository.shared.TruckSharedPreferenceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTruckRepository(repository: TruckRepositoryImpl): TruckRepository

    @Binds
    abstract fun bindTruckSharedPreferenceRepository(truckSharedPreferenceRepositoryImpl: TruckSharedPreferenceRepositoryImpl): TruckSharedPreferenceRepository
}