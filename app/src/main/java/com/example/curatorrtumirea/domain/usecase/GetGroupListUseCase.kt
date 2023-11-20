package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Group
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor() {

    operator fun invoke() = flow {
        emit(Resource.Loading)
        delay(3000L)
        emit(Resource.Success(listOf(
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
        )))
    }
}