package com.kernel.android.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kernel.android.domain.model.TruckModel
import com.kernel.android.ui.nav.Screens
import com.kernel.android.ui.theme.Padding
import com.kernel.android.ui.theme.separator
import com.kernel.android.ui.utils.simpleToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreens(viewModel: HomeViewModel, navController: NavHostController) {


    val list = viewModel.truckItem.collectAsState()
    Scaffold(topBar = simpleToolbar(title ="Truck List")) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TruckList(
                modifier = Modifier.fillMaxSize(),
                list = list.value
            ) {
                navController.navigate("${Screens.DETAILS}/$it")
            }

        }
    }
}



@ExperimentalFoundationApi
@Composable
fun TruckList(
    modifier: Modifier = Modifier,
    list: List<TruckModel>,
    onClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        list.forEach {
            item { TruckItem(data = it, click = onClick) }
            item {
                Spacer(
                    modifier = Modifier
                        .background(separator)
                        .height(0.5.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}





