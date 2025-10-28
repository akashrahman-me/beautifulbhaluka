package com.akash.beautifulbhaluka.presentation.screens.jobs.details

import android.net.Uri
import com.akash.beautifulbhaluka.domain.model.Job

data class JobDetailsUiState(
    val isLoading: Boolean = false,
    val job: Job? = null,
    val error: String? = null,
    val showApplicationDialog: Boolean = false,
    val applicationState: ApplicationState = ApplicationState.Idle,
    val selectedPdfUri: Uri? = null,
    val applicationNote: String = "",
    val applicantName: String = "",
    val applicantEmail: String = "",
    val applicantPhone: String = "",
    val isSubmitting: Boolean = false
)

sealed class ApplicationState {
    object Idle : ApplicationState()
    object Submitting : ApplicationState()
    object Success : ApplicationState()
    object Error : ApplicationState()
}

sealed class JobDetailsAction {
    data class LoadJobDetails(val jobId: String) : JobDetailsAction()
    object ShowApplicationDialog : JobDetailsAction()
    object HideApplicationDialog : JobDetailsAction()
    object DismissApplicationDialog : JobDetailsAction()
    data class SelectPdf(val uri: Uri?) : JobDetailsAction()
    data class UpdateApplicationNote(val note: String) : JobDetailsAction()
    data class UpdateApplicantName(val name: String) : JobDetailsAction()
    data class UpdateApplicantEmail(val email: String) : JobDetailsAction()
    data class UpdateApplicantPhone(val phone: String) : JobDetailsAction()
    object SubmitApplication : JobDetailsAction()
    object ResetApplicationState : JobDetailsAction()
}
