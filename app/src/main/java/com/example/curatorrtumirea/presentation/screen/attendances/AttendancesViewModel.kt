package com.example.curatorrtumirea.presentation.screen.attendances

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.AttendanceGroup
import com.example.curatorrtumirea.domain.usecase.GetAttendancesUseCase
import com.example.curatorrtumirea.domain.usecase.SetAttendanceUseCase
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendancesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAttendancesUseCase: GetAttendancesUseCase,
    private val setAttendanceUseCase: SetAttendanceUseCase
) : BaseViewModel<AttendancesScreenState, AttendancesEffect, AttendancesEvent>(AttendancesScreenState()) {

    private val eventId = checkNotNull(savedStateHandle.get<String>(Destination.EventDetailsScreen.EVENT_ID)).toLong()
    private val attendanceChangeCooldownStudentIds = mutableSetOf<Long>()

    init {
        getAttendancesUseCase(eventId)
            .onEach(::updateWithAttendancesResource)
            .launchIn(viewModelScope)
    }

    private suspend fun updateWithAttendancesResource(resource: Resource<List<AttendanceGroup>>) {
        when (resource) {
            is Resource.Error -> {
                //TODO: show error
                setState(
                    state.value.copy(
                        isLoading = false
                    )
                )
            }
            Resource.Loading -> {
                setState(
                    state.value.copy(
                        isLoading = true
                    )
                )
            }
            is Resource.Success -> {
                setState(
                    state.value.copy(
                        isLoading = false,
                    ).copyWithChangedAttendances(resource.data)
                )
            }
        }
    }

    override fun onEvent(event: AttendancesEvent) {
        when (event) {
            is AttendancesEvent.OnExpandedGroupChanged -> {
                viewModelScope.launch {
                    setState(state.value.copy(
                        attendances = state.value.attendances.map {
                            if (it.attendanceGroup.groupId == event.groupId) {
                                it.copy(isExpanded = event.isExpanded)
                            } else {
                                it
                            }
                        }
                    ))
                }
            }

            is AttendancesEvent.OnAttendanceChanged -> {
                if (event.studentId !in attendanceChangeCooldownStudentIds) {

                    setAttendanceUseCase(
                        eventId = eventId,
                        groupId = event.groupId,
                        studentId = event.studentId,
                        isPresent = event.isPresent
                    ).onEach(::updateWithAttendancesResource).launchIn(viewModelScope)
                }
            }
        }
    }

    companion object {
        const val ATTENDANCE_CHANGE_COOLDOWN = 2000L
    }
}