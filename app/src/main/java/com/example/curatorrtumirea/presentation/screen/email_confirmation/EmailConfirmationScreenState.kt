package com.example.curatorrtumirea.presentation.screen.email_confirmation

data class EmailConfirmationScreenState(
    val code: String = "",
    val resendEmailCooldown: Int = EmailConfirmationViewModel.INITIAL_RESEND_EMAIL_COOLDOWN,
    val isLoading: Boolean = false,
)

sealed class EmailConfirmationEffect {

}

sealed class EmailConfirmationEvent {

    data object NavigateBack : EmailConfirmationEvent()
    data class OnCodeChanged(val code: String) : EmailConfirmationEvent()
    data object ResendEmail : EmailConfirmationEvent()
}

