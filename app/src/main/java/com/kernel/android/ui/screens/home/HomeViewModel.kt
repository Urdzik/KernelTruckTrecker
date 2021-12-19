package com.kernel.android.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kernel.android.domain.model.TruckModel
import com.kernel.android.domain.usecase.GetTrucksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrucksUseCase: GetTrucksUseCase
) : ViewModel() {


    val truckItem: StateFlow<List<TruckModel>> =
        getTrucksUseCase()
            .stateIn(viewModelScope, started = SharingStarted.Lazily, emptyList())

}
