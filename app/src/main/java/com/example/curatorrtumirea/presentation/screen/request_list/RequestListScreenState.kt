package com.example.curatorrtumirea.presentation.screen.request_list

import com.example.curatorrtumirea.presentation.screen.request_list.model.RequestListItem

data class RequestListScreenState(
    val requests: List<RequestListItem> = emptyList(),
    val isLoadingRequests: Boolean = false,
)

sealed class RequestListEffect {

}

sealed class RequestListEvent {
    data object RefreshRequestList : RequestListEvent()
    data object CreateRequestClicked : RequestListEvent()
}
