package com.calendly.compose.data.repository.impl

import com.calendly.compose.data.database.MockUserDao
import com.calendly.compose.data.database.UserDao
import com.calendly.compose.data.mapper.toScheduleEventDto
import com.calendly.compose.data.mapper.toUser
import com.calendly.compose.data.model.ScheduleEvent
import com.calendly.compose.data.model.User
import com.calendly.compose.data.network.api.BookingApi
import com.calendly.compose.data.repository.BookingRepository

internal class BookingRepositoryImpl(
    private val bookingApi: BookingApi = BookingApi(),
    private val userDao: UserDao = MockUserDao(),
) : BookingRepository {

    override suspend fun getAvailabilities(month: Int): Result<List<String>> {
        return bookingApi.getAvailabilities(month).map { it.availableTimes }
    }

    override suspend fun scheduleEvent(scheduleEvent: ScheduleEvent): Result<Unit> {
        val requestBody = scheduleEvent.toScheduleEventDto()
        return bookingApi.scheduleEvent(requestBody)
    }

    override suspend fun readUser(userId: String): User? {
        return userDao.readUser(id = userId)?.toUser()
    }
}
