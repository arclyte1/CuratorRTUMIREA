package com.example.curatorrtumirea.presentation.screen.group_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.curatorrtumirea.domain.model.Group
import com.example.curatorrtumirea.presentation.navigation.BottomNavItem
import com.example.curatorrtumirea.presentation.core.group_card.GroupCard
import com.example.curatorrtumirea.presentation.ui.theme.CuratorRTUMIREATheme
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    screenState: GroupListScreenState,
    effect: SharedFlow<GroupListEffect>,
    onEvent: (GroupListEvent) -> Unit
) {
    val bottomNavBarState = LocalBottomNavBarState.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = screenState.isListLoading,
        onRefresh = { onEvent(GroupListEvent.RefreshList) }
    )

    LaunchedEffect(Unit) {
        bottomNavBarState.setupBottomBar(
            currentItem = BottomNavItem.Groups,
            isVisible = true
        )
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.group_list)) },
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
            if (!screenState.isListLoading && screenState.groups.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.group_list_empty),
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
                modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)
            ) {
                items(screenState.groups) { group ->
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
                refreshing = screenState.isListLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
@Preview
fun GroupListScreenPreview() {
    var state by remember {
        mutableStateOf(
            GroupListScreenState(
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
            )
        )
    }
    CuratorRTUMIREATheme {
        GroupListScreen(
            screenState = state,
            effect = MutableSharedFlow(),
            onEvent = { }
        )
    }
}