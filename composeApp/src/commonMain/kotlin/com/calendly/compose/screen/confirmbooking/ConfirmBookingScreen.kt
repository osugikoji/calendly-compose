package com.calendly.compose.screen.confirmbooking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import calendly_compose.composeapp.generated.resources.Res
import calendly_compose.composeapp.generated.resources.compose_multiplatform
import calendly_compose.composeapp.generated.resources.conference_details
import calendly_compose.composeapp.generated.resources.enter_details
import calendly_compose.composeapp.generated.resources.generic_error
import calendly_compose.composeapp.generated.resources.input_label_email
import calendly_compose.composeapp.generated.resources.input_label_name
import calendly_compose.composeapp.generated.resources.schedule_event
import calendly_compose.composeapp.generated.resources.schedule_event_success_message
import calendly_compose.composeapp.generated.resources.terms_and_privacy_part_four
import calendly_compose.composeapp.generated.resources.terms_and_privacy_part_one
import calendly_compose.composeapp.generated.resources.terms_and_privacy_part_three
import calendly_compose.composeapp.generated.resources.terms_and_privacy_part_two
import calendly_compose.composeapp.generated.resources.time_minute
import calendly_compose.composeapp.generated.resources.time_minute_interview
import com.calendly.compose.ui.icons.Calendar
import com.calendly.compose.ui.icons.CameraVideo
import com.calendly.compose.ui.icons.Clock
import com.calendly.compose.ui.icons.Globe
import com.calendly.compose.ui.theme.MediumGray
import com.calendly.compose.ui.theme.NavyBlue
import com.calendly.compose.ui.theme.PrimaryBlue
import com.calendly.compose.ui.theme.PrimaryDarkBlue
import com.calendly.compose.ui.theme.SoftBlue
import com.calendly.compose.ui.widget.backbutton.BackButtonWidget
import com.calendly.compose.ui.widget.button.ButtonWidget
import com.calendly.compose.ui.widget.inforow.InfoRowWidget
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ConfirmBookingScreen(
    interviewerUserId: String,
    selectedDateTime: LocalDateTime,
    viewModel: ConfirmBookingViewModel = viewModel {
        ConfirmBookingViewModel(interviewerUserId, selectedDateTime)
    },
    onBackClick: () -> Unit = {},
    onSuccess: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is ConfirmBookingUiEvent.ShowGenericError -> {
                    val message = getString(Res.string.generic_error)
                    scaffoldState.snackbarHostState.showSnackbar(message)
                }

                ConfirmBookingUiEvent.ScheduleEventSucceed -> {
                    val message = getString(Res.string.schedule_event_success_message)
                    scaffoldState.snackbarHostState.showSnackbar(message)
                    onSuccess()
                }
            }
        }
    }
    ConfirmBookingScreen(
        scaffoldState = scaffoldState,
        uiState = uiState,
        onBackClick = onBackClick,
        onNameInputChange = { viewModel.onNameInputChange(it) },
        onEmailInputChange = { viewModel.onEmailInputChange(it) },
        onScheduleEventClick = { viewModel.scheduleEvent() }
    )
}

@Composable
internal fun ConfirmBookingScreen(
    scaffoldState: ScaffoldState,
    uiState: ConfirmBookingUiState,
    onBackClick: () -> Unit = {},
    onNameInputChange: (String) -> Unit = {},
    onEmailInputChange: (String) -> Unit = {},
    onScheduleEventClick: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Top Bar Region
                TopBarWidget(onBackClick = onBackClick)
                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp))

                // Interview Duration Region
                Text(
                    text = stringResource(Res.string.time_minute_interview),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    color = Color.NavyBlue,
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Info Rows Region
                InfoRowWidget(
                    icon = Icons.Clock,
                    text = stringResource(Res.string.time_minute),
                )
                InfoRowWidget(
                    icon = Icons.CameraVideo,
                    text = stringResource(Res.string.conference_details),
                )
                InfoRowWidget(
                    icon = Icons.Calendar,
                    text = uiState.bookingDateTime,
                )
                InfoRowWidget(
                    icon = Icons.Globe,
                    text = uiState.timeZoneDisplay,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp))

                // Input Fields Region
                Text(
                    text = stringResource(Res.string.enter_details),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Color.NavyBlue,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextInputWidget(
                    labelText = stringResource(Res.string.input_label_name),
                    value = uiState.name,
                    enabled = !uiState.isLoading,
                    onValueChange = onNameInputChange,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextInputWidget(
                    labelText = stringResource(Res.string.input_label_email),
                    value = uiState.email,
                    enabled = !uiState.isLoading,
                    onValueChange = onEmailInputChange,
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Spannable Text Region
                SpannableTermsAndPrivacyTextWidget()
                Spacer(modifier = Modifier.height(24.dp))

                // Confirm Button Region
                ButtonWidget(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.schedule_event),
                    cornerRadius = 24,
                    enabled = uiState.buttonEnabled,
                    isLoading = uiState.isLoading,
                    onClick = onScheduleEventClick,
                )
            }
        }
    )
}

@Composable
private fun TextInputWidget(
    labelText: String,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = labelText, style = MaterialTheme.typography.subtitle2, color = Color.NavyBlue)
        Spacer(modifier = Modifier.height(8.dp))
    }
    OutlinedTextField(
        value = value,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.PrimaryBlue,
            unfocusedBorderColor = Color.SoftBlue,
            cursorColor = Color.Black,
        ),
    )
}

@Composable
private fun TopBarWidget(onBackClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BackButtonWidget(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart))

        Icon(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(48.dp).align(Alignment.Center)
        )
    }
}

@Composable
private fun SpannableTermsAndPrivacyTextWidget() {
    val notClickableSpanStyle = SpanStyle(color = Color.MediumGray, fontSize = 12.sp)
    val clickableSpanStyle =
        SpanStyle(color = Color.PrimaryDarkBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    val annotatedString = buildAnnotatedString {
        withStyle(style = notClickableSpanStyle) {
            append(stringResource(Res.string.terms_and_privacy_part_one))
        }
        withLink(
            link = LinkAnnotation.Url(
                url = "https://calendly.com/legal/customer-terms-conditions",
                styles = TextLinkStyles(style = clickableSpanStyle),
            ),
            block = { append(stringResource(Res.string.terms_and_privacy_part_two)) },
        )
        withStyle(
            style = notClickableSpanStyle,
            block = { append(stringResource(Res.string.terms_and_privacy_part_three)) }
        )
        withLink(
            link = LinkAnnotation.Url(
                url = "https://calendly.com/legal/privacy-notice",
                styles = TextLinkStyles(style = clickableSpanStyle),
            ),
            block = { append(stringResource(Res.string.terms_and_privacy_part_four)) },
        )
    }
    Text(text = annotatedString)
}
