package com.example.curatorrtumirea.presentation.screen.group_list

import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetGroupListUseCase
import com.example.curatorrtumirea.presentation.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val getGroupListUseCase: GetGroupListUseCase
) : BaseViewModel<GroupListScreenState, GroupListEffect, GroupListEvent>(GroupListScreenState()) {

    init {
        getGroupList()
    }

    override fun onEvent(event: GroupListEvent) {
        TODO("Not yet implemented")
    }

    private fun getGroupList() {
        getGroupListUseCase().onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isListLoading = false
                    ))
                    TODO("make one time event handling")
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isListLoading = true
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isListLoading = false,
                        groups = resource.data
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }
}