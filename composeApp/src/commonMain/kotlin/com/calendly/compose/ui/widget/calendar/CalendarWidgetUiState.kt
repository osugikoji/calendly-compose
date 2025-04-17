package com.calendly.compose.ui.widget.calendar

internal data class CalendarWidgetUiState(
    val year: Int,
    val month: Int,
    val isLoading: Boolean,
    val dayItems: List<Item>,
) {

    data class Item(
        val dayOfMonth: String = "",
        val isToday: Boolean = false,
        val isSelected: Boolean = false,
    )
}
