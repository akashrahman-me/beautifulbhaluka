package com.akash.beautifulbhaluka.presentation.screens.voterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoterListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VoterListUiState())
    val uiState: StateFlow<VoterListUiState> = _uiState.asStateFlow()

    init {
        onAction(VoterListAction.LoadData)
    }

    fun onAction(action: VoterListAction) {
        when (action) {
            is VoterListAction.LoadData -> loadData()
            is VoterListAction.DownloadFile -> downloadFile(action.item)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val voterListItems = listOf(
                    VoterListItem(
                        unionName = "১নং উথুরা ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Uthura-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "২নং মেদুয়ারী ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Meduary-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "৩নং ভরাডোবা ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Bharadoba-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "৪নং ধীতপুর ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Dhitpur-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "৫নং বিরুনিয়া ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/BIRONIA-UNION.zip"
                    ),
                    VoterListItem(
                        unionName = "৬নং ভালুকা ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Bhaluka-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "৭নং মল্লিকবাড়ী ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Mollikbari-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "৮নং ডাকাতিয়া ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Dhakatia-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "৯নং কাচিনা ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Kachina-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "১০নং হবিরবাড়ী ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Habirbari-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "১১নং রাজৈ ইউনিয়ন",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Rajoy-Union.zip"
                    ),
                    VoterListItem(
                        unionName = "ভালুকা পৌরসভা",
                        downloadUrl = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/Pouroshoba.zip"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        voterListItems = voterListItems,
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

    private fun downloadFile(item: VoterListItem) {
        // Handle file download logic here
        // This could open a browser or use a download manager
    }
}
