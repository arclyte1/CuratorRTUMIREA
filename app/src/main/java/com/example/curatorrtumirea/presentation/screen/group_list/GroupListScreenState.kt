package com.example.curatorrtumirea.presentation.screen.group_list

import com.example.curatorrtumirea.domain.model.Group

data class GroupListScreenState(
    val groups: List<Group> = emptyList(),
    val isListLoading: Boolean = false,
)

sealed class GroupListEffect {

}

sealed class GroupListEvent {
    data class OnGroupClicked(val id: Long) : GroupListEvent()
    data object RefreshList : GroupListEvent()
}