package com.akash.beautifulbhaluka.presentation.screens.butchercook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ButcherCookViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ButcherCookUiState())
    val uiState: StateFlow<ButcherCookUiState> = _uiState.asStateFlow()

    init {
        onAction(ButcherCookAction.LoadData)
    }

    fun onAction(action: ButcherCookAction) {
        when (action) {
            is ButcherCookAction.LoadData -> loadData()
            is ButcherCookAction.SearchQueryChanged -> updateSearchQuery(action.query)
            is ButcherCookAction.TypeFilterChanged -> updateTypeFilter(action.type)
            is ButcherCookAction.SearchExpandedChanged -> updateSearchExpanded(action.expanded)
            is ButcherCookAction.CallNumber -> {
                // Handle phone call action - can be implemented later
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val butcherCooks = listOf(
                    ButcherCook("🐄", "নুরুল ইসলাম", "01709158834", "তামাট বাজার", "কসাই"),
                    ButcherCook(
                        "🐄",
                        "লুৎফর রহমান",
                        "01762152244",
                        "নারাঙ্গি উথুরা, ভালুকা",
                        "কসাই"
                    ),
                    ButcherCook("🧑‍🍳", "জহির", "01709158834", "ভালুকা", "বাবুর্চি"),
                    ButcherCook(
                        "🐄",
                        "তারা মিয়া ( তারেক )",
                        "০১৭৪৫৯৭০৫৩৪",
                        "ভালুকা উপজেলা স্বাস্থ্য কমপ্লেক্স রোড, ব্রিজ মোড় এ। শাহজালাল গোস্তো বিতান।",
                        "কসাই"
                    ),
                    ButcherCook("🧑‍🍳", "জুয়েল", "+880 1720-043197", "মেদিলা", "বাবুর্চি"),
                    ButcherCook("🐄", "Mazahar", "+8801847871258", "মুছার ভিটা", "কসাই"),
                    ButcherCook("🐄", "সাইদুর কসাই", "+880 1716-854285", "জামুর ভিটা", "কসাই"),
                    ButcherCook("🐄", "আঃ মতিন", "01731151630", "বরটিলা, কলেজ বাজার", "কসাই"),
                    ButcherCook("🧑‍🍳", "মো:উজ্জল মিয়া", "01791952018", "রাজৈ ভালুকা", "বাবুর্চি"),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ আব্দুল হামিদ",
                        "01708446664",
                        "গ্রাম:নিঝুরী, ইউনিয়ন:মেদুয়ারি",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "খোরশেদ আলম খসরু",
                        "০১৭৮৩৮০৯৭৮২",
                        "আঙ্গারগাড়া, ইস্তারঘাট, ডাকাতিয়া, ভালুকা।",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "কালাম বাবুর্চি",
                        "০১৯৬০১০৯২৪৯",
                        "কাশর হবিরবাড়ি ভালুকা ময়মনসিংহ",
                        "বাবুর্চি"
                    ),
                    ButcherCook("🧑‍🍳", "মো ফিরোজ মিয়া", "০১৭৭১৯০৯২১১", "পুরুরা", "বাবুর্চি"),
                    ButcherCook(
                        "🐄",
                        "সিরাজুল ইসলাম (সিরু)",
                        "+8801722260268",
                        "জিবনতলা,হবিরবাড়ী,ভালুকা,ময়মনসিংহ",
                        "কসাই"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মো নুরুল ইসলাম",
                        "01737575740",
                        "বাঁশিল ভালুকা",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "রনি , মাহিম, ওয়ালিদ, রুদ্র",
                        "01618062549",
                        "বাঁশীল",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🐄",
                        "সেলিম কসাই",
                        "01776541458",
                        "মেদুয়ারী, ভালুকা। মাংসা কাটার জন্য যোগাযোগ করুন।",
                        "কসাই"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ খাইরুল ইসলাম (হাক্কুল)",
                        "01720-450867",
                        "নারাঙ্গী, ১নং উথুরা ইউনিয়ন, ভালুকা, ময়মনসিংহ।",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ সাইফুল ইসলাম",
                        "01776-622330",
                        "মেনজেনা, ১নং উথুরা ইউনিয়ন, ভালুকা, ময়মনসিংহ।",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ সাইদুল ইসলাম",
                        "01716616697",
                        "নারাঙ্গী, ১নং উথুরা ইউনিয়ন, ভালুকা, ময়মনসিংহ।",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "আবদুল খালেক",
                        "01760-309751",
                        "ধাইরাপাড়া ভালুকা",
                        "বাবুর্চি"
                    ),
                    ButcherCook("🧑‍🍳", "জামান খান", "+8801795010248", "সাতেংগা ভালুকা", "বাবুর্চি"),
                    ButcherCook(
                        "🧑‍🍳",
                        "আজিজুল হক ( ভালুকা পাইলট স্কুলের চতুর্থ শ্রেণীর কর্মচারী )",
                        "01312-910785",
                        "ভালুকা পৌরসভা ১ নং ওয়ার্ড কোটভবন",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ তোতা মিয়া",
                        "+880 1731-208782",
                        "সিডষ্টোর বাজার (হবিরবাড়ী)",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ আইনূল হক",
                        "+880 1929-830450",
                        "সিডষ্টোর বাজার (হবিরবাড়ী)",
                        "বাবুর্চি"
                    ),
                    ButcherCook("🐄", "সবুজ খান", "01730162737", "ভালুকা বাজার", "কসাই"),
                    ButcherCook(
                        "🐄",
                        "মোঃ সবুজ খান",
                        "০১৭ ৩০১৬২৭৩৭",
                        "মা এর দুয়া মাংস বিতরণ । ভালুকা মধ্য বাজার।",
                        "কসাই"
                    ),
                    ButcherCook("🧑‍🍳", "আরিফ সরকার", "+8801978-638797", "কাচিনা বাজার", "বাবুর্চি"),
                    ButcherCook(
                        "🧑‍🍳",
                        "জব্বার বাবুচি",
                        "01733418468",
                        "৭নং ওয়ার্ড টিনটি রোড ভালুকা",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মো ময়জুদ্দিন",
                        "০১৭১৯০৭৯৯৬০",
                        "সিডিস্টুর বাজার",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মো ময়জুদ্দিন বাবুর্চি",
                        "০১৭১৯০৭৯৯৬০",
                        "১০ নং হবির বাড়ী ইউনিয়ন সিডিস্টুর জিবন তলা",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🧑‍🍳",
                        "মোঃ শাহীন মিয়া",
                        "01737984779",
                        "মেদুয়ারী, ভালুকা ময়মনসিংহ",
                        "বাবুর্চি"
                    ),
                    ButcherCook(
                        "🐄",
                        "মো: আসাদুল ইসলাম",
                        "01773338795",
                        "কাষর চৌরাস্তা, ভালুকা ,ময়মনসিংহ",
                        "কসাই"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        butcherCooks = butcherCooks,
                        filteredButcherCooks = butcherCooks
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterData()
    }

    private fun updateTypeFilter(type: ButcherCookType) {
        _uiState.update { it.copy(selectedType = type) }
        filterData()
    }

    private fun updateSearchExpanded(expanded: Boolean) {
        _uiState.update { it.copy(isSearchExpanded = expanded) }
    }

    private fun filterData() {
        val currentState = _uiState.value
        val query = currentState.searchQuery.lowercase()
        val type = currentState.selectedType

        val filtered = currentState.butcherCooks.filter { butcherCook ->
            val matchesSearch = query.isEmpty() ||
                    butcherCook.name.lowercase().contains(query) ||
                    butcherCook.address.lowercase().contains(query) ||
                    butcherCook.type.lowercase().contains(query)

            val matchesType = type == ButcherCookType.ALL || butcherCook.type == type.value

            matchesSearch && matchesType
        }

        _uiState.update { it.copy(filteredButcherCooks = filtered) }
    }
}
