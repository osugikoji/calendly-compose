package com.calendly.compose

import androidx.compose.ui.window.ComposeUIViewController
import com.calendly.compose.screen.BookingScreen

fun MainViewController() = ComposeUIViewController { BookingScreen() }