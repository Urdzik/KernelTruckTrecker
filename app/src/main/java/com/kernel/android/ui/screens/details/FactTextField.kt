package com.kernel.android.ui.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import com.kernel.android.ui.theme.AppTheme
import com.kernel.android.ui.theme.Padding

@Composable
fun FactTextField(modifier: Modifier = Modifier, value: String, number: Int, onValueChange: (String, Int) -> Unit) {
   Surface(color = MaterialTheme.colorScheme.background) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$number: ", style = MaterialTheme.typography.labelLarge)
        BasicTextField(
            modifier = Modifier.padding(vertical = Padding.tiny),
            value = value,
            textStyle = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.onBackground),
            onValueChange = {
                            onValueChange.invoke(it, number)
            } ,
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        )
    }

}}


@Preview()
@Composable
fun PreviewLightFact() {
    AppTheme(false) {
        FactTextField(value = "fff", onValueChange = { s: String, i: Int -> }, number = 1)
    }
}

@Preview()
@Composable
fun PreviewDarkFact() {
    AppTheme(true) {
        FactTextField(value = "Text", onValueChange = { s: String, i: Int ->}  , number = 1)

    }
}