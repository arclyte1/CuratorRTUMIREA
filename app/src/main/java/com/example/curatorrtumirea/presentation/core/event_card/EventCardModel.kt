package com.example.curatorrtumirea.presentation.core.event_card

import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.presentation.formatter.EventTypeFormatter
import java.time.format.DateTimeFormatter

data class EventCardModel(
    val title: String,
    val typeResourceId: Int?,
    val date: String?,
) {

    companion object {

        fun fromEvent(event: Event): EventCardModel {
            return EventCardModel(
                title = event.title,
                typeResourceId = EventTypeFormatter.getEventTypeStringResourceId(event.type),
                date = event.date?.format(DateTimeFormatter.ofPattern("dd.MM"))
            )
        }
    }
}
