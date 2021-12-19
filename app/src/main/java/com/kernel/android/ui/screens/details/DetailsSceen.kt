package com.kernel.android.ui.screens.details

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kernel.android.ui.theme.Padding
import com.kernel.android.ui.theme.progress_background
import com.kernel.android.ui.theme.separator
import com.kernel.android.ui.utils.ObserveRequestPermission
import com.kernel.android.ui.utils.ShapeViewer
import com.kernel.android.ui.utils.getContent

@ExperimentalMaterialApi
@SuppressLint("CoroutineCreationDuringComposition", "FlowOperatorInvokedInComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    navController: NavHostController
) {

    var needRepeatedStartOfChooser by rememberSaveable { mutableStateOf(false) }

    val data by viewModel.truckInfo.collectAsState()
    val bitmap by viewModel.photos.collectAsState()
    val numbers by viewModel.numbers.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val error by viewModel.error.collectAsState()
    val navigate by viewModel.navigate.collectAsState()


    if (navigate){
        navController.popBackStack()
    }

    val rememberPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    val (launcher, intent) = getContent(viewModel::setPhotoAutoRotate)

    ObserveRequestPermission(rememberPermissions) {
        if (needRepeatedStartOfChooser) {
            launcher.launch(intent)
            needRepeatedStartOfChooser = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        if (error.isNotEmpty()) {
            AlertDialog(modifier = Modifier.align(Alignment.Center),
                title = {
                    Text(text = error)
                },
                onDismissRequest = {
                    viewModel.setEmptyError()
                },
                confirmButton = {
                    Box(modifier = Modifier.size(0.dp))
                }
            )
        }
    }
    Column(modifier = Modifier.padding(Padding.normal)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        ) {
            ShapeViewer(
                height = 6.dp,
                width = 50.dp,
                shape = RoundedCornerShape(10.dp),
                color = separator
            )
        }

        TruckInformation(
            modifier = Modifier.padding(top = Padding.normal),
            data = data,
            onClick = viewModel::save
        )

        if (numbers.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = Padding.small),
                text = "Fact",
                style = MaterialTheme.typography.labelMedium
            )
            numbers.forEachIndexed { index, s ->
                FactTextField(
                    modifier = Modifier.padding(top = Padding.tiny),
                    value = s,
                    number = index + 1,
                    onValueChange = viewModel::setNumber
                )
            }
        }

        FactsList(modifier = Modifier.padding(top = Padding.normal), bitmap) {
            if (rememberPermissions.permissionRequested) {
                launcher.launch(intent)
            } else {
                needRepeatedStartOfChooser = true
                rememberPermissions.launchMultiplePermissionRequest()
            }
        }
    }

    ProgressBar(progress)
}

@Composable
private fun ProgressBar(progress: Boolean) {
    if (progress) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .height(600.dp)
                .background(progress_background)
                .clickable(enabled = false) {}) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}


