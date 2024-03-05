package com.example.mvicomposeproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UI_STATE, EFFECT, INTENT>: ViewModel() {

    private val initialState: UI_STATE by lazy { createInitialState() }

    private val _uiState: MutableStateFlow<UI_STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val intents: MutableSharedFlow<INTENT> = MutableSharedFlow()

    private val _effect = Channel<EFFECT>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        subscribeIntents()
    }

    private fun subscribeIntents() {
        viewModelScope.launch {
            intents.collect {
                handleIntent(it)
            }
        }
    }

    fun setIntent(intent: INTENT) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    protected fun setState(reduce: UI_STATE.() -> UI_STATE) {
        _uiState.value = uiState.value.reduce()
    }

    protected fun setEvent(builder: () -> EFFECT) {
        viewModelScope.launch { _effect.send(builder()) }
    }

    protected abstract fun createInitialState(): UI_STATE

    protected abstract fun handleIntent(intent: INTENT)

}