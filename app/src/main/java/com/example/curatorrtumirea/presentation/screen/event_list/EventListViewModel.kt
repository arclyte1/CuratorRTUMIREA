package com.example.curatorrtumirea.presentation.screen.event_list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.usecase.GetEventListUseCase
import com.example.curatorrtumirea.domain.usecase.GetGroupListUseCase
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val getEventListUseCase: GetEventListUseCase,
    private val getGroupListUseCase: GetGroupListUseCase,
) : BaseViewModel<EventListScreenState, EventListEffect, EventListEvent>(EventListScreenState()) {

    private val forceRefresh =
        checkNotNull(savedStateHandle.get<String>(Destination.EventListScreen.FORCE_REFRESH)).toBoolean()
    private var groupFilterId =
        checkNotNull(savedStateHandle.get<String>(Destination.EventListScreen.GROUP_FILTER_ID)).toLong().let {
            if (it == Destination.EventListScreen.GROUP_FILTER_ID_NULL_VALUE)
                null
            else
                it
        }

    private var eventsData: List<Event> = emptyList()

    init {
        groupFilterId?.let {  id ->
            getGroupListUseCase(false, listOf(id)).onEach { resource ->
                when(resource) {
                    is Resource.Error -> {
                        //TODO show error
                        setState(state.value.copy(
                            isGroupLoading = false
                        ))
                    }
                    Resource.Loading -> {
                        setState(state.value.copy(
                            isGroupLoading = true
                        ))
                    }
                    is Resource.Success -> {
                        setState(state.value.copy(
                            isGroupLoading = false,
                            groupFilterTitle = resource.data.find { it.id == id }?.title
                        ))
                    }
                }
            }.launchIn(viewModelScope)
        }
        getEventList(forceRefresh)
    }

    override fun onEvent(event: EventListEvent) {
        viewModelScope.launch {
            when (event) {
                is EventListEvent.OnEventClicked -> {
                    appNavigator.tryNavigateTo(Destination.EventDetailsScreen(event.eventId))
                }

                is EventListEvent.RefreshList -> {
                    if (!state.value.isListLoading)
                        getEventList(event.forceRefresh)
                }

                EventListEvent.RemoveGroupFilter -> {
                    groupFilterId = null
                    setState(state.value.copy(
                        groupFilterTitle = null
                    ))
                    updateWithEventsData()
                }

                EventListEvent.OnCreateEventClicked -> {
                    appNavigator.tryNavigateTo(Destination.CreateEventScreen())
                }
            }
        }
    }

    private suspend fun updateWithEventsData() {
        setState(state.value.copy(
            events = groupFilterId?.let { eventsData.filter { groupFilterId in it.groupIds } }
                ?: eventsData
        ))
    }

    private fun getEventList(forceRefresh: Boolean) {
        getEventListUseCase(forceRefresh).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    setState(
                        state.value.copy(
                            isListLoading = false
                        )
                    )
                    TODO("make one time event handling")
                }

                Resource.Loading -> {
                    setState(
                        state.value.copy(
                            isListLoading = true
                        )
                    )
                }

                is Resource.Success -> {
                    Log.d(this.javaClass.simpleName, resource.data.toString())
                    eventsData = resource.data
                    updateWithEventsData()
                    setState(
                        state.value.copy(
                            isListLoading = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}