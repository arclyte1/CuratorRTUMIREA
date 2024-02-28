package com.example.curatorrtumirea.presentation.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State, Effect, Event>(initialState: State) : ViewModel() {

    private val _state = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    abstract fun onEvent(event: Event)

    protected suspend fun setState(newState: State) {
        _state.emit(newState)
    }

    protected suspend fun sendEffect(newEffect: Effect) {
        _effect.emit(newEffect)
    }
}