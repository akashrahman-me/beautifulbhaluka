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
                    // Private Cars (প্রাইভেটকার) - 10 items
                    Car(
                        id = "1",
                        carName = "Honda Civic",
                        driverName = "সুজন আহমেদ",
                        mobile = "01671-470422",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1590362891991-f776e747a588?w=800",
                        pricePerDay = "৩,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "2",
                        carName = "Toyota Corolla",
                        driverName = "রফিক উদ্দিন",
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
                        id = "3",
                        carName = "Honda Accord",
                        driverName = "জাকির হোসেন",
                        mobile = "01712-345678",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1606220945770-b5b6c2c55bf1?w=800",
                        pricePerDay = "৩,২০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "4",
                        carName = "Toyota Allion",
                        driverName = "শফিক মিয়া",
                        mobile = "01811-223344",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?w=800",
                        pricePerDay = "২,৯০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "5",
                        carName = "Toyota Premio",
                        driverName = "রাসেল মিয়া",
                        mobile = "01916-556677",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=800",
                        pricePerDay = "৩,১০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "6",
                        carName = "Nissan Sylphy",
                        driverName = "আলাউদ্দিন",
                        mobile = "01722-998877",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800",
                        pricePerDay = "২,৭০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "7",
                        carName = "Mazda Axela",
                        driverName = "হাবিব মিয়া",
                        mobile = "01833-445566",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1583267746897-d2n5b0e0b0e0?w=800",
                        pricePerDay = "৩,৩০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "8",
                        carName = "Honda City",
                        driverName = "শাহাদাত হোসেন",
                        mobile = "01944-776655",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=800",
                        pricePerDay = "২,৬০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "9",
                        carName = "Toyota Fielder",
                        driverName = "মনির হোসেন",
                        mobile = "01755-334422",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1566933293069-b55c7f0e90ad?w=800",
                        pricePerDay = "২,৮৫০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "10",
                        carName = "Nissan Sunny",
                        driverName = "কবির হোসেন",
                        mobile = "01866-112233",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "প্রাইভেট কার",
                        category = CarCategory.PRIVATE_CAR,
                        imageUrl = "https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800",
                        pricePerDay = "২,৫০০ টাকা",
                        availability = true
                    ),

                    // Hiace (হায়েস) - 8 items
                    Car(
                        id = "11",
                        carName = "Toyota Hiace",
                        driverName = "শরিফ উদ্দিন",
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
                        id = "12",
                        carName = "Toyota Hiace (Non-AC)",
                        driverName = "কামাল উদ্দিন",
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
                        id = "13",
                        carName = "Toyota Hiace (AC)",
                        driverName = "জসিম উদ্দিন",
                        mobile = "01977-665544",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৪,৮০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "14",
                        carName = "Toyota Hiace Super GL",
                        driverName = "সালাম মিয়া",
                        mobile = "01788-223311",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1583267746897-d2n5b0e0b0e0?w=800",
                        pricePerDay = "৪,৬০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "15",
                        carName = "Toyota Hiace Commuter",
                        driverName = "বাদল মিয়া",
                        mobile = "01899-445566",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1570733577733-e1f170a8d1c9?w=800",
                        pricePerDay = "৪,৩০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "16",
                        carName = "Toyota Hiace GL",
                        driverName = "আনোয়ার হোসেন",
                        mobile = "01700-998877",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1527786356703-4b100091cd2c?w=800",
                        pricePerDay = "৪,২০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "17",
                        carName = "Toyota Hiace DX",
                        driverName = "মোক্তার মিয়া",
                        mobile = "01611-778899",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৩,৯০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "18",
                        carName = "Toyota Hiace Van",
                        driverName = "হেলাল মিয়া",
                        mobile = "01922-334455",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাইক্রোবাস",
                        category = CarCategory.HIACE,
                        imageUrl = "https://images.unsplash.com/photo-1570733577733-e1f170a8d1c9?w=800",
                        pricePerDay = "৪,১০০ টাকা",
                        availability = true
                    ),

                    // Noah (নোয়া) - 8 items
                    Car(
                        id = "19",
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
                        id = "20",
                        carName = "Toyota Noah (AC)",
                        driverName = "জাহাঙ্গীর আলম",
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
                        id = "21",
                        carName = "Toyota Noah Si",
                        driverName = "তারেক মিয়া",
                        mobile = "01833-556677",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1619767886558-efdc259cde1a?w=800",
                        pricePerDay = "৫,৩০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "22",
                        carName = "Toyota Noah X",
                        driverName = "লিটন মিয়া",
                        mobile = "01944-778899",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800",
                        pricePerDay = "৫,২০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "23",
                        carName = "Toyota Noah G",
                        driverName = "রিপন মিয়া",
                        mobile = "01755-889900",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1619767886558-efdc259cde1a?w=800",
                        pricePerDay = "৫,৪০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "24",
                        carName = "Toyota Noah YY",
                        driverName = "সাগর মিয়া",
                        mobile = "01866-990011",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800",
                        pricePerDay = "৫,১০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "25",
                        carName = "Toyota Noah Hybrid",
                        driverName = "সোহেল মিয়া",
                        mobile = "01977-112233",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "হাইব্রিড এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1619767886558-efdc259cde1a?w=800",
                        pricePerDay = "৬,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "26",
                        carName = "Toyota Noah S",
                        driverName = "পলাশ মিয়া",
                        mobile = "01688-223344",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি মাইক্রোবাস",
                        category = CarCategory.NOAH,
                        imageUrl = "https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800",
                        pricePerDay = "৫,৬০০ টাকা",
                        availability = true
                    ),

                    // Bus (বাস) - 6 items
                    Car(
                        id = "27",
                        carName = "মিনি বাস",
                        driverName = "আলমগীর হোসেন",
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
                        id = "28",
                        carName = "এসি বাস",
                        driverName = "সেলিম মিয়া",
                        mobile = "01799-445566",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "এসি বাস",
                        category = CarCategory.BUS,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৮,০০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "29",
                        carName = "Hino বাস",
                        driverName = "জহির মিয়া",
                        mobile = "01800-556677",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "বড় বাস",
                        category = CarCategory.BUS,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৭,৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "30",
                        carName = "Rosa বাস",
                        driverName = "শাহিন মিয়া",
                        mobile = "01911-667788",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মাঝারি বাস",
                        category = CarCategory.BUS,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৬,৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "31",
                        carName = "Coaster বাস",
                        driverName = "মিলন মিয়া",
                        mobile = "01722-778899",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মিনি বাস",
                        category = CarCategory.BUS,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৬,২০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "32",
                        carName = "Isuzu বাস",
                        driverName = "শাকিল মিয়া",
                        mobile = "01833-889900",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "বড় বাস",
                        category = CarCategory.BUS,
                        imageUrl = "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800",
                        pricePerDay = "৭,৮০০ টাকা",
                        availability = true
                    ),

                    // Truck (ট্রাক) - 6 items
                    Car(
                        id = "33",
                        carName = "মাহিন্দ্রা বোলেরো পিকআপ",
                        driverName = "মিজান উদ্দিন",
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
                        id = "34",
                        carName = "Tata পিকআপ",
                        driverName = "রাজু মিয়া",
                        mobile = "01844-990011",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "পিকআপ",
                        category = CarCategory.TRUCK,
                        imageUrl = "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?w=800",
                        pricePerDay = "২,৮০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "35",
                        carName = "ছোট ট্রাক",
                        driverName = "বাবুল মিয়া",
                        mobile = "01955-112233",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "ছোট ট্রাক",
                        category = CarCategory.TRUCK,
                        imageUrl = "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?w=800",
                        pricePerDay = "৩,২০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "36",
                        carName = "কভার ভ্যান",
                        driverName = "লাভলু মিয়া",
                        mobile = "01766-223344",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "কভার ভ্যান",
                        category = CarCategory.TRUCK,
                        imageUrl = "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?w=800",
                        pricePerDay = "২,৯০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "37",
                        carName = "বড় ট্রাক",
                        driverName = "মোস্তফা মিয়া",
                        mobile = "01877-334455",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "বড় ট্রাক",
                        category = CarCategory.TRUCK,
                        imageUrl = "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?w=800",
                        pricePerDay = "৪,৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "38",
                        carName = "Isuzu পিকআপ",
                        driverName = "হাসান মিয়া",
                        mobile = "01988-445566",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "পিকআপ",
                        category = CarCategory.TRUCK,
                        imageUrl = "https://images.unsplash.com/photo-1601584115197-04ecc0da31d7?w=800",
                        pricePerDay = "৩,০০০ টাকা",
                        availability = true
                    ),

                    // Motorcycle (মোটরসাইকেল) - 8 items
                    Car(
                        id = "39",
                        carName = "Yamaha FZS",
                        driverName = "সুমন আহমেদ",
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
                        id = "40",
                        carName = "Honda CB",
                        driverName = "রিয়াদ মিয়া",
                        mobile = "01799-556677",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৭০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "41",
                        carName = "Suzuki Gixxer",
                        driverName = "তানভীর মিয়া",
                        mobile = "01600-778899",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৮৫০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "42",
                        carName = "Hero Glamour",
                        driverName = "সাকিব মিয়া",
                        mobile = "01711-889900",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৬৫০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "43",
                        carName = "Bajaj Pulsar",
                        driverName = "রাফি মিয়া",
                        mobile = "01822-990011",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৭৫০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "44",
                        carName = "TVS Apache",
                        driverName = "নয়ন মিয়া",
                        mobile = "01933-112233",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৮০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "45",
                        carName = "Yamaha R15",
                        driverName = "জুয়েল মিয়া",
                        mobile = "01744-223344",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "স্পোর্টস বাইক",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "১,২০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "46",
                        carName = "Honda Hornet",
                        driverName = "রনি মিয়া",
                        mobile = "01855-334455",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "মোটরসাইকেল",
                        category = CarCategory.MOTORCYCLE,
                        imageUrl = "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=800",
                        pricePerDay = "৯০০ টাকা",
                        availability = true
                    ),

                    // Beku (বেকু) - 4 items
                    Car(
                        id = "47",
                        carName = "ইজিবাইক",
                        driverName = "নাহিদ মিয়া",
                        mobile = "01307-909384",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "ইলেকট্রিক বাইক",
                        category = CarCategory.BEKU,
                        imageUrl = "https://images.unsplash.com/photo-1609078434212-f4c0f71c8de1?w=800",
                        pricePerDay = "৫০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "48",
                        carName = "ইলেকট্রিক স্কুটার",
                        driverName = "আরিফ মিয়া",
                        mobile = "01966-445566",
                        address = "ভালুকা সদর",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "ইলেকট্রিক স্কুটার",
                        category = CarCategory.BEKU,
                        imageUrl = "https://images.unsplash.com/photo-1609078434212-f4c0f71c8de1?w=800",
                        pricePerDay = "৪৫০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "49",
                        carName = "বেকু অটো",
                        driverName = "জামাল মিয়া",
                        mobile = "01777-556677",
                        address = "মাস্টারবাড়ী",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "ইলেকট্রিক অটো",
                        category = CarCategory.BEKU,
                        imageUrl = "https://images.unsplash.com/photo-1609078434212-f4c0f71c8de1?w=800",
                        pricePerDay = "৬০০ টাকা",
                        availability = true
                    ),
                    Car(
                        id = "50",
                        carName = "ইলেকট্রিক রিকশা",
                        driverName = "করিম মিয়া",
                        mobile = "01888-667788",
                        address = "ভালুকা",
                        location = "ভালুকা, ময়মনসিংহ",
                        carType = "ই-রিকশা",
                        category = CarCategory.BEKU,
                        imageUrl = "https://images.unsplash.com/photo-1609078434212-f4c0f71c8de1?w=800",
                        pricePerDay = "৪০০ টাকা",
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
