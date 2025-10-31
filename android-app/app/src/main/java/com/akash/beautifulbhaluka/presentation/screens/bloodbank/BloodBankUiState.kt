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
    val lastDonation: String,
    val lastDonationDate: String, // সর্বশেষ রক্তদান তারিখ
    val facebookLink: String? = null,
    val whatsappNumber: String? = null
)

sealed class BloodBankAction {
    object LoadData : BloodBankAction()
    data class CallPhone(val phoneNumber: String) : BloodBankAction()
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
            lastDonation = "২ মাস ২৪ দিন",
            lastDonationDate = "০৮/০৮/২০২৫",
            facebookLink = "https://facebook.com/kibriya",
            whatsappNumber = "০১৭২৬৭৮৮৮৮"
        ),
        DonorInfo(
            id = "2",
            name = "রাকিব হাসান",
            status = "সময় হয়নি",
            phone = "০১৮১২৩৪৫৬৭৮",
            bloodGroup = "B+",
            location = "ভালুকা পৌরসভা",
            lastDonation = "১ মাস ১০ দিন",
            lastDonationDate = "২০/০৯/২০২৫",
            facebookLink = "https://facebook.com/rakib",
            whatsappNumber = null
        ),
        DonorInfo(
            id = "3",
            name = "সাকিব আহমেদ",
            status = "সময় হয়েছে",
            phone = "০১৭১৫৬৭৮৯০১",
            bloodGroup = "O+",
            location = "রামপুর, ভালুকা",
            lastDonation = "৪ মাস ৫ দিন",
            lastDonationDate = "২৫/০৬/২০২৫",
            facebookLink = null,
            whatsappNumber = "০১৭১৫৬৭৮৯০১"
        ),
        DonorInfo(
            id = "4",
            name = "তানভীর আলম",
            status = "সময় হয়নি",
            phone = "০১৯২৩৪৫৬৭৮৯",
            bloodGroup = "AB+",
            location = "ভালুকা বাজার",
            lastDonation = "২ মাস ১৫ দিন",
            lastDonationDate = "১৫/০৮/২০২৫",
            facebookLink = "https://facebook.com/tanvir",
            whatsappNumber = "০১৯২৩৪৫৬৭৮৯"
        ),
        DonorInfo(
            id = "5",
            name = "ফারহান ইসলাম",
            status = "সময় হয়েছে",
            phone = "০১৩১২৩৪৫৬৭৮",
            bloodGroup = "A-",
            location = "মাসতালবেড়ি, বালুকা",
            lastDonation = "৫ মাস ২ দিন",
            lastDonationDate = "২৮/০৫/২০২৫",
            facebookLink = "https://facebook.com/farhan",
            whatsappNumber = "০১৩১২৩৪৫৬৭৮"
        )
    )
}

