package com.kernel.android.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.kernel.android.ui.theme.Padding

@Composable
fun ShapeViewer(shape: Shape, color: Color, height: Dp, width: Dp) {
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(shape)
            .background(color)
    )
}

@Composable
fun simpleToolbar(title: String): @Composable () -> Unit = {
    Surface(color = MaterialTheme.colorScheme.background) {
        Text(
            modifier = Modifier.padding(start = Padding.normal, top = Padding.big).fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
    }

}

