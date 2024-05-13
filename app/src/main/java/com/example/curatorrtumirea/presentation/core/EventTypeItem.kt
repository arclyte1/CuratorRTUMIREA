package com.example.curatorrtumirea.presentation.core

import androidx.annotation.StringRes
import com.example.curatorrtumirea.domain.model.EventType
import com.example.curatorrtumirea.presentation.formatter.EventTypeFormatter

data class EventTypeItem(
    val eventType: EventType,
    @StringRes val nameStringRes: Int,
) {

    companion object {
        val entries: List<EventTypeItem>
            get() = EventType.entries.map(::fromEventType)

        fun fromEventType(eventType: EventType): EventTypeItem {
            return EventTypeItem(
                eventType,
                EventTypeFormatter.getEventTypeStringResourceId(eventType)
            )
        }
    }
}
