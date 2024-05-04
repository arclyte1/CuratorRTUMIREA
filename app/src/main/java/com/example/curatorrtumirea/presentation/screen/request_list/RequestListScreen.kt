package com.example.curatorrtumirea.presentation.screen.request_list

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.common.LocalBottomNavBarState
import com.example.curatorrtumirea.presentation.navigation.BottomNavItem
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestListScreen(
    state: RequestListScreenState,
    onEvent: (RequestsListEvent) -> Unit,
    effects: SharedFlow<RequestsListEffect>
) {
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
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            items(state.requests) { item ->

            }
        }
    }
}