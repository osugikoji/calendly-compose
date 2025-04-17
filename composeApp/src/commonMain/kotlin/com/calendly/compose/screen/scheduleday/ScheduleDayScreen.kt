package com.calendly.compose.screen.scheduleday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import calendly_compose.composeapp.generated.resources.Res
import calendly_compose.composeapp.generated.resources.compose_multiplatform
import calendly_compose.composeapp.generated.resources.conference_details
import calendly_compose.composeapp.generated.resources.select_day
import calendly_compose.composeapp.generated.resources.time_minute
import calendly_compose.composeapp.generated.resources.time_minute_interview
import com.calendly.compose.ui.icons.CameraVideo
import com.calendly.compose.ui.icons.Clock
import com.calendly.compose.ui.theme.MediumGray
import com.calendly.compose.ui.theme.NavyBlue
import com.calendly.compose.ui.widget.calendar.CalendarWidget
import com.calendly.compose.ui.widget.circularimage.CircularImageWidget
import com.calendly.compose.ui.widget.inforow.InfoRowWidget
import com.calendly.compose.ui.widget.timezone.TimeZoneWidget
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ScheduleDayScreen(
    interviewerUserId: String,
    viewModel: ScheduleDayViewModel = viewModel { ScheduleDayViewModel(interviewerUserId) },
    selectedAvailabilitiesByDay: (List<LocalDateTime>) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    ScheduleDayScreen(
        uiState = uiState,
        onPreviousMonthClick = { viewModel.onPreviousMonthClick() },
        onNextMonthClick = { viewModel.onNextMonthClick() },
        selectedDayOfMonth = {
            selectedAvailabilitiesByDay(viewModel.getAvailabilitiesByDayOfMonth(it))
        },
    )
}

@Composable
internal fun ScheduleDayScreen(
    uiState: ScheduleDayUiState,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
    selectedDayOfMonth: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Top Bar Region
        TopBarWidget()
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Image, Name and Minutes Region
        CircularImageWidget(imageUrl = uiState.profileImageUrl)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = uiState.userName,
            color = Color.MediumGray,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(Res.string.time_minute_interview),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
            color = Color.NavyBlue,
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Info details
        InfoRowWidget(
            modifier = Modifier.padding(horizontal = 48.dp),
            icon = Icons.Clock,
            text = stringResource(Res.string.time_minute),
        )
        InfoRowWidget(
            modifier = Modifier.padding(horizontal = 48.dp),
            icon = Icons.CameraVideo,
            text = stringResource(Res.string.conference_details),
        )
        Divider(modifier = Modifier.padding(vertical = 24.dp))

        // Select Day Region
        Text(
            text = stringResource(Res.string.select_day),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        CalendarWidget(
            availableDaysOfMonth = uiState.availableDaysOfMonth,
            isLoading = uiState.isLoading,
            year = uiState.currentYear,
            month = uiState.currentMonth,
            modifier = Modifier.padding(horizontal = 16.dp),
            onNextMonthClick = onNextMonthClick,
            onPreviousMonthClick = onPreviousMonthClick,
            onClickItem = { selectedDayOfMonth(it.dayOfMonth) },
        )
        TimeZoneWidget(modifier = Modifier.align(Alignment.Start).padding(16.dp))
    }
}

@Composable
private fun TopBarWidget() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))
        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.fillMaxWidth())
    }
}
