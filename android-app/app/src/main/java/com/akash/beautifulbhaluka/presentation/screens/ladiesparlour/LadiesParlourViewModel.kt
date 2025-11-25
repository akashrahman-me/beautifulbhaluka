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
            is LadiesParlourAction.RateParlour -> rateParlour(action.parlourId, action.rating)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val parlours = listOf(
                BeautyParlour(
                    id = "parlour_1",
                    name = "বঁধুয়া হারবাল বিউটি পার্লার",
                    address = "ভালুকা বাসস্ট্যান্ড আকতার উদ্দিন কমপ্লেক্সের ৩য় তলায়",
                    phone = "01733-338905",
                    image = "https://images.unsplash.com/photo-1560066984-138dadb4c035?w=400&h=300&fit=crop",
                    averageRating = 4.6f,
                    ratingCount = 28,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_2",
                    name = "প্যারাডাইস বিউটি পার্লার",
                    address = "হক সুপার মার্কেট ৩য় তলা, ভালুকা বাসস্ট্যান্ড",
                    phone = "01800-000000",
                    image = "https://images.unsplash.com/photo-1487412947147-5cebf100ffc2?w=400&h=300&fit=crop",
                    averageRating = 4.3f,
                    ratingCount = 15,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_3",
                    name = "মুনা বিউটি পার্লার",
                    address = "ওয়াহেদ টাওয়ার ২য় তলা",
                    phone = "01900-000000",
                    image = "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=400&h=300&fit=crop",
                    averageRating = 4.5f,
                    ratingCount = 22,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_4",
                    name = "রাইসা হারবাল বিউটি পার্লার",
                    address = "ভালুকা পাঁচ রাস্তা মোড় বাজার রোড",
                    phone = "01795-756891",
                    image = "https://images.unsplash.com/photo-1516975080664-ed2fc6a32937?w=400&h=300&fit=crop",
                    averageRating = 4.8f,
                    ratingCount = 35,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_5",
                    name = "এময়না পাখি বিউটি পার্লার",
                    address = "ভালুকা পাঁচ রাস্তার মোড় সুজ বাজারের উপরের তলায়",
                    phone = "01700-000000",
                    image = "https://images.unsplash.com/photo-1519415510236-718bdfcd89c8?w=400&h=300&fit=crop",
                    averageRating = 4.4f,
                    ratingCount = 19,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_6",
                    name = "অধরা মেকওভার",
                    address = "ভালুকা পাইলট স্কুলের পিছনে",
                    phone = "01876-7844256",
                    image = "https://images.unsplash.com/photo-1457972729786-0411a3b2b626?w=400&h=300&fit=crop",
                    averageRating = 4.7f,
                    ratingCount = 42,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_7",
                    name = "ইউনিক বিউটিস বাই আঁখি",
                    address = "ভালুকা সরকারি কলেজ গেইট এর বিপরীতে মতিন ডাক্তার এর বাসা ২য় তলায়",
                    phone = "01321-197824",
                    image = "https://images.unsplash.com/photo-1595476108010-b4d1f102b1b1?w=400&h=300&fit=crop",
                    averageRating = 4.5f,
                    ratingCount = 26,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_8",
                    name = "সাজমনি বিউটি পার্লার",
                    address = "ভালুকা কলেজ রোড, রোজবার্ড স্কুলের দ্বিতীয় তলায়",
                    phone = "01600-000000",
                    image = "https://images.unsplash.com/photo-1560066984-138dadb4c035?w=400&h=300&fit=crop",
                    averageRating = 4.2f,
                    ratingCount = 12,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_9",
                    name = "ছোঁয়া বিউটি পার্লার",
                    address = "ভালুকা বাজার",
                    phone = "01500-000000",
                    image = "https://images.unsplash.com/photo-1487412947147-5cebf100ffc2?w=400&h=300&fit=crop",
                    averageRating = 4.1f,
                    ratingCount = 8,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_10",
                    name = "জবা বিউটি পার্লার",
                    address = "ভালুকা কলেজ রোড ২২ ফিট মোড় হাজি পাড়া",
                    phone = "01684-816617",
                    image = "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=400&h=300&fit=crop",
                    averageRating = 4.4f,
                    ratingCount = 20,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_11",
                    name = "লাভলি স্কিন কেয়ার বিউটি পার্লার",
                    address = "ভালুুকা প্লাজা ৩য় তলা",
                    phone = "01728-223243",
                    image = "https://images.unsplash.com/photo-1516975080664-ed2fc6a32937?w=400&h=300&fit=crop",
                    averageRating = 4.9f,
                    ratingCount = 48,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_12",
                    name = "সাজ বিউটি পার্লার",
                    address = "কায়া মসজিদ ভালুকা মেজর ভিটা",
                    phone = "01400-000000",
                    image = "https://images.unsplash.com/photo-1519415510236-718bdfcd89c8?w=400&h=300&fit=crop",
                    averageRating = 4.3f,
                    ratingCount = 16,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_13",
                    name = "সাজ বিউটি পার্লার ও ট্রেনিং সেন্টার",
                    address = "ভালুকা কলেজ রোড",
                    phone = "01749-702858",
                    image = "https://images.unsplash.com/photo-1457972729786-0411a3b2b626?w=400&h=300&fit=crop",
                    averageRating = 4.4f,
                    ratingCount = 21,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_14",
                    name = "মোহনা বিউটি পার্লার",
                    address = "গফরগাঁও রোড ভালুকা, সরকার টাওয়ার ১ম তলা",
                    phone = "01719-217177",
                    image = "https://images.unsplash.com/photo-1595476108010-b4d1f102b1b1?w=400&h=300&fit=crop",
                    averageRating = 4.6f,
                    ratingCount = 25,
                    userRating = 0
                ),
                BeautyParlour(
                    id = "parlour_15",
                    name = "ঐশী'স মেকওভার",
                    address = "ভালুকা পৌরসভা ২নং ওয়ার্ড, পাবলিক হল রোড",
                    phone = "01770-451288",
                    image = "https://images.unsplash.com/photo-1560066984-138dadb4c035?w=400&h=300&fit=crop",
                    averageRating = 4.7f,
                    ratingCount = 33,
                    userRating = 0
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
        // Phone call handled in Screen composable
    }

    private fun rateParlour(parlourId: String, rating: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedParlours = currentState.parlours.map { parlour ->
                    if (parlour.id == parlourId) {
                        val newRatingCount =
                            if (parlour.userRating == 0) parlour.ratingCount + 1 else parlour.ratingCount
                        val totalRating =
                            (parlour.averageRating * parlour.ratingCount) - parlour.userRating + rating
                        val newAverageRating = totalRating / newRatingCount

                        parlour.copy(
                            userRating = rating,
                            averageRating = newAverageRating,
                            ratingCount = newRatingCount
                        )
                    } else {
                        parlour
                    }
                }
                currentState.copy(parlours = updatedParlours)
            }
        }
    }
}
