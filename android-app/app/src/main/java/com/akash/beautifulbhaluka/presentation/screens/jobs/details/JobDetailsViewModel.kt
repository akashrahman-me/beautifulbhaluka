package com.akash.beautifulbhaluka.presentation.screens.jobs.details

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JobDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JobDetailsUiState())
    val uiState: StateFlow<JobDetailsUiState> = _uiState.asStateFlow()

    fun onAction(action: JobDetailsAction) {
        when (action) {
            is JobDetailsAction.LoadJobDetails -> loadJobDetails(action.jobId)
            is JobDetailsAction.ShowApplicationDialog -> showApplicationDialog()
            is JobDetailsAction.HideApplicationDialog -> hideApplicationDialog()
            is JobDetailsAction.SelectPdf -> selectPdf(action.uri)
            is JobDetailsAction.UpdateApplicationNote -> updateApplicationNote(action.note)
            is JobDetailsAction.SubmitApplication -> submitApplication()
            is JobDetailsAction.ResetApplicationState -> resetApplicationState()
        }
    }

    private fun loadJobDetails(jobId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Simulate API call delay
                delay(500)

                // Get mock job details - in real app, this would call repository
                val job = getMockJobDetails(jobId)

                if (job != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            job = job,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Job not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load job details"
                    )
                }
            }
        }
    }

    private fun showApplicationDialog() {
        _uiState.update { it.copy(showApplicationDialog = true) }
    }

    private fun hideApplicationDialog() {
        _uiState.update {
            it.copy(
                showApplicationDialog = false,
                selectedPdfUri = null,
                applicationNote = "",
                applicationState = ApplicationState.Idle
            )
        }
    }

    private fun selectPdf(uri: Uri?) {
        _uiState.update { it.copy(selectedPdfUri = uri) }
    }

    private fun updateApplicationNote(note: String) {
        _uiState.update { it.copy(applicationNote = note) }
    }

    private fun submitApplication() {
        viewModelScope.launch {
            _uiState.update { it.copy(applicationState = ApplicationState.Submitting) }

            try {
                // Simulate API submission delay
                delay(2000)

                // Simulate successful submission
                _uiState.update { it.copy(applicationState = ApplicationState.Success) }

                // Auto-hide dialog after 1 second
                delay(1000)
                hideApplicationDialog()

            } catch (e: Exception) {
                _uiState.update { it.copy(applicationState = ApplicationState.Error) }
            }
        }
    }

    private fun resetApplicationState() {
        _uiState.update { it.copy(applicationState = ApplicationState.Idle) }
    }

    // Mock data function - replace with actual repository call
    private fun getMockJobDetails(jobId: String): JobItem? {
        // Generate mock job data based on jobId - same pattern as JobsViewModel
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

        val index = jobId.removePrefix("job_").toIntOrNull() ?: 1

        return JobItem(
            id = jobId,
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
            imageUrl = "https://picsum.photos/400/250?random=${20 + index}",
            categoryId = categories[index % categories.size],
            isFeatured = index % 7 == 0,
            positionCount = positionCounts[index % positionCounts.size],
            jobType = jobTypes[index % jobTypes.size],
            workingHours = workingHours[index % workingHours.size],
            workLocation = workLocations[index % workLocations.size]
        )
    }
}
