package com.calendly.compose.data.repository

import com.calendly.compose.data.database.MockUserDao
import com.calendly.compose.data.database.UserDao
import com.calendly.compose.data.mapper.toScheduleEventDto
import com.calendly.compose.data.mapper.toUser
import com.calendly.compose.data.model.ScheduleEvent
import com.calendly.compose.data.model.User
import com.calendly.compose.data.network.api.BookingApi

internal class BookingRepository(
    private val bookingApi: BookingApi = BookingApi(),
    private val userDao: UserDao = MockUserDao(),
) {

    suspend fun getAvailabilities(month: Int): Result<List<String>> {
        return bookingApi.getAvailabilities(month).map { it.availableTimes }
    }

    suspend fun scheduleEvent(scheduleEvent: ScheduleEvent): Result<Unit> {
        val requestBody = scheduleEvent.toScheduleEventDto()
        return bookingApi.scheduleEvent(requestBody)
    }

    suspend fun readUser(userId: String): User? {
        return userDao.readUser(id = userId)?.toUser()
    }
}
