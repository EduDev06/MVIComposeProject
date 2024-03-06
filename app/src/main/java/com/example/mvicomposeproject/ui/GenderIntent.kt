package com.example.mvicomposeproject.ui

sealed class GenderIntent {
    data class SearchGenderUser(val name: String): GenderIntent()
}