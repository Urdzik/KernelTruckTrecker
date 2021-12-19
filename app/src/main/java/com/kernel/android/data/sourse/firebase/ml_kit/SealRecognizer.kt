package com.kernel.android.data.firebase.ml_kit

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.io.File


interface SealRecognizer {
    suspend fun recognize(uri: Uri): String?
    fun close()
}

class OfflineSealRecognizer @Inject constructor(
    @ApplicationContext private val context: Context
) : SealRecognizer {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override suspend fun recognize(uri: Uri): String? = withContext(Dispatchers.Default) {
        val image = InputImage.fromFilePath(context, uri)
        val visionText = recognizer.process(image).await()
        val elements = visionText.textBlocks.flatMap { it.lines }.flatMap { it.elements }.map { it.text }
        elements.find(::isValidNumber)
    }

    private fun isValidNumber(number: String): Boolean {
        return number.matches(".[0-9]{8}$".toRegex())
    }

    override fun close() {
        recognizer.close()
    }
}