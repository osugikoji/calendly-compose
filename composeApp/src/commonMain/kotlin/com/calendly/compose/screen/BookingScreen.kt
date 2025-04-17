package com.calendly.compose.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.calendly.compose.screen.confirmbooking.ConfirmBookingScreen
import com.calendly.compose.screen.scheduleday.ScheduleDayScreen
import com.calendly.compose.screen.scheduletime.ScheduleTimeScreen

@Composable
fun BookingScreen(navController: NavHostController = rememberNavController()) {
    val sharedViewModel: BookingSharedViewModel = viewModel { BookingSharedViewModel() }
    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = BookingRoute.SCHEDULE_DAY.name,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(route = BookingRoute.SCHEDULE_DAY.name) {
                ScheduleDayScreen(
                    interviewerUserId = sharedViewModel.getInterviewerUserId(),
                    selectedAvailabilitiesByDay = {
                        sharedViewModel.setSelectedAvailabilities(it)
                        navController.navigate(BookingRoute.SCHEDULE_TIME.name)
                    },
                )
            }
            composable(route = BookingRoute.SCHEDULE_TIME.name) {
                ScheduleTimeScreen(
                    timeAvailabilities = sharedViewModel.getSelectedAvailabilities(),
                    onBackClick = { navController.popBackStack() },
                    onNextClick = {
                        sharedViewModel.setSelectedDateTime(it)
                        navController.navigate(BookingRoute.CONFIRM_BOOKING.name)
                    }
                )
            }
            composable(route = BookingRoute.CONFIRM_BOOKING.name) {
                ConfirmBookingScreen(
                    interviewerUserId = sharedViewModel.getInterviewerUserId(),
                    selectedDateTime = sharedViewModel.getSelectedDateTime(),
                    onBackClick = { navController.popBackStack() },
                    onSuccess = {
                        navController.navigate(BookingRoute.SCHEDULE_DAY.name) {
                            popUpTo(BookingRoute.SCHEDULE_DAY.name) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
