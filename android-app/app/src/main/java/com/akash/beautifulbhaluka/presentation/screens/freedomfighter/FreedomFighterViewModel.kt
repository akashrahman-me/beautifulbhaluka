package com.akash.beautifulbhaluka.presentation.screens.freedomfighter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FreedomFighterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FreedomFighterUiState())
    val uiState: StateFlow<FreedomFighterUiState> = _uiState.asStateFlow()

    init {
        onAction(FreedomFighterAction.LoadData)
    }

    fun onAction(action: FreedomFighterAction) {
        when (action) {
            is FreedomFighterAction.LoadData -> loadData()
            is FreedomFighterAction.DownloadPdf -> downloadPdf()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the table provided
            val freedomFighters = listOf(
                FreedomFighter(
                    "১", "01610002407", "মোঃ আবুল হোসেন পাঠান", "লিয়াকত আলী পাঠান",
                    "পাঁচগাঁও", "আংগারগাড়া", "বেসামরিক গেজেট (1806), লাল মুক্তিবার্তা (0115060506)"
                ),
                FreedomFighter(
                    "২",
                    "01610002414",
                    "মোঃ ফজলুল হক তালুকদার",
                    "মোঃ আঃ জব্বার তালুকদার",
                    "ডাকাতিয়া",
                    "ডাকাতিয়া",
                    "মুক্তিযোদ্ধাদের ভারতীয় তালিকা (11286), লাল মুক্তিবার্তা (0115060157), বেসামরিক গেজেট (1811)"
                ),
                FreedomFighter(
                    "৩", "01610002415", "মোঃ আবদুল হামিদ", "আবু আলী শেখ",
                    "রাংচাপড়া", "রাংচাপড়া", "লাল মুক্তিবার্তা (0115060828)"
                ),
                FreedomFighter(
                    "৪", "01610002422", "মোঃ মকবুল হোসাইন", "ইছব আলী",
                    "ঢুলিভিটা", "বিরুনীয়া", "বেসামরিক গেজেট (2121), লাল মুক্তিবার্তা (0115060604)"
                ),
                FreedomFighter(
                    "৫", "01610002429", "মোঃ মকবুল হোসেন", "হাছেন আলী সরকার",
                    "মেদুয়ারী", "মেদুয়ারী", "বেসামরিক গেজেট (1889), লাল মুক্তিবার্তা (0115060316)"
                ),
                FreedomFighter(
                    "৬", "01610002430", "মোঃ আঃ মতিন পাঠান", "লিয়াকত আলী পাঠান",
                    "পাঁচগাঁও", "আংগারগাড়া", "লাল মুক্তিবার্তা (0115060564), বেসামরিক গেজেট (1805)"
                ),
                FreedomFighter(
                    "৭", "01610002439", "মজিবুর রহমান", "শোমেশ উদ্দিন",
                    "ডুমনিঘাট", "আংগারগাড়া", "বেসামরিক গেজেট (1815), লাল মুক্তিবার্তা (0115060731)"
                ),
                FreedomFighter(
                    "৮",
                    "01610002443",
                    "মোঃ মোন্তাজ উদ্দিন",
                    "কলিম উদ্দিন",
                    "পাচগাঁও",
                    "আংগারগাড়া",
                    "সেনাবাহিনী গেজেট (18719), লাল মুক্তিবার্তা (0115060072)"
                ),
                FreedomFighter(
                    "৯", "01610002444", "মোঃ আবদুস সালাম", "মৃতঃ ইমান আলী",
                    "হোসেনপুর", "আংগারগাড়া", "বেসামরিক গেজেট (1993), লাল মুক্তিবার্তা (0115060078)"
                ),
                FreedomFighter(
                    "১০", "01610002445", "মোঃ আবুল কালাম আজাদ", "হাজী নাজিম উদ্দিন সরকার",
                    "হোসেনপুর", "আংগারগাড়া", "বেসামরিক গেজেট (1959), লাল মুক্তিবার্তা (0115060726)"
                ),
                FreedomFighter(
                    "১১",
                    "01610002446",
                    "মোঃ কুবেদ আলী ফকির",
                    "জবেদ আলী ফকির",
                    "সোনাখালী",
                    "আংগারগাড়া",
                    "বেসামরিক গেজেট (1842), লাল মুক্তিবার্তা (0115060153), মুক্তিযোদ্ধাদের ভারতীয় তালিকা (11283)"
                ),
                FreedomFighter(
                    "১২",
                    "01610002450",
                    "মোঃ শামছুল আলম",
                    "উসমান আলী",
                    "আংগারগাড়া",
                    "আংগারগাড়া",
                    "বেসামরিক গেজেট (1978), লাল মুক্তিবার্তা (0115060261)"
                ),
                FreedomFighter(
                    "১৩",
                    "01610002456",
                    "মোঃ আঃ বারেক",
                    "মোকছেদ আলী",
                    "বালিয়াপাড়া",
                    "ডাকাতিয়া",
                    "বেসামরিক গেজেট (5473), লাল মুক্তিবার্তা (0115060018)"
                ),
                FreedomFighter(
                    "১৪", "01610002535", "আঃ কাদির", "মরহুম আঃ মজিদ",
                    "রাজৈ", "চান্দাব", "বেসামরিক গেজেট (5154), লাল মুক্তিবার্তা (0115060524)"
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    freedomFighters = freedomFighters,
                    error = null
                )
            }
        }
    }

    private fun downloadPdf() {
        // TODO: Implement PDF download functionality
        // For now, this is just a placeholder
    }
}
