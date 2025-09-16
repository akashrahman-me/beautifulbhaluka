package com.akash.beautifulbhaluka.presentation.screens.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JobsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JobsUiState())
    val uiState: StateFlow<JobsUiState> = _uiState.asStateFlow()

    init {
        loadJobs()
    }

    fun onAction(action: JobsAction) {
        when (action) {
            is JobsAction.LoadJobs -> loadJobs()
            is JobsAction.Refresh -> refresh()
            is JobsAction.ViewJobDetails -> viewJobDetails(action.jobId)
        }
    }

    private fun loadJobs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Simulate API call - replace with actual repository/use case
                val jobs = getMockJobs()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        jobs = jobs,
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

            try {
                val jobs = getMockJobs()
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        jobs = jobs,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        error = e.message ?: "Failed to refresh"
                    )
                }
            }
        }
    }

    private fun viewJobDetails(jobId: String) {
        // Handle job details navigation
        // This could trigger navigation to job details screen
    }

    // Mock data - replace with actual repository call
    private fun getMockJobs(): List<JobItem> {
        return listOf(
            JobItem(
                id = "1",
                title = "Android Developer",
                company = "Tech Company Ltd.",
                location = "Dhaka, Bangladesh",
                salary = "50,000 - 80,000 BDT",
                experience = "2-3 years",
                education = "Bachelor's in CSE/IT",
                description = "We are looking for an experienced Android developer to join our team...",
                postedDate = "2 days ago",
                deadline = "30th Sep, 2025",
                contactInfo = "hr@techcompany.com"
            ),
            JobItem(
                id = "2",
                title = "Flutter Developer",
                company = "Mobile Solutions Inc.",
                location = "Chittagong, Bangladesh",
                salary = "40,000 - 70,000 BDT",
                experience = "1-2 years",
                education = "Bachelor's in Computer Science",
                description = "Join our mobile development team to build amazing Flutter applications...",
                postedDate = "1 week ago",
                deadline = "15th Oct, 2025",
                contactInfo = "careers@mobilesolutions.com"
            )
        )
    }
}
