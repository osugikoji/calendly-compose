package com.calendly.compose.screen.confirmbooking

internal sealed class ConfirmBookingUiEvent {

    data object ScheduleEventSucceed : ConfirmBookingUiEvent()

    data object ShowGenericError : ConfirmBookingUiEvent()
}
