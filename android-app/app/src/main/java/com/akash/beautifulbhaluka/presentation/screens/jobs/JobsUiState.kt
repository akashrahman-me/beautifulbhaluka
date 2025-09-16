package com.akash.beautifulbhaluka.presentation.screens.jobs

data class JobsUiState(
    val isLoading: Boolean = false,
    val jobs: List<JobItem> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)

data class JobItem(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val experience: String,
    val education: String,
    val description: String,
    val postedDate: String,
    val deadline: String,
    val contactInfo: String
)

sealed class JobsAction {
    object LoadJobs : JobsAction()
    object Refresh : JobsAction()
    data class ViewJobDetails(val jobId: String) : JobsAction()
}
