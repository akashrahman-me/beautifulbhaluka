package com.akash.beautifulbhaluka.presentation.screens.kazioffice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KaziOfficeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(KaziOfficeUiState())
    val uiState: StateFlow<KaziOfficeUiState> = _uiState.asStateFlow()

    init {
        onAction(KaziOfficeAction.LoadData)
    }

    fun onAction(action: KaziOfficeAction) {
        when (action) {
            is KaziOfficeAction.LoadData -> loadData()
            is KaziOfficeAction.CallPhone -> {
                // Handle phone call - this would typically be handled by the UI layer
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val kazis = listOf(
                    Kazi(
                        name = "কাজী ভালুকা সদর",
                        address = "ভালুকা পৌরসভা, পশ্চিম বাজার",
                        phone = "01711-462338",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "কাজী মোহাম্মদ আলী শেখ",
                        address = "ভালুকা মেজর ভিটা",
                        phone = "+8801718-186767",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "আশরাফুল কাজী",
                        address = "মেদুয়ারী ইউনিয়ন",
                        phone = "01715-470995",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "কাজী আবুল হোসেন",
                        address = "মল্লিকবাড়ী ইউনিয়ন",
                        phone = "+8801716-815857",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "কাজী মোহাম্মদ আজাহার",
                        address = "ডাকাতিয়া ইউনিয়ন",
                        phone = "+8801715-592025",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "কাজী মতিউর রহমান মতি",
                        address = "হবিরবাড়ী ইউনিয়ন",
                        phone = "01711-315756",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "কাজী জামাল উদ্দিন",
                        address = "ধীতপুর ইউনিয়ন",
                        phone = "01713-960577",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "কাজী মোহাম্মদ আলী",
                        address = "বিরুনিয়া ইউনিয়ন",
                        phone = "01624-964092",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    ),
                    Kazi(
                        name = "বাবুল কাজী",
                        address = "ভরাডোবা ইউনিয়ন",
                        phone = "+8801727-559004",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735199734348.png"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        kazis = kazis
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "ডেটা লোড করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }
}
