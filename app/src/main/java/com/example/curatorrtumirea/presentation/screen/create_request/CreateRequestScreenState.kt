package com.example.curatorrtumirea.presentation.screen.create_request

data class CreateRequestScreenState(
    val title: String = "",
    val description: String = "",
    val isCreating: Boolean = false,
    val isInputValid: Boolean = false,
)

sealed class CreateRequestEffect {

}

sealed class CreateRequestEvent {
    data class ChangeTitle(val value: String) : CreateRequestEvent()
    data class ChangeDescription(val value: String) : CreateRequestEvent()
    data object CreateRequest : CreateRequestEvent()
}
