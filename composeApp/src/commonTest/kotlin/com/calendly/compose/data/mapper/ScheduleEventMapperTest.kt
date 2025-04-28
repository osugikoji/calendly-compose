package com.calendly.compose.data.mapper

import com.calendly.compose.data.model.ScheduleEvent
import com.calendly.compose.utils.CalendarUtils
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ScheduleEventMapperTest {

    private val timeZone = TimeZone.of(zoneId = "UTC")

    @BeforeTest
    fun setup() {
        CalendarUtils.setTimeZone(timeZone = timeZone)
    }

    @AfterTest
    fun tearDown() {
        CalendarUtils.resetTestTimeZone()
    }

    @Test
    fun `when converting ScheduleEvent to ScheduleEventDto then returns correctly mapped DTO`() {
        // arrange
        val selectedDateTime = LocalDateTime(
            year = 2025,
            monthNumber = 4,
            dayOfMonth = 28,
            hour = 10,
            minute = 0
        )
        val scheduleEvent = ScheduleEvent(
            interviewerUserId = "2",
            selectedDateTime = selectedDateTime,
            name = "Koji",
            email = "koji@email.com",
        )

        // act
        val result = scheduleEvent.toScheduleEventDto()

        // assert
        assertEquals("2", result.interviewerUserId)
        assertEquals("2025-04-28T10:00:00Z", result.selectedDateTime)
        assertEquals("Koji", result.name)
        assertEquals("koji@email.com", result.email)
    }
}
