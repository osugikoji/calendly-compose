package com.calendly.compose.ui.widget.circularimage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage

@Composable
internal fun CircularImageWidget(
    imageUrl: String,
    size: Int = 64,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        loading = { Placeholder() },
        error = { Placeholder() },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}

@Composable
private fun Placeholder() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.LightGray)
    )
}
