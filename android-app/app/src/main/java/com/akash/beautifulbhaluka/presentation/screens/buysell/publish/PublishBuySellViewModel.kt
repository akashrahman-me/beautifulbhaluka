package com.akash.beautifulbhaluka.presentation.screens.buysell.publish

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.presentation.screens.buysell.BuySellCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishBuySellViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishBuySellUiState())
    val uiState: StateFlow<PublishBuySellUiState> = _uiState.asStateFlow()

    val categories = listOf(
        BuySellCategory("1", "ইলেকট্রনিক্স", "Electronics", "Devices", 45, 0xFF2196F3),
        BuySellCategory("2", "মোবাইল ফোন", "Mobile Phones", "Smartphone", 89, 0xFF4CAF50),
        BuySellCategory("3", "গাড়ি", "Vehicles", "DirectionsCar", 23, 0xFFFF5722),
        BuySellCategory("4", "বাইক", "Motorbikes", "TwoWheeler", 67, 0xFF9C27B0),
        BuySellCategory("5", "আসবাবপত্র", "Furniture", "Chair", 34, 0xFFFF9800),
        BuySellCategory("6", "ফ্যাশন", "Fashion", "Checkroom", 78, 0xFFE91E63),
        BuySellCategory("7", "বাড়ি", "Property", "Home", 12, 0xFF00BCD4),
        BuySellCategory("8", "চাকরি", "Jobs", "Work", 56, 0xFF795548),
        BuySellCategory("9", "সেবা", "Services", "HomeRepairService", 43, 0xFF607D8B),
        BuySellCategory("10", "পোষা প্রাণী", "Pets", "Pets", 19, 0xFF8BC34A),
        BuySellCategory("11", "বই", "Books", "Book", 28, 0xFF3F51B5),
        BuySellCategory("12", "খেলাধুলা", "Sports", "SportsBasketball", 15, 0xFFFFC107)
    )

    val conditions = listOf(
        "NEW" to "নতুন",
        "LIKE_NEW" to "প্রায় নতুন",
        "GOOD" to "ভালো",
        "FAIR" to "মোটামুটি",
        "USED" to "ব্যবহৃত"
    )

    val types = listOf(
        "SELL" to "বিক্রয়",
        "BUY" to "ক্রয়",
        "EXCHANGE" to "বিনিময়"
    )

    fun onAction(action: PublishBuySellAction) {
        when (action) {
            is PublishBuySellAction.UpdateTitle -> updateTitle(action.title)
            is PublishBuySellAction.UpdateDescription -> updateDescription(action.description)
            is PublishBuySellAction.UpdatePrice -> updatePrice(action.price)
            is PublishBuySellAction.UpdateOriginalPrice -> updateOriginalPrice(action.originalPrice)
            is PublishBuySellAction.SelectCategory -> selectCategory(action.categoryId)
            is PublishBuySellAction.SelectCondition -> selectCondition(action.condition)
            is PublishBuySellAction.SelectType -> selectType(action.type)
            is PublishBuySellAction.UpdateLocation -> updateLocation(action.location)
            is PublishBuySellAction.UpdateContact -> updateContact(action.contact)
            is PublishBuySellAction.AddImages -> addImages(action.uris)
            is PublishBuySellAction.RemoveImage -> removeImage(action.uri)
            is PublishBuySellAction.ToggleUrgent -> toggleUrgent(action.isUrgent)
            is PublishBuySellAction.ToggleFeatured -> toggleFeatured(action.isFeatured)
            is PublishBuySellAction.ToggleNegotiable -> toggleNegotiable(action.isNegotiable)
            is PublishBuySellAction.ShowCategoryPicker -> showCategoryPicker()
            is PublishBuySellAction.HideCategoryPicker -> hideCategoryPicker()
            is PublishBuySellAction.ShowConditionPicker -> showConditionPicker()
            is PublishBuySellAction.HideConditionPicker -> hideConditionPicker()
            is PublishBuySellAction.ShowTypePicker -> showTypePicker()
            is PublishBuySellAction.HideTypePicker -> hideTypePicker()
            is PublishBuySellAction.PublishItem -> publishItem()
            is PublishBuySellAction.ClearError -> clearError()
        }
    }

    private fun updateTitle(title: String) {
        _uiState.update {
            it.copy(
                title = title,
                titleError = if (title.isBlank()) null else it.titleError
            )
        }
    }

    private fun updateDescription(description: String) {
        _uiState.update {
            it.copy(
                description = description,
                descriptionError = if (description.isBlank()) null else it.descriptionError
            )
        }
    }

    private fun updatePrice(price: String) {
        // Only allow digits and decimal point
        val filtered = price.filter { it.isDigit() || it == '.' }
        _uiState.update {
            it.copy(
                price = filtered,
                priceError = if (filtered.isBlank()) null else it.priceError
            )
        }
    }

    private fun updateOriginalPrice(originalPrice: String) {
        val filtered = originalPrice.filter { it.isDigit() || it == '.' }
        _uiState.update { it.copy(originalPrice = filtered) }
    }

    private fun selectCategory(categoryId: String?) {
        _uiState.update {
            it.copy(
                selectedCategory = categoryId,
                categoryError = if (categoryId != null) null else it.categoryError,
                showCategoryPicker = false
            )
        }
    }

    private fun selectCondition(condition: String) {
        _uiState.update {
            it.copy(
                selectedCondition = condition,
                showConditionPicker = false
            )
        }
    }

    private fun selectType(type: String) {
        _uiState.update {
            it.copy(
                selectedType = type,
                showTypePicker = false
            )
        }
    }

    private fun updateLocation(location: String) {
        _uiState.update {
            it.copy(
                location = location,
                locationError = if (location.isBlank()) null else it.locationError
            )
        }
    }

    private fun updateContact(contact: String) {
        // Only allow digits, +, -, and spaces
        val filtered = contact.filter { it.isDigit() || it in listOf('+', '-', ' ') }
        _uiState.update {
            it.copy(
                contactNumber = filtered,
                contactError = if (filtered.isBlank()) null else it.contactError
            )
        }
    }

    private fun addImages(uris: List<Uri>) {
        _uiState.update { state ->
            val currentImages = state.selectedImages
            val newImages = (currentImages + uris).take(5) // Max 5 images
            state.copy(
                selectedImages = newImages,
                imageError = if (newImages.isNotEmpty()) null else state.imageError
            )
        }
    }

    private fun removeImage(uri: Uri) {
        _uiState.update { state ->
            state.copy(selectedImages = state.selectedImages.filter { it != uri })
        }
    }

    private fun toggleUrgent(isUrgent: Boolean) {
        _uiState.update { it.copy(isUrgent = isUrgent) }
    }

    private fun toggleFeatured(isFeatured: Boolean) {
        _uiState.update { it.copy(isFeatured = isFeatured) }
    }

    private fun toggleNegotiable(isNegotiable: Boolean) {
        _uiState.update { it.copy(isNegotiable = isNegotiable) }
    }

    private fun showCategoryPicker() {
        _uiState.update { it.copy(showCategoryPicker = true) }
    }

    private fun hideCategoryPicker() {
        _uiState.update { it.copy(showCategoryPicker = false) }
    }

    private fun showConditionPicker() {
        _uiState.update { it.copy(showConditionPicker = true) }
    }

    private fun hideConditionPicker() {
        _uiState.update { it.copy(showConditionPicker = false) }
    }

    private fun showTypePicker() {
        _uiState.update { it.copy(showTypePicker = true) }
    }

    private fun hideTypePicker() {
        _uiState.update { it.copy(showTypePicker = false) }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun publishItem() {
        // Validate all fields
        val state = _uiState.value
        var hasError = false

        if (state.title.isBlank()) {
            _uiState.update { it.copy(titleError = "শিরোনাম দিন") }
            hasError = true
        }

        if (state.description.isBlank()) {
            _uiState.update { it.copy(descriptionError = "বিবরণ দিন") }
            hasError = true
        }

        if (state.price.isBlank()) {
            _uiState.update { it.copy(priceError = "মূল্য দিন") }
            hasError = true
        }

        if (state.selectedCategory == null) {
            _uiState.update { it.copy(categoryError = "ক্যাটাগরি নির্বাচন করুন") }
            hasError = true
        }

        if (state.location.isBlank()) {
            _uiState.update { it.copy(locationError = "অবস্থান দিন") }
            hasError = true
        }

        if (state.contactNumber.isBlank()) {
            _uiState.update { it.copy(contactError = "যোগাযোগ নম্বর দিন") }
            hasError = true
        }

        if (state.selectedImages.isEmpty()) {
            _uiState.update { it.copy(imageError = "অন্তত একটি ছবি যোগ করুন") }
            hasError = true
        }

        if (hasError) {
            _uiState.update { it.copy(error = "সব তথ্য সঠিকভাবে পূরণ করুন") }
            return
        }

        // Publish item
        viewModelScope.launch {
            _uiState.update { it.copy(isPublishing = true, error = null) }
            delay(2000) // Simulate network delay

            // TODO: Implement actual publish logic

            _uiState.update {
                it.copy(
                    isPublishing = false,
                    successMessage = "বিজ্ঞাপন সফলভাবে প্রকাশিত হয়েছে"
                )
            }
        }
    }
}

