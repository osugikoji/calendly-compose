package com.calendly.compose.screen.scheduletime

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import calendly_compose.composeapp.generated.resources.Res
import calendly_compose.composeapp.generated.resources.duration_minutes
import calendly_compose.composeapp.generated.resources.next
import calendly_compose.composeapp.generated.resources.select_time
import com.calendly.compose.ui.theme.NavyBlue
import com.calendly.compose.ui.theme.PrimaryBlue
import com.calendly.compose.ui.theme.SlateGray
import com.calendly.compose.ui.widget.backbutton.BackButtonWidget
import com.calendly.compose.ui.widget.button.ButtonWidget
import com.calendly.compose.ui.widget.timezone.TimeZoneWidget
import com.calendly.compose.utils.CalendarUtils.toTimeFormat
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ScheduleTimeScreen(
    timeAvailabilities: List<LocalDateTime> = emptyList(),
    viewModel: ScheduleTimeViewModel = viewModel { ScheduleTimeViewModel(timeAvailabilities) },
    onBackClick: () -> Unit = {},
    onNextClick: (LocalDateTime) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    ScheduleTimeScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onTimeSelected = { viewModel.onTimeSelected(it) },
        onNextClick = onNextClick,
    )
}

@Composable
internal fun ScheduleTimeScreen(
    uiState: ScheduleTimeUiState,
    onBackClick: () -> Unit = {},
    onTimeSelected: (LocalDateTime) -> Unit = {},
    onNextClick: (LocalDateTime) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Top Bar Region
        TopBarWidget(
            title = uiState.toolbarTitle,
            subtitle = uiState.toolbarSubtitle,
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Time Zone Region
        TimeZoneWidget(timeZone = uiState.timeZone)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.fillMaxWidth())

        // Select Time Option Region
        TimeOptionsWidget(
            timeOptionSelected = uiState.timeOptionSelected,
            timeOptions = uiState.timeOptions,
            onTimeSelected = onTimeSelected,
            onNextClick = onNextClick,
        )
    }
}

@Composable
private fun TopBarWidget(
    title: String,
    subtitle: String,
    onBackClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        BackButtonWidget(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = Color.NavyBlue,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body1,
                color = Color.NavyBlue,
            )
        }
    }
}

@Composable
private fun TimeOptionsWidget(
    timeOptionSelected: LocalDateTime?,
    timeOptions: List<LocalDateTime>,
    onTimeSelected: (LocalDateTime) -> Unit = {},
    onNextClick: (LocalDateTime) -> Unit = {},
) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
            Text(
                text = stringResource(Res.string.select_time),
                style = MaterialTheme.typography.h5,
                color = Color.NavyBlue,
                fontWeight = FontWeight.Bold,
            )
        }

        item {
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
            Text(
                text = stringResource(Res.string.duration_minutes),
                color = Color.NavyBlue,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
        }

        items(timeOptions) { timeOption ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 16.dp),
            ) {
                val isSelected = timeOption == timeOptionSelected
                val animatedWeight by animateFloatAsState(
                    targetValue = if (isSelected) 0.5f else 1f,
                )
                val backgroundColor = if (isSelected) Color.SlateGray else Color.Transparent
                ButtonWidget(
                    modifier = Modifier.weight(animatedWeight),
                    text = timeOption.toTimeFormat(),
                    enabled = !isSelected,
                    backgroundColor = backgroundColor,
                    disabledBackgroundColor = backgroundColor,
                    textColor = if (isSelected) Color.White else Color.PrimaryBlue,
                    borderColor = if (isSelected) null else Color.PrimaryBlue,
                    onClick = { onTimeSelected(timeOption) },
                )

                if (isSelected) {
                    ButtonWidget(
                        text = stringResource(Res.string.next),
                        modifier = Modifier.weight(0.5f),
                        onClick = { onNextClick(timeOption) }
                    )
                }
            }
        }
    }
}
