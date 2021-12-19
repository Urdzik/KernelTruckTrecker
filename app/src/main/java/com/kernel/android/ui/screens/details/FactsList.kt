package com.kernel.android.ui.screens.details

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kernel.android.R
import com.kernel.android.ui.screens.home.noRippleClickable
import com.kernel.android.ui.theme.AppTheme
import com.kernel.android.ui.theme.Padding
import com.kernel.android.ui.utils.ShapeViewer

@ExperimentalMaterialApi
@Composable
fun FactsList(modifier: Modifier = Modifier, list: List<Bitmap>, onClick: () -> Unit) {
    val cartModifier = Modifier.padding(vertical = Padding.tiny)
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = modifier) {

            LazyRow {
                item(key = Int.MIN_VALUE) {
                    AddPhotoItem(
                        cartModifier.padding(
                            end = Padding.small
                        ), onClick
                    )
                }
                itemsIndexed(items = list, key = { index, item -> item }) { index, item ->
                    PhotoItem(
                        cartModifier.padding(horizontal = Padding.small), bitmap = item, index + 1
                    )
                }
            }
        }
    }
}


@Composable
private fun PhotoItem(modifier: Modifier, bitmap: Bitmap? = null, number: Int = 1) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .size(128.dp),
            backgroundColor = MaterialTheme.colorScheme.background,
            elevation = 1.5.dp,
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier
                .align(Alignment.TopStart)){

                bitmap?.asImageBitmap()?.let {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        bitmap = it, contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Box(Modifier.wrapContentSize().padding(Padding.tiny), contentAlignment = Alignment.Center) {
                    ShapeViewer(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        height = 24.dp,
                        width = 24.dp
                    )
                    Text(
                        text = "$number",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.wrapContentSize()
                    )
                }
        }
    }}
}


@ExperimentalMaterialApi
@Composable
private fun AddPhotoItem(modifier: Modifier, onClick: () -> Unit) {
    Box(modifier = modifier.noRippleClickable(onClick)) {
        Card(
            modifier = Modifier.size(128.dp),
            backgroundColor = MaterialTheme.colorScheme.background,
            elevation = 1.5.dp,
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                ShapeViewer(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    height = 64.dp,
                    width = 64.dp
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_image_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewItem() {
    AppTheme {
        PhotoItem(Modifier)
    }
}