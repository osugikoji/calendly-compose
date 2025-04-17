package com.calendly.compose.data.mapper

import com.calendly.compose.data.model.ScheduleEvent
import com.calendly.compose.data.network.dto.ScheduleEventDto
import com.calendly.compose.utils.CalendarUtils.convertToUtcIsoString

internal fun ScheduleEvent.toScheduleEventDto(): ScheduleEventDto {
    return ScheduleEventDto(
        interviewerUserId = this.interviewerUserId,
        selectedDateTime = this.selectedDateTime.convertToUtcIsoString(),
        name = this.name,
        email = this.email,
    )
}
