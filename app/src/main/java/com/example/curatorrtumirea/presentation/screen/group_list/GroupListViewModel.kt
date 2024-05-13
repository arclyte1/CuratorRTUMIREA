package com.example.curatorrtumirea.presentation.screen.group_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetGroupListUseCase
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val getGroupListUseCase: GetGroupListUseCase
) : BaseViewModel<GroupListScreenState, GroupListEffect, GroupListEvent>(GroupListScreenState()) {

    private val forceRefresh = checkNotNull(savedStateHandle.get<String>(Destination.EventListScreen.FORCE_REFRESH)).toBoolean()

    init {
        getGroupList(forceRefresh)
    }

    override fun onEvent(event: GroupListEvent) {
        when(event) {
            is GroupListEvent.OnGroupClicked -> {
                appNavigator.tryNavigateTo(Destination.GroupDetailsScreen(event.id))
            }
            GroupListEvent.RefreshList -> {
                if (!state.value.isListLoading)
                    getGroupList(true)
            }
        }
    }

    private fun getGroupList(forceRefresh: Boolean) {
        getGroupListUseCase(forceRefresh).onEach { resource ->
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