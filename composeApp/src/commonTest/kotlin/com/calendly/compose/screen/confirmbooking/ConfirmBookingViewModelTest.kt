package com.calendly.compose.screen.confirmbooking

import com.calendly.compose.data.repository.BookingRepository
import com.calendly.compose.utils.CalendarUtils
import com.calendly.compose.utils.CalendarUtils.parseToLocalDateTime
import dev.mokkery.mock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmBookingViewModelTest {

    private val bookingRepository: BookingRepository = mock()

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
    fun `on init then initial UI state is set correctly`() {
        // arrange
        val interviewerUserId = "2"
        val selectedDateTime = "2025-05-09T13:00:00Z".parseToLocalDateTime()

        // act
        val viewModel = buildViewModel(
            interviewerUserId = interviewerUserId,
            selectedDateTime = selectedDateTime,
        )
        val uiState = viewModel.uiState.value

        // assert
        assertEquals("", uiState.name)
        assertEquals("", uiState.email)
        assertEquals("09:00 - 09:30, Friday, May 09, 2025", uiState.bookingDateTime)
        assertEquals(false, uiState.isLoading)
        assertEquals(false, uiState.buttonEnabled)
        assertEquals(timeZone.id, uiState.timeZoneDisplay)
    }

    @Test
    fun `when name input changes then name is updated and button remains disabled`() {
        // arrange
        val interviewerUserId = "2"
        val selectedDateTime = "2025-05-09T13:00:00Z".parseToLocalDateTime()

        // act
        val viewModel = buildViewModel(
            interviewerUserId = interviewerUserId,
            selectedDateTime = selectedDateTime,
        )
        viewModel.onNameInputChange(input = "Koji")
        val uiState = viewModel.uiState.value

        // assert
        assertEquals("Koji", uiState.name)
        assertEquals(false, uiState.buttonEnabled)
    }

    @Test
    fun `when email input changes then email is updated and button remains disabled`() {
        // arrange
        val interviewerUserId = "2"
        val selectedDateTime = "2025-05-09T13:00:00Z".parseToLocalDateTime()

        // act
        val viewModel = buildViewModel(
            interviewerUserId = interviewerUserId,
            selectedDateTime = selectedDateTime,
        )
        viewModel.onEmailInputChange(input = "koji@email.com")
        val uiState = viewModel.uiState.value

        // assert
        assertEquals("koji@email.com", uiState.email)
        assertEquals(false, uiState.buttonEnabled)
    }

    @Test
    fun `when name and email are filled THEN button is enabled`() {
        // arrange
        val interviewerUserId = "2"
        val selectedDateTime = "2025-05-09T13:00:00Z".parseToLocalDateTime()

        // act
        val viewModel = buildViewModel(
            interviewerUserId = interviewerUserId,
            selectedDateTime = selectedDateTime,
        )
        viewModel.onNameInputChange(input = "Koji")
        viewModel.onEmailInputChange(input = "koji@email.com")
        val uiState = viewModel.uiState.value

        // assert
        assertEquals(true, uiState.buttonEnabled)
    }

    private fun buildViewModel(
        interviewerUserId: String = "",
        selectedDateTime: LocalDateTime = "2025-04-09T13:00:00Z".parseToLocalDateTime(),
    ): ConfirmBookingViewModel {
        return ConfirmBookingViewModel(
            interviewerUserId = interviewerUserId,
            selectedDateTime = selectedDateTime,
            bookingRepository = bookingRepository,
        )
    }
}
