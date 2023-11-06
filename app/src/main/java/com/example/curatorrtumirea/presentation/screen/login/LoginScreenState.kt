package com.example.curatorrtumirea.presentation.screen.login

data class LoginScreenState(
    val email: String = "",
    val isEmailReadOnly: Boolean = false,
    val isSignInButtonEnabled: Boolean = false,
    val isSigningIn: Boolean = false,
)
