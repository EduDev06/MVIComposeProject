package com.example.mvicomposeproject.ui

sealed class GenderEvent {
    data class ShowToast(val message: String): GenderEvent()
}