package com.kernel.android.ui.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionsRequired
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private var pictureImagePath = ""

@ExperimentalPermissionsApi
@Composable
fun ObserveRequestPermission(
    rememberPermissions: MultiplePermissionsState,
    result: @Composable () -> Unit
) {
    PermissionsRequired(
        multiplePermissionsState = rememberPermissions,
        permissionsNotGrantedContent = {},
        permissionsNotAvailableContent = result,
        content = {}
    )
}

@Composable
fun getContent(
    result: (Bitmap) -> Unit,
): Pair<ManagedActivityResultLauncher<Intent, ActivityResult>, Intent> {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        activityResult(it, result, context)
    }
    return launcher to createChooser()
}



private fun activityResult(
    it: ActivityResult,
    result: (Bitmap) -> Unit,
    context: Context
) {
    if (it.data?.data == null) {

        val imgFile = File(pictureImagePath)
        pictureImagePath = ""
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            result.invoke(myBitmap)
        }
    } else {
        it.data?.data?.let {
            result.invoke(
                if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)

                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
            )
        }
    }
}

private fun createChooser(): Intent {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ROOT).format(Date())
    val imageFileName = "$timeStamp.jpg"
    val storageDir = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    )
    pictureImagePath = storageDir.absolutePath + "/" + imageFileName
    val file = File(pictureImagePath)
    val outputFileUri = Uri.fromFile(file)
    val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)

    val pickIntent = Intent()
    pickIntent.type = "image/*"
    pickIntent.action = Intent.ACTION_GET_CONTENT

    val pickTitle = "Choose a Picture"
    val chooserIntent = Intent.createChooser(pickIntent, pickTitle)
    chooserIntent.putExtra(
        Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePhotoIntent)
    )
    return chooserIntent
}
