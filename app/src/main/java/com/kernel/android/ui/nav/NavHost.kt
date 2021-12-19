package com.kernel.android.ui.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.kernel.android.ui.nav.Screens.HOME
import com.kernel.android.ui.screens.details.DetailsScreen
import com.kernel.android.ui.screens.details.DetailsViewModel
import com.kernel.android.ui.screens.home.HomeScreens
import com.kernel.android.ui.screens.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialNavigationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavHost() {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    ModalBottomSheetLayout(
        bottomSheetNavigator,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        NavHost(navController = navController, startDestination = HOME) {
            composable(HOME) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreens(
                    viewModel,
                    navController
                )

            }
            bottomSheet(
                route = "details/{truckId}",
                arguments = listOf(navArgument("truckId") { type = NavType.StringType })
            ) {
                val viewModel = hiltViewModel<DetailsViewModel>()
                DetailsScreen(viewModel, navController)

            }
        }
    }
}

object Screens {
    const val HOME = "home"
    const val DETAILS = "details"
}

