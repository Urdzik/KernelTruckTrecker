package com.kernel.android

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kernel.android.ui.nav.NavHost
import com.kernel.android.ui.screens.home.HomeScreens
import com.kernel.android.ui.screens.home.HomeViewModel
import com.kernel.android.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialNavigationApi
@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("PermissionLaunchedDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            AppTheme {

                Surface(
                    Modifier.background(MaterialTheme.colorScheme.background),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavHost()
                }
            }
        }
    }}