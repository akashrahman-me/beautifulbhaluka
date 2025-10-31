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
            status = "সময় হয়েছে",
            lastDonation = "৩ মাস",
            lastDonationDate = "০১/০৮/২০২৫",
            facebookLink = "https://facebook.com/kibriya",
            whatsappNumber = "০১৭২৬৭৮৮৮৮"
        ),
        DonorInfo(
            id = "2",
            name = "আরিফুল ইসলাম",
            phone = "০১৮১২৩৪৫৬৭৮",
            bloodGroup = "B+",
            location = "ভালুকা পৌরসভা",
            status = "সময় হয়নি",
            lastDonation = "১ মাস",
            lastDonationDate = "০১/১০/২০২৫",
            facebookLink = null,
            whatsappNumber = "০১৮১২৩৪৫৬৭৮"
        )
    )
}

