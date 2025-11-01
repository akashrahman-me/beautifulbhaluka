package com.akash.beautifulbhaluka.presentation.screens.bloodbank.manage

import com.akash.beautifulbhaluka.presentation.screens.bloodbank.DonorInfo

data class ManageDonorsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val myPublishedDonors: List<DonorInfo> = emptyList()
)

sealed class ManageDonorsAction {
    object LoadData : ManageDonorsAction()
    data class DeleteDonor(val donorId: String) : ManageDonorsAction()
    object ClearError : ManageDonorsAction()
}


