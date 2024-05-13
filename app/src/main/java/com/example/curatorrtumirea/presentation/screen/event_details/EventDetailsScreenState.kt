package com.example.curatorrtumirea.presentation.screen.event_details

import android.net.Uri
import androidx.annotation.StringRes
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.presentation.formatter.EventTypeFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class EventDetailsScreenState(
    val isLoading: Boolean = false,
    val title: String? = null,
    @StringRes val typeId: Int? = null,
    val dateTimeAndLocation: String? = null,
    val cloudUrl: String? = null,
    val isGroupsLoading: Boolean = false,
    val groups: List<String>? = null
) {

    companion object {
        object Formatter {
            fun format(currentState: EventDetailsScreenState, event: Event): EventDetailsScreenState {
                val dateString = if (event.date != null) {
                    if (LocalDate.now().year == event.date.year)
                        event.date.format(DateTimeFormatter.ofPattern("dd MMM "))
                    else
                        event.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy "))
                } else ""
                val timeRangeString = if (event.startTime != null && event.endTime != null) {
                    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                    "${event.startTime.format(timeFormatter)}-${event.endTime.format(timeFormatter)} "
                } else ""
                return currentState.copy(
                    title = event.title,
                    typeId = EventTypeFormatter.getEventTypeStringResourceId(event.type),
                    dateTimeAndLocation = dateString + timeRangeString + (event.location ?: ""),
                    cloudUrl = event.cloudUrl
                )
            }
        }
    }
}

sealed class EventDetailsEffect {
    data class OpenUri(val uri: Uri) : EventDetailsEffect()
    data class CopyToClipboard(val text: String) : EventDetailsEffect()
}

sealed class EventDetailsEvent {
    data object OnEditClicked : EventDetailsEvent()
    data object DeleteEvent : EventDetailsEvent()
    data object OnCloudUrlClicked : EventDetailsEvent()
    data object OnCopyCloudUrlClicked : EventDetailsEvent()
    data object OnSetUpAttendancesClicked : EventDetailsEvent()
}