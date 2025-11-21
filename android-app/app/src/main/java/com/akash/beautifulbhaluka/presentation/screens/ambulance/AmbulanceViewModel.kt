package com.akash.beautifulbhaluka.presentation.screens.ambulance

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.akash.beautifulbhaluka.domain.model.AmbulanceInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AmbulanceViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(AmbulanceUiState())
    val uiState: StateFlow<AmbulanceUiState> = _uiState.asStateFlow()

    init {
        loadAmbulanceData()
    }

    fun onAction(action: AmbulanceAction) {
        when (action) {
            is AmbulanceAction.DialPhone -> dialPhone(action.phoneNumber)
        }
    }

    private fun dialPhone(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$phoneNumber".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            // Handle error silently or update UI state with error
        }
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
