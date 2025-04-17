package com.calendly.compose.screen.scheduleday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calendly.compose.data.repository.BookingRepository
import com.calendly.compose.utils.CalendarUtils
import com.calendly.compose.utils.CalendarUtils.filterFutureDates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

internal class ScheduleDayViewModel(
    private val interviewerUserId: String,
    private val bookingRepository: BookingRepository = BookingRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleDayUiState())
    val uiState: StateFlow<ScheduleDayUiState> = _uiState.asStateFlow()

    private var availabilities = listOf<LocalDateTime>()

    init {
        getData()
    }

    fun onNextMonthClick() = viewModelScope.launch {
        val currentMonth = uiState.value.currentMonth + 1
        _uiState.update { it.copy(currentMonth = currentMonth, availableDaysOfMonth = emptyList()) }
        fetchFutureAvailabilities()
    }

    fun onPreviousMonthClick() = viewModelScope.launch {
        val currentMonth = uiState.value.currentMonth - 1
        _uiState.update { it.copy(currentMonth = currentMonth, availableDaysOfMonth = emptyList()) }
        fetchFutureAvailabilities()
    }

    fun getAvailabilitiesByDayOfMonth(day: String): List<LocalDateTime> {
        return availabilities.filter { it.dayOfMonth == day.toInt() }
    }

    private fun getData() = viewModelScope.launch {
        fetchInterviewerUser()
        fetchFutureAvailabilities()
    }

    private suspend fun fetchInterviewerUser() {
        val interviewerUser = bookingRepository.readUser(userId = interviewerUserId)
        _uiState.update {
            it.copy(
                profileImageUrl = interviewerUser?.imageUrl.orEmpty(),
                userName = interviewerUser?.fullName.orEmpty(),
            )
        }
    }

    private suspend fun fetchFutureAvailabilities() {
        _uiState.update { it.copy(isLoading = true) }
        availabilities = bookingRepository
            .getAvailabilities(month = uiState.value.currentMonth)
            .getOrDefault(emptyList())
            .map { CalendarUtils.parseToLocalDateTime(it) }
            .filterFutureDates()
        val availableDaysOfMonth = availabilities.map { it.dayOfMonth.toString() }.distinct()
        _uiState.update { it.copy(isLoading = false, availableDaysOfMonth = availableDaysOfMonth) }
    }
}
