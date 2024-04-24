package com.example.curatorrtumirea.presentation.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    sendEvent: (ProfileEvent) -> Unit,
    effects: SharedFlow<ProfileEffect>
) {
    var editUsernameDialogShown by remember { mutableStateOf(false) }

    Surface {
        Column(modifier = Modifier.fillMaxWidth()) {
            state.email?.let { email ->
                Text(
                    email,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 32.dp)
                )
            }
            if (state.username.isNullOrBlank()) {
                TextButton(
                    onClick = { editUsernameDialogShown = true },
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 16.dp)
                ) {
                    Text(stringResource(id = R.string.set_username), fontSize = 20.sp)
                }
            } else {
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 8.dp)
                ) {
                    Text(state.username, fontSize = 20.sp, modifier = Modifier.alpha(0.6f))
                    IconButton(
                        onClick = { editUsernameDialogShown = true },
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                    }
                }
            }
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp), thickness = 1.dp)
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { sendEvent(ProfileEvent.OnRequestsClicked) }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Text(
                    stringResource(id = R.string.requests),
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                state.requestsCount?.let {
                    Text(
                        it.toString(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .alpha(0.6f)
                            .padding(start = 4.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "arrow",
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .padding(start = 4.dp)
                )
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { sendEvent(ProfileEvent.OnLogoutClicked) }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Text(
                    stringResource(id = R.string.logout),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        }
    }
}