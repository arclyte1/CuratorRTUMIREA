package com.example.curatorrtumirea.presentation.screen.group_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetGroupDetailsUseCase
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val getGroupDetailsUseCase: GetGroupDetailsUseCase,
) : BaseViewModel<GroupDetailsScreenState, GroupDetailsEffect, GroupDetailsEvent>(
    GroupDetailsScreenState()
) {

    private val groupId: Long = checkNotNull(savedStateHandle.get<String>(Destination.GroupDetailsScreen.GROUP_ID)).toLong()

    init {
        getGroupDetailsUseCase(groupId).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isLoading = false
                    ))
                    //TODO: error handle
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isLoading = true
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isLoading = false,
                        title = resource.data.title,
                        students = resource.data.students
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: GroupDetailsEvent) {
        when(event) {
            GroupDetailsEvent.OnViewEventsClicked -> {
                appNavigator.tryNavigateTo(Destination.EventListScreen(false, groupId))
            }
        }
    }
}