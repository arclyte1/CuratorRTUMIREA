package com.example.curatorrtumirea.presentation.screen.create_request

import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.CreateRequestUseCase
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRequestViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val createRequestUseCase: CreateRequestUseCase,
) : BaseViewModel<CreateRequestScreenState, CreateRequestEffect, CreateRequestEvent>(
    CreateRequestScreenState()
) {

    override fun onEvent(event: CreateRequestEvent) {
        viewModelScope.launch {
            when (event) {
                is CreateRequestEvent.ChangeDescription -> {
                    setState(state.value.copy(description = event.value))
                    updateInputValid()
                }

                is CreateRequestEvent.ChangeTitle -> {
                    setState(state.value.copy(title = event.value))
                    updateInputValid()
                }

                CreateRequestEvent.CreateRequest -> {
                    createRequest()
                }
            }
        }
    }

    private fun createRequest() {
        createRequestUseCase(
            state.value.title,
            state.value.description
        ).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(isCreating = false))
                    // TODO: show error
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

    private suspend fun updateInputValid() {
        setState(
            state.value.copy(
                isInputValid = state.value.title.isNotBlank() && state.value.description.isNotBlank()
            )
        )
    }
}