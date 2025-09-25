package com.akash.beautifulbhaluka.presentation.screens.upazila_admin

data class UpazilaAdminUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val contactCards: List<UpazilaContactInfo> = getDefaultContactCards(),
    val formerOfficers: List<FormerOfficer> = getDefaultFormerOfficers()
)

data class UpazilaContactInfo(
    val id: String,
    val title: String,
    val image: String,
    val phone: String,
    val email: String
)

data class FormerOfficer(
    val serialNo: String,
    val name: String,
    val fromDate: String,
    val toDate: String
)

sealed class UpazilaAdminAction {
    object LoadData : UpazilaAdminAction()
    data class CallPhone(val phoneNumber: String) : UpazilaAdminAction()
    data class SendEmail(val email: String) : UpazilaAdminAction()
}

private fun getDefaultContactCards(): List<UpazilaContactInfo> {
    return listOf(
        UpazilaContactInfo(
            id = "1",
            title = "উপজেলা নির্বাহী অফিসার (UNO)",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/4042356.png",
            phone = "01733-373334",
            email = "unobhaluka@mopa.gov.bd"
        ),
        UpazilaContactInfo(
            id = "2",
            title = "ভূমি অফিসার (এসি ল্যান্ড)",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/4042356.png",
            phone = "০১৭৩৩৩৭৩৩৩৫",
            email = "aclandbhaluka2020@gmail.com"
        )
    )
}

private fun getDefaultFormerOfficers(): List<FormerOfficer> {
    return listOf(
        FormerOfficer("০১।", "সালমা খাতুন", "০৮-০৯-২০২০", "০২-০৫-২০২৩"),
        FormerOfficer("০২।", "মাসুদ কামাল", "১৭-১০-২০১৭", "০৮-০৯-২০২০"),
        FormerOfficer("০৩।", "মোঃ এরশাদুল আহমেদ", "০২-০৫-২০২৩", "২২-০২-২০২৪"),
        FormerOfficer("০৪।", "সুমাইয়া আক্তার (অঃদাঃ)", "২২-০২-২০২৪", "২৫-০২-২০২৪"),
        FormerOfficer("০৫।", "আলীনূর খান", "২৫-০২-২০২৪", "১৫-১২-২০২৪")
    )
}
