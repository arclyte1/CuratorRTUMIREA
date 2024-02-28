package com.example.curatorrtumirea.presentation.screen.event_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.presentation.navigation.BottomNavItem
import com.example.curatorrtumirea.presentation.core.event_card.EventCard
import com.example.curatorrtumirea.presentation.core.event_card.EventCardModel
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    screenState: EventListScreenState,
    effect: SharedFlow<EventListEffect>,
    sendEvent: (EventListEvent) -> Unit
) {
    val bottomNavBarState = LocalBottomNavBarState.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = screenState.isListLoading,
        onRefresh = { sendEvent(EventListEvent.RefreshList) }
    )

    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(
            currentItem = BottomNavItem.Events,
            isVisible = true
        )
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.event_list)) },
                )
                Divider(modifier = Modifier.fillMaxWidth())
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            if (!screenState.isListLoading && screenState.events.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.event_list_empty),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Column {
                AnimatedVisibility(
                    visible = screenState.groupFilterTitle != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(start = 8.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = screenState.groupFilterTitle.toString())
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "remove filter",
                            modifier = Modifier
                                .clickable {
                                    sendEvent(EventListEvent.RemoveGroupFilter)
                                }
                                .width(20.dp)
                        )
                    }
                }
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 64.dp),
                    modifier = Modifier.pullRefresh(pullRefreshState)
                ) {
                    items(screenState.events) { event ->
                        EventCard(
                            model = EventCardModel.fromEvent(event),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                .clickable {
                                    sendEvent(EventListEvent.OnEventClicked(event.id))
                                }
                        )
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = screenState.isListLoading || screenState.isGroupLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
@Preview
fun EventListScreenPreview() {
    var screenState by remember { mutableStateOf(EventListScreenState()) }
    CuratorRTUMIREATheme {
        EventListScreen(
            screenState = screenState,
            effect = MutableSharedFlow(),
            sendEvent = {}
        )
    }
}