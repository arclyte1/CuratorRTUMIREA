package com.example.curatorrtumirea.presentation.screen.login

data class LoginScreenState(
    val email: String = "",
    val isEmailReadOnly: Boolean = false,
    val isSignInButtonEnabled: Boolean = false,
    val isSigningIn: Boolean = false,
)

sealed class LoginEffect {

}

sealed class LoginEvent {

    data class OnEmailChanged(val email: String) : LoginEvent()
    data object SignIn : LoginEvent()
}