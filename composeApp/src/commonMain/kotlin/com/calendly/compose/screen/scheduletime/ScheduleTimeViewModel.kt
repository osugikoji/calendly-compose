package com.calendly.compose.screen.scheduletime

import androidx.lifecycle.ViewModel
import com.calendly.compose.utils.CalendarUtils.formatAsFullMonthDateYear
import com.calendly.compose.utils.CalendarUtils.getDayOfWeekName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime

internal class ScheduleTimeViewModel(
    private val timeAvailabilities: List<LocalDateTime>
): ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleTimeUiState())
    val uiState: StateFlow<ScheduleTimeUiState> = _uiState.asStateFlow()

    init {
        updateUiState()
    }

    fun onTimeSelected(time: LocalDateTime) {
        _uiState.update { it.copy(timeOptionSelected = time) }
    }

    private fun updateUiState() {
        val firstDateTime = timeAvailabilities.first()
        _uiState.update {
            it.copy(
                toolbarTitle = firstDateTime.getDayOfWeekName(),
                toolbarSubtitle = firstDateTime.formatAsFullMonthDateYear(),
                timeOptions = timeAvailabilities,
            )
        }
    }
}
