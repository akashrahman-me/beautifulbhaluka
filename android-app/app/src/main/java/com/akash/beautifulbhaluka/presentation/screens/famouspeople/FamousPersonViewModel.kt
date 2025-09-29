package com.akash.beautifulbhaluka.presentation.screens.famouspeople

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.akash.beautifulbhaluka.domain.model.FamousPerson

class FamousPersonViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FamousPersonUiState())
    val uiState: StateFlow<FamousPersonUiState> = _uiState.asStateFlow()

    init {
        loadFamousPeopleData()
    }

    fun onAction(action: FamousPersonAction) {
        when (action) {
            is FamousPersonAction.LoadData -> loadFamousPeopleData()
            is FamousPersonAction.ViewPerson -> {
                // Handle person view logic here
                // For now, this is a placeholder for future implementation
            }
        }
    }

    private fun loadFamousPeopleData() {
        _uiState.update { it.copy(isLoading = true) }

        // Static data as per requirement
        val famousPeople = listOf(
            FamousPerson(
                title = "ডক্টর শাহ মুহাম্মদ ফারুক",
                thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2025/08/1000045653-1024x738.jpg",
                author = "admin",
                date = "August 31, 2025",
                excerpt = "ডক্টর শাহ মুহাম্মদ ফারুকসাবেক ভাইস চ্যান্সেলর (ভিসি)বাংলাদেশ কৃষি বিশ্ববিদ্যালয়, ময়মনসিংহ। জন্ম: উথুরা ফকির বাড়ি, ভালুকা, ময়মনসিংহ। পিতা: মরহুম এডভোকেট শাহ […]",
                category = "প্রসিদ্ধ ব্যক্তিত্ব"
            ),
            FamousPerson(
                title = "কেবিএম আছমত আলী সরকার",
                thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/IMG-20241229-WA0018-819x1024.jpg",
                author = "admin",
                date = "December 29, 2024",
                excerpt = "কেবিএম আছমত আলী সরকার ১৯৩৫ সালে বৃহত্তর ময়মনসিংহ জেলার তৎকালীন গফরগাঁও (বর্তমান ভালুকা ) থানার খিরু নদীর অববাহিকায় অবস্থিত খারুয়ালী",
                category = "প্রসিদ্ধ ব্যক্তিত্ব"
            ),
            FamousPerson(
                title = "মেজর আফসার উদ্দিন",
                thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-37.jpeg",
                author = "admin2",
                date = "December 29, 2024",
                excerpt = "মেজর আফসার উদ্দিন আহমেদ ছিলেন একজন বীর মুক্তিযোদ্ধা, যিনি ১৯৭১ সালের মুক্তিযুদ্ধে 'আফসার বাহিনী' গঠন করে পাকিস্তানি বাহিনীর বিরুদ্ধে",
                category = "প্রসিদ্ধ ব্যক্তিত্ব"
            ),
            FamousPerson(
                title = "অধ্যাপক ডা.আমান উল্যাহ চৌধুরী",
                thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/আমান_উল্লাহ_চৌধুরী.png",
                author = "admin2",
                date = "December 29, 2024",
                excerpt = "আমান উল্লাহ চৌধুরী ময়মনসিংহ জেলার ভালুকা উপজেলায় জন্মগ্রহণ করেন। তার পিতা আফতাব উদ্দিন চৌধুরী তৎকালীন ময়মনসিংহ-১০ আসনের সংসদ সদস্য ও",
                category = "প্রসিদ্ধ ব্যক্তিত্ব"
            ),
            FamousPerson(
                title = "অধ্যাপক ডা: এম. আমান উল্লাহ",
                thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735402407005-1024x1024.jpg",
                author = "admin",
                date = "December 28, 2024",
                excerpt = "অধ্যাপক ডা: এম. আমান উল্লাহ স্যার উপমহাদেশের প্রখ্যাত হৃদরোগ বিশেষজ্ঞ ছিলেন। অধ্যাপক ডা: এম. আমান উল্লাহ ১৯৩৯ইং সালের ১৬ই মার্চ",
                category = "প্রসিদ্ধ ব্যক্তিত্ব"
            )
        )

        _uiState.update {
            it.copy(
                isLoading = false,
                famousPeople = famousPeople,
                error = null
            )
        }
    }
}
