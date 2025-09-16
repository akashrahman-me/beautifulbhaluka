package com.akash.beautifulbhaluka.presentation.screens.shops.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCondition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PublishProductViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PublishProductUiState())
    val uiState: StateFlow<PublishProductUiState> = _uiState.asStateFlow()

    private val mockCategories = listOf(
        ProductCategory("1", "ইলেকট্রনিক্স", "Electronics", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("2", "পোশাক", "Clothing", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("3", "খাবার", "Food", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("4", "বই", "Books", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("5", "মোবাইল", "Mobile", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("6", "গাড়ি", "Vehicles", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("7", "আসবাবপত্র", "Furniture", R.drawable.ic_launcher_foreground, 0),
        ProductCategory("8", "স্বাস্থ্য", "Health", R.drawable.ic_launcher_foreground, 0)
    )

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            // Simulate network delay
            delay(500)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                categories = mockCategories
            )
        }
    }

    fun onAction(action: PublishProductAction) {
        when (action) {
            is PublishProductAction.UpdateProductName -> updateProductName(action.name)
            is PublishProductAction.UpdateProductDescription -> updateProductDescription(action.description)
            is PublishProductAction.UpdatePrice -> updatePrice(action.price)
            is PublishProductAction.UpdateOriginalPrice -> updateOriginalPrice(action.originalPrice)
            is PublishProductAction.UpdateCondition -> updateCondition(action.condition)
            is PublishProductAction.UpdateStock -> updateStock(action.stock)
            is PublishProductAction.UpdateSellerName -> updateSellerName(action.name)
            is PublishProductAction.UpdateSellerContact -> updateSellerContact(action.contact)
            is PublishProductAction.UpdateLocation -> updateLocation(action.location)
            is PublishProductAction.SelectCategory -> selectCategory(action.category)
            is PublishProductAction.AddImage -> addImage(action.imageUri)
            is PublishProductAction.RemoveImage -> removeImage(action.imageUri)
            PublishProductAction.PublishProduct -> publishProduct()
            PublishProductAction.ClearForm -> clearForm()
            PublishProductAction.DismissSuccessMessage -> dismissSuccessMessage()
        }
    }

    private fun updateProductName(name: String) {
        _uiState.value = _uiState.value.copy(
            productName = name,
            validationErrors = _uiState.value.validationErrors - "productName"
        )
    }

    private fun updateProductDescription(description: String) {
        _uiState.value = _uiState.value.copy(
            productDescription = description,
            validationErrors = _uiState.value.validationErrors - "productDescription"
        )
    }

    private fun updatePrice(price: String) {
        _uiState.value = _uiState.value.copy(
            price = price,
            validationErrors = _uiState.value.validationErrors - "price"
        )
    }

    private fun updateOriginalPrice(originalPrice: String) {
        _uiState.value = _uiState.value.copy(originalPrice = originalPrice)
    }

    private fun updateCondition(condition: ProductCondition) {
        _uiState.value = _uiState.value.copy(condition = condition)
    }

    private fun updateStock(stock: String) {
        _uiState.value = _uiState.value.copy(
            stock = stock,
            validationErrors = _uiState.value.validationErrors - "stock"
        )
    }

    private fun updateSellerName(name: String) {
        _uiState.value = _uiState.value.copy(
            sellerName = name,
            validationErrors = _uiState.value.validationErrors - "sellerName"
        )
    }

    private fun updateSellerContact(contact: String) {
        _uiState.value = _uiState.value.copy(
            sellerContact = contact,
            validationErrors = _uiState.value.validationErrors - "sellerContact"
        )
    }

    private fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(
            location = location,
            validationErrors = _uiState.value.validationErrors - "location"
        )
    }

    private fun selectCategory(category: ProductCategory) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            validationErrors = _uiState.value.validationErrors - "category"
        )
    }

    private fun addImage(imageUri: String) {
        val currentImages = _uiState.value.selectedImages
        if (currentImages.size < 5 && !currentImages.contains(imageUri)) {
            _uiState.value = _uiState.value.copy(
                selectedImages = currentImages + imageUri
            )
        }
    }

    private fun removeImage(imageUri: String) {
        _uiState.value = _uiState.value.copy(
            selectedImages = _uiState.value.selectedImages - imageUri
        )
    }

    private fun publishProduct() {
        val currentState = _uiState.value
        val validationErrors = validateForm(currentState)

        if (validationErrors.isNotEmpty()) {
            _uiState.value = currentState.copy(
                validationErrors = validationErrors,
                error = "ফর্মে ভুল রয়েছে। দয়া করে সংশোধন করুন।"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(
                isPublishing = true,
                error = null,
                validationErrors = emptyMap()
            )

            try {
                // Simulate API call
                delay(2000)

                // Success
                _uiState.value = _uiState.value.copy(
                    isPublishing = false,
                    isPublishSuccessful = true
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isPublishing = false,
                    error = "পণ্য প্রকাশ করতে সমস্যা হয়েছে: ${e.message}"
                )
            }
        }
    }

    private fun validateForm(state: PublishProductUiState): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (state.productName.isBlank()) {
            errors["productName"] = "পণ্যের নাম লিখুন"
        }

        if (state.productDescription.isBlank()) {
            errors["productDescription"] = "পণ্যের বিবরণ লিখুন"
        }

        if (state.price.isBlank()) {
            errors["price"] = "দাম লিখুন"
        } else {
            try {
                val price = state.price.toDouble()
                if (price <= 0) {
                    errors["price"] = "দাম ০ এর চেয়ে বেশি হতে হবে"
                }
            } catch (e: NumberFormatException) {
                errors["price"] = "সঠিক দাম লিখুন"
            }
        }

        if (state.stock.isBlank()) {
            errors["stock"] = "স্টকের পরিমাণ লিখুন"
        } else {
            try {
                val stock = state.stock.toInt()
                if (stock < 0) {
                    errors["stock"] = "স্টক ০ বা তার চেয়ে বেশি হতে হবে"
                }
            } catch (e: NumberFormatException) {
                errors["stock"] = "সঠিক স্টকের পরিমাণ লিখুন"
            }
        }

        if (state.selectedCategory == null) {
            errors["category"] = "ক্যাটেগরি নির্বাচন করুন"
        }

        if (state.sellerName.isBlank()) {
            errors["sellerName"] = "বিক্রেতার নাম লিখুন"
        }

        if (state.sellerContact.isBlank()) {
            errors["sellerContact"] = "যোগাযোগের নম্বর লিখুন"
        }

        if (state.location.isBlank()) {
            errors["location"] = "ঠিকানা লিখুন"
        }

        return errors
    }

    private fun clearForm() {
        _uiState.value = PublishProductUiState(
            categories = _uiState.value.categories
        )
    }

    private fun dismissSuccessMessage() {
        _uiState.value = _uiState.value.copy(isPublishSuccessful = false)
    }
}
