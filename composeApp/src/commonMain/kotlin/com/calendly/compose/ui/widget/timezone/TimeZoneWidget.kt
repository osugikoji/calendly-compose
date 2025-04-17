package com.calendly.compose.ui.widget.timezone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import calendly_compose.composeapp.generated.resources.Res
import calendly_compose.composeapp.generated.resources.time_zone
import com.calendly.compose.ui.icons.Globe
import com.calendly.compose.ui.theme.NavyBlue
import com.calendly.compose.utils.CalendarUtils
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TimeZoneWidget(
    timeZone: TimeZone = CalendarUtils.currentTimeZone,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable { }
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(Res.string.time_zone),
            color = Color.NavyBlue,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Globe,
                tint = Color.NavyBlue,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = CalendarUtils.getTimeZoneDisplay(timeZone),
                color = Color.NavyBlue,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.body2,
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                tint = Color.NavyBlue,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}
