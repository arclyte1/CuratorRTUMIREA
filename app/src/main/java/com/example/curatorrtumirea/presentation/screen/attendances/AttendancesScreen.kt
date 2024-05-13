package com.example.curatorrtumirea.presentation.screen.attendances

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.presentation.core.expandable_column.ExpandableColumn
import com.example.curatorrtumirea.presentation.core.expandable_column.ExpandableColumnHeaderIcon
import com.example.curatorrtumirea.presentation.navigation.BottomNavItem
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsEvent
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendancesScreen(
    screenState: AttendancesScreenState,
    effects: SharedFlow<AttendancesEffect>,
    sendEvent: (AttendancesEvent) -> Unit
) {
    val bottomNavBarState = LocalBottomNavBarState.current
    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(isVisible = true, currentItem = BottomNavItem.Events)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(
                                id = R.string.attendances_title,
                                screenState.attendancesCountString
                            )
                        )
                    },
                    actions = {
                        IconButton(onClick = {
                            // TODO: save image
                        }) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "download"
                            )
                        }
                    }
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(screenState.attendances) { item ->
                ExpandableColumn(
                    isExpanded = item.isExpanded,
                    header = {
                        val interactionSource = remember { MutableInteractionSource() }

                        Row(modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                sendEvent(
                                    AttendancesEvent.OnExpandedGroupChanged(
                                        item.attendanceGroup.groupId,
                                        !item.isExpanded
                                    )
                                )
                            }
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = item.attendanceGroup.groupTitle,
                                modifier = Modifier.weight(1.0f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${item.attendanceGroup.attendances.count { it.isPresent }}/${item.attendanceGroup.attendances.size}",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            ExpandableColumnHeaderIcon(isExpanded = item.isExpanded)
                        }
                    },
                    data = item.attendanceGroup.attendances
                ) { attendance ->
                    AttendanceItem(
                        attendance = attendance,
                        onAttendanceChanged = {
                            sendEvent(
                                AttendancesEvent.OnAttendanceChanged(
                                    studentId = attendance.student.id,
                                    isPresent = it,
                                    groupId = item.attendanceGroup.groupId
                                )
                            )
                        }
                    )
                }
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}