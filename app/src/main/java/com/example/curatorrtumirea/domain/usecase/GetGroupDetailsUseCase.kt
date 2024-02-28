package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.GroupRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupDetailsUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {

    operator fun invoke(groupId: Long) = flow {
        try {
            emit(Resource.Loading)
            val group = groupRepository.getGroupListByIds(false, listOf(groupId))[0]
            emit(Resource.Success(group))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}