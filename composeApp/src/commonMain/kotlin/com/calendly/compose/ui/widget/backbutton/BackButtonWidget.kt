package com.calendly.compose.ui.widget.backbutton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.calendly.compose.ui.theme.PrimaryBlue
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun BackButtonWidget(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .background(color = Color.Transparent, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.PrimaryBlue,
        )
    }
}

@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        BackButtonWidget(modifier = Modifier.background(Color.White))
    }
}
