package com.akash.beautifulbhaluka.presentation.screens.bloodbank.manage

data class ManageDonorsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val myPublishedDonors: List<PublishedDonor> = getDefaultPublishedDonors()
)

data class PublishedDonor(
    val id: String,
    val name: String,
    val phone: String,
    val bloodGroup: String,
    val location: String,
    val status: String,
    val publishedDate: String
)

sealed class ManageDonorsAction {
    object LoadData : ManageDonorsAction()
    data class DeleteDonor(val donorId: String) : ManageDonorsAction()
    object ClearError : ManageDonorsAction()
}

private fun getDefaultPublishedDonors(): List<PublishedDonor> {
    return listOf(
        PublishedDonor(
            id = "1",
            name = "Kibriya zaman munna",
            phone = "০১৭২৬৭৮৮৮৮",
            bloodGroup = "A+",
            location = "মাসতালবেড়ি, বালুকা",
            status = "Active",
            publishedDate = "২ দিন আগে"
        )
    )
}

