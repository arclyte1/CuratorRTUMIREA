package com.example.curatorrtumirea.presentation.screen.create_event

import com.example.curatorrtumirea.domain.model.EventType
import com.example.curatorrtumirea.presentation.core.EventTypeItem
import com.example.curatorrtumirea.presentation.core.GroupListItem
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class CreateEventScreenState(
    val title: String = "",
    val type: EventTypeItem = EventTypeItem.fromEventType(EventType.OTHER),
    val date: LocalDate? = null,
    val displayDate: String = "",
    val startTime: LocalTime? = null,
    val displayStartTime: String = "",
    val endTime: LocalTime? = null,
    val displayEndTime: String = "",
    val location: String? = null,
    val cloudUrl: String? = null,
    val addedGroups: List<GroupListItem> = emptyList(),
    val availableGroups: List<GroupListItem> = emptyList(),
    val isCreating: Boolean = false,
    val isLoading: Boolean = false,
    val isInputValid: Boolean = false,
) {

    fun setDateFromMillis(millis: Long): CreateEventScreenState {
        val date = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
            .toLocalDate()
        val formattedDate = if (LocalDate.now().year == date.year)
            date.format(DateTimeFormatter.ofPattern("dd MMM "))
        else
            date.format(DateTimeFormatter.ofPattern("dd MMM yyyy "))
        return this.copy(
            date = date,
            displayDate = formattedDate
        )
    }

    fun setStartTime(hour: Int, minute: Int): CreateEventScreenState {
        val time = LocalTime.of(hour, minute)
        val formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"))
        return this.copy(
            startTime = time,
            displayStartTime = formattedTime
        )
    }

    fun setEndTime(hour: Int, minute: Int): CreateEventScreenState {
        val time = LocalTime.of(hour, minute)
        val formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"))
        return this.copy(
            endTime = time,
            displayEndTime = formattedTime
        )
    }
}

sealed class CreateEventEffect {

}

sealed class CreateEventEvent {
    data class ChangeTitle(val value: String) : CreateEventEvent()
    data class ChangeLocation(val value: String) : CreateEventEvent()
    data class ChangeCloudUrl(val value: String) : CreateEventEvent()
    data class RemoveGroup(val group: GroupListItem) : CreateEventEvent()
    data class AddGroup(val group: GroupListItem) : CreateEventEvent()
    data object CreateEvent : CreateEventEvent()
    data class ChangeEventType(val eventType: EventTypeItem) : CreateEventEvent()
    data class ChangeDate(val millis: Long) : CreateEventEvent()
    data class ChangeStartTime(val hour: Int, val minute: Int) : CreateEventEvent()
    data class ChangeEndTime(val hour: Int, val minute: Int) : CreateEventEvent()
    data object ClearDate : CreateEventEvent()
    data object ClearStartTime : CreateEventEvent()
    data object ClearEndTime : CreateEventEvent()
}