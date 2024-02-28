package com.example.curatorrtumirea.presentation.screen.group_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.presentation.core.expandable_column.DefaultExpandableColumn
import com.example.curatorrtumirea.presentation.screen.event_details.EventDetailsEvent
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(
    screenState: GroupDetailsScreenState,
    effects: SharedFlow<GroupDetailsEffect>,
    sendEvent: (GroupDetailsEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    var isStudentListExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.group_details)) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
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
                DefaultExpandableColumn(
                    isExpanded = isStudentListExpanded,
                    onExpandedChanged = { isStudentListExpanded = it },
                    headerTitle = stringResource(id = R.string.student_list, screenState.students?.size ?: 0),
                    data = screenState.students?.map { it.name } ?: emptyList(),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                )
                Spacer(modifier = Modifier.height(96.dp))
            }
            Button(
                onClick = { sendEvent(GroupDetailsEvent.OnViewEventsClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = stringResource(id = R.string.view_events))
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