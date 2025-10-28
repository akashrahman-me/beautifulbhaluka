package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.JobRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.JobCategory
import com.akash.beautifulbhaluka.domain.repository.JobRepository
import com.akash.beautifulbhaluka.domain.usecase.GetFavoriteJobsUseCase
import com.akash.beautifulbhaluka.domain.usecase.GetJobsUseCase
import com.akash.beautifulbhaluka.domain.usecase.ToggleFavoriteJobUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for Jobs Screen following MVVM architecture
 * Uses Use Cases for business logic operations
 */
class JobsViewModel : ViewModel() {

    // Create instances directly without dependency injection
    private val jobRepository: JobRepository = JobRepositoryImpl()
    private val getJobsUseCase: GetJobsUseCase = GetJobsUseCase(jobRepository)
    private val toggleFavoriteJobUseCase: ToggleFavoriteJobUseCase = ToggleFavoriteJobUseCase(jobRepository)
    private val getFavoriteJobsUseCase: GetFavoriteJobsUseCase = GetFavoriteJobsUseCase(jobRepository)

    private val _uiState = MutableStateFlow(JobsUiState())
    val uiState: StateFlow<JobsUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
        observeFavorites()
    }

    fun onAction(action: JobsAction) {
        when (action) {
            is JobsAction.LoadJobs -> loadJobs()
            is JobsAction.Refresh -> refresh()
            is JobsAction.ViewJobDetails -> {
                // Navigation handled at screen level
            }
            is JobsAction.NavigateToPublishJob -> {
                // Navigation handled at screen level
            }
            is JobsAction.SelectTab -> selectTab(action.tab)
            is JobsAction.FilterByCategory -> filterByCategory(action.category)
            is JobsAction.SearchJobs -> searchJobs(action.query)
            is JobsAction.ToggleFavorite -> toggleFavorite(action.jobId)
            is JobsAction.LoadPage -> loadPage(action.page)
            is JobsAction.LoadNextPage -> loadNextPage()
            is JobsAction.LoadPreviousPage -> loadPreviousPage()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Load featured jobs
            jobRepository.getFeaturedJobs()
                .onSuccess { featured ->
                    _uiState.update { it.copy(featuredJobs = featured) }
                }

            // Load applications
            jobRepository.getUserApplications()
                .onSuccess { applications ->
                    _uiState.update { it.copy(appliedJobs = applications) }
                }

            // Load first page of jobs
            loadJobsForPage(1)
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            jobRepository.observeFavoriteJobIds()
                .collect { favoriteIds ->
                    _uiState.update { it.copy(favoriteJobIds = favoriteIds) }
                }
        }
    }

    private fun loadJobs() {
        loadJobsForPage(_uiState.value.currentPage)
    }

    private fun loadJobsForPage(page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val currentState = _uiState.value
            getJobsUseCase(
                category = if (currentState.selectedCategory == JobCategory.ALL) null else currentState.selectedCategory,
                searchQuery = currentState.searchQuery.ifBlank { null },
                page = page,
                pageSize = 10
            ).onSuccess { (jobs, totalPages) ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        jobs = jobs,
                        currentPage = page,
                        totalPages = totalPages,
                        hasNextPage = page < totalPages,
                        hasPreviousPage = page > 1,
                        error = null
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "একটি ত্রুটি ঘটেছে"
                    )
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            loadInitialData()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    private fun selectTab(tab: JobTab) {
        _uiState.update { it.copy(currentTab = tab) }

        when (tab) {
            JobTab.JOB_FEEDS -> loadJobs()
            JobTab.MY_APPLICATIONS -> loadApplications()
            JobTab.FAVORITES -> loadFavorites()
        }
    }

    private fun filterByCategory(category: JobCategory) {
        _uiState.update { it.copy(selectedCategory = category, currentPage = 1) }
        loadJobsForPage(1)
    }

    private fun searchJobs(query: String) {
        _uiState.update { it.copy(searchQuery = query, currentPage = 1) }
        loadJobsForPage(1)
    }

    private fun toggleFavorite(jobId: String) {
        viewModelScope.launch {
            toggleFavoriteJobUseCase(jobId)
                .onFailure { error ->
                    // Show error message
                    _uiState.update { it.copy(error = error.message) }
                }
        }
    }

    private fun loadPage(page: Int) {
        loadJobsForPage(page)
    }

    private fun loadNextPage() {
        val currentState = _uiState.value
        if (currentState.hasNextPage) {
            loadJobsForPage(currentState.currentPage + 1)
        }
    }

    private fun loadPreviousPage() {
        val currentState = _uiState.value
        if (currentState.hasPreviousPage) {
            loadJobsForPage(currentState.currentPage - 1)
        }
    }

    private fun loadApplications() {
        viewModelScope.launch {
            jobRepository.getUserApplications()
                .onSuccess { applications ->
                    _uiState.update { it.copy(appliedJobs = applications) }
                }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getFavoriteJobsUseCase()
                .onSuccess { favorites ->
                    _uiState.update {
                        it.copy(isLoading = false, jobs = favorites, error = null)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message)
                    }
                }
        }
    }
}
