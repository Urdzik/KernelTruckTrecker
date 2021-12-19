package com.kernel.android.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kernel.android.domain.model.TruckModel
import com.kernel.android.domain.model.TruckType
import com.kernel.android.ui.theme.AppTheme
import com.kernel.android.ui.theme.Padding

@Composable
fun TruckInformation(modifier: Modifier = Modifier, data: TruckModel, onClick: () -> Unit = {}) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = modifier.wrapContentHeight()) {
            Row {
                Column(modifier = Modifier.weight(3f)) {
                    Text(style = MaterialTheme.typography.labelMedium, text = "State Number")
                    Text(style = MaterialTheme.typography.titleLarge, text = data.stateNumber)
                }
                FilledTonalButton(onClick = onClick, modifier = Modifier.weight(1f)) {
                    Text(text = "Save")
                }

            }
            Row(modifier = Modifier.padding(top = Padding.small)) {
                Column(modifier = Modifier.weight(1f)) {
                    if (data.trailerNumber.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(top = Padding.tiny),
                            style = MaterialTheme.typography.labelMedium,
                            text = "Trailer State Number"
                        )
                        Text(
                            modifier = Modifier.padding(bottom = Padding.small),
                            style = MaterialTheme.typography.bodyMedium,
                            text = data.trailerNumber
                        )
                    }
                    Text(
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.labelMedium,
                        text = "Driver information"
                    )
                    Text(
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.bodyMedium,
                        text = data.driverData
                    )
                    Text(
                        modifier = Modifier.padding(top = Padding.small),
                        style = MaterialTheme.typography.labelMedium,
                        text = "Details of truck"
                    )
                    Text(
                        modifier = Modifier.padding(end = Padding.normal),
                        style = MaterialTheme.typography.bodyMedium,
                        text = data.details
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = Padding.normal)
                        .weight(1f)
                ) {
                    Text(
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.labelMedium,
                        text = "Lifting capacity"
                    )
                    Text(
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.bodyMedium,
                        text = "${data.liftingCapacity} tons"
                    )
                    Text(
                        modifier = Modifier.padding(top = Padding.small),
                        style = MaterialTheme.typography.labelMedium,
                        text = "Truck type"
                    )
                    Text(
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.bodyMedium,
                        text = data.truckType.type
                    )
                }
            }


        }
    }
}

@Preview(name = "PreviewLight", showBackground = false)
@Composable
fun PreviewLight() {
    AppTheme(false) {
        TruckInformation(
            modifier = Modifier.padding(16.dp),
            data = TruckModel(
                stateNumber = "AB5555BC",
                driverData = "Макс Ферстапен",
                details = "Mercedes-Beans, Red",
                liftingCapacity = 5.6F,
                truckType = TruckType.SMALL_TRUCK_WITH_TRAILER,
                trailerNumber = "",
                inProgress = true
            )
        )
    }
}

@Preview(name = "PreviewDark", showBackground = false)
@Composable
fun PreviewDark() {
    AppTheme(true) {
        TruckInformation(
            modifier = Modifier.padding(16.dp),
            data = TruckModel(
                stateNumber = "AB5555BC",
                driverData = "Макс Ферстапен",
                details = "Mercedes-Beans, Red",
                liftingCapacity = 5.6F,
                truckType = TruckType.TRUCK,
                trailerNumber = "AB5555BC",
                inProgress = true
            )
        )
    }


}
