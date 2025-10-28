package com.akash.beautifulbhaluka.presentation.screens.jobs.publish

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.JobCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublishJobViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishJobUiState())
    val uiState: StateFlow<PublishJobUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun onAction(action: PublishJobAction) {
        when (action) {
            is PublishJobAction.UpdateTitle -> updateTitle(action.title)
            is PublishJobAction.UpdateCompany -> updateCompany(action.company)
            is PublishJobAction.UpdateDescription -> updateDescription(action.description)
            is PublishJobAction.UpdateLocation -> updateLocation(action.location)
            is PublishJobAction.UpdateSalary -> updateSalary(action.salary)
            is PublishJobAction.UpdatePositionCount -> updatePositionCount(action.count)
            is PublishJobAction.UpdateDeadline -> updateDeadline(action.deadline)
            is PublishJobAction.UpdateEducation -> updateEducation(action.education)
            is PublishJobAction.UpdateExperience -> updateExperience(action.experience)
            is PublishJobAction.UpdateJobType -> updateJobType(action.jobType)
            is PublishJobAction.UpdateWorkingHours -> updateWorkingHours(action.hours)
            is PublishJobAction.UpdateWorkLocation -> updateWorkLocation(action.workLocation)
            is PublishJobAction.UpdatePhoneNumber -> updatePhoneNumber(action.phoneNumber)
            is PublishJobAction.UpdateEmail -> updateEmail(action.email)
            is PublishJobAction.SelectImage -> selectImage(action.uri)
            is PublishJobAction.SelectCategory -> selectCategory(action.category)
            is PublishJobAction.LoadCategories -> loadCategories()
            is PublishJobAction.PublishJob -> publishJob()
            is PublishJobAction.ClearError -> clearError()
            is PublishJobAction.ResetForm -> resetForm()
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun updateCompany(company: String) {
        _uiState.update { it.copy(company = company) }
    }

    private fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    private fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    private fun updateSalary(salary: String) {
        _uiState.update { it.copy(salary = salary) }
    }

    private fun updatePositionCount(count: String) {
        _uiState.update { it.copy(positionCount = count) }
    }

    private fun updateDeadline(deadline: String) {
        _uiState.update { it.copy(deadline = deadline) }
    }

    private fun updateEducation(education: String) {
        _uiState.update { it.copy(education = education) }
    }

    private fun updateExperience(experience: String) {
        _uiState.update { it.copy(experience = experience) }
    }

    private fun updateJobType(jobType: String) {
        _uiState.update { it.copy(jobType = jobType) }
    }

    private fun updateWorkingHours(hours: String) {
        _uiState.update { it.copy(workingHours = hours) }
    }

    private fun updateWorkLocation(workLocation: String) {
        _uiState.update { it.copy(workLocation = workLocation) }
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun selectImage(uri: Uri?) {
        _uiState.update { it.copy(selectedImageUri = uri) }
    }

    private fun selectCategory(category: JobCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Mock categories - replace with actual repository call
                val categories = getMockCategories()
                _uiState.update {
                    it.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load categories"
                    )
                }
            }
        }
    }

    private fun publishJob() {
        viewModelScope.launch {
            val currentState = _uiState.value

            // Validation
            val validationError = validateJobData(currentState)
            if (validationError != null) {
                _uiState.update { it.copy(error = validationError) }
                return@launch
            }

            _uiState.update { it.copy(isPublishing = true, error = null) }

            try {
                // Simulate API call
                delay(2000)

                // In real app, call repository to publish job
                // publishJobRepository.publishJob(createJobFromState(currentState))

                _uiState.update {
                    it.copy(
                        isPublishing = false,
                        publishSuccess = true
                    )
                }

                // Auto-reset success state after 3 seconds
                delay(3000)
                resetForm()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isPublishing = false,
                        error = e.message ?: "Failed to publish job"
                    )
                }
            }
        }
    }

    private fun validateJobData(state: PublishJobUiState): String? {
        return when {
            state.title.isBlank() -> "Job title is required"
            state.company.isBlank() -> "Company name is required"
            state.location.isBlank() -> "Location is required"
            state.salary.isBlank() -> "Salary is required"
            state.positionCount.isBlank() -> "Position count is required"
            state.deadline.isBlank() -> "Application deadline is required"
            state.education.isBlank() -> "Education requirement is required"
            state.experience.isBlank() -> "Experience requirement is required"
            state.jobType.isBlank() -> "Job type is required"
            state.workingHours.isBlank() -> "Working hours is required"
            state.workLocation.isBlank() -> "Work location is required"
            state.phoneNumber.isBlank() -> "Phone number is required"
            state.email.isBlank() -> "Email is required"
            state.selectedCategory == null -> "Please select a job category"
            state.selectedImageUri == null -> "Please select a job thumbnail image"
            else -> null
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun resetForm() {
        _uiState.update {
            PublishJobUiState(
                categories = it.categories,
                publishSuccess = false
            )
        }
    }

    // Mock data function - replace with actual repository call
    private fun getMockCategories(): List<JobCategory> {
        return listOf(
            JobCategory.IT,
            JobCategory.ENGINEERING,
            JobCategory.HEALTHCARE,
            JobCategory.EDUCATION,
            JobCategory.BUSINESS,
            JobCategory.MARKETING,
            JobCategory.DESIGN,
            JobCategory.FINANCE,
            JobCategory.SALES,
            JobCategory.HR,
            JobCategory.OPERATIONS
        )
    }
}
