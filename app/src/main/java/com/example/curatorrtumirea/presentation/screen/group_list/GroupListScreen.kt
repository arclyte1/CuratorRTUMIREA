package com.example.curatorrtumirea.presentation.screen.group_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.domain.model.Group
import com.example.curatorrtumirea.presentation.shared.group_card.GroupCard
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    state: GroupListScreenState,
    effect: SharedFlow<GroupListEffect>,
    onEvent: (GroupListEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isListLoading,
        onRefresh = { onEvent(GroupListEvent.RefreshList) }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.event_list)) },
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)) {
            if (!state.isListLoading && state.groups.isEmpty()) {
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
            LazyColumn(
                contentPadding = PaddingValues(bottom = 64.dp),
                modifier = Modifier.pullRefresh(pullRefreshState)
            ) {
                items(state.groups) { group ->
                    GroupCard(
                        title = group.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable {
                                onEvent(GroupListEvent.OnGroupClicked(group.id))
                            }
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = state.isListLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
@Preview
fun GroupListScreenPreview() {
    var state by remember { mutableStateOf(GroupListScreenState(
        groups = listOf(
            Group(
                id = 6L,
                title = "ИКБО-06-20"
            ),
            Group(
                id = 7L,
                title = "ИКБО-07-20"
            ),
            Group(
                id = 8L,
                title = "ИКБО-08-20"
            ),
            Group(
                id = 9L,
                title = "ИКБО-09-20"
            ),
            Group(
                id = 10L,
                title = "ИКБО-10-20"
            ),
            Group(
                id = 11L,
                title = "ИКБО-11-20"
            ),
            Group(
                id = 12L,
                title = "ИКБО-12-20"
            ),
            Group(
                id = 13L,
                title = "ИКБО-13-20"
            ),
            Group(
                id = 14L,
                title = "ИКБО-14-20"
            ),
        )
    )) }
    CuratorRTUMIREATheme {
        GroupListScreen(
            state = state,
            effect = MutableSharedFlow(),
            onEvent = { }
        )
    }
}