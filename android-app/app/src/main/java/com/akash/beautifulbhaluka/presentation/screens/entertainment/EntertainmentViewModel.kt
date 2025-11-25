package com.akash.beautifulbhaluka.presentation.screens.entertainment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntertainmentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EntertainmentUiState())
    val uiState: StateFlow<EntertainmentUiState> = _uiState.asStateFlow()

    init {
        onAction(EntertainmentAction.LoadData)
    }

    fun onAction(action: EntertainmentAction) {
        when (action) {
            is EntertainmentAction.LoadData -> loadData()
            is EntertainmentAction.SearchQueryChanged -> updateSearchQuery(action.query)
            is EntertainmentAction.TypeFilterChanged -> updateTypeFilter(action.type)
            is EntertainmentAction.SearchExpandedChanged -> updateSearchExpanded(action.expanded)
            is EntertainmentAction.CallNumber -> {
            }

            is EntertainmentAction.NavigateToPublish -> {
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val entertainers = listOf(
                    Entertainer(
                        "সোনালী সাউন্ড সিস্টেম",
                        "01712345678",
                        "সাউন্ড সিস্টেম",
                        "ভালুকা বাজার, ময়মনসিংহ",
                        "https://picsum.photos/seed/sound1/600/600"
                    ),
                    Entertainer(
                        "মেলোডি ব্যান্ড পার্টি",
                        "01823456789",
                        "ব্যান্ড পার্টি",
                        "ভালুকা সদর, ময়মনসিংহ",
                        ""
                    ),
                    Entertainer(
                        "রয়েল সাউন্ড সার্ভিস",
                        "01934567890",
                        "সাউন্ড সিস্টেম",
                        "মল্লিকবাড়ী, ভালুকা",
                        "https://picsum.photos/seed/sound2/600/600"
                    ),
                    Entertainer(
                        "স্বপ্ন ব্যান্ড পার্টি",
                        "01645678901",
                        "ব্যান্ড পার্টি",
                        "উথুরা, ভালুকা",
                        "https://picsum.photos/seed/band2/600/600"
                    ),
                    Entertainer(
                        "ডিজিটাল সাউন্ড সিস্টেম",
                        "01756789012",
                        "সাউন্ড সিস্টেম",
                        "ভালুকা পৌরসভা",
                        ""
                    ),
                    Entertainer(
                        "আনন্দ ব্যান্ড টিম",
                        "01867890123",
                        "ব্যান্ড পার্টি",
                        "বিরুনিয়া, ভালুকা",
                        "https://picsum.photos/seed/band3/600/600"
                    ),
                    Entertainer(
                        "প্রিমিয়াম সাউন্ড",
                        "01978901234",
                        "সাউন্ড সিস্টেম",
                        "সিডস্টোর, ভালুকা",
                        "https://picsum.photos/seed/sound3/600/600"
                    ),
                    Entertainer(
                        "রিদম ব্যান্ড পার্টি",
                        "01689012345",
                        "ব্যান্ড পার্টি",
                        "জুলুর বাজার, ভালুকা",
                        "https://picsum.photos/seed/band4/600/600"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        entertainers = entertainers,
                        filteredEntertainers = entertainers
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

    private fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        filterEntertainers()
    }

    private fun updateTypeFilter(type: EntertainmentType) {
        _uiState.update { it.copy(selectedType = type) }
        filterEntertainers()
    }

    private fun updateSearchExpanded(expanded: Boolean) {
        _uiState.update { it.copy(isSearchExpanded = expanded) }
    }

    private fun filterEntertainers() {
        val current = _uiState.value
        val filtered = current.entertainers.filter { entertainer ->
            val matchesSearch = if (current.searchQuery.isBlank()) {
                true
            } else {
                entertainer.name.contains(current.searchQuery, ignoreCase = true) ||
                        entertainer.address.contains(current.searchQuery, ignoreCase = true) ||
                        entertainer.type.contains(current.searchQuery, ignoreCase = true)
            }

            val matchesType = if (current.selectedType == EntertainmentType.ALL) {
                true
            } else {
                entertainer.type == current.selectedType.value
            }

            matchesSearch && matchesType
        }

        _uiState.update { it.copy(filteredEntertainers = filtered) }
    }
}

