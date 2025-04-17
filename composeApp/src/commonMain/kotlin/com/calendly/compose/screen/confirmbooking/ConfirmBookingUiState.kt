package com.calendly.compose.screen.confirmbooking

internal data class ConfirmBookingUiState(
    val bookingDateTime: String = "",
    val timeZoneDisplay: String = "",
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
)
