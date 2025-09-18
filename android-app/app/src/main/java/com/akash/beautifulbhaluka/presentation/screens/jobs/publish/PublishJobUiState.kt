package com.akash.beautifulbhaluka.presentation.screens.jobs.publish

import android.net.Uri
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobCategory

data class PublishJobUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val company: String = "",
    val location: String = "",
    val salary: String = "",
    val positionCount: String = "",
    val deadline: String = "",
    val education: String = "",
    val experience: String = "",
    val jobType: String = "",
    val workingHours: String = "",
    val workLocation: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val selectedImageUri: Uri? = null,
    val selectedCategory: JobCategory? = null,
    val categories: List<JobCategory> = emptyList(),
    val error: String? = null,
    val isPublishing: Boolean = false,
    val publishSuccess: Boolean = false
)

sealed class PublishJobAction {
    data class UpdateTitle(val title: String) : PublishJobAction()
    data class UpdateCompany(val company: String) : PublishJobAction()
    data class UpdateLocation(val location: String) : PublishJobAction()
    data class UpdateSalary(val salary: String) : PublishJobAction()
    data class UpdatePositionCount(val count: String) : PublishJobAction()
    data class UpdateDeadline(val deadline: String) : PublishJobAction()
    data class UpdateEducation(val education: String) : PublishJobAction()
    data class UpdateExperience(val experience: String) : PublishJobAction()
    data class UpdateJobType(val jobType: String) : PublishJobAction()
    data class UpdateWorkingHours(val hours: String) : PublishJobAction()
    data class UpdateWorkLocation(val workLocation: String) : PublishJobAction()
    data class UpdatePhoneNumber(val phoneNumber: String) : PublishJobAction()
    data class UpdateEmail(val email: String) : PublishJobAction()
    data class SelectImage(val uri: Uri?) : PublishJobAction()
    data class SelectCategory(val category: JobCategory) : PublishJobAction()
    object LoadCategories : PublishJobAction()
    object PublishJob : PublishJobAction()
    object ClearError : PublishJobAction()
    object ResetForm : PublishJobAction()
}
