package com.kernel.android.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kernel.android.R
import com.kernel.android.domain.model.TruckModel
import com.kernel.android.ui.theme.AppTheme
import com.kernel.android.ui.theme.Padding

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@ExperimentalFoundationApi
@Composable
fun TruckItem(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    data: TruckModel,
    click: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .padding(Padding.tiny_x2)
            .fillMaxWidth()
            .noRippleClickable { click.invoke(data.id) },
        color = MaterialTheme.colorScheme.surface,

        ) {
        Row(Modifier.padding(Padding.normal)) {
            Column(modifier = Modifier.weight(10F)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(style = MaterialTheme.typography.titleMedium, text = data.stateNumber)
                    Text(
                        modifier = Modifier.padding(start = Padding.tiny),
                        style = MaterialTheme.typography.labelMedium,
                        text = "(${data.details})"
                    )
                }

                Text(
                    modifier = Modifier.padding(top = Padding.tiny),
                    style = MaterialTheme.typography.bodySmall,
                    text = data.driverData,
                )
            }

            Image(
                modifier = Modifier
                    .weight(1F)
                    .align(Alignment.CenterVertically)
                    .wrapContentSize(),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward),
                contentDescription = "Arrow",
                colorFilter = ColorFilter.tint(if (isDarkTheme) Color.DarkGray else Color.Gray)
            )


        }
    }
}


@ExperimentalFoundationApi
@Preview(name = "Truck Item Light")
@Composable
fun PreviewLight() {
    AppTheme(false) {
        TruckItem(
            data = TruckModel(
                stateNumber = "AB5555AB",
                driverData = "Василь Васильвич",
                details = "Сіра",
                trailerNumber = "",
            )
        ) {}
    }
}

@ExperimentalFoundationApi
@Preview(name = "Truck Item Dark")
@Composable
fun PreviewBlack() {
    AppTheme(true) {
        TruckItem(
            data = TruckModel(
                stateNumber = "AB5555AB",
                driverData = "Василь Васильвич",
                trailerNumber = "",
            )
        ) {}
    }
}