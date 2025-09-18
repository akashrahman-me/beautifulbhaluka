package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JobsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JobsUiState())
    val uiState: StateFlow<JobsUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    fun onAction(action: JobsAction) {
        when (action) {
            is JobsAction.LoadJobs -> loadJobs()
            is JobsAction.Refresh -> refresh()
            is JobsAction.ViewJobDetails -> {
                // Navigation is now handled at the screen level, not in ViewModel
                // This case is handled in JobsScreen.kt
            }

            is JobsAction.NavigateToPublishJob -> {
                // Navigation is now handled at the screen level, not in ViewModel
                // This case is handled in JobsScreen.kt
            }

            is JobsAction.SelectTab -> selectTab(action.tab)
            is JobsAction.SelectCategory -> selectCategory(action.categoryId)
            is JobsAction.ToggleFavorite -> toggleFavorite(action.jobId)
            is JobsAction.LoadPage -> loadPage(action.page)
            is JobsAction.LoadNextPage -> loadNextPage()
            is JobsAction.LoadPreviousPage -> loadPreviousPage()
            is JobsAction.ViewCarouselItem -> viewCarouselItem(action.itemId)
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Load all initial data
            val categories = getMockCategories()
            val carouselItems = getMockCarouselItems()
            val appliedJobs = getMockAppliedJobs()
            loadJobsForPage(1)

            _uiState.update { currentState ->
                currentState.copy(
                    categories = categories,
                    carouselItems = carouselItems,
                    appliedJobs = appliedJobs,
                    isLoading = false
                )
            }
        }
    }

    private fun loadJobs() {
        loadJobsForPage(_uiState.value.currentPage)
    }

    private fun loadJobsForPage(page: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Simulate API call delay
                delay(500)

                val (jobs, totalPages) = getMockJobsForPage(page)

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        jobs = jobs,
                        currentPage = page,
                        totalPages = totalPages,
                        hasNextPage = page < totalPages,
                        hasPreviousPage = page > 1,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
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
        _uiState.update { it.copy(selectedTab = tab) }
    }

    private fun selectCategory(categoryId: String) {
        // Handle category selection - filter jobs or navigate to category screen
        println("Category selected: $categoryId")
    }

    private fun toggleFavorite(jobId: String) {
        _uiState.update { currentState ->
            val favoriteJobs = currentState.favoriteJobs.toMutableSet()
            if (favoriteJobs.contains(jobId)) {
                favoriteJobs.remove(jobId)
            } else {
                favoriteJobs.add(jobId)
            }
            currentState.copy(favoriteJobs = favoriteJobs)
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

    private fun viewCarouselItem(itemId: String) {
        // Handle carousel item click
        println("Carousel item clicked: $itemId")
    }

    // Mock data functions
    private fun getMockCategories(): List<JobCategory> {
        return listOf(
            JobCategory("1", "Software Development", "https://picsum.photos/200/120?random=1", 45),
            JobCategory("2", "Digital Marketing", "https://picsum.photos/200/120?random=2", 32),
            JobCategory("3", "Design & Creative", "https://picsum.photos/200/120?random=3", 28),
            JobCategory("4", "Sales & Business", "https://picsum.photos/200/120?random=4", 19),
            JobCategory("5", "Education", "https://picsum.photos/200/120?random=5", 15),
            JobCategory("6", "Healthcare", "https://picsum.photos/200/120?random=6", 22),
            JobCategory("7", "Engineering", "https://picsum.photos/200/120?random=7", 31),
            JobCategory("8", "Finance", "https://picsum.photos/200/120?random=8", 18)
        )
    }

    private fun getMockCarouselItems(): List<CarouselItem> {
        return listOf(
            CarouselItem(
                "1",
                "Join Top Tech Companies",
                "Discover amazing opportunities in leading tech firms",
                "https://picsum.photos/400/200?random=10"
            ),
            CarouselItem(
                "2",
                "Remote Work Opportunities",
                "Work from anywhere with flexible remote positions",
                "https://picsum.photos/400/200?random=11"
            ),
            CarouselItem(
                "3",
                "Career Growth Programs",
                "Accelerate your career with mentorship and training",
                "https://picsum.photos/400/200?random=12"
            ),
            CarouselItem(
                "4",
                "High Salary Jobs",
                "Find jobs with competitive salaries and benefits",
                "https://picsum.photos/400/200?random=13"
            )
        )
    }

    private fun getMockJobsForPage(page: Int): Pair<List<JobItem>, Int> {
        val jobsPerPage = 6
        val totalJobs = 50
        val totalPages = (totalJobs + jobsPerPage - 1) / jobsPerPage

        val startIndex = (page - 1) * jobsPerPage
        val endIndex = minOf(startIndex + jobsPerPage, totalJobs)

        val allJobs = generateMockJobs()
        val pageJobs = if (startIndex < allJobs.size) {
            allJobs.subList(startIndex, minOf(endIndex, allJobs.size))
        } else {
            emptyList()
        }

        return Pair(pageJobs, totalPages)
    }

    private fun generateMockJobs(): List<JobItem> {
        val companies = listOf(
            "Tech Corp",
            "Digital Solutions",
            "Creative Agency",
            "StartupXYZ",
            "Global Tech",
            "Innovation Hub"
        )
        val jobTitles = listOf(
            "Android Developer",
            "iOS Developer",
            "Full Stack Developer",
            "UI/UX Designer",
            "Product Manager",
            "DevOps Engineer",
            "Data Scientist",
            "QA Engineer"
        )
        val locations = listOf("ঢাকা", "চট্টগ্রাম", "সিলেট", "রাজশাহী", "খুলনা", "বরিশাল")
        val categories = listOf("1", "2", "3", "4", "5", "6", "7", "8")
        val experiences = listOf("ফ্রেশার", "১-২ বছর", "২-৩ বছর", "৩-৫ বছর", "৫+ বছর")
        val educations = listOf("এস এস সি/ সমমান", "এইচ এস সি/ সমমান", "স্নাতক", "স্নাতকোত্তর")
        val jobTypes = listOf("ফুল-টাইম", "পার্ট-টাইম", "চুক্তিভিত্তিক", "ইন্টার্নশিপ")
        val workingHours = listOf("৮ ঘন্টা", "৬ ঘন্টা", "১০ ঘন্টা", "ফ্লেক্সিবল")
        val workLocations = listOf("অন-সাইট", "রিমোট", "হাইব্রিড")
        val positionCounts = listOf("৫ টি", "১০ টি", "২০ টি", "৫০ টি", "১০০ টি")

        return (1..50).map { index ->
            JobItem(
                id = "job_$index",
                title = jobTitles[index % jobTitles.size],
                company = companies[index % companies.size],
                location = locations[index % locations.size],
                salary = "${(19 + index * 2)},০০০-${(25 + index * 3)},০০০ টাকা",
                experience = experiences[index % experiences.size],
                education = educations[index % educations.size],
                description = "আমরা একজন অভিজ্ঞ ${jobTitles[index % jobTitles.size]} খুঁজছি যিনি আমাদের দলে যোগ দেবেন।",
                postedDate = "${1 + index % 30} দিন আগে",
                deadline = "${15 + index % 15}/${if (index % 2 == 0) "০৯" else "১০"}/২০২৫",
                contactInfo = "+০১৮১২৩৪৫৬৭৮৯\nadmin@${
                    companies[index % companies.size].lowercase().replace(" ", "")
                }.com",
                imageUrl = "https://picsum.photos/300/200?random=${20 + index}",
                categoryId = categories[index % categories.size],
                isFeatured = index % 7 == 0,
                positionCount = positionCounts[index % positionCounts.size],
                jobType = jobTypes[index % jobTypes.size],
                workingHours = workingHours[index % workingHours.size],
                workLocation = workLocations[index % workLocations.size]
            )
        }
    }

    private fun getMockAppliedJobs(): List<AppliedJob> {
        val allJobs = generateMockJobs()
        val appliedStatuses = listOf(
            ApplicationStatus.APPLIED,
            ApplicationStatus.REVIEWED,
            ApplicationStatus.SHORTLISTED,
            ApplicationStatus.REJECTED,
            ApplicationStatus.ACCEPTED
        )

        // Simulate user has applied to first 8 jobs with different statuses
        return allJobs.take(8).mapIndexed { index, job ->
            AppliedJob(
                jobItem = job,
                applicationStatus = appliedStatuses[index % appliedStatuses.size],
                appliedDate = "${(index + 1) * 2} দিন আগে"
            )
        }
    }
}
