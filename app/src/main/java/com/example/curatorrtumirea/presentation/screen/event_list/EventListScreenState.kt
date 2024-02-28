package com.example.curatorrtumirea.presentation.screen.event_list

import com.example.curatorrtumirea.domain.model.Event

data class EventListScreenState(
    val events: List<Event> = emptyList(),
    val isListLoading: Boolean = false,
    val isGroupLoading: Boolean = false,
    val groupFilterTitle: String? = null,
)

sealed class EventListEffect {

}

sealed class EventListEvent {
    data class OnEventClicked(val eventId: Long) : EventListEvent()
    data object RefreshList : EventListEvent()
    data object RemoveGroupFilter : EventListEvent()
}
