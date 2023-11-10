package com.example.curatorrtumirea.presentation.screen.email_confirmation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.ConfirmEmailUseCase
import com.example.curatorrtumirea.domain.usecase.SendEmailConfirmationCodeUseCase
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appNavigator: AppNavigator,
    private val confirmEmailUseCase: ConfirmEmailUseCase,
    private val sendEmailConfirmationCodeUseCase: SendEmailConfirmationCodeUseCase,
) : ViewModel() {

    private val email = checkNotNull(savedStateHandle.get<String>(Destination.EmailConfirmationScreen.EMAIL_KEY))
    private var resendEmailCooldownJob: Job? = null

    private val _screenState = MutableStateFlow(EmailConfirmationScreenState())
    val screenState: StateFlow<EmailConfirmationScreenState> = _screenState

    init {
        resetResendEmailCooldown()
    }

    fun onEvent(event: EmailConfirmationUIEvent) {
        when(event) {
            EmailConfirmationUIEvent.NavigateBack -> {
                appNavigator.tryNavigateBack()
            }
            is EmailConfirmationUIEvent.OnCodeChanged -> {
                _screenState.update { state ->
                    state.copy(
                        code = event.code.filter { it.isDigit() }
                    )
                }
                if (_screenState.value.code.length == CONFIRMATION_CODE_LENGTH) {
                    confirmEmail(_screenState.value.code)
                }
            }
            EmailConfirmationUIEvent.ResendEmail -> {
                if (_screenState.value.resendEmailCooldown == 0) {
                    resendEmail()
                }
            }
        }
    }

    private fun resendEmail() {
        resetResendEmailCooldown()
        sendEmailConfirmationCodeUseCase(email).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false,
                            resendEmailCooldown = 0
                        )
                    }
                    TODO("make one time event handling")
                }
                Resource.Loading -> {
                    _screenState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun resetResendEmailCooldown() {
        _screenState.update {
            it.copy(resendEmailCooldown = INITIAL_RESEND_EMAIL_COOLDOWN)
        }
        resendEmailCooldownJob?.cancel()
        resendEmailCooldownJob = viewModelScope.launch {
            delay(1000L)
            while(_screenState.value.resendEmailCooldown > 0) {
                _screenState.update {
                    it.copy(
                        resendEmailCooldown = it.resendEmailCooldown - 1
                    )
                }
                delay(1000L)
            }
        }
    }

    private fun confirmEmail(code: String) {
        confirmEmailUseCase(code).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false,
                            code = ""
                        )
                    }
                    TODO("make one time event handling")
                }
                Resource.Loading -> {
                    _screenState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    _screenState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    if (resource.data) {
                        TODO("navigate to next screen")
                    } else {
                        _screenState.update {
                            it.copy(
                                code = ""
                            )
                        }
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