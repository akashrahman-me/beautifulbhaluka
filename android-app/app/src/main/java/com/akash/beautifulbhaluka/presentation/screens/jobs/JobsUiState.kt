package com.akash.beautifulbhaluka.presentation.screens.jobs

import com.akash.beautifulbhaluka.domain.model.Job
import com.akash.beautifulbhaluka.domain.model.JobApplication
import com.akash.beautifulbhaluka.domain.model.JobCategory

data class JobsUiState(
    val isLoading: Boolean = false,
    val jobs: List<Job> = emptyList(),
    val featuredJobs: List<Job> = emptyList(),
    val selectedCategory: JobCategory = JobCategory.ALL,
    val searchQuery: String = "",
    val currentTab: JobTab = JobTab.JOB_FEEDS,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
    val appliedJobs: List<JobApplication> = emptyList(),
    val favoriteJobIds: Set<String> = emptySet()
)

enum class JobTab(val displayName: String, val banglaName: String) {
    JOB_FEEDS("Job Feeds", "চাকরি ফিড"),
    MY_APPLICATIONS("My Applications", "আমার আবেদন"),
    FAVORITES("Favorites", "সেভ করা")
}

sealed class JobsAction {
    object LoadJobs : JobsAction()
    object Refresh : JobsAction()
    data class ViewJobDetails(val jobId: String) : JobsAction()
    data class SelectTab(val tab: JobTab) : JobsAction()
    data class FilterByCategory(val category: JobCategory) : JobsAction()
    data class SearchJobs(val query: String) : JobsAction()
    data class ToggleFavorite(val jobId: String) : JobsAction()
    data class LoadPage(val page: Int) : JobsAction()
    object LoadNextPage : JobsAction()
    object LoadPreviousPage : JobsAction()
    object NavigateToPublishJob : JobsAction()
}
