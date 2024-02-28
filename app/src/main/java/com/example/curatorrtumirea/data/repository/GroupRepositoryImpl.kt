package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.domain.model.Group
import com.example.curatorrtumirea.domain.repository.GroupRepository
import kotlinx.coroutines.delay

class GroupRepositoryImpl : GroupRepository {

    private var groups: List<Group>? = null

    private suspend fun getRemoteGroups(ids: List<Long> = emptyList()): List<Group> {
        Log.d(this.javaClass.simpleName, "getRemoteGroups")
        delay(1000)
        return listOf(
            Group(
                id = 6L,
                title = "ИКБО-06-20"
            ),
            Group(
                id = 7L,
                title = "ИКБО-07-20"
            ),
            Group(
                id = 8L,
                title = "ИКБО-08-20"
            ),
            Group(
                id = 9L,
                title = "ИКБО-09-20"
            ),
            Group(
                id = 10L,
                title = "ИКБО-10-20"
            ),
            Group(
                id = 11L,
                title = "ИКБО-11-20"
            ),
            Group(
                id = 12L,
                title = "ИКБО-12-20"
            ),
            Group(
                id = 13L,
                title = "ИКБО-13-20"
            ),
            Group(
                id = 14L,
                title = "ИКБО-14-20"
            ),
        )
    }

    override suspend fun getGroupList(forceRefresh: Boolean): List<Group> {
        if (forceRefresh || groups == null) {
            groups = getRemoteGroups()
        }
        return groups ?: emptyList()
    }

    override suspend fun getGroupListByIds(forceRefresh: Boolean, ids: List<Long>): List<Group> {
        if (forceRefresh || groups == null || !groups!!.map { it.id }.containsAll(ids)) {
            groups = getRemoteGroups(ids)
        }
        return groups?.filter { it.id in ids } ?: emptyList()
    }
}