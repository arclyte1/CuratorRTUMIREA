package com.example.curatorrtumirea.presentation.screen.group_details

import com.example.curatorrtumirea.domain.model.Student

data class GroupDetailsScreenState(
    val title: String? = null,
    val students: List<Student>? = null,
    val isLoading: Boolean = false,
)

sealed class GroupDetailsEffect {

}

sealed class GroupDetailsEvent {
    data object OnViewEventsClicked : GroupDetailsEvent()
}

