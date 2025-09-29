package com.akash.beautifulbhaluka.presentation.screens.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PlacesUiState())
    val uiState: StateFlow<PlacesUiState> = _uiState.asStateFlow()

    init {
        onAction(PlacesAction.LoadData)
    }

    fun onAction(action: PlacesAction) {
        when (action) {
            is PlacesAction.LoadData -> loadData()
            is PlacesAction.OnPlaceClick -> {
                // Handle place click (could navigate to detail screen)
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val places = listOf(
                    Place(
                        title = "ড্রিম হাউস পার্ক",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-31-2.jpeg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "ড্রিম হাউস পার্ক ময়মনসিংহ জেলার ভালুকা উপজেলার কাঁঠালি এলাকায় অবস্থিত একটি বিনোদন কেন্দ্র। এটি পরিবার এবং বন্ধুদের সঙ্গে সময় কাটানোর […]",
                        category = "ভালুকার দর্শনীয় স্থান"
                    ),
                    Place(
                        title = "ধনকুড়া রিসোর্ট",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20241231_163724.jpg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "ময়মনসিংহ শহরের প্রানকেন্দ্র ভালুকা মল্লিকবাড়ি রোডের ঠিক বিপরীত পার্শে অবস্থিত এই রিসোর্ট। রিসোর্ট একটি পরিবেশ যেখানে প্রাকৃতিক সৌন্দর্য, আরাম, বিভিন্ন",
                        category = "ভালুকার দর্শনীয় স্থান"
                    ),
                    Place(
                        title = "অরণ্য ইকো রিসোর্ট",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-50.jpeg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "সবুজের কাছে নিজেকে সঁপে দিতে চাইলে একবার ঘুরে আসা যেতে পারে অরন্য ইকো রিসোর্ট থেকে। ময়মনসিংহ জেলার ভালুকা উপজেলার নিশিন্দায়",
                        category = "ভালুকার দর্শনীয় স্থান"
                    ),
                    Place(
                        title = "চন্দ্রমল্লিকা রিসোর্ট",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-42.jpeg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "ভালুকা উপজেলা বর্তমানে বেশ কয়েকটি পার্ক ও রিসোর্টের জন্য জনপ্রিয় হয়ে উঠছে। তেমনি একটি রিসোর্ট ভালুকার কাঠালীতে অবস্থিত চন্দ্রমল্লিকা হলিডে",
                        category = "ভালুকার দর্শনীয় স্থান"
                    ),
                    Place(
                        title = "মেঘমাটি ভিলেজ রিসোর্ট",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-41.jpeg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "মাঝে মাঝেই আমাদের মন ছুটে যেতে চায় গ্রামের জীবনে। গ্রামের মেঠো পথ আর খোলা হাওয়ায় দিন কাটাতে হৃদয় ব্যাকুল হয়ে",
                        category = "ভালুকার দর্শনীয় স্থান"
                    ),
                    Place(
                        title = "কাদিগড় জাতীয় উদ্যান",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG_20241229_131638.jpg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "বন বা উদ্যান যাদের পছন্দের জায়গা তাদের জন্য ভাওয়াল গড় বা মধুপুরের গড়ের বিকল্প হতে পারে ভালুকা উপজেলার কাচিনা ইউনিয়নে",
                        category = "ভালুকার দর্শনীয় স্থান"
                    ),
                    Place(
                        title = "গ্রীণ অরণ‍্য পার্ক",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-32-678x381-1.jpeg",
                        author = "admin2",
                        date = "December 29, 2024",
                        content = "—",
                        category = "ভালুকার দর্শনীয় স্থান"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        places = places,
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
}
