package com.example.curatorrtumirea.presentation.screen.request_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.presentation.navigation.BottomNavItem
import com.example.curatorrtumirea.presentation.screen.event_list.EventListEvent
import com.example.curatorrtumirea.presentation.screen.request_list.components.RequestListCard
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
    state: RequestListScreenState,
    sendEvent: (RequestListEvent) -> Unit,
    effects: SharedFlow<RequestListEffect>
) {
    val bottomNavBarState = LocalBottomNavBarState.current
    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(isVisible = true, currentItem = BottomNavItem.Profile)
        sendEvent(RequestListEvent.RefreshRequestList)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.requests)) },
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (!state.isLoadingRequests && state.requests.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.request_list_empty),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.requests) { item ->
                    RequestListCard(
                        title = item.title,
                        status = stringResource(id = item.status),
                        description = item.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
            }
            FloatingActionButton(
                onClick = { sendEvent(RequestListEvent.CreateRequestClicked) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "create")
            }
        }
    }
    if (state.isLoadingRequests) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}