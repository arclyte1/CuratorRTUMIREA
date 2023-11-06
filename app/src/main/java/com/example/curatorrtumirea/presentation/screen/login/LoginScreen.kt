package com.example.curatorrtumirea.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.conditional
import com.example.curatorrtumirea.common.drawAnimatedBorder
import com.example.curatorrtumirea.presentation.shared.textfield.BaseTextField
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme

@Composable
fun LoginScreen(
    screenState: LoginScreenState,
    onEvent: (LoginUIEvent) -> Unit,
) {
    val animationColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.signing_in),
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            BaseTextField(
                value = screenState.email,
                onValueChange = { onEvent(LoginUIEvent.OnEmailChanged(it)) },
                label = stringResource(id = R.string.prompt_email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                readOnly = screenState.isEmailReadOnly
            )
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            Button(
                onClick = { onEvent(LoginUIEvent.SignIn) },
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

@Preview
@Composable
fun LoginScreenPreview() {
    val screenState = LoginScreenState()
    CuratorRTUMIREATheme {
        Surface {
            LoginScreen(
                screenState = screenState,
                onEvent = {},
            )
        }
    }
}
