package com.example.curatorrtumirea.presentation.screen.create_request

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.common.conditional
import com.example.curatorrtumirea.common.drawAnimatedBorder
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRequestScreen(
    screenState: CreateRequestScreenState,
    effect: SharedFlow<CreateRequestEffect>,
    sendEvent: (CreateRequestEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val bottomNavBarState = LocalBottomNavBarState.current
    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(isVisible = false)
    }
    val animationColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.request_creation)) },
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = stringResource(id = R.string.title),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                TextField(
                    value = screenState.title,
                    onValueChange = { sendEvent(CreateRequestEvent.ChangeTitle(it)) },
                    readOnly = screenState.isCreating,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                )
                AnimatedVisibility(
                    visible = screenState.title.isBlank(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Text(
                        text = stringResource(id = R.string.title_could_not_be_empty),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    )
                }
                Text(
                    text = stringResource(id = R.string.description),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                TextField(
                    value = screenState.description,
                    onValueChange = { sendEvent(CreateRequestEvent.ChangeDescription(it)) },
                    readOnly = screenState.isCreating,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                )
                AnimatedVisibility(
                    visible = screenState.description.isBlank(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Text(
                        text = stringResource(id = R.string.description_could_not_be_empty),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(96.dp))
            }
            AnimatedVisibility(
                visible = screenState.isInputValid,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Button(
                    onClick = { sendEvent(CreateRequestEvent.CreateRequest) },
                    enabled = !screenState.isCreating && screenState.isInputValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .conditional(screenState.isCreating) {
                            drawAnimatedBorder(
                                2.dp,
                                CircleShape,
                                { Brush.sweepGradient(animationColors) },
                                2000
                            )
                        },
                ) {
                    Text(text = stringResource(id = R.string.create))
                }
            }
        }
    }
}