package com.akash.beautifulbhaluka.presentation.screens.schoolcollege

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolCollegeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SchoolCollegeUiState())
    val uiState: StateFlow<SchoolCollegeUiState> = _uiState.asStateFlow()

    init {
        onAction(SchoolCollegeAction.LoadData)
    }

    fun onAction(action: SchoolCollegeAction) {
        when (action) {
            is SchoolCollegeAction.LoadData -> loadData()
            is SchoolCollegeAction.OnInstitutionClick -> {
                // Handle institution click (could navigate to detail screen or show more info)
            }

            is SchoolCollegeAction.OnCategorySelected -> filterByCategory(action.category)
            is SchoolCollegeAction.OnPublishClick -> {
                // Navigation handled in screen
            }
        }
    }

    private fun filterByCategory(category: InstitutionCategory) {
        _uiState.update { currentState ->
            val filtered = if (category == InstitutionCategory.ALL) {
                currentState.allInstitutions
            } else {
                currentState.allInstitutions.filter { it.category == category }
            }
            currentState.copy(
                selectedCategory = category,
                filteredInstitutions = filtered
            )
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                // Featured institutions data
                val featuredInstitutions = listOf(
                    FeaturedInstitution(
                        title = "ভালুকা সরকারি কলেজ",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-26.jpeg",
                        founder = "মোস্তফা এমএ মতিন",
                        established = "১৩ জুন ১৯৭২",
                        eiin = "111354",
                        collegeCode = "5219"
                    ),
                    FeaturedInstitution(
                        title = "ভালুকা সরকারি বালিকা উচ্চ বিদ্যালয়",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-29-1.jpeg",
                        founder = "আলহাজ্ব আমানউল্লাহ চৌধুরী",
                        established = "০১-০৬-১৯৮৬",
                        eiin = "111278",
                        website = "http://www.bgghs.gov.bd/"
                    ),
                    FeaturedInstitution(
                        title = "ভালুকা পাইলট উচ্চ বিদ্যালয়",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-27.jpeg",
                        founder = "আফতাব উদ্দিন চৌধুরী",
                        established = "১৯৪৮ সাল",
                        eiin = "111280"
                    ),
                    FeaturedInstitution(
                        title = "হালিমুনেছা চৌধুরানী মেমোরিয়াল বালিকা উচ্চ বিদ্যালয়",
                        thumbnail = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/images-30-1.jpeg",
                        established = "১৯৮৯",
                        eiin = "111279",
                        website = "https://www.halimunnessa.edu.bd/",
                        details = "মরহুম আমান উল্যাহ চৌধুরী তাঁর দাদীর নামে অত্র হালিমুন্নেছা চৌধুরানী মেমোরিয়েল বালিকা উচ্চ বিদ্যালয়টি ১৯৮৯ খ্রীঃ প্রতিষ্ঠা করেন।"
                    )
                )

                // Colleges data
                val colleges = listOf(
                    CollegeInstitution("1", "বাটাজোর সোনার বাংলা ডিগ্রী কলেজ", "111353"),
                    CollegeInstitution("2", "ভালুকা সরকারি কলেজ", "111354"),
                    CollegeInstitution(
                        "3",
                        "এ্যাপোলো ইন্সটিটিউট অব কম্পিউটার বি. এম. কলেজ",
                        "132789"
                    ),
                    CollegeInstitution("4", "উথুরা হাই স্কুল এন্ড কলেজ", "111358"),
                    CollegeInstitution("5", "ধলিয়া বহুলী স্কুল এন্ড কলেজ", "111357"),
                    CollegeInstitution("6", "সায়েরা সাফায়েত স্কুল এন্ড কলেজ", "111297"),
                    CollegeInstitution("7", "বিরুনিয়া সদর আলী সরকার মহিলা কলেজ", "111355"),
                    CollegeInstitution("8", "শহীদ নাজিম উদ্দিন স্কুল এন্ড কলেজ", "111289"),
                    CollegeInstitution("9", "শহীদ স্মৃতি বি এম কলেজ -বান্দিয়া", "137642"),
                    CollegeInstitution("10", "মর্নিংসান মডেল কলেজ", "137602"),
                    CollegeInstitution(
                        "11",
                        "জামিরদিয়া আবদুল গণি মাস্টার স্কুল অ্যান্ড কলেজ",
                        "111261"
                    ),
                    CollegeInstitution("12", "ভালুকা মডেল গার্লস কলেজ", "136861")
                )

                // High schools data
                val highSchools = listOf(
                    HighSchoolInstitution("1", "রাজৈ উচ্চ বিদ্যালয়", "111263"),
                    HighSchoolInstitution(
                        "2",
                        "গোয়ারী ভাওয়ালিয়া বাজার উচ্চ বিদ্যালয়",
                        "111285"
                    ),
                    HighSchoolInstitution("3", "মোস্তফা মতিন উচ্চ বিদ্যালয় সাতেঙ্গা", "111305"),
                    HighSchoolInstitution(
                        "4",
                        "শহীদ শামসুদ্দিন হাই স্কুল, বরাইদ, ভালুকা",
                        "111257"
                    ),
                    HighSchoolInstitution("5", "কাচিনা উচ্চ বিদ্যালয়", "111274"),
                    HighSchoolInstitution("6", "শহীদ কুতুব উদ্দিন উচ্চ বিদ্যালয়", "130712"),
                    HighSchoolInstitution("7", "ভালুকা পাইলট উচ্চ বিদ্যালয়", "111280"),
                    HighSchoolInstitution("8", "রহমতে আলম একাডেমি উচ্চ বিদ্যালয়", "111269"),
                    HighSchoolInstitution(
                        "9",
                        "ডাকাতিয়া শহীদ মুক্তিযোদ্ধা উচ্চ বিদ্যালয়",
                        "111264"
                    ),
                    HighSchoolInstitution("10", "ভালুকা সরকারি বালিকা উচ্চ বিদ্যালয়", "111278"),
                    HighSchoolInstitution("11", "ভরাডোবা উচ্চ বিদ্যালয়", "111262"),
                    HighSchoolInstitution("12", "ভরাডোবা বালিকা উচ্চ বিদ্যালয়", "111276"),
                    HighSchoolInstitution("13", "বাটাজোর বি.এম উচ্চ বিদ্যালয়", "111282"),
                    HighSchoolInstitution("14", "আংগারগাড়া ইউনাইটেড উচ্চ বিদ্যালয়", "111272"),
                    HighSchoolInstitution("15", "মেদিলা আদর্শ উচ্চ বিদ্যালয়", "111300"),
                    HighSchoolInstitution(
                        "16",
                        "হবিরবাড়ী ইউনিয়ন সোনার বাংলা উচ্চ বিদ্যালয়",
                        "111290"
                    ),
                    HighSchoolInstitution(
                        "17",
                        "হালিমুন্নেছা চৌধুরাণী মেমোরিয়াল বালিকা উচ্চ বিদ্যালয়",
                        "111279"
                    ),
                    HighSchoolInstitution("18", "শমলা তাহের আদর্শ উচ্চ বিদ্যালয়", "111302"),
                    HighSchoolInstitution(
                        "19",
                        "জামিরদিয়া আঃ গণি মাস্টার উচ্চ বিদ্যালয়",
                        "111261"
                    ),
                    HighSchoolInstitution("20", "আশকা উচ্চ বিদ্যালয়", "111291"),
                    HighSchoolInstitution("21", "জামিরাপাড়া এস এম উচ্চ বিদ্যালয়", "111288"),
                    HighSchoolInstitution("22", "কংশেরকুল উচ্চ বিদ্যালয়", "111286"),
                    HighSchoolInstitution("23", "সাইদুর রহমান উচ্চ বিদ্যালয়", "138228"),
                    HighSchoolInstitution("24", "ঝালপাজা উচ্চ বিদ্যালয়", "111277"),
                    HighSchoolInstitution("25", "রান্দিয়া হাই স্কুল", "111275"),
                    HighSchoolInstitution("26", "সোনাউল্লাহ উচ্চ বিদ্যালয় ও কলেজ", "111265"),
                    HighSchoolInstitution("27", "শহীদ স্বরানী উচ্চ বিদ্যালয়", "111258"),
                    HighSchoolInstitution("28", "পালগাঁও উচ্চ বিদ্যালয়", "111299"),
                    HighSchoolInstitution("29", "লোহাবাই এ এইচ সরকার উচ্চ বিদ্যালয়", "111293"),
                    HighSchoolInstitution("30", "বান্দিয়া আদর্শ উচ্চ বিদ্যালয়", "111303")
                )

                val allInstitutions = listOf(
                    Institution(
                        "1",
                        "ভালুকা সরকারি কলেজ",
                        InstitutionCategory.COLLEGE,
                        null,
                        "১৯৭২",
                        "111354",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "2",
                        "ভালুকা পাইলট উচ্চ বিদ্যালয়",
                        InstitutionCategory.SCHOOL,
                        null,
                        "১৯৪৮",
                        "111280",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "3",
                        "ভালুকা সরকারি বালিকা উচ্চ বিদ্যালয়",
                        InstitutionCategory.SCHOOL,
                        null,
                        "১৯৮৬",
                        "111278",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "4",
                        "বাটাজোর সোনার বাংলা ডিগ্রী কলেজ",
                        InstitutionCategory.COLLEGE,
                        null,
                        "২০০০",
                        "111353",
                        "বাটাজোর",
                        null,
                        null
                    ),
                    Institution(
                        "5",
                        "ভালুকা ইসলামিয়া মাদ্রাসা",
                        InstitutionCategory.MADRASA,
                        null,
                        "১৯৫০",
                        "111400",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "6",
                        "আল-আমিন মাদ্রাসা",
                        InstitutionCategory.MADRASA,
                        null,
                        "১৯৮৫",
                        "111401",
                        "কাচিনা",
                        null,
                        null
                    ),
                    Institution(
                        "7",
                        "ভালুকা কোচিং সেন্টার",
                        InstitutionCategory.COACHING_CENTER,
                        null,
                        "২০১০",
                        "111500",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "8",
                        "মেধাবী কোচিং সেন্টার",
                        InstitutionCategory.COACHING_CENTER,
                        null,
                        "২০১৫",
                        "111501",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "9",
                        "লিটল স্টার কিন্ডারগার্টেন",
                        InstitutionCategory.KINDERGARTEN,
                        null,
                        "২০০৮",
                        "111600",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "10",
                        "শিশু নিকেতন কিন্ডারগার্টেন",
                        InstitutionCategory.KINDERGARTEN,
                        null,
                        "২০১২",
                        "111601",
                        "মেদুয়ারী",
                        null,
                        null
                    ),
                    Institution(
                        "11",
                        "ভালুকা সরকারি প্রাথমিক বিদ্যালয়",
                        InstitutionCategory.PRIMARY_SCHOOL,
                        null,
                        "১৯৫৫",
                        "111700",
                        "ভালুকা সদর",
                        null,
                        null
                    ),
                    Institution(
                        "12",
                        "কাচিনা সরকারি প্রাথমিক বিদ্যালয়",
                        InstitutionCategory.PRIMARY_SCHOOL,
                        null,
                        "১৯৬০",
                        "111701",
                        "কাচিনা",
                        null,
                        null
                    ),
                    Institution(
                        "13",
                        "উথুরা হাই স্কুল এন্ড কলেজ",
                        InstitutionCategory.COLLEGE,
                        null,
                        "১৯৯৫",
                        "111358",
                        "উথুরা",
                        null,
                        null
                    ),
                    Institution(
                        "14",
                        "রাজৈ উচ্চ বিদ্যালয়",
                        InstitutionCategory.SCHOOL,
                        null,
                        "১৯৭০",
                        "111263",
                        "রাজৈ",
                        null,
                        null
                    ),
                    Institution(
                        "15",
                        "হামিদিয়া মাদ্রাসা",
                        InstitutionCategory.MADRASA,
                        null,
                        "১৯৭৫",
                        "111402",
                        "ডাকাতিয়া",
                        null,
                        null
                    )
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        featuredInstitutions = featuredInstitutions,
                        colleges = colleges,
                        highSchools = highSchools,
                        allInstitutions = allInstitutions,
                        filteredInstitutions = allInstitutions,
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
