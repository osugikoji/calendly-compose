package com.calendly.compose.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class ScheduleEventDto(
    @SerialName("user_id") val interviewerUserId: String,
    @SerialName("selected_date_time") val selectedDateTime: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
)
