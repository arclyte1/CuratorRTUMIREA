package com.example.curatorrtumirea.presentation.screen.event_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.presentation.shared.event_card.EventCard
import com.example.curatorrtumirea.presentation.shared.event_card.EventCardModel
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    screenState: EventListScreenState,
    onEvent: (EventListUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.event_list)) },
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(screenState.events) { event ->
                EventCard(
                    model = EventCardModel.fromEvent(event),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .clickable {
                            onEvent(EventListUiEvent.OnEventClicked(event.id))
                        }
                )
            }
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
            onEvent = {}
        )
    }
}