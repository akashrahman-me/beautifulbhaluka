package com.akash.beautifulbhaluka.presentation.screens.police

import androidx.lifecycle.ViewModel
import com.akash.beautifulbhaluka.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.akash.beautifulbhaluka.domain.model.PoliceInfo
import com.akash.beautifulbhaluka.domain.model.PoliceStation

class PoliceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PoliceUiState())
    val uiState: StateFlow<PoliceUiState> = _uiState.asStateFlow()

    init {
        loadPoliceData()
    }

    fun onAction(action: PoliceAction) {
        when (action) {
            is PoliceAction.LoadData -> loadPoliceData()
            is PoliceAction.CallNumber -> {
                // Handle phone call logic here
                // For now, this is a placeholder for future implementation
            }
        }
    }

    private fun loadPoliceData() {
        _uiState.update { it.copy(isLoading = true) }

        // Static data as per requirement - will integrate with backend later
        val mockData = PoliceInfo(
            title = "থানা পুলিশ",
            stations = listOf(
                PoliceStation(
                    avatarUrl = R.drawable.b6998114, // Int drawable resource
                    officerName = "ওসি ভালুকা",
                    location = "ভালুকা মডেল থানা পুলিশ",
                    phoneNumbers = listOf("০১৭১৩৩৭৩৪৪১")
                ),
                PoliceStation(
                    avatarUrl = R.drawable.b6998114,
                    officerName = "ওসি হাইওয়ে",
                    location = "ভরাডোবা হাইওয়ে থানা, ভালুকা",
                    phoneNumbers = listOf("+8801320182800")
                ),
                PoliceStation(
                    avatarUrl = R.drawable.b6998114,
                    officerName = "ডিউটি অফিসার",
                    location = "ভালুকা মডেল থানা পুলিশ",
                    phoneNumbers = listOf("01733-338958")
                ),
                PoliceStation(
                    avatarUrl = R.drawable.b6998114,
                    officerName = "ওসি ভালুকা তদন্ত",
                    location = "ভালুকা মডেল থানা পুলিশ",
                    phoneNumbers = listOf("০১৩২০১০৩২৯৬")
                ),
            )
        )

        _uiState.update {
            it.copy(
                isLoading = false,
                policeInfo = mockData,
                error = null
            )
        }
    }
}
