package com.example.curatorrtumirea.presentation.screen.event_list

sealed class EventListUiEvent {

    data class OnEventClicked(val eventId: Long) : EventListUiEvent()
    data object RefreshList : EventListUiEvent()
}
