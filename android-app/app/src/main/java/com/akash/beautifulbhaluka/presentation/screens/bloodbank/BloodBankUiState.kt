package com.akash.beautifulbhaluka.presentation.screens.bloodbank

import com.akash.beautifulbhaluka.domain.model.Donor

data class BloodBankUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val donors: List<DonorInfo> = emptyList(),
    val selectedBloodGroup: String? = null,
    val selectedAvailability: String? = null,
    val bloodGroupCounts: Map<String, Int> = emptyMap(),
    val totalDonors: Int = 0
)

data class DonorInfo(
    val id: String,
    val name: String,
    val status: String,
    val phone: String,
    val bloodGroup: String,
    val location: String,
    val lastDonation: String,
    val lastDonationDate: String,
    val facebookLink: String? = null,
    val whatsappNumber: String? = null,
    val totalDonations: Int = 0
)

sealed class BloodBankAction {
    object LoadData : BloodBankAction()
    data class CallPhone(val phoneNumber: String) : BloodBankAction()
    data class FilterByBloodGroup(val bloodGroup: String?) : BloodBankAction()
    data class FilterByAvailability(val availability: String?) : BloodBankAction()
}

/**
 * Mapper to convert domain Donor to presentation DonorInfo
 */
fun Donor.toDonorInfo(): DonorInfo {
    return DonorInfo(
        id = id,
        name = name,
        status = status,
        phone = phone,
        bloodGroup = bloodGroup,
        location = location,
        lastDonation = lastDonationTimeAgo,
        lastDonationDate = lastDonationDateFormatted,
        facebookLink = facebookLink,
        whatsappNumber = whatsappNumber,
        totalDonations = totalDonations
    )
}

