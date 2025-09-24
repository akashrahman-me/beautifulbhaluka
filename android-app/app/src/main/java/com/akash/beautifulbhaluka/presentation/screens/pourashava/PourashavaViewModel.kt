package com.akash.beautifulbhaluka.presentation.screens.pourashava

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.akash.beautifulbhaluka.domain.model.PourashavaInfo
import com.akash.beautifulbhaluka.domain.model.PourashavaSection
import com.akash.beautifulbhaluka.domain.model.WardsTable
import com.akash.beautifulbhaluka.domain.model.Ward

class PourashavaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PourashavaUiState())
    val uiState: StateFlow<PourashavaUiState> = _uiState.asStateFlow()

    init {
        loadPourashavaData()
    }

    fun onAction(action: PourashavaAction) {
        when (action) {
            is PourashavaAction.LoadData -> loadPourashavaData()
            is PourashavaAction.ToggleSection -> toggleSection(action.index)
        }
    }

    private fun loadPourashavaData() {
        _uiState.update { it.copy(isLoading = true) }

        // Static data as per requirement - will integrate with backend later
        val mockData = PourashavaInfo(
            title = "ভালুকা পৌরসভা",
            sections = getSections(),
            wardsTable = getWardsTable()
        )

        _uiState.update {
            it.copy(
                isLoading = false,
                pourashavaInfo = mockData,
                error = null
            )
        }
    }

    private fun toggleSection(index: Int) {
        _uiState.update { state ->
            val expandedSections = state.expandedSections.toMutableSet()
            if (expandedSections.contains(index)) {
                expandedSections.remove(index)
            } else {
                expandedSections.add(index)
            }
            state.copy(expandedSections = expandedSections)
        }
    }

    private fun getSections(): List<PourashavaSection> {
        return listOf(
            PourashavaSection(
                title = "পৌরসভার সাধারণ তথ্য",
                content = "১. জেলাঃ ময়মনসিংহ, উপজেলাঃ ভালুকা।\n২. সীমানাঃ উত্তরে ভরাডোবা ও মল্লিকবাড়ী ইউনিয়ন , পূর্বে ভরাডোবা ভালুকা ইউনিয়ন, দক্ষিণে ভালুকা ও মল্লিকবাড়ী ইউনিয়ন, এবং পশ্চিমে মল্লিকবাড়ী ইউনিয়ন।\n৩. জেলা সদর হতে দূরত্বঃ ৪৮ কিঃমিঃ।\n৪. আয়তনঃ ১০.৪২ বর্গ কিলোমিটার।\n৫. জনসংখ্যাঃ পাঁচ বৎসর পূর্বের তথ্য অনুযায়ী ৬৩,৭৭৩ জন। বর্তমান লোকসংখ্যা প্রায় ১,৬০,০০০ জন। পুরুষ- ৩২,৫২৫ জন। মহিলা: ৩১,২৪৮ জন।"
            ),
            PourashavaSection(
                title = "পৌরসভায় অবস্থিত শিক্ষা, ধর্মীয় ও সামাজিক প্রতিষ্ঠান",
                content = "### ১. শিক্ষা প্রতিষ্ঠান\n- ক) কিন্ডার গার্ডেন : ৩০টি।\n- খ) মোট প্রাথমিক বিদ্যালয় : ৬টি (সরকারী ৪টি, বেঃকমিউঃ ২টি)।\n- গ) নিম্ন মাধ্যমিক বিদ্যালয় : ১টি।\n- ঘ) উচ্চ মাধ্যমিক বিদ্যালয় : ২টি।\n- ঙ) মহাবিদ্যালয় : ১টি।\n- চ) কারিগরী মহাবিদ্যালয় : ১টি।\n- ছ) কামিল মাদ্রাসা : ১টি।\n- জ) দাখিল মাদ্রাসা : ২টি।\n- ঝ) কওমী মাদ্রাসা : ২টি।\n\n### ২. ধর্মীয় প্রতিষ্ঠান\n- ক) মসজিদ : ৪১টি।\n- খ) মন্দির : ৪টি।\n- গ) গীর্জা : ১টি।\n- ঘ) ঈদগাহমাঠ : ৬টি।\n- ঙ) মাজার : ৪টি।\n- চ) তীর্থস্থান : ২টি।\n\n### ৩. সামাজিক প্রতিষ্ঠান\n- ক) সমবায় সমিতি : ১০টি।\n- খ) এতিমখানা : ৩টি।\n- গ) কবরস্থান : ১টি।\n- ঘ) শ্মশান : ১টি।"
            ),
            PourashavaSection(
                title = "পৌরসভায় অবস্থিত বিভিন্ন প্রতিষ্ঠান",
                content = "### ১. স্বাস্থ্য কেন্দ্র\n- ক) সরকারী হাসপাতাল : ১টি (৫০ শয্যা)।\n- খ) বেসরকারী হাসপাতাল : ৩টি।\n- গ) কমিউনিটি ক্লিনিক : ১টি।\n- ঘ) এনজিও স্বাস্থ্যকেন্দ্র : ১টি।\n- ঙ) ডায়াগোনোস্টিক সেন্টার : ৬টি।\n\n### ২. সরকারী/সেবামূলক প্রতিষ্ঠান\n- ক) ভূমি অফিস : ১টি।\n- খ) ব্যাংক : ১০টি।\n- গ) ডাকবাংলো/রেষ্টহাউজ : ২টি।\n- ঘ) ফায়ার সার্ভিস ষ্টেশন : ১টি।\n- ঙ) খাদ্য গুদাম : ১টি (ক্ষমতা ৫০০ মেট্রিকটন)।\n- চ) বিএডিসি উদ্যান উন্নয়ন কেন্দ্র : ১টি।\n- ছ) বিবাহ ও তালাক নিবন্ধন অফিস : ৩টি।\n- জ) হাট-বাজার : ১টি।\n- ঝ) পেট্রোল পাম্প : ১টি।\n- ঞ) সি.এন.জি. ষ্টেশন : ২টি।\n- ট) মোবাইল টাওয়ার : ১০টি।\n\n### ৩. শিল্প/ব্যবসা প্রতিষ্ঠান\n- শিল্প প্রতিষ্ঠান : ১৬টি।\n- অটো রাইসমিল : ৬টি।\n- রাইসমিল : ৬টি।\n- স' মিল : ১৬টি।\n\n### ৪. পেশা\nকৃষি ৫%, কৃষিশ্রমিক ৫%, দিনমজুর ৫%, ব্যবসা ৫০%, চাকুরী ৩০%, অন্যান্য ৫%।\n\n### ৫. কৃষি\nমোট কৃষিজমি-১৮০ একর (এক ফসলী জমি ১৭০ একর, দুই ফসলী জমি ১০ একর)।\n\n### ৬. পশু সম্পদ\n- পশু হাসপাতাল : ১টি।\n- কৃত্রিম প্রজনন কেন্দ্র : ১টি।\n- পোল্ট্রি খামার : ২টি।\n- হ্যাচারী : ২টি।\n\n### ৭. ডাকঘর\n১টি (পোষ্টাল কোড-২২৪০)।\n\n### ৈ. সাংস্কৃতিক প্রতিষ্ঠান\nপাবলিক হল ১টি, ক্রীড়া সংস্থা ১টি, পাঠাগার ১টি, খেলার মাঠ ৩টি, অফিসার্স ক্লাব ১টি, স্থানীয় ক্লাব ২টি।\n\n### ৯. এনজিও কার্যক্রম\nগ্রামীণ ব্যাংক, ব্রাক, আশা, বাসা, প্রশিকা, প্রতিশ্রুতি, ব্যুরো, ব্যুরো টাঙ্গাইল, ওয়ার্ল্ডভিশন, পদক্ষেপ, পরশমনি, পপি সহ-৫০টি।\n\n### ১০. সরকারী সুবিধা\n- বিধবা ভাতাভোগী : ১৮৯ জন।\n- বয়স্ক ভাতাভোগী : ২১৩ জন।\n- প্রতিবন্ধী ভাতাভোগী : ১৬ জন।\n- মুক্তিযোদ্ধা ভাতাভোগী : ৭ জন।"
            ),
            PourashavaSection(
                title = "পৌরসেবা",
                content = "### ১. পৌরসেবা\n- দৈনিক পানির চাহিদা: ২৫ গ্যালন।\n- উৎপাদিত আবর্জনা: ০.৩ মেট্রিকটন।\n- দৈনিক অপসারণ: ০.৩ মেট্রিকটন।\n\n### ২. ষ্ট্রিট লাইট\nসংখ্যা: ৩৫০টি।\n\n### ৩. পৌরসভার যানবাহন\n- নিজস্ব যানবাহন: ৩টি।\n- ভ্যান: ৮টি।\n- গণশৌচাগার: ২টি।"
            )
        )
    }

    private fun getWardsTable(): WardsTable {
        return WardsTable(
            title = "ভালুকা পৌরসভার ওয়ার্ড সমূহ",
            wards = listOf(
                Ward("১নং", "ভান্ডাব"),
                Ward("২নং", "ভালুকা বাজার"),
                Ward("৩নং", "চাপরবাড়ী"),
                Ward("৪নং", "গফরগাঁও রোড"),
                Ward("৫নং", "পূর্ব ভালুকা"),
                Ward("৬নং", "মেজরভিটা"),
                Ward("৭নং", "খারুয়ালী"),
                Ward("৮নং", "কাঠালী বাঘড়া পাড়া"),
                Ward("৯নং", "কাঠালী")
            )
        )
    }
}
