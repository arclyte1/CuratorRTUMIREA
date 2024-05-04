package com.example.curatorrtumirea.presentation.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.common.conditional
import com.example.curatorrtumirea.common.drawAnimatedBorder
import com.example.curatorrtumirea.common.isImeVisibleAsState
import com.example.curatorrtumirea.presentation.core.textfield.BaseTextField
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun LoginScreen(
    screenState: LoginScreenState,
    effect: SharedFlow<LoginEffect>,
    onEvent: (LoginEvent) -> Unit,
) {
    val bottomNavBarState = LocalBottomNavBarState.current
    val animationColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
    )
    val isImeVisible by isImeVisibleAsState()

    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(isVisible = false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
                if (!(isImeVisible && LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)) {
                    Text(
                        text = stringResource(id = R.string.signing_in),
                        fontSize = 32.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
                BaseTextField(
                    value = screenState.email,
                    onValueChange = { onEvent(LoginEvent.OnEmailChanged(it)) },
                    label = stringResource(id = R.string.prompt_email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                    readOnly = screenState.isEmailReadOnly,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    keyboardActions = KeyboardActions(onDone = {
                        if (screenState.isSignInButtonEnabled) onEvent(LoginEvent.SignIn)
                    })
                )
            }
            if (!(isImeVisible && LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)) {
                Column(verticalArrangement = Arrangement.Bottom) {
                    Button(
                        onClick = { onEvent(LoginEvent.SignIn) },
                        enabled = screenState.isSignInButtonEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .conditional(screenState.isSigningIn) {
                                drawAnimatedBorder(
                                    2.dp,
                                    CircleShape,
                                    { Brush.sweepGradient(animationColors) },
                                    2000
                                )
                            }
                    ) {
                        Text(
                            text = stringResource(id = R.string.action_sign_in)
                        )
                    }
                }
            }
        }
        if (screenState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    val screenState = LoginScreenState()
    CuratorRTUMIREATheme {
        Surface {
            LoginScreen(
                screenState = screenState,
                effect = MutableSharedFlow(),
                onEvent = {},
            )
        }
    }
}
