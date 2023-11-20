package com.example.curatorrtumirea.presentation.screen.event_list

import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetEventListUseCase
import com.example.curatorrtumirea.presentation.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase,
) : BaseViewModel<EventListScreenState, EventListEffect, EventListEvent>(EventListScreenState()) {

    init {
        getEventList()
    }

    fun onEvent(uiEvent: EventListUiEvent) {
        when(uiEvent) {
            is EventListUiEvent.OnEventClicked -> {
                TODO("Navigate to event details screen")
            }
            EventListUiEvent.RefreshList -> {
                if (!state.value.isListLoading)
                    getEventList()
            }
        }
    }

    private fun getEventList() {
        getEventListUseCase().onEach { resource ->
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
                        events = resource.data
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }
}