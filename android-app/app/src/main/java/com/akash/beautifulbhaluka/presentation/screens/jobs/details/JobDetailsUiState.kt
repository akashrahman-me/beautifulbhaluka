package com.akash.beautifulbhaluka.presentation.screens.jobs.details

import android.net.Uri
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobItem

data class JobDetailsUiState(
    val isLoading: Boolean = false,
    val job: JobItem? = null,
    val error: String? = null,
    val showApplicationDialog: Boolean = false,
    val applicationState: ApplicationState = ApplicationState.Idle,
    val selectedPdfUri: Uri? = null,
    val applicationNote: String = ""
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
    data class SelectPdf(val uri: Uri?) : JobDetailsAction()
    data class UpdateApplicationNote(val note: String) : JobDetailsAction()
    object SubmitApplication : JobDetailsAction()
    object ResetApplicationState : JobDetailsAction()
}
