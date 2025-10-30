package com.akash.beautifulbhaluka.presentation.screens.bloodbank.manage

import com.akash.beautifulbhaluka.presentation.screens.bloodbank.DonorInfo

data class ManageDonorsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val myPublishedDonors: List<DonorInfo> = getDefaultPublishedDonors()
)

sealed class ManageDonorsAction {
    object LoadData : ManageDonorsAction()
    data class DeleteDonor(val donorId: String) : ManageDonorsAction()
    object ClearError : ManageDonorsAction()
}

private fun getDefaultPublishedDonors(): List<DonorInfo> {
    return listOf(
        DonorInfo(
            id = "1",
            name = "Kibriya zaman munna",
            phone = "০১৭২৬৭৮৮৮৮",
            bloodGroup = "A+",
            location = "মাসতালবেড়ি, বালুকা",
            status = "Active",
            lastDonation = "২ দিন",
            facebookLink = "https://facebook.com/kibriya",
            whatsappNumber = "০১৭২৬৭৮৮৮৮"
        )
    )
}

