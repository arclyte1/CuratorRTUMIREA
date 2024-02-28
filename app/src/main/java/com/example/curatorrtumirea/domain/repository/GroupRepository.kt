package com.example.curatorrtumirea.domain.repository

import com.example.curatorrtumirea.domain.model.Group

interface GroupRepository {

    suspend fun getGroupList(forceRefresh: Boolean): List<Group>

    suspend fun getGroupListByIds(forceRefresh: Boolean, ids: List<Long>): List<Group>
}