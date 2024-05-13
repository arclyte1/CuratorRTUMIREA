package com.example.curatorrtumirea.presentation.screen.create_event

import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.CreateEventUseCase
import com.example.curatorrtumirea.domain.usecase.GetGroupListUseCase
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.core.GroupListItem
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val getGroupListUseCase: GetGroupListUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val appNavigator: AppNavigator,
) : BaseViewModel<CreateEventScreenState, CreateEventEffect, CreateEventEvent>(
    CreateEventScreenState()
) {

    init {
        getGroupListUseCase(false).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    setState(state.value.copy(isLoading = false))
                }

                Resource.Loading -> {
                    setState(state.value.copy(isLoading = true))
                }

                is Resource.Success -> {
                    setState(
                        state.value.copy(
                            isLoading = false,
                            availableGroups = resource.data.map { GroupListItem.fromGroup(it) }
                                .sortedBy { it.title }
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: CreateEventEvent) {
        viewModelScope.launch {
            when (event) {
                is CreateEventEvent.ChangeCloudUrl -> {
                    setState(state.value.copy(cloudUrl = event.value))
                }

                is CreateEventEvent.ChangeLocation -> {
                    setState(state.value.copy(location = event.value))
                }

                is CreateEventEvent.ChangeTitle -> {
                    setState(state.value.copy(title = event.value))
                    updateInputValidState()
                }

                is CreateEventEvent.AddGroup -> {
                    setState(
                        state.value.copy(
                            availableGroups = (state.value.availableGroups - event.group).sortedBy { it.title },
                            addedGroups = (state.value.addedGroups + event.group).sortedBy { it.title }
                        )
                    )
                    updateInputValidState()
                }

                is CreateEventEvent.RemoveGroup -> {
                    setState(
                        state.value.copy(
                            availableGroups = (state.value.availableGroups + event.group).sortedBy { it.title },
                            addedGroups = (state.value.addedGroups - event.group).sortedBy { it.title }
                        )
                    )
                    updateInputValidState()
                }

                CreateEventEvent.CreateEvent -> {
                    createEvent()
                }

                is CreateEventEvent.ChangeEventType -> {
                    setState(state.value.copy(type = event.eventType))
                }

                is CreateEventEvent.ChangeDate -> {
                    setState(state.value.setDateFromMillis(event.millis))
                }

                is CreateEventEvent.ChangeEndTime -> {
                    setState(state.value.setEndTime(event.hour, event.minute))
                }

                is CreateEventEvent.ChangeStartTime -> {
                    setState(state.value.setStartTime(event.hour, event.minute))
                }

                CreateEventEvent.ClearDate -> {
                    setState(state.value.copy(date = null, displayDate = ""))
                }

                CreateEventEvent.ClearEndTime -> {
                    setState(state.value.copy(endTime = null, displayEndTime = ""))
                }

                CreateEventEvent.ClearStartTime -> {
                    setState(state.value.copy(startTime = null, displayStartTime = ""))
                }
            }
        }
    }

    private fun createEvent() {
        createEventUseCase(
            title = state.value.title,
            type = state.value.type.eventType,
            groupIds = state.value.addedGroups.map { it.id },
            date = state.value.date,
            startTime = state.value.startTime,
            endTime = state.value.endTime,
            location = state.value.location,
            cloudUrl = state.value.cloudUrl
        ).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    //TODO: show error
                    setState(state.value.copy(isCreating = false))
                }

                Resource.Loading -> {
                    setState(state.value.copy(isCreating = true))
                }

                is Resource.Success -> {
                    setState(state.value.copy(isCreating = false))
                    appNavigator.navigateBack()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateInputValidState() {
        viewModelScope.launch {
            setState(
                state.value.copy(
                    isInputValid = state.value.title.isNotBlank() && state.value.addedGroups.isNotEmpty()
                )
            )
        }
    }
}