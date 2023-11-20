package com.example.curatorrtumirea.presentation.screen.email_confirmation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.ConfirmEmailUseCase
import com.example.curatorrtumirea.domain.usecase.SendEmailConfirmationCodeUseCase
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val confirmEmailUseCase: ConfirmEmailUseCase,
    private val sendEmailConfirmationCodeUseCase: SendEmailConfirmationCodeUseCase,
) : BaseViewModel<EmailConfirmationScreenState, EmailConfirmationEffect, EmailConfirmationEvent>(
    EmailConfirmationScreenState()
) {

    private val email = checkNotNull(savedStateHandle.get<String>(Destination.EmailConfirmationScreen.EMAIL_KEY))
    private var resendEmailCooldownJob: Job? = null

    init {
        resetResendEmailCooldown()
    }

    override fun onEvent(event: EmailConfirmationEvent) {
        viewModelScope.launch {
            when (event) {
                EmailConfirmationEvent.NavigateBack -> {
                    appNavigator.tryNavigateBack()
                }

                is EmailConfirmationEvent.OnCodeChanged -> {
                    setState(state.value.copy(
                        code = event.code.filter { it.isDigit() }
                    ))
                    if (state.value.code.length == CONFIRMATION_CODE_LENGTH) {
                        confirmEmail(state.value.code)
                    }
                }

                EmailConfirmationEvent.ResendEmail -> {
                    if (state.value.resendEmailCooldown == 0) {
                        resendEmail()
                    }
                }
            }
        }
    }

    private fun resendEmail() {
        resetResendEmailCooldown()
        sendEmailConfirmationCodeUseCase(email).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isLoading = false,
                        resendEmailCooldown = 0
                    ))
                    TODO("make one time event handling")
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
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun resetResendEmailCooldown() {
        viewModelScope.launch {
            setState(state.value.copy(
                resendEmailCooldown = INITIAL_RESEND_EMAIL_COOLDOWN
            ))
            resendEmailCooldownJob?.cancel()
            resendEmailCooldownJob = viewModelScope.launch {
                delay(1000L)
                while (state.value.resendEmailCooldown > 0) {
                    setState(state.value.copy(
                        resendEmailCooldown = state.value.resendEmailCooldown - 1
                    ))
                    delay(1000L)
                }
            }
        }
    }

    private fun confirmEmail(code: String) {
        confirmEmailUseCase(code).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isLoading = false,
                        code = ""
                    ))
                    TODO("make one time event handling")
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
                    if (resource.data) {
                        appNavigator.tryNavigateTo(
                            route = Destination.EventListScreen(),
                            popUpToRoute = Destination.LoginScreen(),
                            inclusive = true
                        )
                    } else {
                        setState(state.value.copy(
                            code = ""
                        ))
                        TODO("make one time event handling (invalid code)")
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val INITIAL_RESEND_EMAIL_COOLDOWN = 30
        const val CONFIRMATION_CODE_LENGTH = 4
    }
}