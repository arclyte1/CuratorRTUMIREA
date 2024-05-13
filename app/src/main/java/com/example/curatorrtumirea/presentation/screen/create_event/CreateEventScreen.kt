package com.example.curatorrtumirea.presentation.screen.create_event

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.common.conditional
import com.example.curatorrtumirea.common.drawAnimatedBorder
import com.example.curatorrtumirea.presentation.core.EventTypeItem
import com.example.curatorrtumirea.presentation.core.edit_button.EditButton
import com.example.curatorrtumirea.presentation.core.time_picker_dialog.TimePickerDialog
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    screenState: CreateEventScreenState,
    effect: SharedFlow<CreateEventEffect>,
    sendEvent: (CreateEventEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    var selectTypeDialogVisible by remember { mutableStateOf(false) }
    val selectDatePickerState = rememberDatePickerState()
    var selectDatePickerVisible by remember { mutableStateOf(false) }
    val selectStartTimePickerState = rememberTimePickerState()
    var selectStartTimePickerVisible by remember { mutableStateOf(false) }
    val selectEndTimePickerState = rememberTimePickerState()
    var selectEndTimePickerVisible by remember { mutableStateOf(false) }
    var addGroupDialogVisible by remember { mutableStateOf(false) }
    val animationColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.onPrimary,
        MaterialTheme.colorScheme.primary,
    )

    val bottomNavBarState = LocalBottomNavBarState.current
    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(isVisible = false)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.event_creation)) },
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
                    onValueChange = { sendEvent(CreateEventEvent.ChangeTitle(it)) },
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
                    text = stringResource(id = R.string.event_type),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                EditButton(
                    value = stringResource(id = screenState.type.nameStringRes),
                    onClick = { selectTypeDialogVisible = true },
                    clickable = !screenState.isCreating,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.date),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                EditButton(
                    value = screenState.displayDate,
                    onClick = { selectDatePickerVisible = true },
                    clickable = !screenState.isCreating,
                    emptyPlaceholder = stringResource(id = R.string.no_date),
                    clearButtonEnabled = true,
                    onClearClicked = { sendEvent(CreateEventEvent.ClearDate) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.start_time),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        text = stringResource(id = R.string.end_time),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = CenterVertically
                ) {
                    EditButton(
                        value = screenState.displayStartTime,
                        onClick = { selectStartTimePickerVisible = true },
                        clickable = !screenState.isCreating,
                        emptyPlaceholder = stringResource(id = R.string.no_time),
                        clearButtonEnabled = true,
                        onClearClicked = { sendEvent(CreateEventEvent.ClearStartTime) },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "—",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    EditButton(
                        value = screenState.displayEndTime,
                        onClick = { selectEndTimePickerVisible = true },
                        clickable = !screenState.isCreating,
                        emptyPlaceholder = stringResource(id = R.string.no_time),
                        clearButtonEnabled = true,
                        onClearClicked = { sendEvent(CreateEventEvent.ClearEndTime) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Text(
                    text = stringResource(id = R.string.location),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                TextField(
                    value = screenState.location.orEmpty(),
                    onValueChange = { sendEvent(CreateEventEvent.ChangeLocation(it)) },
                    singleLine = true,
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
                Text(
                    text = stringResource(id = R.string.cloud_url).dropLast(1),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                TextField(
                    value = screenState.cloudUrl.orEmpty(),
                    onValueChange = { sendEvent(CreateEventEvent.ChangeCloudUrl(it)) },
                    singleLine = true,
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
                Text(
                    text = stringResource(id = R.string.groups),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                screenState.addedGroups.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = it.title,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { sendEvent(CreateEventEvent.RemoveGroup(it)) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "remove group"
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = screenState.addedGroups.isEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                ) {
                    Text(
                        text = stringResource(id = R.string.at_least_1_group_required),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp)
                    )
                }
                if (screenState.availableGroups.isNotEmpty()) {
                    TextButton(
                        onClick = { addGroupDialogVisible = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(stringResource(id = R.string.add_group))
                    }
                }
                Spacer(modifier = Modifier.height(96.dp))
            }
            AnimatedVisibility(
                visible = screenState.isInputValid,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier
                    .align(BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Button(
                    onClick = { sendEvent(CreateEventEvent.CreateEvent) },
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
    if (screenState.isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
    if (addGroupDialogVisible) {
        Dialog(onDismissRequest = { addGroupDialogVisible = false }) {
            Card {
                LazyColumn {
                    items(screenState.availableGroups) { item ->
                        Text(
                            item.title,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    sendEvent(CreateEventEvent.AddGroup(item))
                                    addGroupDialogVisible = false
                                }
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                    }
                }
            }
        }
    }
    if (selectTypeDialogVisible) {
        Dialog(onDismissRequest = { selectTypeDialogVisible = false }) {
            Card {
                LazyColumn {
                    items(EventTypeItem.entries) { item ->
                        Text(
                            text = stringResource(id = item.nameStringRes),
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    sendEvent(CreateEventEvent.ChangeEventType(item))
                                    selectTypeDialogVisible = false
                                }
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                    }
                }
            }
        }
    }
    if (selectDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { selectDatePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectDatePickerVisible = false
                        selectDatePickerState.selectedDateMillis?.let {
                            sendEvent(CreateEventEvent.ChangeDate(it))
                        }
                    }
                ) {
                    Text("OK", fontSize = 20.sp)
                }
            }
        ) {
            DatePicker(state = selectDatePickerState)
        }
    }
    if (selectStartTimePickerVisible) {
        TimePickerDialog(
            onDismissRequest = { selectStartTimePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectStartTimePickerVisible = false
                        sendEvent(
                            CreateEventEvent.ChangeStartTime(
                                hour = selectStartTimePickerState.hour,
                                minute = selectStartTimePickerState.minute
                            )
                        )
                    }
                ) {
                    Text("OK", fontSize = 20.sp)
                }
            }
        ) {
            TimePicker(state = selectStartTimePickerState)
        }
    }
    if (selectEndTimePickerVisible) {
        TimePickerDialog(
            onDismissRequest = { selectEndTimePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectEndTimePickerVisible = false
                        sendEvent(
                            CreateEventEvent.ChangeEndTime(
                                hour = selectEndTimePickerState.hour,
                                minute = selectEndTimePickerState.minute
                            )
                        )
                    }
                ) {
                    Text("OK", fontSize = 20.sp)
                }
            }
        ) {
            TimePicker(state = selectEndTimePickerState)
        }
    }
}