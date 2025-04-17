package com.calendly.compose.ui.widget.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.calendly.compose.ui.theme.PrimaryBlue
import com.calendly.compose.ui.widget.loading.LoadingDotsWidget
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ButtonWidget(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.PrimaryBlue,
    disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    borderColor: Color? = null,
    enabled: Boolean = true,
    cornerRadius: Int = 4,
    isLoading: Boolean = false,
    textColor: Color = Color.White,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor,
        ),
        border = borderColor?.let { BorderStroke(0.5.dp, it) },
        elevation = null,
        shape = RoundedCornerShape(cornerRadius.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        if (isLoading) {
            LoadingDotsWidget(size = 8)
        } else {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        ButtonWidget(text = "Confirm", Modifier.background(Color.White))
    }
}
