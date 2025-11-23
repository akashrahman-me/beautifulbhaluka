package com.akash.beautifulbhaluka.presentation.screens.carrent

data class CarRentUiState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val selectedCategory: String = "সব",
    val error: String? = null
)

data class Car(
    val id: String = "",
    val carName: String,
    val driverName: String,
    val mobile: String,
    val address: String = "",
    val location: String = "",
    val carType: String = "",
    val category: String = "প্রাইভেটকার",
    val imageUrl: String = "",
    val pricePerDay: String = "",
    val averageRating: Float = 0f,
    val ratingCount: Int = 0,
    val userRating: Int = 0,
    val availability: Boolean = true
)

object CarCategory {
    const val ALL = "সব"
    const val PRIVATE_CAR = "প্রাইভেটকার"
    const val HIACE = "হায়েস"
    const val NOAH = "নোয়া"
    const val BUS = "বাস"
    const val TRUCK = "ট্রাক"
    const val MOTORCYCLE = "মোটরসাইকেল"
    const val BEKU = "বেকু"

    val categories = listOf(ALL, PRIVATE_CAR, HIACE, NOAH, BUS, TRUCK, MOTORCYCLE, BEKU)
}

sealed interface CarRentAction {
    data object LoadData : CarRentAction
    data class OnCarClick(val car: Car) : CarRentAction
    data class OnPhoneClick(val phoneNumber: String) : CarRentAction
    data class OnCategoryChange(val category: String) : CarRentAction
    data class OnShowMore(val category: String) : CarRentAction
}
