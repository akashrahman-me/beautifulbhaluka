package com.akash.beautifulbhaluka.presentation.screens.ladiesparlour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LadiesParlourViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LadiesParlourUiState())
    val uiState: StateFlow<LadiesParlourUiState> = _uiState.asStateFlow()

    init {
        onAction(LadiesParlourAction.LoadData)
    }

    fun onAction(action: LadiesParlourAction) {
        when (action) {
            is LadiesParlourAction.LoadData -> loadData()
            is LadiesParlourAction.CallParlour -> callParlour(action.phone)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Sample data from the provided beauty parlour data
            val parlours = listOf(
                BeautyParlour(
                    name = "বঁধুয়া হারবাল বিউটি পার্লার",
                    address = "ভালুকা বাসস্ট্যান্ড আকতার উদ্দিন কমপ্লেক্সের ৩য় তলায়",
                    phone = "01733-338905",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "প্যারাডাইস বিউটি পার্লার",
                    address = "হক সুপার মার্কেট ৩য় তলা, ভালুকা বাসস্ট্যান্ড",
                    phone = null,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "মুনা বিউটি পার্লার",
                    address = "ওয়াহেদ টাওয়ার ২য় তলা",
                    phone = null,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "রাইসা হারবাল বিউটি পার্লার",
                    address = "ভালুকা পাঁচ রাস্তা মোড় বাজার রোড",
                    phone = "01795-756891",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "এময়না পাখি বিউটি পার্লার",
                    address = "ভালুকা পাঁচ রাস্তার মোড় সুজ বাজারের উপরের তলায়",
                    phone = null,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "অধরা মেকওভার",
                    address = "ভালুকা পাইলট স্কুলের পিছনে",
                    phone = "01876-7844256",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "ইউনিক বিউটিস বাই আঁখি",
                    address = "ভালুকা সরকারি কলেজ গেইট এর বিপরীতে মতিন ডাক্তার এর বাসা ২য় তলায়",
                    phone = "01321-197824",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "সাজমনি বিউটি পার্লার",
                    address = "ভালুকা কলেজ রোড, রোজবার্ড স্কুলের দ্বিতীয় তলায়",
                    phone = null,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "ছোঁয়া বিউটি পার্লার",
                    address = null,
                    phone = null,
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "জবা বিউটি পার্লার",
                    address = "ভালুকা কলেজ রোড ২২ ফিট মোড় হাজি পাড়া",
                    phone = "01684-816617",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "লাভলি স্কিন কেয়ার বিউটি পার্লার",
                    address = "ভালুুকা প্লাজা ৩য় তলা",
                    phone = "01728-223243",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "সাজ বিউটি পার্লার",
                    address = "কায়া মসজিদ ভালুকা মেজর ভিটা",
                    phone = "01749-702858",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "মোহনা বিউটি পার্লার",
                    address = "গফরগাঁও রোড ভালুকা, সরকার টাওয়ার ১ম তলা",
                    phone = "01719-217177",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                ),
                BeautyParlour(
                    name = "ঐশী'স মেকওভার",
                    address = "ভালুকা পৌরসভা ২নং ওয়ার্ড, পাবলিক হল রোড",
                    phone = "01770-451288",
                    image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/make-up.png"
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    parlours = parlours,
                    error = null
                )
            }
        }
    }

    private fun callParlour(phone: String) {
        // TODO: Implement phone call functionality
        // For now, this is just a placeholder
    }
}
