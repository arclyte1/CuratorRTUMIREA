package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.GroupRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {

    operator fun invoke(forceRefresh: Boolean) = flow {
        try {
            emit(Resource.Loading)
            val groups = groupRepository.getGroupList(forceRefresh)
            emit(Resource.Success(groups))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }

    operator fun invoke(forceRefresh: Boolean, ids: List<Long>) = flow {
        try {
            emit(Resource.Loading)
            val groups = groupRepository.getGroupListByIds(forceRefresh, ids)
            emit(Resource.Success(groups))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}