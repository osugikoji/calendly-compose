package com.calendly.compose.screen.scheduleday

import com.calendly.compose.utils.CalendarUtils

internal data class ScheduleDayUiState(
    val profileImageUrl: String = "",
    val userName: String = "",
    val currentMonth: Int = CalendarUtils.getCurrentMonth(),
    val currentYear: Int = CalendarUtils.getCurrentYear(),
    val availableDaysOfMonth: List<String> = emptyList(),
    val isLoading: Boolean = true,
)
