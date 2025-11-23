package com.akash.beautifulbhaluka.presentation.screens.carrent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarRentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CarRentUiState())
    val uiState: StateFlow<CarRentUiState> = _uiState.asStateFlow()

    init {
        onAction(CarRentAction.LoadData)
    }

    fun onAction(action: CarRentAction) {
        when (action) {
            is CarRentAction.LoadData -> loadData()
            is CarRentAction.OnCarClick -> {
            }

            is CarRentAction.OnPhoneClick -> {
            }

            is CarRentAction.OnCategoryChange -> {
                _uiState.update { it.copy(selectedCategory = action.category) }
            }

            is CarRentAction.OnShowMore -> {
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val cars = listOf(
                    Car(
                        id = "1",
                        carName = "Toyota Noah",
                        driverName = "শামিম হাসান খলিল",
                        mobile = "01719-866835",
                        address = "মাস্টারবাড়ী, ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1619767886558-efdc259cde1a?w=800",
                        pricePerDay = "৫,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "2",
                        carName = "Toyota Hiace",
                        driverName = "শরিফ",
                        mobile = "01611-340720",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1527786356703-4b100091cd2c?w=800",
                        pricePerDay = "৪,৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "3",
                        carName = "Honda Civic",
                        driverName = "সুজন",
                        mobile = "01671-470422",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1590362891991-f776e747a588?w=800",
                        pricePerDay = "৩,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "4",
                        carName = "Toyota Corolla",
                        driverName = "রফিক",
                        mobile = "01790-961329",
                        address = "বাসস্ট্যান্ড, ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb?w=800",
                        pricePerDay = "২,৮০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "5",
                        carName = "Toyota Noah (AC)",
                        driverName = "জাহাঙ্গীর",
                        mobile = "01747-920503",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800",
                        pricePerDay = "৫,৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "6",
                        carName = "Toyota Hiace (Non-AC)",
                        driverName = "কামাল",
                        mobile = "01684-013000",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1570733577733-e1f170a8d1c9?w=800",
                        pricePerDay = "৪,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "7",
                        carName = "Yamaha FZS",
                        driverName = "সুমন",
                        mobile = "01409-677350",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৮০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "8",
                        carName = "মাহিন্দ্রা বোলেরো পিকআপ",
                        driverName = "মিজান",
                        mobile = "01775-134760",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "পিকআপ ভ্যান",
                        category = CarCategory.TRUCK,
                        imageUrl = "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?w=800",
                        pricePerDay = "২,৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "9",
                        carName = "মিনি বাস",
                        driverName = "আলমগীর",
                        mobile = "01765-324638",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মিনি বাস",
                        category = CarCategory.BUS,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৬,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "10",
                        carName = "ইজিবাইক",
                        driverName = "নাহিদ",
                        mobile = "01307-909384",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "ইলেকট্রিক বাইক",
                        category = CarCategory.BEKU,
                        imageUrl = "https://images.unsplash.com/photo-1609078434212-f4c0f71c8de1?w=800",
                        pricePerDay = "৫০০ টাকা",
                        availability = true
                    )
                )

                _uiState.update {
                    it.copy(
                        cars = cars,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "একটি সমস্যা হয়েছে",
                        isLoading = false
                    )
                }
            }
        }
    }
}
