package com.akash.beautifulbhaluka.presentation.screens.houserent.publish

import com.akash.beautifulbhaluka.domain.model.Amenity
import com.akash.beautifulbhaluka.domain.model.PropertyType

data class PublishHouseRentUiState(
    val title: String = "",
    val description: String = "",
    val propertyType: PropertyType? = null,
    val monthlyRent: String = "",
    val advancePayment: String = "",
    val isNegotiable: Boolean = false,
    val bedrooms: String = "",
    val bathrooms: String = "",
    val area: String = "",
    val floor: String = "",
    val totalFloors: String = "",
    val selectedAmenities: List<Amenity> = emptyList(),
    val location: String = "",
    val detailedAddress: String = "",
    val ownerName: String = "",
    val ownerContact: String = "",
    val ownerWhatsApp: String = "",
    val imageUrls: List<String> = emptyList(),
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
    val canSubmit: Boolean
        get() = title.isNotBlank() &&
                description.isNotBlank() &&
                propertyType != null &&
                monthlyRent.isNotBlank() &&
                bedrooms.isNotBlank() &&
                bathrooms.isNotBlank() &&
                area.isNotBlank() &&
                location.isNotBlank() &&
                detailedAddress.isNotBlank() &&
                ownerName.isNotBlank() &&
                ownerContact.isNotBlank()
}

sealed class PublishHouseRentAction {
    data class UpdateTitle(val title: String) : PublishHouseRentAction()
    data class UpdateDescription(val description: String) : PublishHouseRentAction()
    data class UpdatePropertyType(val type: PropertyType) : PublishHouseRentAction()
    data class UpdateMonthlyRent(val rent: String) : PublishHouseRentAction()
    data class UpdateAdvancePayment(val payment: String) : PublishHouseRentAction()
    object ToggleNegotiable : PublishHouseRentAction()
    data class UpdateBedrooms(val bedrooms: String) : PublishHouseRentAction()
    data class UpdateBathrooms(val bathrooms: String) : PublishHouseRentAction()
    data class UpdateArea(val area: String) : PublishHouseRentAction()
    data class UpdateFloor(val floor: String) : PublishHouseRentAction()
    data class UpdateTotalFloors(val totalFloors: String) : PublishHouseRentAction()
    data class ToggleAmenity(val amenity: Amenity) : PublishHouseRentAction()
    data class UpdateLocation(val location: String) : PublishHouseRentAction()
    data class UpdateDetailedAddress(val address: String) : PublishHouseRentAction()
    data class UpdateOwnerName(val name: String) : PublishHouseRentAction()
    data class UpdateOwnerContact(val contact: String) : PublishHouseRentAction()
    data class UpdateOwnerWhatsApp(val whatsapp: String) : PublishHouseRentAction()
    data class AddImage(val imageUrl: String) : PublishHouseRentAction()
    data class RemoveImage(val imageUrl: String) : PublishHouseRentAction()
    object Submit : PublishHouseRentAction()
}

