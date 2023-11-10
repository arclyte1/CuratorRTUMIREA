package com.example.curatorrtumirea.presentation.screen.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.SendEmailConfirmationCodeUseCase
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val sendEmailConfirmationCodeUseCase: SendEmailConfirmationCodeUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(LoginScreenState())
    val screenState: StateFlow<LoginScreenState> = _screenState

    fun onEvent(event: LoginUiEvent) {
        when(event) {
            is LoginUiEvent.OnEmailChanged -> {
                val email = event.email.trim()
                _screenState.update {
                    it.copy(
                        email = email
                    )
                }
                validateEmail(email)
            }
            LoginUiEvent.SignIn -> {
                signIn(_screenState.value.email)
            }
        }
    }

    private fun signIn(email: String) {
        sendEmailConfirmationCodeUseCase(email).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    _screenState.update {
                        it.copy(
                            isSigningIn = false,
                            isEmailReadOnly = false,
                            isSignInButtonEnabled = true
                        )
                    }
                    TODO("make one time event handling")
                }
                Resource.Loading -> {
                    _screenState.update {
                        it.copy(
                            isSigningIn = true,
                            isEmailReadOnly = true,
                            isSignInButtonEnabled = false
                        )
                    }
                }
                is Resource.Success -> {
                    _screenState.update {
                        it.copy(
                            isSigningIn = false,
                            isEmailReadOnly = false,
                            isSignInButtonEnabled = true
                        )
                    }
                    appNavigator.tryNavigateTo(Destination.EmailConfirmationScreen(email))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun validateEmail(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _screenState.update {
            it.copy(
                isSignInButtonEnabled = isEmailValid
            )
        }
    }

    companion object {
        private const val DEBOUNCING_DELAY = 1000L
    }
}