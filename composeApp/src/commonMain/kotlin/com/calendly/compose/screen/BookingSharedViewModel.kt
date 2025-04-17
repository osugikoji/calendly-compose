package com.calendly.compose.screen

import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalDateTime

internal class BookingSharedViewModel(
    // Hardcoded for the purpose of the home assignment challenge.
    // In a real implementation, interviewerUserId would be passed from upstream (e.g., navigation args or DI).
    private val interviewerUserId: String = "2",
) : ViewModel() {

    private var selectedAvailabilities = listOf<LocalDateTime>()

    private lateinit var selectedDateTime: LocalDateTime

    fun setSelectedAvailabilities(availabilities: List<LocalDateTime>) {
        this.selectedAvailabilities = availabilities
    }

    fun getSelectedAvailabilities(): List<LocalDateTime> {
        return selectedAvailabilities
    }

    fun getInterviewerUserId(): String {
        return interviewerUserId
    }

    fun setSelectedDateTime(selectedDateTime: LocalDateTime) {
        this.selectedDateTime = selectedDateTime
    }

    fun getSelectedDateTime(): LocalDateTime {
        return this.selectedDateTime
    }
}
