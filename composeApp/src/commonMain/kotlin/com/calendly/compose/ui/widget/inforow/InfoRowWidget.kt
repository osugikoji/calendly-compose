package com.calendly.compose.ui.widget.inforow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.calendly.compose.ui.theme.MediumGray
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun InfoRowWidget(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp).fillMaxWidth()
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.MediumGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text,
            style = MaterialTheme.typography.body2,
            color = Color.MediumGray,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            InfoRowWidget(icon = Icons.Default.ShoppingCart, text = "30 min")
            InfoRowWidget(icon = Icons.Default.Settings, text = "Web conferencing details provided upon confirmation.")
            InfoRowWidget(icon = Icons.Default.Call, text = "09:30 - 10:00, Thursday, April 17, 2025")
        }
    }
}
