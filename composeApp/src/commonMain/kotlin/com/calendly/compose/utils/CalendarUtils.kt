package com.calendly.compose.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

internal object CalendarUtils {

    val currentTimeZone get() = TimeZone.currentSystemDefault()

    fun parseToLocalDateTime(
        isoString: String,
        timeZone: TimeZone = currentTimeZone,
    ): LocalDateTime {
        val instant = Instant.parse(isoString)
        return instant.toLocalDateTime(timeZone)
    }

    /**
     * Returns a list of abbreviated English day-of-week names starting from Sunday.
     *
     * Example output: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"]
     *
     * @return A list of abbreviated day names in English, starting from Sunday.
     */
    fun daysOfWeekNames(): List<String> {
        return DayOfWeekNames.ENGLISH_ABBREVIATED.names
    }

    fun getMonthName(monthNumber: Int): String {
        return Month(monthNumber).name.lowercase().replaceFirstChar { it.uppercase() }
    }

    /**
     * Returns a list of strings representing the calendar days for a given month.
     * Empty strings ("") are used for padding at the beginning and end of the month
     * so the grid aligns correctly with a Sunday-start week.
     */
    fun getCalendarDays(year: Int, month: Int): List<String> {
        val firstDayOfMonth = LocalDate(year, month, 1)
        val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.ordinal) % 7
        val nextMonth = firstDayOfMonth.plus(DatePeriod(months = 1))
        val daysInMonth = firstDayOfMonth.daysUntil(nextMonth)
        val calendarDays = buildList {
            repeat(firstDayOfWeek) { add("") } // Padding for first week
            addAll((1..daysInMonth).map { it.toString() })
        }.toMutableList()

        return calendarDays
    }

    fun getCurrentYear(timeZone: TimeZone = currentTimeZone): Int {
        val now = Clock.System.now().toLocalDateTime(timeZone)
        return now.date.year
    }

    fun getCurrentMonth(timeZone: TimeZone = currentTimeZone): Int {
        val now = Clock.System.now().toLocalDateTime(timeZone)
        return now.date.monthNumber
    }

    fun getTimeZoneDisplay(timeZone: TimeZone = currentTimeZone): String {
        val nowInstant = Clock.System.now()
        val localDateTime = nowInstant.toLocalDateTime(timeZone)
        // Format the time as HH:mm
        val formattedTime = localDateTime.formatAsHourMinute()

        return "${timeZone.id} ($formattedTime)"
    }

    fun List<LocalDateTime>.filterFutureDates(
        timeZone: TimeZone = currentTimeZone,
    ): List<LocalDateTime> {
        val now = Clock.System.now().toLocalDateTime(timeZone)
        return this.filter { it > now }
    }

    fun LocalDateTime.getDayOfWeekName(): String {
        return this.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    }

    fun LocalDateTime.formatAsHourMinute(): String {
        val timeFormat = LocalDateTime.Format {
            hour()
            chars(":")
            minute()
        }
        return this.format(timeFormat)
    }

    fun LocalDateTime.formatAsFullMonthDateYear(): String {
        val customFormat = LocalDateTime.Format {
            monthName(MonthNames.ENGLISH_FULL)
            chars(", ")
            dayOfMonth()
            chars(", ")
            year()
        }
        return this.format(customFormat)
    }

    fun LocalDateTime.plusMinutes(
        minutesToAdd: Int,
        timeZone: TimeZone = currentTimeZone,
    ): LocalDateTime {
        val instant = this.toInstant(timeZone)
        val newInstant = instant.plus(minutesToAdd, DateTimeUnit.MINUTE)
        return newInstant.toLocalDateTime(timeZone)
    }

    fun LocalDateTime.formatInterviewSlot(): String {
        val start = this
        val end = this.plusMinutes(30)

        val dateFormat = LocalDate.Format {
            dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
            chars(", ")
            monthName(MonthNames.ENGLISH_FULL)
            chars(" ")
            dayOfMonth()
            chars(", ")
            year()
        }

        val startTime = start.formatAsHourMinute()
        val endTime = end.formatAsHourMinute()
        val datePart = start.date.format(dateFormat)

        return "$startTime - $endTime, $datePart"
    }

    fun LocalDateTime.convertToUtcIsoString(timeZone: TimeZone = currentTimeZone): String {
        val instant = this.toInstant(timeZone)
        return instant.toString() // Returns ISO 8601 format, e.g., "2025-02-01T13:00:00Z"
    }

    fun LocalDate.isToday(timeZone: TimeZone = currentTimeZone): Boolean {
        val now = Clock.System.now().toLocalDateTime(timeZone)
        return this.month == now.month && this.dayOfMonth == now.dayOfMonth
    }

    fun LocalDateTime.toTimeFormat(): String {
        val customFormat = LocalDateTime.Format {
            hour(); char(':'); minute()
        }
        return this.format(customFormat)
    }
}
