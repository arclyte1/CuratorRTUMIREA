package com.example.curatorrtumirea.presentation.screen.email_confirmation

sealed class EmailConfirmationUiEvent {

    data object NavigateBack : EmailConfirmationUiEvent()
    data class OnCodeChanged(val code: String) : EmailConfirmationUiEvent()
    data object ResendEmail : EmailConfirmationUiEvent()
}
