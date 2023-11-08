package com.example.curatorrtumirea.presentation.screen.email_confirmation

sealed class EmailConfirmationUIEvent {
    data object NavigateBack : EmailConfirmationUIEvent()
    data class OnCodeChanged(val code: String, val isInputFinished: Boolean) : EmailConfirmationUIEvent()
    data object ResendEmail : EmailConfirmationUIEvent()
}
