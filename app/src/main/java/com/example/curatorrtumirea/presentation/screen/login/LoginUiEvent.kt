package com.example.curatorrtumirea.presentation.screen.login

sealed class LoginUiEvent {

    data class OnEmailChanged(val email: String) : LoginUiEvent()
    data object SignIn : LoginUiEvent()
}