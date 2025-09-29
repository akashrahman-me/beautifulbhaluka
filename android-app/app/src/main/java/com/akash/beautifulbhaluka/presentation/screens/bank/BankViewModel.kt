package com.akash.beautifulbhaluka.presentation.screens.bank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BankViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BankUiState())
    val uiState: StateFlow<BankUiState> = _uiState.asStateFlow()

    init {
        onAction(BankAction.LoadData)
    }

    fun onAction(action: BankAction) {
        when (action) {
            is BankAction.LoadData -> loadData()
            is BankAction.OnBankClick -> {
                // Handle bank click (could navigate to detail screen or show more info)
            }

            is BankAction.OnPhoneClick -> {
                // Handle phone click (could open dialer)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Using bank-appropriate data instead of courier services
                val banks = listOf(
                    Bank(
                        name = "সোনালী ব্যাংক লিমিটেড",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-03-07.jpg",
                        address = "ভালুকা শাখা, ভালুকা বাজার",
                        phone = "01952-255785"
                    ),
                    Bank(
                        name = "জনতা ব্যাংক লিমিটেড",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-03-25.jpg",
                        address = "ভালুকা শাখা, মূল সড়ক",
                        phone = "01324-719196"
                    ),
                    Bank(
                        name = "রূপালী ব্যাংক লিমিটেড",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/FB_IMG_1678364968266.jpg",
                        address = "ভালুকা শাখা, কলেজ রোড",
                        phone = "01713-228474"
                    ),
                    Bank(
                        name = "কৃষি ব্যাংক",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-12-07.jpg",
                        address = "ভালুকা শাখা, বাজার এলাকা",
                        phone = "01321-230745"
                    ),
                    Bank(
                        name = "ইসলামী ব্যাংক বাংলাদেশ লিমিটেড",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2021-11-11.jpg",
                        address = "ভালুকা শাখা, মূল বাজার",
                        phone = "01810-152890"
                    ),
                    Bank(
                        name = "ডাচ-বাংলা ব্যাংক লিমিটেড",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-06-18.jpg",
                        address = "ভালুকা শাখা, কেন্দ্রীয় সড়ক",
                        phone = "01777-667372"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        banks = banks,
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
