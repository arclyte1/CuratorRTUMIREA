package com.example.curatorrtumirea.presentation.screen.event_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.presentation.core.expandable_column.DefaultExpandableColumn
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    screenState: EventDetailsScreenState,
    sendEvent: (EventDetailsEvent) -> Unit,
    effects: SharedFlow<EventDetailsEffect>
) {
    val scrollState = rememberScrollState()
    var isGroupListExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.event_details)) },
                    actions = {
                        IconButton(onClick = { sendEvent(EventDetailsEvent.OnEditClicked) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "edit"
                            )
                        }
                        IconButton(onClick = { sendEvent(EventDetailsEvent.OnDeleteClicked) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete"
                            )
                        }
                    }
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                screenState.title?.let {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
                screenState.typeId?.let {
                    Text(
                        text = stringResource(id = it),
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
                screenState.dateTimeAndLocation?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
                if (!screenState.isLoading) {
                    Row(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.cloud_url),
                                modifier = Modifier
                                    .alpha(0.4f)
                            )
                            screenState.cloudUrl?.let {
                                Text(
                                    text = it,
                                    color = Color.Blue,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier.clickable { sendEvent(EventDetailsEvent.OnCloudUrlClicked) }
                                )
                            } ?: Text(text = "—")
                        }
                        if (screenState.cloudUrl != null) {
                            IconButton(onClick = { sendEvent(EventDetailsEvent.OnCopyCloudUrlClicked) }) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "copy"
                                )
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    visible = !(screenState.isLoading || screenState.isGroupsLoading),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    DefaultExpandableColumn(
                        isExpanded = isGroupListExpanded,
                        onExpandedChanged = { isGroupListExpanded = it },
                        headerTitle = "${stringResource(id = R.string.group_list)} (${screenState.groups?.size ?: 0})",
                        data = screenState.groups ?: emptyList(),
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    )
                }
                Spacer(modifier = Modifier.height(96.dp))
            }
            Button(
                onClick = { sendEvent(EventDetailsEvent.OnSetUpAttendancesClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(id = R.string.set_up_attendances))
            }
        }
        if (screenState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.6f))
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}