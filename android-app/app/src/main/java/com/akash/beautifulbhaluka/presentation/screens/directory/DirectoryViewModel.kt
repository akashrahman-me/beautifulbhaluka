package com.akash.beautifulbhaluka.presentation.screens.directory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DirectoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DirectoryUiState())
    val uiState: StateFlow<DirectoryUiState> = _uiState.asStateFlow()

    init {
        onAction(DirectoryAction.LoadData)
    }

    fun onAction(action: DirectoryAction) {
        when (action) {
            is DirectoryAction.LoadData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val offices = listOf(
                    Office(
                        title = "উপ আয়কর অফিস",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ওয়াহেদ টাওয়ার", "ভালুকা")
                    ),
                    Office(
                        title = "সাবরেজিস্টার অফিস",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ভালুকা বাজার", "ক্ষীরু নদীর পাড়")
                    ),
                    Office(
                        title = "বিকাশ কাস্টমার কেয়ার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735537627624.png",
                        address = listOf("ভালুকা মাস্টারবাড়ি", "পুরাতন হাজী মার্কেট ২য় তলা")
                    ),
                    Office(
                        title = "গ্রামীনফোন সেন্টার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735537865791.png",
                        address = listOf("ভালুকা বাসস্ট্যান্ড", "রাস্তার পশ্চিম পাশে")
                    ),
                    Office(
                        title = "উপজেলা স্বাস্থ্য ও পরিবার কল্যাণ কর্মকর্তার কার্যালয়",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ভালুকা সরকারি হাসপাতাল", "ভালুকা")
                    ),
                    Office(
                        title = "ট্রমা সেন্টার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ভালুকা সরকারি হাসপাতাল", ".")
                    ),
                    Office(
                        title = "ফায়ার সার্ভিস",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ভালুকা টু গফরগাঁও রোড", "রাস্তার উত্তর পাশে")
                    ),
                    Office(
                        title = "পোস্ট অফিস",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ভালুকা পাচ রাস্তার মোড়", "পাইলট স্কুল রোড")
                    ),
                    Office(
                        title = "বাংলাদেশ বিদ্যুৎ উন্নয়ন বোর্ড বিক্রয় ও বিতরণ অফিস,ভালুকা",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("পল্লী বিদ্যুৎ সমিতি-২", ".")
                    ),
                    Office(
                        title = "ভরাডোবা হাইওয়ে থানা",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf(
                            "ভালুকা টু ঢাকা মহাসড়কের খীরু নদীর ব্রীজ পার হয়ে",
                            "বামপাশে নদীর পাড় সংলগ্ন।"
                        )
                    ),
                    Office(
                        title = "ট্রাফিক পুলিশ ফাড়ি",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("ভালুকা মডেল থানা সংলগ্ন", ".")
                    ),
                    Office(
                        title = "শিল্প পুলিশ ব্যারাক",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf(
                            "ভালুকা টু ময়মনসিংহ মহাসড়কের পাশে",
                            "পাকিস্তানি মিল সংলগ্ন"
                        )
                    ),
                    Office(
                        title = "জেলা পরিষদ ডাক বাংলো",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf("পাচ রাস্তার মোড়", "ভালুকা পাইলট স্কুল রোড")
                    ),
                    Office(
                        title = "সওজ ডাকবাংলো",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483433122.png",
                        address = listOf(
                            "ভালুকা টু ময়মনসিংহ মহাসড়কের পাশে",
                            "পুরাতন কোর্টবিল্ডিং সংলগ্ন।"
                        )
                    )
                )

                val subDistrictOffices = listOf(
                    "উপজেলা নির্বাহী কর্মকর্তার কার্যালয়।",
                    "নির্বাচন কমিশন অফিস।(পুরাতন উপজেলা ভবন চত্বরে)",
                    "উপজেলা সমাজসেবা অফিস।(পুরাতন উপজেলা ভবন চত্বরে)",
                    "উপজেলা কৃষি অফিস।(পুরাতন উপজেলা ভবন চত্বরে)",
                    "উপজেলা যুব উন্নয়ন অফিস।(পুরাতন উপজেলা ভবন চত্বরে)",
                    "উপজেলা মহিলা বিষয়ক কার্যালয়। (পুরাতন উপজেলা ভবন চত্বরে)",
                    "আনসার ও ভিডিপি অফিস।(পুরাতন উপজেলা ভবন চত্বরে)",
                    "উপজেলা মৎস অফিস।",
                    "উপজেলা সমবায় অফিস।",
                    "উপজেলা প্রাথমিক শিক্ষা অফিস।",
                    "উপজেলা মাধ্যমিক শিক্ষা অফিস।(পুরাতন উপজেলা ভবন চত্বরে)",
                    "উপজেলা প্রকৌশলীর কার্যালয়।",
                    "উপজেলা প্রকল্প বাস্তবায়ন অফিস।",
                    "উপজেলা স্বাস্থ্য প্রকৌশলীর কার্যালয়।(শাপলা হল মোড় সংলগ্ন)",
                    "উপজেলা খাদ্য গুদাম। (শাপলা হল মোড় সংলগ্ন)",
                    "বিআরডিবি অফিস।"
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        offices = offices,
                        subDistrictOffices = subDistrictOffices
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
