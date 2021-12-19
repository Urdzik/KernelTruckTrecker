package com.kernel.android.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import androidx.core.net.toUri
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

infix fun Bitmap.rotate(degrees: Number): Bitmap =
    Bitmap.createBitmap(
        this,
        0,
        0,
        width,
        height,
        Matrix().apply { postRotate(degrees.toFloat()) },
        true
    )

fun Bitmap.resize(value: Int = 10): Bitmap {

    val width = this.width
    val height = this.height

    val scaleWidth = width / value
    val scaleHeight = height / value

    if (this.byteCount <= 1000000)
        return this

    return Bitmap.createScaledBitmap(this, scaleWidth, scaleHeight, false)
}

fun Bitmap.asUri(context: Context): Uri {
    val file = createImageFile(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
    val fileOutputStream = file.outputStream()
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val bytearray = byteArrayOutputStream.toByteArray()
    with(fileOutputStream) {
        write(bytearray)
        flush()
        close()
    }
    byteArrayOutputStream.close()

    return Uri.fromFile(file)
}

fun Uri.getName(context: Context): String {
    val returnCursor = context.contentResolver.query(this, null, null, null, null)
    val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor?.moveToFirst()
    val fileName = nameIndex?.let { returnCursor?.getString(it) }
    returnCursor?.close()
    return fileName ?: ""
}
private fun createImageFile(externalFilesDir: File?): File {
    val timeStamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
    return File(
        externalFilesDir,
        "JPEG_${timeStamp}",
    )
}