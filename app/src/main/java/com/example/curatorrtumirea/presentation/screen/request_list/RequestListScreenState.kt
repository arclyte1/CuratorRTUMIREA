package com.example.curatorrtumirea.presentation.screen.request_list

data class RequestListScreenState(
    val requests: List<RequestListItem> = emptyList(),
    val isLoadingRequests: Boolean = false,
)

sealed class RequestsListEffect {

}

sealed class RequestsListEvent {

}
