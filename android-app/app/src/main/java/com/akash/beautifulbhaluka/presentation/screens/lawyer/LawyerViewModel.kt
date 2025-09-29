package com.akash.beautifulbhaluka.presentation.screens.lawyer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.akash.beautifulbhaluka.domain.model.LawyerInfo

class LawyerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LawyerUiState())
    val uiState: StateFlow<LawyerUiState> = _uiState.asStateFlow()

    init {
        loadLawyerData()
    }

    fun onAction(action: LawyerAction) {
        when (action) {
            is LawyerAction.LoadData -> loadLawyerData()
            is LawyerAction.CallNumber -> {
                // Handle phone call logic here
                // For now, this is a placeholder for future implementation
            }
        }
    }

    private fun loadLawyerData() {
        _uiState.update { it.copy(isLoading = true) }

        // Static data as per requirement
        val lawyers = listOf(
            LawyerInfo(
                name = "এডভোকেট আশরাফুল হক জর্জ",
                designation = "সাবেক এটর্নি জেনারেল, সুপ্রিমকোর্ট।",
                phone = "+880 1552-437912",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট রহিম মিয়া",
                designation = "এপিপি, সুপ্রিমকোর্ট।",
                phone = "+880 1725-004240",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট রাখাল উকিল",
                designation = "সিনিয়র এডভোকেট জজ কোর্ট, ময়মনসিংহ",
                phone = "01711-101390",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট আনোয়ার টুটু",
                designation = "জজ কোর্ট, ময়মনসিংহ",
                phone = "01711-115055",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট তপু গোপাল",
                designation = "সুপ্রিমকোর্ট।",
                phone = "+8801715-992955",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট পলাশ",
                designation = "জজ কোর্ট, ময়মনসিংহ।",
                phone = "+8801711-354366",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট কামরুল ইসলাম",
                designation = "জজ কোর্ট, ময়মনসিংহ।",
                phone = "+880 1745-538533",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট অন্তর",
                designation = "জজ কোর্ট, ময়মনসিংহ",
                phone = "+880 1722-259640",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            ),
            LawyerInfo(
                name = "এডভোকেট নাজমুল হক হিমেল",
                designation = "জজ কোর্ট, ময়মনসিংহ",
                phone = "+8801716-321592",
                image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735317705899.png"
            )
        )

        _uiState.update {
            it.copy(
                isLoading = false,
                lawyers = lawyers,
                error = null
            )
        }
    }
}
