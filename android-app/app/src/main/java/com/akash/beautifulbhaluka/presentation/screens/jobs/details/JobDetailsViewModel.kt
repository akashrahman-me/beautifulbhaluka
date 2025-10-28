package com.akash.beautifulbhaluka.presentation.screens.jobs.details

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.data.repository.JobRepositoryImpl
import com.akash.beautifulbhaluka.domain.model.Job
import com.akash.beautifulbhaluka.domain.repository.JobRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JobDetailsViewModel : ViewModel() {

    private val jobRepository: JobRepository = JobRepositoryImpl()
    private val _uiState = MutableStateFlow(JobDetailsUiState())
    val uiState: StateFlow<JobDetailsUiState> = _uiState.asStateFlow()

    fun onAction(action: JobDetailsAction) {
        when (action) {
            is JobDetailsAction.LoadJobDetails -> loadJobDetails(action.jobId)
            is JobDetailsAction.ShowApplicationDialog -> showApplicationDialog()
            is JobDetailsAction.HideApplicationDialog -> hideApplicationDialog()
            is JobDetailsAction.DismissApplicationDialog -> hideApplicationDialog()
            is JobDetailsAction.SelectPdf -> selectPdf(action.uri)
            is JobDetailsAction.UpdateApplicationNote -> updateApplicationNote(action.note)
            is JobDetailsAction.UpdateApplicantName -> updateApplicantName(action.name)
            is JobDetailsAction.UpdateApplicantEmail -> updateApplicantEmail(action.email)
            is JobDetailsAction.UpdateApplicantPhone -> updateApplicantPhone(action.phone)
            is JobDetailsAction.SubmitApplication -> submitApplication()
            is JobDetailsAction.ResetApplicationState -> resetApplicationState()
        }
    }

    private fun loadJobDetails(jobId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Use repository to get job details
                jobRepository.getJobById(jobId)
                    .onSuccess { job ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                job = job,
                                error = null
                            )
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "চাকরির তথ্য পাওয়া যায়নি"
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "একটি ত্রুটি ঘটেছে"
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
                applicantName = "",
                applicantEmail = "",
                applicantPhone = "",
                applicationState = ApplicationState.Idle,
                isSubmitting = false
            )
        }
    }

    private fun selectPdf(uri: Uri?) {
        _uiState.update { it.copy(selectedPdfUri = uri) }
    }

    private fun updateApplicationNote(note: String) {
        _uiState.update { it.copy(applicationNote = note) }
    }

    private fun updateApplicantName(name: String) {
        _uiState.update { it.copy(applicantName = name) }
    }

    private fun updateApplicantEmail(email: String) {
        _uiState.update { it.copy(applicantEmail = email) }
    }

    private fun updateApplicantPhone(phone: String) {
        _uiState.update { it.copy(applicantPhone = phone) }
    }

    private fun submitApplication() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    applicationState = ApplicationState.Submitting,
                    isSubmitting = true
                )
            }

            try {
                val currentState = _uiState.value
                val job = currentState.job

                if (job != null) {
                    // Submit application to repository
                    jobRepository.applyForJob(
                        jobId = job.id,
                        coverLetter = currentState.applicationNote,
                        resumeUrl = currentState.selectedPdfUri?.toString()
                    ).onSuccess {
                        _uiState.update {
                            it.copy(
                                applicationState = ApplicationState.Success,
                                isSubmitting = false
                            )
                        }

                        // Auto-hide dialog after 1 second
                        delay(1000)
                        hideApplicationDialog()
                    }.onFailure { error ->
                        _uiState.update {
                            it.copy(
                                applicationState = ApplicationState.Error,
                                isSubmitting = false,
                                error = error.message
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            applicationState = ApplicationState.Error,
                            isSubmitting = false,
                            error = "চাকরির তথ্য পাওয়া যায়নি"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        applicationState = ApplicationState.Error,
                        isSubmitting = false,
                        error = e.message
                    )
                }
            }
        }
    }

    private fun resetApplicationState() {
        _uiState.update { it.copy(applicationState = ApplicationState.Idle) }
    }
}
