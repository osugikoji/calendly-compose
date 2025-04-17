package com.calendly.compose.data.network.api

import com.calendly.compose.data.network.ApiManager
import com.calendly.compose.data.network.dto.AvailabilitiesDto
import com.calendly.compose.data.network.dto.ScheduleEventDto
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.coroutines.delay
import kotlinx.datetime.Month

internal class BookingApi(
    private val apiManager: ApiManager = ApiManager(),
) {

    suspend fun getAvailabilities(month: Int): Result<AvailabilitiesDto> {
        val mockResponseName = when (Month(month)) {
            Month.APRIL -> "Apr2025"
            Month.MAY -> "May2025"
            else -> "Default"
        }
        return apiManager.request {
            method = HttpMethod.Get
            url("appointment_availabilities/available_times")
            parameter(key = "start_date_time", value = "2025-05-01T18:30:00")
            parameter(key = "end_date_time", value = "2025-05-30T12:29:59")
            header(key = "x-mock-response-name", value = mockResponseName)
        }
    }

    suspend fun scheduleEvent(requestBody: ScheduleEventDto): Result<Unit> {
        delay(2000) // Simulate API delay
        return apiManager.request {
            method = HttpMethod.Post
            setBody(requestBody)
            url("schedule_event")
        }
    }
}
