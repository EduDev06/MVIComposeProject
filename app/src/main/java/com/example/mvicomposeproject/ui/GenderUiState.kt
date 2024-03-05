package com.example.mvicomposeproject.ui

import com.example.mvicomposeproject.domain.model.GenderUser

data class GenderUiState(
    val genderUser: GenderUser? = null,
    val isLoading: Boolean = false
)
