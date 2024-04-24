package com.example.curatorrtumirea.presentation.screen.profile

import com.example.curatorrtumirea.presentation.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private val initialState = ProfileScreenState(
    email = "aboba@gmail.com",
    username = "aboba",
    requestsCount = 1,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(


) : BaseViewModel<ProfileScreenState, ProfileEffect, ProfileEvent>(initialState) {

    override fun onEvent(event: ProfileEvent) {
        when(event) {
            ProfileEvent.OnLogoutClicked -> TODO()
            ProfileEvent.OnRequestsClicked -> TODO()
            is ProfileEvent.OnUsernameChanged -> TODO()
        }
    }
}