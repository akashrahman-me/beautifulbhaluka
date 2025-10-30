package com.akash.beautifulbhaluka.presentation.screens.bloodbank

data class BloodBankUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val donors: List<DonorInfo> = getDefaultDonors()
)

data class DonorInfo(
    val id: String,
    val name: String,
    val status: String,
    val phone: String,
    val bloodGroup: String,
    val location: String,
    val lastDonation: String
)

sealed class BloodBankAction {
    object LoadData : BloodBankAction()
    data class CallPhone(val phoneNumber: String) : BloodBankAction()
    object NavigateToPublish : BloodBankAction()
    object NavigateToManage : BloodBankAction()
}

private fun getDefaultDonors(): List<DonorInfo> {
    return listOf(
        DonorInfo(
            id = "1",
            name = "Kibriya zaman munna",
            status = "সময় হয়েছে",
            phone = "০১৭২৬৭৮৮৮৮",
            bloodGroup = "A+",
            location = "মাসতালবেড়ি, বালুকা",
            lastDonation = "২ মাস ২৪ দিন পূর্বে"
        ),
        DonorInfo(
            id = "2",
            name = "রাকিব হাসান",
            status = "সময় হয়েছে",
            phone = "০১৮১২৩৪৫৬৭৮",
            bloodGroup = "B+",
            location = "ভালুকা পৌরসভা",
            lastDonation = "৩ মাস ১০ দিন পূর্বে"
        ),
        DonorInfo(
            id = "3",
            name = "সাকিব আহমেদ",
            status = "সময় হয়েছে",
            phone = "০১৭১৫৬৭৮৯০১",
            bloodGroup = "O+",
            location = "রামপুর, ভালুকা",
            lastDonation = "৪ মাস ৫ দিন পূর্বে"
        ),
        DonorInfo(
            id = "4",
            name = "তানভীর আলম",
            status = "সময় হয়েছে",
            phone = "০১৯২৩৪৫৬৭৮৯",
            bloodGroup = "AB+",
            location = "ভালুকা বাজার",
            lastDonation = "২ মাস ১৫ দিন পূর্বে"
        ),
        DonorInfo(
            id = "5",
            name = "ফারহান ইসলাম",
            status = "সময় হয়েছে",
            phone = "০১৩১২৩৪৫৬৭৮",
            bloodGroup = "A-",
            location = "মাসতালবেড়ি, বালুকা",
            lastDonation = "৫ মাস ২ দিন পূর্বে"
        )
    )
}

