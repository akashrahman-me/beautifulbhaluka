package com.akash.beautifulbhaluka.presentation.screens.ambulance

import androidx.lifecycle.ViewModel
import com.akash.beautifulbhaluka.domain.model.AmbulanceInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AmbulanceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AmbulanceUiState())
    val uiState: StateFlow<AmbulanceUiState> = _uiState.asStateFlow()

    init {
        loadAmbulanceData()
    }

    private fun loadAmbulanceData() {
        _uiState.value = AmbulanceUiState(
            ambulances = listOf(
                AmbulanceInfo(
                    title = "এম্বুলেন্স",
                    organization = "ভালুকা সরকারি হাসপাতাল",
                    phone = "01756-759506",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/ambulance.png"
                ),
                AmbulanceInfo(
                    title = "এম্বুলেন্স",
                    organization = "ভালুকা মাস্টার হাসপাতাল",
                    phone = "০১৭৩১-২১১১২০",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/ambulance.png"
                ),
                AmbulanceInfo(
                    title = "লাশবাহী ফ্রিজিং গাড়ি",
                    phones = listOf("01953-921890", "01758-845430"),
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/ambulance.png"
                )
            )
        )
    }
}
