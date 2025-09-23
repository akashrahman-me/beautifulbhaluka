package com.akash.beautifulbhaluka.presentation.screens.jobs

import com.akash.beautifulbhaluka.presentation.screens.home.CarouselItem

data class JobsUiState(
    val isLoading: Boolean = false,
    val jobs: List<JobItem> = emptyList(),
    val carouselItems: List<CarouselItem> = emptyList(),
    val categories: List<JobCategory> = emptyList(),
    val selectedCategory: String? = null,
    val currentTab: JobTab = JobTab.JOB_FEEDS,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
    val appliedJobs: List<AppliedJob> = emptyList(),
    val favoriteJobs: Set<String> = emptySet() // Added missing property
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
    val contactInfo: String,
    val imageUrl: String? = null,
    val categoryId: String,
    val isFeatured: Boolean = false,
    // Additional fields for complete job details
    val positionCount: String = "১০০ টি",
    val jobType: String = "ফুল-টাইম",
    val workingHours: String = "৮ ঘন্টা",
    val workLocation: String = "অন-সাইট"
)

data class JobCategory(
    val id: String,
    val name: String,
    val imageUrl: String,
    val jobCount: Int
)

enum class JobTab {
    JOB_FEEDS,
    MANAGE_JOBS
}

sealed class JobsAction {
    object LoadJobs : JobsAction()
    object Refresh : JobsAction()
    data class ViewJobDetails(val jobId: String) : JobsAction()
    data class SelectTab(val tab: JobTab) : JobsAction()
    data class SelectCategory(val categoryId: String) : JobsAction()
    data class ToggleFavorite(val jobId: String) : JobsAction()
    data class LoadPage(val page: Int) : JobsAction()
    object LoadNextPage : JobsAction()
    object LoadPreviousPage : JobsAction()
    data class ViewCarouselItem(val itemId: String) : JobsAction()
    object NavigateToPublishJob : JobsAction()
}

data class AppliedJob(
    val jobItem: JobItem,
    val applicationStatus: ApplicationStatus,
    val appliedDate: String
)

enum class ApplicationStatus {
    APPLIED,
    REVIEWED,
    SHORTLISTED,
    REJECTED,
    ACCEPTED
}
