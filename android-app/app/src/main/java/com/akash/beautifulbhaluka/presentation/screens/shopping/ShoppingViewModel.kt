package com.akash.beautifulbhaluka.presentation.screens.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingUiState())
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    init {
        onAction(ShoppingAction.LoadData)
    }

    fun onAction(action: ShoppingAction) {
        when (action) {
            is ShoppingAction.LoadData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val markets = listOf(
                    Market(
                        title = "ভালুকা বাজার",
                        description = "প্রতি শুক্রবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "বাটাজোড় গরুর বাজার",
                        description = "প্রতি মঙ্গলবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "মল্লিকবাড়ী বাজার",
                        description = "প্রতি শনিবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "উথুরা বাজার",
                        description = "প্রতি রবিবার, বুধবার ও শুক্রবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "বিরুনিয়া বাজার",
                        description = "প্রতি বৃহস্পতিবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "সিডস্টোর বাজার",
                        description = "প্রতি সোমবার ও শুক্রবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "জুলুর বাজার",
                        description = "মেদুয়ারী ইউনিয়ন বরাইদ গ্রাম\nপ্রতি রবিবার ও বৃহস্পতিবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "ঝাল বাজার",
                        description = "হবিরবাড়ী ইউনিয়ন, ঝালপাজা গ্রাম\nপ্রতি শনিবার, মঙ্গলবার ও বৃহস্পতিবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "পাড়াগাঁও গতিয়ার বাজার",
                        description = "হবিরবাড়ী ইউনিয়ন পাড়াগাঁও\nপ্রতি শনিবার ও মঙ্গলবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "আঙ্গারগারা বাজার",
                        description = "ডাকাতিয়া ইউনিয়ন, আঙ্গারগারা\nপ্রতি সোমবার, বুধবার ও শুক্রবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "বাহারতা বাজার",
                        description = "মেদুয়ারী ইউনিয়ন\nপ্রতি রবিবার ও বৃহস্পতিবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "নিশিন্দা বাজার",
                        description = "ভরাডোবা ইউনিয়ন, নিশিন্দা গ্রাম\nপ্রতি শনিবার ও মঙ্গলবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    ),
                    Market(
                        title = "ধলিয়া বাজার",
                        description = "ধীতপুর ইউনিয়ন, ধলিয়া গ্রাম\nপ্রতি শনিবার ও মঙ্গলবার",
                        image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735483162735.png"
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        markets = markets
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
