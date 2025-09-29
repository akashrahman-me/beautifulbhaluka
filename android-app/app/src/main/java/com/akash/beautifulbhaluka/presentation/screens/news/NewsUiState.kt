package com.akash.beautifulbhaluka.presentation.screens.news

import com.akash.beautifulbhaluka.domain.model.Journalist

data class NewsUiState(
    val isLoading: Boolean = false,
    val journalists: List<Journalist> = emptyList(),
    val error: String? = null
)
