package com.akash.beautifulbhaluka.presentation.screens.buysell.publish

import android.net.Uri

data class PublishBuySellUiState(
    val isLoading: Boolean = false,
    val isPublishing: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,

    // Form fields
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val originalPrice: String = "",
    val selectedCategory: String? = null,
    val selectedCondition: String = "NEW",
    val selectedType: String = "SELL",
    val location: String = "",
    val contactNumber: String = "",
    val selectedImages: List<Uri> = emptyList(),

    // Validation
    val titleError: String? = null,
    val descriptionError: String? = null,
    val priceError: String? = null,
    val categoryError: String? = null,
    val locationError: String? = null,
    val contactError: String? = null,
    val imageError: String? = null,

    // Options
    val isUrgent: Boolean = false,
    val isFeatured: Boolean = false,
    val isNegotiable: Boolean = false,

    // UI State
    val showCategoryPicker: Boolean = false,
    val showConditionPicker: Boolean = false,
    val showTypePicker: Boolean = false
)

sealed class PublishBuySellAction {
    data class UpdateTitle(val title: String) : PublishBuySellAction()
    data class UpdateDescription(val description: String) : PublishBuySellAction()
    data class UpdatePrice(val price: String) : PublishBuySellAction()
    data class UpdateOriginalPrice(val originalPrice: String) : PublishBuySellAction()
    data class SelectCategory(val categoryId: String?) : PublishBuySellAction()
    data class SelectCondition(val condition: String) : PublishBuySellAction()
    data class SelectType(val type: String) : PublishBuySellAction()
    data class UpdateLocation(val location: String) : PublishBuySellAction()
    data class UpdateContact(val contact: String) : PublishBuySellAction()
    data class AddImages(val uris: List<Uri>) : PublishBuySellAction()
    data class RemoveImage(val uri: Uri) : PublishBuySellAction()
    data class ToggleUrgent(val isUrgent: Boolean) : PublishBuySellAction()
    data class ToggleFeatured(val isFeatured: Boolean) : PublishBuySellAction()
    data class ToggleNegotiable(val isNegotiable: Boolean) : PublishBuySellAction()
    object ShowCategoryPicker : PublishBuySellAction()
    object HideCategoryPicker : PublishBuySellAction()
    object ShowConditionPicker : PublishBuySellAction()
    object HideConditionPicker : PublishBuySellAction()
    object ShowTypePicker : PublishBuySellAction()
    object HideTypePicker : PublishBuySellAction()
    object PublishItem : PublishBuySellAction()
    object ClearError : PublishBuySellAction()
}

