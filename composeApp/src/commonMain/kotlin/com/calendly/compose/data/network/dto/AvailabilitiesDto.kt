package com.calendly.compose.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AvailabilitiesDto(
    @SerialName("available_times") val availableTimes: List<String>,
)
