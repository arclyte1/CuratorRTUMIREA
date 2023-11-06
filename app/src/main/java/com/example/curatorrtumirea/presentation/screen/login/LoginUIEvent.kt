package com.example.curatorrtumirea.presentation.screen.login

sealed class LoginUIEvent {

    data class OnEmailChanged(val email: String) : LoginUIEvent()
    object SignIn : LoginUIEvent()
}