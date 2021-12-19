package com.kernel.android.ui.screens.details

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kernel.android.data.firebase.ml_kit.OfflineSealRecognizer
import com.kernel.android.data.utils.asUri
import com.kernel.android.data.utils.resize
import com.kernel.android.data.utils.rotate
import com.kernel.android.domain.model.Fact
import com.kernel.android.domain.model.Seal
import com.kernel.android.domain.model.TruckModel
import com.kernel.android.domain.usecase.GetTruckInfoUseCase
import com.kernel.android.domain.usecase.SubmitTruckFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getTruckInfoUseCase: GetTruckInfoUseCase,
    private val submitTruckFactUseCase: SubmitTruckFactUseCase, savedStateHandle: SavedStateHandle,
    private val sealRecognizer: OfflineSealRecognizer,
    private val applicationContext: Application
) : AndroidViewModel(applicationContext) {

    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _error.value = throwable.message.toString()
    }

    private val viewModelHandleScope = viewModelScope + handler


    private val truckId = savedStateHandle.get<String>("truckId") ?: ""

    val truckInfo: StateFlow<TruckModel> = flow {
        emit(
            getTruckInfoUseCase.invoke(id = truckId)
        )
    }.stateIn(
        viewModelHandleScope, SharingStarted.Lazily, TruckModel(trailerNumber = "")
    )

    private val _numbers = MutableStateFlow<List<String>>(listOf(""))
    val numbers: StateFlow<List<String>> = _numbers.asStateFlow()

    private val _photos = MutableStateFlow<List<Bitmap>>(emptyList())
    val photos: StateFlow<List<Bitmap>> = _photos.asStateFlow()

    private val _progress = MutableStateFlow(false)
    val progress: StateFlow<Boolean> = _progress.asStateFlow()

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private val _navigate = MutableStateFlow(false)
    val navigate: StateFlow<Boolean> = _navigate.asStateFlow()

    private val bigImage = mutableListOf<String>()

    private var countOfRepeat = 0


    fun setPhotoAutoRotate(bitmap: Bitmap) {
        _progress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            recognize(bitmap)?.let {
                if (_numbers.value[0].isEmpty()) {
                    _numbers.value = listOf(it)
                } else {
                    _numbers.value = _numbers.value.toMutableList().apply {
                        add(it)
                    }
                }
                _photos.value = _photos.value.toMutableList().apply {
                    add(bitmap.resize())
                }
                bigImage.add(bitmap.asUri(applicationContext).toString())
                _progress.value = false
                countOfRepeat = 0
                Timber.d("seal = $it")
            } ?: run {
                if (countOfRepeat <= 2) {
                    countOfRepeat += 1
                    Timber.d("repeat count = $countOfRepeat")
                    setPhotoAutoRotate(bitmap rotate 90)
                } else {
                    Timber.e("After $countOfRepeat seal not recognize")
                    countOfRepeat = 0
                    _progress.value = false
                    _error.value = "Can't recognize"
                }
            }
        }
    }

    fun setNumber(it: String, number: Int) {
        _numbers.value = _numbers.value.toMutableList().apply { set(number - 1, it) }
    }

    fun save() {
        if (bigImage.isNotEmpty() && _numbers.value.isNotEmpty()) {
            viewModelScope.launch {
                submitTruckFactUseCase.invoke(Fact(truckId, createSeal()))
                _navigate.value = true
            }
        } else {
            _error.value = "You can't save a data"
        }
    }

    fun setEmptyError() {
        _error.value = ""
    }

    private suspend fun recognize(bitmap: Bitmap) =
        sealRecognizer.recognize(bitmap.asUri(applicationContext))


    override fun onCleared() {
        super.onCleared()
        sealRecognizer.close()
    }


    private fun createSeal(): List<Seal> {
        return bigImage.mapIndexed { index, uri ->
            Seal(
                numbers.value[index],
                uri
            )
        }
    }


}