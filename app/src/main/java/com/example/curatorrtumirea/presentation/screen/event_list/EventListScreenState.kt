package com.example.curatorrtumirea.presentation.screen.event_list

import com.example.curatorrtumirea.domain.model.Event

data class EventListScreenState(
    val events: List<Event> = emptyList(),
    val isListLoading: Boolean = true,
)
