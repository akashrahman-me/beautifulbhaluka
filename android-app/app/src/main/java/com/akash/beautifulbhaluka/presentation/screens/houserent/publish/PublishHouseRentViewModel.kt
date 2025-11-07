package com.akash.beautifulbhaluka.presentation.screens.houserent.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishHouseRentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishHouseRentUiState())
    val uiState: StateFlow<PublishHouseRentUiState> = _uiState.asStateFlow()

    fun onAction(action: PublishHouseRentAction) {
        when (action) {
            is PublishHouseRentAction.UpdateTitle -> {
                _uiState.update { it.copy(title = action.title) }
            }

            is PublishHouseRentAction.UpdateDescription -> {
                _uiState.update { it.copy(description = action.description) }
            }

            is PublishHouseRentAction.UpdatePropertyType -> {
                _uiState.update { it.copy(propertyType = action.type) }
            }

            is PublishHouseRentAction.UpdateMonthlyRent -> {
                _uiState.update { it.copy(monthlyRent = action.rent) }
            }

            is PublishHouseRentAction.UpdateAdvancePayment -> {
                _uiState.update { it.copy(advancePayment = action.payment) }
            }

            is PublishHouseRentAction.ToggleNegotiable -> {
                _uiState.update { it.copy(isNegotiable = !it.isNegotiable) }
            }

            is PublishHouseRentAction.UpdateBedrooms -> {
                _uiState.update { it.copy(bedrooms = action.bedrooms) }
            }

            is PublishHouseRentAction.UpdateBathrooms -> {
                _uiState.update { it.copy(bathrooms = action.bathrooms) }
            }

            is PublishHouseRentAction.UpdateArea -> {
                _uiState.update { it.copy(area = action.area) }
            }

            is PublishHouseRentAction.UpdateFloor -> {
                _uiState.update { it.copy(floor = action.floor) }
            }

            is PublishHouseRentAction.UpdateTotalFloors -> {
                _uiState.update { it.copy(totalFloors = action.totalFloors) }
            }

            is PublishHouseRentAction.ToggleAmenity -> {
                _uiState.update { state ->
                    val currentAmenities = state.selectedAmenities.toMutableList()
                    if (action.amenity in currentAmenities) {
                        currentAmenities.remove(action.amenity)
                    } else {
                        currentAmenities.add(action.amenity)
                    }
                    state.copy(selectedAmenities = currentAmenities)
                }
            }

            is PublishHouseRentAction.UpdateLocation -> {
                _uiState.update { it.copy(location = action.location) }
            }

            is PublishHouseRentAction.UpdateDetailedAddress -> {
                _uiState.update { it.copy(detailedAddress = action.address) }
            }

            is PublishHouseRentAction.UpdateOwnerName -> {
                _uiState.update { it.copy(ownerName = action.name) }
            }

            is PublishHouseRentAction.UpdateOwnerContact -> {
                _uiState.update { it.copy(ownerContact = action.contact) }
            }

            is PublishHouseRentAction.UpdateOwnerWhatsApp -> {
                _uiState.update { it.copy(ownerWhatsApp = action.whatsapp) }
            }

            is PublishHouseRentAction.AddImage -> {
                _uiState.update { state ->
                    val currentImages = state.imageUrls.toMutableList()
                    if (currentImages.size < 5) {
                        currentImages.add(action.imageUrl)
                    }
                    state.copy(imageUrls = currentImages)
                }
            }

            is PublishHouseRentAction.RemoveImage -> {
                _uiState.update { state ->
                    val currentImages = state.imageUrls.toMutableList()
                    currentImages.remove(action.imageUrl)
                    state.copy(imageUrls = currentImages)
                }
            }

            is PublishHouseRentAction.Submit -> {
                submitProperty()
            }
        }
    }

    private fun submitProperty() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }

            try {
                // Simulate API call
                delay(2000)

                // In real implementation, call repository to save property
                // val result = repository.publishProperty(...)

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        success = true
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        error = "প্রকাশ করতে সমস্যা হয়েছে। আবার চেষ্টা করুন।"
                    )
                }
            }
        }
    }
}

