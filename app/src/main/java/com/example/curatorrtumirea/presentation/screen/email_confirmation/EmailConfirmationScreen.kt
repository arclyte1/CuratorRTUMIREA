package com.example.curatorrtumirea.presentation.screen.email_confirmation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.isImeVisibleAsState
import com.example.curatorrtumirea.presentation.screen.email_confirmation.components.ConfirmationCodeTextField
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailConfirmationScreen(
    screenState: EmailConfirmationScreenState,
    effect: SharedFlow<EmailConfirmationEffect>,
    onEvent: (EmailConfirmationEvent) -> Unit,
) {
    val isImeVisible by isImeVisibleAsState()

    if (screenState.isLoading) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.email_confirmation)) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(EmailConfirmationEvent.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConfirmationCodeTextField(
                value = screenState.code,
                onValueChange = { code ->
                    onEvent(EmailConfirmationEvent.OnCodeChanged(code))
                },
                fontSize = 40.sp,
                charCount = EmailConfirmationViewModel.CONFIRMATION_CODE_LENGTH
            )
            if (!(isImeVisible && LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)) {
                Text(
                    text = stringResource(id = R.string.confirmation_email_sent),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    textAlign = TextAlign.Center
                )
                OutlinedButton(
                    onClick = { onEvent(EmailConfirmationEvent.ResendEmail) },
                    border = BorderStroke(0.dp, color = Color.Transparent),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    val cooldownText = if (screenState.resendEmailCooldown > 0) " (${screenState.resendEmailCooldown})" else ""
                    Text(text = stringResource(id = R.string.resend_email) + cooldownText)
                }
            }
        }
    }
}

@Composable
@Preview
fun EmailConfirmationScreenPreview() {
    var screenState by remember { mutableStateOf(EmailConfirmationScreenState()) }
    CuratorRTUMIREATheme {
        EmailConfirmationScreen(
            screenState = screenState,
            effect = MutableSharedFlow(),
            onEvent = { event ->
                when(event) {
                    is EmailConfirmationEvent.OnCodeChanged -> {
                        screenState = screenState.copy(
                            code = event.code.filter { it.isDigit() }
                        )
                    }
                    else -> { }
                }
            }
        )
    }
}