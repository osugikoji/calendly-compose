package com.calendly.compose.utils

import com.calendly.compose.utils.CalendarUtils.convertToUtcIsoString
import com.calendly.compose.utils.CalendarUtils.filterFutureDates
import com.calendly.compose.utils.CalendarUtils.formatAsFullMonthDateYear
import com.calendly.compose.utils.CalendarUtils.formatAsHourMinute
import com.calendly.compose.utils.CalendarUtils.formatInterviewSlot
import com.calendly.compose.utils.CalendarUtils.getDayOfWeekName
import com.calendly.compose.utils.CalendarUtils.isToday
import com.calendly.compose.utils.CalendarUtils.parseToLocalDateTime
import com.calendly.compose.utils.CalendarUtils.plusMinutes
import com.calendly.compose.utils.CalendarUtils.toTimeFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CalendarUtilsTest {

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
    fun `when getting days of week then correct short names are returned`() {
        // act
        val result = CalendarUtils.daysOfWeekNames()

        // assert
        assertEquals(listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"), result)
    }

    @Test
    fun `when getting month name for number then correct month name is returned`() {
        // act
        val result = CalendarUtils.getMonthName(monthNumber = 1)

        // assert
        assertEquals("January", result)
    }

    @Test
    fun `when getting calendar days for given month then correct days with padding are returned`() {
        // act
        val result = CalendarUtils.getCalendarDays(year = 2025, month = 2)

        // assert
        val expectedResult = listOf(
            "", "", "", "", "", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28"
        )
        assertEquals(expectedResult, result)
    }

    @Test
    fun `when getting current year then returns correct year based on timezone`() {
        // arrange
        val fakeClock = getFixedClock(time = "2025-04-28T10:15:00Z")
        val fixedTimeZone = TimeZone.of("UTC")
        val expectedYear = 2025

        // act
        val result = CalendarUtils.getCurrentYear(timeZone = fixedTimeZone, clock = fakeClock)

        // assert
        assertEquals(expectedYear, result)
    }

    @Test
    fun `when getting current month then returns correct month based on timezone`() {
        // arrange
        val fakeClock = getFixedClock(time = "2025-04-28T10:15:00Z")
        val fixedTimeZone = TimeZone.of("UTC")
        val expectedMonthNumber = 4

        // act
        val result = CalendarUtils.getCurrentMonth(timeZone = fixedTimeZone, clock = fakeClock)

        // assert
        assertEquals(expectedMonthNumber, result)
    }

    @Test
    fun `when getting timezone display then returns timezone id with formatted time`() {
        // arrange
        val fakeClock = getFixedClock(time = "2025-04-28T10:15:00Z")
        val fixedTimeZone = TimeZone.of("UTC")
        val expectedDisplay = "UTC (10:15)"

        // act
        val result = CalendarUtils.getTimeZoneDisplay(timeZone = fixedTimeZone, clock = fakeClock)

        // assert
        assertEquals(expectedDisplay, result)
    }

    @Test
    fun `when parsing ISO string then returns correct LocalDateTime in given timezone`() {
        // arrange
        val isoString = "2025-04-28T10:00:00Z" // ISO 8601 format
        val timeZone = TimeZone.of("UTC")

        // act
        val result = isoString.parseToLocalDateTime(timeZone)

        // assert
        assertEquals(2025, result.year)
        assertEquals(4, result.monthNumber)
        assertEquals(28, result.dayOfMonth)
        assertEquals(10, result.hour)
        assertEquals(0, result.minute)
    }

    @Test
    fun `when filtering dates then returns only future dates`() {
        // arrange
        val fakeClock = getFixedClock(time = "2025-04-28T10:00:00Z")
        val timeZone = TimeZone.of("UTC")
        val now = fakeClock.now().toLocalDateTime(timeZone)
        val dates = listOf(
            "2025-04-27T10:00:00Z".parseToLocalDateTime(timeZone), // Past date
            "2025-04-28T10:00:00Z".parseToLocalDateTime(timeZone), // Exactly now (should NOT be included)
            "2025-04-28T11:00:00Z".parseToLocalDateTime(timeZone), // Future
            "2025-05-28T10:00:00Z".parseToLocalDateTime(timeZone), // Future
        )

        // act
        val result = dates.filterFutureDates(timeZone = timeZone, clock = fakeClock)

        // assert
        assertEquals(2, result.size)
        assertTrue(result.all { it > now })
    }

    @Test
    fun `when getting day of week name then returns capitalized name`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 10, minute = 0)

        // act
        val result = localDateTime.getDayOfWeekName()

        // assert
        assertEquals("Monday", result)
    }

    @Test
    fun `when formatting LocalDateTime then returns hour and minute formatted string`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 9, minute = 5)

        // act
        val result = localDateTime.formatAsHourMinute()

        // assert
        assertEquals("09:05", result)
    }

    @Test
    fun `when formatting LocalDateTime then returns full month name date and year`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 10, minute = 0)

        // act
        val result = localDateTime.formatAsFullMonthDateYear()

        // assert
        assertEquals("April, 28, 2025", result)
    }

    @Test
    fun `when adding minutes to LocalDateTime then returns new LocalDateTime with added minutes`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 10, minute = 0)
        val minutesToAdd = 15
        val timeZone = TimeZone.of("UTC")

        // act
        val result = localDateTime.plusMinutes(minutesToAdd, timeZone)

        // assert
        assertEquals(10, result.hour)  // Hour should remain the same
        assertEquals(15, result.minute) // 15 minutes added
    }

    @Test
    fun `when formatting interview slot then returns correctly formatted time range with date`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 10, minute = 0)

        // act
        val result = localDateTime.formatInterviewSlot()

        // assert
        assertEquals("10:00 - 10:30, Monday, April 28, 2025", result)
    }

    @Test
    fun `when converting LocalDateTime to UTC ISO string then returns correct ISO 8601 format`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 10, minute = 0)
        val timeZone = TimeZone.of("UTC")

        // act
        val result = localDateTime.convertToUtcIsoString(timeZone)

        // assert
        assertEquals("2025-04-28T10:00:00Z", result)
    }

    @Test
    fun `when checking if LocalDate is today then returns true for today's date`() {
        // arrange
        val today = LocalDate(year = 2025, monthNumber = 4, dayOfMonth = 28)
        val timeZone = TimeZone.of("UTC")

        // act
        val result = today.isToday(timeZone)

        // assert
        assertTrue(result) // Should return true since it is today's date
    }

    @Test
    fun `when converting LocalDateTime to time format then returns correct HH mm format`() {
        // arrange
        val localDateTime =
            LocalDateTime(year = 2025, monthNumber = 4, dayOfMonth = 28, hour = 10, minute = 30)

        // act
        val result = localDateTime.toTimeFormat()

        // assert
        assertEquals("10:30", result)
    }

    private fun getFixedClock(time: String) = object : Clock {
        override fun now(): Instant = Instant.parse(time)
    }
}
