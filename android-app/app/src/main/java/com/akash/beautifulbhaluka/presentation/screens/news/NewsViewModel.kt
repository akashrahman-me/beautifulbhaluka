package com.akash.beautifulbhaluka.presentation.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.usecase.GetNewsArticlesUseCase
import com.akash.beautifulbhaluka.domain.usecase.SubmitNewsArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsArticlesUseCase: GetNewsArticlesUseCase,
    private val submitNewsArticleUseCase: SubmitNewsArticleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    fun onAction(action: NewsAction) {
        when (action) {
            is NewsAction.LoadArticles -> loadArticles()
            is NewsAction.Refresh -> refresh()
            is NewsAction.ShowSubmitDialog -> showSubmitDialog()
            is NewsAction.HideSubmitDialog -> hideSubmitDialog()
            is NewsAction.UpdateSubmitUrl -> updateSubmitUrl(action.url)
            is NewsAction.SubmitArticle -> submitArticle()
            is NewsAction.DeleteArticle -> deleteArticle(action.id)
            is NewsAction.OpenArticle -> {} // Handled in UI layer
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getNewsArticlesUseCase()
                .onSuccess { articles ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            articles = articles,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load articles"
                        )
                    }
                }
        }
    }

    private fun refresh() {
        loadArticles()
    }

    private fun showSubmitDialog() {
        _uiState.update {
            it.copy(
                showSubmitDialog = true,
                submitUrl = "",
                submitError = null
            )
        }
    }

    private fun hideSubmitDialog() {
        _uiState.update {
            it.copy(
                showSubmitDialog = false,
                submitUrl = "",
                submitError = null
            )
        }
    }

    private fun updateSubmitUrl(url: String) {
        _uiState.update {
            it.copy(submitUrl = url, submitError = null)
        }
    }

    private fun submitArticle() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, submitError = null) }
            submitNewsArticleUseCase(_uiState.value.submitUrl)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            showSubmitDialog = false,
                            submitUrl = "",
                            submitError = null
                        )
                    }
                    loadArticles() // Refresh the list
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            submitError = error.message ?: "Failed to submit article"
                        )
                    }
                }
        }
    }

    private fun deleteArticle(id: String) {
        viewModelScope.launch {
            // In real app, call repository delete method
            _uiState.update {
                it.copy(articles = it.articles.filter { article -> article.id != id })
            }
        }
    }
}

