package com.example.curatorrtumirea.presentation.screen.event_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.presentation.shared.event_card.EventCard
import com.example.curatorrtumirea.presentation.shared.event_card.EventCardModel
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
    onEvent: (EventListEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = screenState.isListLoading,
        onRefresh = { onEvent(EventListEvent.RefreshList) }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.event_list)) },
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            if (!screenState.isListLoading && screenState.events.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.event_list_empty),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth().padding(top = 64.dp),
                    fontWeight = FontWeight.Bold
                )
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
                                onEvent(EventListEvent.OnEventClicked(event.id))
                            }
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = screenState.isListLoading,
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
            onEvent = {}
        )
    }
}