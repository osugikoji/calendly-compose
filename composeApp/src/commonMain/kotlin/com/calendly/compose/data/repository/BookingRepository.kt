package com.calendly.compose.data.repository

import com.calendly.compose.data.model.ScheduleEvent
import com.calendly.compose.data.model.User

internal interface BookingRepository {

    suspend fun getAvailabilities(month: Int): Result<List<String>>

    suspend fun scheduleEvent(scheduleEvent: ScheduleEvent): Result<Unit>

    suspend fun readUser(userId: String): User?
}
