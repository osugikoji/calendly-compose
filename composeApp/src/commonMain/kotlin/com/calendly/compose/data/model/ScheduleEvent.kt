package com.calendly.compose.data.model

import kotlinx.datetime.LocalDateTime

internal data class ScheduleEvent(
    val interviewerUserId: String,
    val selectedDateTime: LocalDateTime,
    val name: String,
    val email: String,
)
