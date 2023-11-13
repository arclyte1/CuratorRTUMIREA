package com.example.curatorrtumirea.presentation.screen.event_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetEventListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow(EventListScreenState())
    val screenState: StateFlow<EventListScreenState> = _screenState

    init {
        getEventList()
    }

    fun onEvent(uiEvent: EventListUiEvent) {

    }

    private fun getEventList() {
        getEventListUseCase().onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    _screenState.update {
                        it.copy(
                            isListLoading = false
                        )
                    }
                    TODO("make one time event handling")
                }
                Resource.Loading -> {
                    _screenState.update {
                        it.copy(
                            isListLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    _screenState.update {
                        it.copy(
                            isListLoading = false,
                            events = resource.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}