package com.akash.beautifulbhaluka.presentation.screens.gentsparlour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GentsParlourViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GentsParlourUiState())
    val uiState: StateFlow<GentsParlourUiState> = _uiState.asStateFlow()

    init {
        onAction(GentsParlourAction.LoadData)
    }

    fun onAction(action: GentsParlourAction) {
        when (action) {
            is GentsParlourAction.LoadData -> loadData()
            is GentsParlourAction.CallPhone -> {
                // Handle phone call - this would typically be handled by the UI layer
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val parlours = listOf(
                    GentsParlour(
                        title = "নোবেল রিফ্লেক্সশন সেলুন এন্ড স্পা",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2025/08/IMG_20250812_200628.jpg",
                        description = "ভালুকার প্রথম প্রিমিয়াম সেলুন এবং স্পা সার্ভিস। স্বল্পমূল্যে প্রিমিয়াম সেবার নিশ্চয়তা।",
                        address = "ওয়াহেদ টাওয়ার গ্রাউন্ড ফ্লোর, ভালুকা",
                        phones = listOf("01764-034199", "01819-822224")
                    ),
                    GentsParlour(
                        title = "ফেমাস জেন্টস পার্লার এন্ড লাক্সারি সেলুন",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2023-10-02.jpg",
                        address = "ওয়াহেদ টাওয়ার নিচ তলা",
                        phones = listOf("01642-410089")
                    ),
                    GentsParlour(
                        title = "স্টার জেন্টস পার্লার এন্ড লাক্সারি সেলুন",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2022-01-02.jpg",
                        address = "ভালুকা প্লাজা ২য় তলা",
                        phones = listOf("01772-517777")
                    ),
                    GentsParlour(
                        title = "হেয়ার ফোর্স জেন্টস পার্লার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-11-05.jpg",
                        address = "ভালুকা মেজর ভিটা মোড়",
                        phones = emptyList()
                    ),
                    GentsParlour(
                        title = "মনের মুকুরে এসি জেন্টস পার্লার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/2024-01-09-12.jpg",
                        phones = listOf("01406-566706")
                    ),
                    GentsParlour(
                        title = "সাত রঙ লাক্সারি সেলুন",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Screenshot_20241229-1359312.jpg",
                        address = "ভালুকা প্লাজা ৩য় তলা",
                        phones = emptyList()
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        parlours = parlours
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
