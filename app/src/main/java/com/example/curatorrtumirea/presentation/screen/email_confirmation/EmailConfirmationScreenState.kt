package com.example.curatorrtumirea.presentation.screen.email_confirmation

data class EmailConfirmationScreenState(
    val code: String = "",
    val resendEmailCooldown: Int = EmailConfirmationViewModel.INITIAL_RESEND_EMAIL_COOLDOWN,
    val isLoading: Boolean = false,
)
