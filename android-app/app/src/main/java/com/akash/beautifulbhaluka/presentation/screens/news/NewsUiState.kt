package com.akash.beautifulbhaluka.presentation.screens.news

import com.akash.beautifulbhaluka.domain.model.NewsArticle

data class NewsUiState(
    val isLoading: Boolean = false,
    val articles: List<NewsArticle> = emptyList(),
    val error: String? = null,
    val isSubmitting: Boolean = false,
    val showSubmitDialog: Boolean = false,
    val submitUrl: String = "",
    val submitError: String? = null
)

sealed class NewsAction {
    object LoadArticles : NewsAction()
    object Refresh : NewsAction()
    object ShowSubmitDialog : NewsAction()
    object HideSubmitDialog : NewsAction()
    data class UpdateSubmitUrl(val url: String) : NewsAction()
    object SubmitArticle : NewsAction()
    data class DeleteArticle(val id: String) : NewsAction()
    data class OpenArticle(val url: String) : NewsAction()
}

