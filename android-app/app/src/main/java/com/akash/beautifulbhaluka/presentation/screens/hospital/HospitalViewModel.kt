package com.akash.beautifulbhaluka.presentation.screens.hospital

import androidx.lifecycle.ViewModel
import com.akash.beautifulbhaluka.domain.model.HospitalInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HospitalViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HospitalUiState())
    val uiState: StateFlow<HospitalUiState> = _uiState.asStateFlow()

    init {
        loadHospitalData()
    }

    private fun loadHospitalData() {
        _uiState.value = HospitalUiState(
            hospitals = listOf(
                HospitalInfo(
                    title = "ভালুকা সরকারি হাসপাতাল",
                    address = null,
                    phones = listOf("01733-338905"),
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-19-1.jpeg"
                ),
                HospitalInfo(
                    title = "বন্ধন হাসপাতাল",
                    address = "জালাল ভিলা, সরকারি হাসপাতাল রোড ভালুকা।",
                    phones = listOf("01710-991127"),
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2025/01/IMG-20250102-WA0020.jpg"
                ),
                HospitalInfo(
                    title = "মাস্টার হাসপাতা",
                    address = "ভালুকা বাজার রোড ন্যাশনাল ব্যাংকের উপরতলা",
                    phones = listOf("01731-211120"),
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2025/01/IMG-20250106-WA0052-scaled.jpg"
                ),
                HospitalInfo(
                    title = "লিলি এন্ড তাজ ডেন্টাল সার্জারী",
                    address = "১৯২/২ বাইতুসসাফ লিলি ভিলা হাই স্কুল রোড ভালুকা",
                    phones = listOf("01952-532442"),
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2025/01/IMG-20250102-WA0099.jpg"
                )
            )
        )
    }
}
