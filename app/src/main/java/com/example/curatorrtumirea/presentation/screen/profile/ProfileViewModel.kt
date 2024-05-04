package com.example.curatorrtumirea.presentation.screen.profile

import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.GetUserEmailUseCase
import com.example.curatorrtumirea.domain.usecase.LogoutUseCase
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel<ProfileScreenState, ProfileEffect, ProfileEvent>(ProfileScreenState()) {

    init {
        refreshEmail()
    }

    override fun onEvent(event: ProfileEvent) {
        when(event) {
            ProfileEvent.OnLogoutClicked -> {
                logout()
            }
            ProfileEvent.OnRequestsClicked -> TODO()
            is ProfileEvent.OnUsernameChanged -> TODO()
        }
    }

    private fun logout() {
        logoutUseCase().onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isLoading = false
                    ))
                    // TODO: show error
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isLoading = true
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isLoading = false
                    ))
                    appNavigator.tryNavigateTo(route = Destination.LoginScreen(), clearBackStack = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun refreshEmail() {
        getUserEmailUseCase().onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isLoading = false
                    ))
                    // TODO: show error
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isLoading = true
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isLoading = false,
                        email = resource.data
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }
}