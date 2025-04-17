package com.calendly.compose.ui.widget.loading

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.calendly.compose.ui.theme.PrimaryBlue
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val DOT_SIZE = 16
private const val DELAY_UNIT = 200
private const val MAX_OFF_SET = 10f

@Composable
internal fun LoadingDotsWidget(
    color: Color = Color.PrimaryBlue,
    size: Int = DOT_SIZE,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val offset1 by animateOffsetWithDelay(infiniteTransition, 0)
    val offset2 by animateOffsetWithDelay(infiniteTransition, DELAY_UNIT)
    val offset3 by animateOffsetWithDelay(infiniteTransition, DELAY_UNIT * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        val spaceSize = 8.dp
        DotWidget(size, color, offset1)
        Spacer(Modifier.width(spaceSize))
        DotWidget(size, color, offset2)
        Spacer(Modifier.width(spaceSize))
        DotWidget(size, color, offset3)
    }
}

@Composable
private fun DotWidget(
    size: Int,
    color: Color,
    offset: Float
) = Spacer(
    Modifier
        .size(size.dp)
        .offset(y = -offset.dp)
        .background(
            color = color,
            shape = CircleShape,
        )
)

@Composable
private fun animateOffsetWithDelay(
    infiniteTransition: InfiniteTransition,
    delay: Int,
) = infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            durationMillis = DELAY_UNIT * 4
            0f at delay using LinearEasing
            MAX_OFF_SET at delay + DELAY_UNIT using LinearEasing
            0f at delay + DELAY_UNIT * 2
        }
    )
)

@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LoadingDotsWidget()
        }
    }
}
