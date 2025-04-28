package com.calendly.compose.screen.scheduleday

import com.calendly.compose.data.model.User
import com.calendly.compose.data.repository.BookingRepository
import com.calendly.compose.utils.CalendarUtils.convertToUtcIsoString
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration

class ScheduleDayViewModelTest {

    private val bookingRepository: BookingRepository = mock()

    private val interviewerUserId = "1"

    @Test
    fun `on init when user and availabilities are loaded then uiState is populated correctly`() {
        // arrange
        val user = User(id = "1", fullName = "Koji", imageUrl = "test.com")
        val futureDate = getFutureDate()
        val availabilities = listOf(futureDate.convertToUtcIsoString())
        everySuspend { bookingRepository.readUser(any()) } returns user
        everySuspend { bookingRepository.getAvailabilities(any()) } returns Result.success(availabilities)

        // act
        val viewModel = buildViewModel()
        val uiState = viewModel.uiState.value

        // assert
        assertEquals("Koji", uiState.userName)
        assertEquals("test.com", uiState.profileImageUrl)
        assertEquals(futureDate.month.number, uiState.currentMonth)
        assertEquals(futureDate.year, uiState.currentYear)
        assertEquals(listOf(futureDate.dayOfMonth.toString()), uiState.availableDaysOfMonth)
    }

    @Test
    fun `when next month is clicked then current month updates and availabilities are fetched`() {
        // arrange
        val user = User(id = "1", fullName = "Koji", imageUrl = "test.com")
        val futureDate = getFutureDate()
        val availabilities = listOf(futureDate.convertToUtcIsoString())
        everySuspend { bookingRepository.readUser(any()) } returns user
        everySuspend { bookingRepository.getAvailabilities(any()) } returns Result.success(availabilities)

        // act
        val viewModel = buildViewModel()
        viewModel.onNextMonthClick()
        val uiState = viewModel.uiState.value

        // assert
        val expectedCurrentMonth = futureDate.month.number + 1
        assertEquals(expectedCurrentMonth, uiState.currentMonth)
        verifySuspend { bookingRepository.getAvailabilities(expectedCurrentMonth) }
    }

    @Test
    fun `when previous month is clicked then current month updates and availabilities are fetched`() {
        // arrange
        val user = User(id = "1", fullName = "Koji", imageUrl = "test.com")
        val futureDate = getFutureDate()
        val availabilities = listOf(futureDate.convertToUtcIsoString())
        everySuspend { bookingRepository.readUser(any()) } returns user
        everySuspend { bookingRepository.getAvailabilities(any()) } returns Result.success(availabilities)

        // act
        val viewModel = buildViewModel()
        viewModel.onPreviousMonthClick()
        val uiState = viewModel.uiState.value

        // assert
        val expectedCurrentMonth = futureDate.month.number - 1
        assertEquals(expectedCurrentMonth, uiState.currentMonth)
        verifySuspend { bookingRepository.getAvailabilities(expectedCurrentMonth) }
    }

    @Test
    fun `when getting availabilities by day then correct number of slots is returned`() {
        // arrange
        val user = User(id = "1", fullName = "Koji", imageUrl = "test.com")
        val futureDate = getFutureDate()
        val availabilities = listOf(futureDate, futureDate).map { it.convertToUtcIsoString() }
        everySuspend { bookingRepository.readUser(any()) } returns user
        everySuspend { bookingRepository.getAvailabilities(any()) } returns Result.success(availabilities)

        // act
        val viewModel = buildViewModel()
        val result = viewModel.getAvailabilitiesByDayOfMonth(day = futureDate.dayOfMonth.toString())

        // assert
        assertEquals(2, result.size)
    }

    private fun getFutureDate(): LocalDateTime {
        return Clock
            .System.now()
            .plus(Duration.parse("10s"))
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    private fun buildViewModel(): ScheduleDayViewModel {
        return ScheduleDayViewModel(
            interviewerUserId = interviewerUserId,
            dispatcher = Dispatchers.Unconfined,
            bookingRepository = bookingRepository,
        )
    }
}
