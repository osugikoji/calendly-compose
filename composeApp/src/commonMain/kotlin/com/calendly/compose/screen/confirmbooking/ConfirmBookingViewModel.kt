package com.calendly.compose.screen.confirmbooking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calendly.compose.data.model.ScheduleEvent
import com.calendly.compose.data.repository.BookingRepository
import com.calendly.compose.utils.CalendarUtils
import com.calendly.compose.utils.CalendarUtils.formatInterviewSlot
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

internal class ConfirmBookingViewModel(
    private val interviewerUserId: String,
    private val selectedDateTime: LocalDateTime,
    private val bookingRepository: BookingRepository = BookingRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfirmBookingUiState())
    val uiState: StateFlow<ConfirmBookingUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ConfirmBookingUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        updateUiState()
    }

    fun scheduleEvent() = viewModelScope.launch {
        val event = ScheduleEvent(
            interviewerUserId = interviewerUserId,
            selectedDateTime = selectedDateTime,
            name = uiState.value.name,
            email = uiState.value.email,
        )
        _uiState.update { it.copy(isLoading = true) }
        bookingRepository.scheduleEvent(event)
            .onSuccess { _uiEvent.emit(ConfirmBookingUiEvent.ScheduleEventSucceed) }
            .onFailure { _uiEvent.emit(ConfirmBookingUiEvent.ShowGenericError) }
        _uiState.update { it.copy(isLoading = false) }
    }

    fun onNameInputChange(input: String) {
        _uiState.update { it.copy(name = input) }
        updateButtonState()
    }

    fun onEmailInputChange(input: String) {
        _uiState.update { it.copy(email = input) }
        updateButtonState()
    }

    private fun updateUiState() {
        _uiState.update {
            it.copy(
                bookingDateTime = selectedDateTime.formatInterviewSlot(),
                timeZoneDisplay = CalendarUtils.currentTimeZone.id,
            )
        }
    }

    private fun updateButtonState() {
        val buttonEnabled = uiState.value.email.isNotBlank() && uiState.value.name.isNotBlank()
        _uiState.update { it.copy(buttonEnabled = buttonEnabled) }
    }
}
