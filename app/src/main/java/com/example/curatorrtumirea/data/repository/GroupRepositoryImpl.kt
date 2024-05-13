package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.data.remote.api.MainApi
import com.example.curatorrtumirea.data.remote.dto.GroupDto
import com.example.curatorrtumirea.domain.model.Group
import com.example.curatorrtumirea.domain.repository.GroupRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val api: MainApi
) : GroupRepository {

    private var groups: List<Group>? = null

    private suspend fun getRemoteGroups(): List<Group> {
        val response = api.getGroupList().map { group ->
            group.copy(
                students = group.students.sortedBy { it.name }
            )
        }.sortedBy { it.title }
        Log.d(this.javaClass.simpleName, response.joinToString())
        return response.map(GroupDto::toGroup)
    }

    override suspend fun getGroupList(forceRefresh: Boolean): List<Group> {
        if (forceRefresh || groups == null) {
            groups = getRemoteGroups()
        }
        return groups ?: emptyList()
    }

    override suspend fun getGroupListByIds(forceRefresh: Boolean, ids: List<Long>): List<Group> {
        if (forceRefresh || groups == null || !groups!!.map { it.id }.containsAll(ids)) {
            groups = getRemoteGroups()
        }
        return groups?.filter { it.id in ids } ?: emptyList()
    }
}