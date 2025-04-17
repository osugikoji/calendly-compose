package com.calendly.compose.screen.scheduletime

import com.calendly.compose.utils.CalendarUtils
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

internal data class ScheduleTimeUiState(
    val toolbarTitle: String = "",
    val toolbarSubtitle: String = "",
    val timeZone: TimeZone = CalendarUtils.currentTimeZone,
    val timeOptionSelected: LocalDateTime? = null,
    val timeOptions: List<LocalDateTime> = emptyList(),
)
