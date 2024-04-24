package com.example.curatorrtumirea.presentation.screen.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.usecase.SendEmailConfirmationCodeUseCase
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import com.example.curatorrtumirea.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val sendEmailConfirmationCodeUseCase: SendEmailConfirmationCodeUseCase
) : BaseViewModel<LoginScreenState, LoginEffect, LoginEvent>(LoginScreenState()) {

    override fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.OnEmailChanged -> {
                    val email = event.email.trim()
                    setState(state.value.copy(
                        email = email
                    ))
                    validateEmail(email)
                }
                LoginEvent.SignIn -> {
                    signIn(state.value.email)
                }
            }
        }
    }

    private fun signIn(email: String) {
        sendEmailConfirmationCodeUseCase(email).onEach { resource ->
            when(resource) {
                is Resource.Error -> {
                    setState(state.value.copy(
                        isSigningIn = false,
                        isEmailReadOnly = false,
                        isSignInButtonEnabled = true
                    ))
                    Log.e(this.javaClass.simpleName, resource.message)
//                    TODO("make one time event handling")
                }
                Resource.Loading -> {
                    setState(state.value.copy(
                        isSigningIn = true,
                        isEmailReadOnly = true,
                        isSignInButtonEnabled = false
                    ))
                }
                is Resource.Success -> {
                    setState(state.value.copy(
                        isSigningIn = false,
                        isEmailReadOnly = false,
                        isSignInButtonEnabled = true
                    ))
                    appNavigator.tryNavigateTo(Destination.EmailConfirmationScreen(email))
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun validateEmail(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        setState(state.value.copy(
            isSignInButtonEnabled = isEmailValid
        ))
    }
}