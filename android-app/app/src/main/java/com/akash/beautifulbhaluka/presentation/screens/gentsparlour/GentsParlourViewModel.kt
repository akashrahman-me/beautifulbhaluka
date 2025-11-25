package com.akash.beautifulbhaluka.presentation.screens.gentsparlour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GentsParlourViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GentsParlourUiState())
    val uiState: StateFlow<GentsParlourUiState> = _uiState.asStateFlow()

    init {
        onAction(GentsParlourAction.LoadData)
    }

    fun onAction(action: GentsParlourAction) {
        when (action) {
            is GentsParlourAction.LoadData -> loadData()
            is GentsParlourAction.CallPhone -> {
                // Handle phone call - this would typically be handled by the UI layer
            }

            is GentsParlourAction.RateParlour -> rateParlour(action.parlourId, action.rating)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val parlours = listOf(
                    GentsParlour(
                        id = "gents_1",
                        title = "নোবেল রিফ্লেক্সশন সেলুন এন্ড স্পা",
                        image = "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?w=400&h=300&fit=crop",
                        description = "ভালুকার প্রথম প্রিমিয়াম সেলুন এবং স্পা সার্ভিস। স্বল্পমূল্যে প্রিমিয়াম সেবার নিশ্চয়তা।",
                        address = "ওয়াহেদ টাওয়ার গ্রাউন্ড ফ্লোর, ভালুকা",
                        phones = listOf("01764-034199", "01819-822224"),
                        averageRating = 4.8f,
                        ratingCount = 45,
                        userRating = 0
                    ),
                    GentsParlour(
                        id = "gents_2",
                        title = "ফেমাস জেন্টস পার্লার এন্ড লাক্সারি সেলুন",
                        image = "https://images.unsplash.com/photo-1585747860715-2ba37e788b70?w=400&h=300&fit=crop",
                        address = "ওয়াহেদ টাওয়ার নিচ তলা",
                        phones = listOf("01642-410089"),
                        averageRating = 4.5f,
                        ratingCount = 32,
                        userRating = 0
                    ),
                    GentsParlour(
                        id = "gents_3",
                        title = "স্টার জেন্টস পার্লার এন্ড লাক্সারি সেলুন",
                        image = "https://images.unsplash.com/photo-1622286342621-4bd786c2447c?w=400&h=300&fit=crop",
                        address = "ভালুকা প্লাজা ২য় তলা",
                        phones = listOf("01772-517777"),
                        averageRating = 4.6f,
                        ratingCount = 38,
                        userRating = 0
                    ),
                    GentsParlour(
                        id = "gents_4",
                        title = "হেয়ার ফোর্স জেন্টস পার্লার",
                        image = "https://images.unsplash.com/photo-1621605815971-fbc98d665033?w=400&h=300&fit=crop",
                        address = "ভালুকা মেজর ভিটা মোড়",
                        phones = listOf("01700-000000"),
                        averageRating = 4.3f,
                        ratingCount = 24,
                        userRating = 0
                    ),
                    GentsParlour(
                        id = "gents_5",
                        title = "মনের মুকুরে এসি জেন্টস পার্লার",
                        image = "https://images.unsplash.com/photo-1599351431202-1e0f0137899a?w=400&h=300&fit=crop",
                        address = "ভালুকা বাজার",
                        phones = listOf("01406-566706"),
                        averageRating = 4.7f,
                        ratingCount = 41,
                        userRating = 0
                    ),
                    GentsParlour(
                        id = "gents_6",
                        title = "সাত রঙ লাক্সারি সেলুন",
                        image = "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?w=400&h=300&fit=crop",
                        address = "ভালুকা প্লাজা ৩য় তলা",
                        phones = listOf("01800-000000"),
                        averageRating = 4.4f,
                        ratingCount = 28,
                        userRating = 0
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        parlours = parlours
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
