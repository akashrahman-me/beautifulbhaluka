package com.akash.beautifulbhaluka.presentation.screens.broadband

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BroadbandViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BroadbandUiState())
    val uiState: StateFlow<BroadbandUiState> = _uiState.asStateFlow()

    init {
        onAction(BroadbandAction.LoadData)
    }

    fun onAction(action: BroadbandAction) {
        when (action) {
            is BroadbandAction.LoadData -> loadData()
            is BroadbandAction.CallService -> callService(action.phone)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the provided broadband services
            val broadbandServices = listOf(
                BroadbandService(
                    name = "অরেঞ্জ কমিউনিকেশন",
                    verified = true,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2025/03/20250323_134445.jpg",
                    details = "সংযোগ চার্জ একদম ফ্রী\nভালুকা সদরে নিরবিচ্ছিন্ন ও দ্রুতগতির ইন্টারনেট সেবার নিশ্চয়তা এবং ২৪ ঘন্টা কাস্টমার সাপোর্ট",
                    phone = "01713-522540",
                    address = "ভালুকা আকরাম মেনশন, ২য় তলা।",
                    rating = "5/5"
                ),
                BroadbandService(
                    name = "ফাস্ট স্পিড ইন্টারনেট",
                    verified = false,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2025/03/20250323_135041.jpg",
                    details = "নিরবিচ্ছিন্ন ও দ্রুতগতির ইন্টারনেট সেবার নিশ্চয়তা এবং ২৪ ঘন্টা কাস্টমার সাপোর্ট।",
                    phone = "01956-565680",
                    address = "সিডস্টোর বাজার, ভালুকা।",
                    rating = "5/5"
                ),
                BroadbandService(
                    name = "অ্যাম্বার আইটি",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "01958-036952",
                    address = "",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "টপ টেন ব্রডব্যান্ড ইন্টারনেট",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "+880 1974-751200",
                    address = "",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "হাই স্পিড ব্রডব্যান্ড ইন্টারনেট",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "09639-800100",
                    address = "তৃতীয় তলা, ওয়াহেদ টাওয়ার।",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "ভালুকা ব্রডব্যান্ড ইন্টারনেট",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "880 1844-454496",
                    address = "গ্যাসলাইন মোড়",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "এনএসডি নেটওয়ার্ক",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "০১৮২৫-২৭৮৬০১",
                    address = "সিডস্টোর, ভালুকা।",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "মিলেনিয়াম ইন্টারনেট",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "01701-226651",
                    address = "দ্বিতীয় তলা, ওয়াহেদ টাওয়ার।",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "আর,এস,খান এন্টারপ্রাইজ",
                    verified = false,
                    image = null,
                    details = "",
                    phone = "01710-544226",
                    address = "মাস্টারবাড়ি",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "SRM BROADBAND NETWORK",
                    verified = false,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2025/07/IMG-20250724-WA0005.jpg",
                    details = "",
                    phone = "০১৭১০-৬৬৫৭৩৭",
                    address = "কাঁঠালী, ভালুকা",
                    rating = "0/5"
                ),
                BroadbandService(
                    name = "ব্রডব্যান্ড নেটওয়ার্ক হাজির বাজার",
                    verified = false,
                    image = null,
                    details = "ইন্টারনেট সেবা দান",
                    phone = "01885095203",
                    address = "হাজিরবাজার এরিয়া",
                    rating = "0/5"
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    broadbandServices = broadbandServices,
                    error = null
                )
            }
        }
    }

    private fun callService(phone: String) {
        // TODO: Implement phone call functionality
        // For now, this is just a placeholder
    }
}
