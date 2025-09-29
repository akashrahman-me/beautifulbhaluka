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
            is CarRentAction.OnCarRentServiceClick -> {
                // Handle car rent service click (could navigate to detail screen or show more info)
            }

            is CarRentAction.OnPhoneClick -> {
                // Handle phone click (could open dialer)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Car rental service data
                val carRentServices = listOf(
                    CarRentService(
                        name = "রামিশা রেন্ট-এ কার",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2025/03/IMG-20250319-WA0003.jpg",
                        description = "আনন্দ ভ্রমণসহ বিভিন্ন অনুষ্ঠানে বিলাসবহুল প্রাইভেটকার ভাড়া দেওয়া হয়।",
                        phone = listOf("01613-880230", "01721-880230")
                    )
                )

                // Drivers data
                val drivers = listOf(
                    Driver("1", "শামিম হাসান খলিল (মাস্টারবাড়ী)", "+880 1719-866835"),
                    Driver("2", "শরিফ ড্রাইভার", "+880 1611-340720"),
                    Driver("3", "শরিফ", "01735-028189"),
                    Driver("4", "সজিব অ্যাম্বুলেন্স", "+880 1753-136197"),
                    Driver("5", "সজিব", "01763-514922"),
                    Driver("6", "সুজন", "01671-470422"),
                    Driver("7", "সুমন", "+880 1409-677350"),
                    Driver("8", "সুমন বিরুনিয়া", "01723-947935"),
                    Driver("9", "সুটন টুন্ডাপাড়া", "+880 1620-681268"),
                    Driver("10", "রফিক বাসস্ট্যান্ড", "+880 1790-961329"),
                    Driver("11", "আব্দুল হক", "+8801791-072824"),
                    Driver("12", "রনি", "01720-304931"),
                    Driver("13", "সেলিম ড্রাইভার", "01734-835118"),
                    Driver("14", "শাহিদ", "+880 1916-940358"),
                    Driver("15", "শামিম", "+880 1757-389714"),
                    Driver("16", "হোরে ড্রাইভার", "01746-180198"),
                    Driver("17", "হুমায়ন", "01937-126626"),
                    Driver("18", "জাহাঙ্গীর মাস্টারবাড়ী", "+880 1747-920503"),
                    Driver("19", "জীবন", "+8801718-212651"),
                    Driver("20", "জীবন", "01735-386365"),
                    Driver("21", "জুবায়েদ", "01711-968843"),
                    Driver("22", "কালাম", "01729-162711"),
                    Driver("23", "কামাল", "+8801684-013000"),
                    Driver("24", "ড্রাইভার খায়রুল", "+8801726-982514"),
                    Driver("25", "একরামুল", "+8801818-766607"),
                    Driver("26", "ফালান", "01744-254764"),
                    Driver("27", "হাবিব", "+880 1727-091429"),
                    Driver("28", "হীরা হাজির বাজার", "+880 1611-627534"),
                    Driver("29", "ইলিয়াস এক্সিও", "+8801776-539737"),
                    Driver("30", "ইলিয়াস এক্সিও", "01615-547577"),
                    Driver("31", "আহাদ", "+8801716-530252"),
                    Driver("32", "আলমগীর", "01765-324638"),
                    Driver("33", "আনোয়ার ড্রাইভার", "+8801717-471086"),
                    Driver("34", "অনুয়ার", "016280-56060"),
                    Driver("35", "বুলবুল", "01911-306579"),
                    Driver("36", "বুরহান", "+880 1711-788803"),
                    Driver("37", "কামাল ড্রাইভার", "01918-737738"),
                    Driver("38", "লাবলু", "+880 1704-478106"),
                    Driver("39", "লাখপতি ড্রাইভার", "01724-653274"),
                    Driver("40", "মিজান", "+8801775-134760"),
                    Driver("41", "মিরাজ ড্রাইভার", "01762-138615"),
                    Driver("42", "মনির", "01720-469594"),
                    Driver("43", "মনির", "+8801902-598260"),
                    Driver("44", "মুলতাসিম", "01719-623823"),
                    Driver("45", "নাহিদ ড্রাইভার", "01307-909384"),
                    Driver("46", "মোঃ আতাহার", "01918-008288"),
                    Driver("47", "রফিক", "880 1778-516862"),
                    Driver("48", "আরাফ রেন্ট-এ কার", "01846524440")
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        carRentServices = carRentServices,
                        drivers = drivers,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "তথ্য লোড করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }
}
