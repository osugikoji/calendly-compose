package com.calendly.compose.screen.scheduletime

import com.calendly.compose.utils.CalendarUtils
import com.calendly.compose.utils.CalendarUtils.parseToLocalDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ScheduleTimeViewModelTest {

    private val timeZone = TimeZone.of(zoneId = "America/New_York")

    @BeforeTest
    fun setup() {
        CalendarUtils.setTimeZone(timeZone = timeZone)
    }

    @AfterTest
    fun tearDown() {
        CalendarUtils.resetTestTimeZone()
    }

    @Test
    fun `on init when time availabilities are loaded then ui state is populated correctly`() {
        val timeAvailabilities = listOf(
            "2025-04-09T13:00:00Z",
            "2025-04-09T16:00:00Z",
        ).map { it.parseToLocalDateTime() }

        // act
        val viewModel = buildViewModel(timeAvailabilities = timeAvailabilities)
        val uiState = viewModel.uiState.value

        // assert
        assertEquals("Wednesday", uiState.toolbarTitle)
        assertEquals("April, 09, 2025", uiState.toolbarSubtitle)
        assertEquals(timeZone.id, uiState.timeZone.id)
        assertEquals(timeAvailabilities, uiState.timeOptions)
        assertEquals(null, uiState.timeOptionSelected)

    }

    @Test
    fun `when a time is selected then selected time is updated in ui state`() {
        val timeAvailabilities = listOf(
            "2025-04-09T13:00:00Z",
            "2025-04-09T16:00:00Z",
        ).map { it.parseToLocalDateTime() }
        val selectedTime = "2025-04-09T16:00:00Z".parseToLocalDateTime()

        // act
        val viewModel = buildViewModel(timeAvailabilities = timeAvailabilities)
        viewModel.onTimeSelected(selectedTime)
        val uiState = viewModel.uiState.value

        // assert
        assertEquals(selectedTime, uiState.timeOptionSelected)

    }

    private fun buildViewModel(
        timeAvailabilities: List<LocalDateTime> = emptyList(),
    ): ScheduleTimeViewModel {
        return ScheduleTimeViewModel(
            timeAvailabilities = timeAvailabilities,
        )
    }
}
