package com.example.curatorrtumirea.presentation.screen.event_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetEventDetailsUseCase
import com.example.curatorrtumirea.domain.usecase.GetGroupListUseCase
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val getGroupListUseCase: GetGroupListUseCase,
) : BaseViewModel<EventDetailsScreenState, EventDetailsEffect, EventDetailsEvent>(EventDetailsScreenState()) {

    private val eventId = checkNotNull(savedStateHandle.get<String>(Destination.EventDetailsScreen.EVENT_ID)).toLong()

    init {
        getEventDetailsUseCase(eventId).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    // TODO: show error
                    setState(state.value.copy(
                        isLoading = false
                    ))
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isLoading = true
                    ))
                }
                is Resource.Success -> {
                    getGroups(resource.data.groupIds)
                    setState(EventDetailsScreenState.Companion.Formatter.format(state.value.copy(
                        isLoading = false
                    ), resource.data))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getGroups(ids: List<Long>) {
        getGroupListUseCase(false, ids).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    // TODO: show error
                    setState(state.value.copy(
                        isGroupsLoading = false
                    ))
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isGroupsLoading = true
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isGroupsLoading = false,
                        groups = resource.data.map { it.title }
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: EventDetailsEvent) {
        when(event) {
            EventDetailsEvent.OnCloudUrlClicked -> TODO()
            EventDetailsEvent.OnCopyCloudUrlClicked -> TODO()
            EventDetailsEvent.OnDeleteClicked -> TODO()
            EventDetailsEvent.OnEditClicked -> TODO()
            EventDetailsEvent.OnSetUpAttendancesClicked -> {
                appNavigator.tryNavigateTo(Destination.AttendancesScreen(eventId))
            }
        }
    }
}