package com.example.curatorrtumirea.presentation.core

import com.example.curatorrtumirea.domain.model.Group

data class GroupListItem(
    val id: Long,
    val title: String
) {

    companion object {
        fun fromGroup(group: Group): GroupListItem {
            return GroupListItem(
                id = group.id,
                title = group.title
            )
        }
    }
}
