package com.akash.beautifulbhaluka.presentation.screens.jobs

data class JobsUiState(
    val isLoading: Boolean = false,
    val jobs: List<JobItem> = emptyList(),
    val appliedJobs: List<AppliedJob> = emptyList(),
    val categories: List<JobCategory> = emptyList(),
    val carouselItems: List<CarouselItem> = emptyList(),
    val favoriteJobs: Set<String> = emptySet(),
    val selectedTab: JobTab = JobTab.JOB_FEEDS,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val hasNextPage: Boolean = false,
    val hasPreviousPage: Boolean = false,
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

data class CarouselItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val actionUrl: String? = null
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
