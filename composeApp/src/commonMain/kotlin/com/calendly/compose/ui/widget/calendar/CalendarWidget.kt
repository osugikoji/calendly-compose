package com.calendly.compose.ui.widget.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calendly.compose.ui.theme.LightBlue
import com.calendly.compose.ui.theme.MediumGray
import com.calendly.compose.ui.theme.NavyBlue
import com.calendly.compose.ui.theme.PrimaryBlue
import com.calendly.compose.ui.theme.SemiTransparentDarkGray
import com.calendly.compose.ui.widget.loading.LoadingDotsWidget
import com.calendly.compose.utils.CalendarUtils
import com.calendly.compose.utils.CalendarUtils.isToday
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun CalendarWidget(
    availableDaysOfMonth: List<String>,
    year: Int = CalendarUtils.getCurrentYear(),
    month: Int = CalendarUtils.getCurrentMonth(),
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
    onClickItem: (CalendarWidgetUiState.Item) -> Unit = {},
) {
    val items = CalendarUtils.getCalendarDays(year = year, month = month).map {
        CalendarWidgetUiState.Item(
            dayOfMonth = it,
            isToday = if (it.isEmpty()) false else LocalDate(year, month, it.toInt()).isToday(),
            isSelected = availableDaysOfMonth.contains(it),
        )
    }
    val uiState =
        CalendarWidgetUiState(isLoading = isLoading, year = year, month = month, dayItems = items)
    CalendarWidget(
        uiState = uiState,
        modifier = modifier,
        onPreviousMonthClick = onPreviousMonthClick,
        onNextMonthClick = onNextMonthClick,
        onClickItem = onClickItem,
    )
}

@Composable
internal fun CalendarWidget(
    uiState: CalendarWidgetUiState,
    modifier: Modifier = Modifier,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
    onClickItem: (CalendarWidgetUiState.Item) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        CalendarHeaderWidget(
            headerText = "${CalendarUtils.getMonthName(uiState.month)} ${uiState.year}",
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick,
        )
        WeekDayHeaderWidget()
        CalendarContentWidget(
            isLoading = uiState.isLoading,
            items = uiState.dayItems,
            onClickItem = onClickItem,
        )
    }
}

@Composable
private fun CalendarHeaderWidget(
    headerText: String,
    onPreviousMonthClick: () -> Unit = {},
    onNextMonthClick: () -> Unit = {},
) {
    Row {
        IconButton(
            onClick = onPreviousMonthClick,
            content = {
                Icon(
                    tint = Color.SemiTransparentDarkGray,
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                )
            }
        )
        Text(
            text = headerText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            color = Color.NavyBlue,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = onNextMonthClick,
            content = {
                Icon(
                    tint = Color.SemiTransparentDarkGray,
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                )
            }
        )
    }
}

@Composable
private fun WeekDayHeaderWidget() {
    Row {
        val days = CalendarUtils.daysOfWeekNames().map { it.uppercase() }
        repeat(days.size) {
            val item = days[it]
            DayItemWidget(item, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun DayItemWidget(day: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = day,
            style = MaterialTheme.typography.body1,
            color = Color.NavyBlue,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
        )
    }
}

@Composable
private fun CalendarContentWidget(
    isLoading: Boolean,
    items: List<CalendarWidgetUiState.Item>,
    onClickItem: (CalendarWidgetUiState.Item) -> Unit = {},
) {
    Box {
        if (isLoading) {
            LoadingDotsWidget(modifier = Modifier.align(Alignment.Center))
        }
        Column {
            var index = 0
            repeat(6) {
                if (index >= items.size) return@repeat
                Row {
                    repeat(7) {
                        val item =
                            if (index < items.size) items[index] else CalendarWidgetUiState.Item()
                        ContentItemWidget(
                            isLoading = isLoading,
                            item = item,
                            onClickItem = onClickItem,
                            modifier = Modifier.weight(1f),
                        )
                        index++
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentItemWidget(
    isLoading: Boolean,
    item: CalendarWidgetUiState.Item,
    modifier: Modifier = Modifier,
    onClickItem: (CalendarWidgetUiState.Item) -> Unit = {},
) {
    val backgroundColor = if (item.isSelected) Color.LightBlue else Color.Transparent
    var textColor = if (item.isSelected) Color.PrimaryBlue else Color.SemiTransparentDarkGray
    val fontWeight = if (item.isSelected) FontWeight.ExtraBold else null
    if (isLoading) textColor = textColor.copy(alpha = 0.2F)
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(item.isSelected) { onClickItem(item) }
            .padding(1.dp)
            .background(color = backgroundColor)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.dayOfMonth,
                color = textColor,
                fontWeight = fontWeight,
                style = MaterialTheme.typography.body1,
            )
            if (item.isToday) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(color = Color.MediumGray, shape = CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        val selectedDay = listOf("3", "4", "5", "12", "14")
        val items = CalendarUtils.getCalendarDays(2025, 4)
            .map { CalendarWidgetUiState.Item(it, selectedDay.contains(it)) }
        val uiState = CalendarWidgetUiState(
            isLoading = false,
            year = 2025,
            month = 4,
            dayItems = items,
        )
        CalendarWidget(uiState = uiState, modifier = Modifier.background(color = Color.White))
    }
}
