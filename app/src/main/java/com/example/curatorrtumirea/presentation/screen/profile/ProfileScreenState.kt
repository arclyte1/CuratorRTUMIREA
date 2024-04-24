package com.example.curatorrtumirea.presentation.screen.profile

data class ProfileScreenState(
    val email: String? = null,
    val username: String? = null,
    val isLoading: Boolean = false,
    val requestsCount: Int? = null,
)

sealed class ProfileEvent {
    data class OnUsernameChanged(val value: String) : ProfileEvent()
    data object OnRequestsClicked : ProfileEvent()
    data object OnLogoutClicked : ProfileEvent()
}

sealed class ProfileEffect {

}