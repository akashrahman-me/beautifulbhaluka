package com.akash.beautifulbhaluka.presentation.screens.bloodbank.guidelines

data class BloodDonationGuidelinesUiState(
    val expandedSections: Set<GuidelineSection> = emptySet()
)

enum class GuidelineSection {
    BEFORE_DONATION,
    DURING_DONATION,
    AFTER_DONATION
}

sealed class GuidelinesAction {
    data class ToggleSection(val section: GuidelineSection) : GuidelinesAction()
}

// Static content data
data class GuidelineData(
    val beforeDonation: List<String> = listOf(
        "পর্যাপ্ত পরিমাণে পানি ও তরল জাতীয় খাবার গ্রহণ করুন",
        "রক্তদানের আগের রাতে ভালোভাবে ঘুমান",
        "হালকা খাবার গ্রহণ করুন রক্তদানের ২-৩ ঘন্টা আগে",
        "ফ্যাটি বা চর্বিযুক্ত খাবার এড়িয়ে চলুন",
        "রক্তদানের আগে কোনো প্রকার অ্যালকোহল সেবন করবেন না",
        "নিজের স্বাস্থ্য সম্পর্কে ডাক্তারকে সকল তথ্য প্রদান করুন"
    ),
    val duringDonation: List<String> = listOf(
        "রিল্যাক্স থাকুন এবং স্বাভাবিকভাবে শ্বাস নিন",
        "ডাক্তার বা নার্সের সকল নির্দেশনা মেনে চলুন",
        "রক্তদান প্রক্রিয়ার সময় অন্য কথায় মনোনিবেশ করুন",
        "কোনো অসুবিধা অনুভব করলে সঙ্গে সঙ্গে স্টাফকে জানান"
    ),
    val afterDonation: List<String> = listOf(
        "রক্তদান শেষে কমপক্ষে ১০-১৫ মিনিট বিশ্রাম নিন",
        "পর্যাপ্ত পরিমাণে তরল পানীয় ও জুস গ্রহণ করুন",
        "রক্তদানের পরের ৪-৫ ঘন্টা ভারী কাজ করা থেকে বিরত থাকুন",
        "সিগারেট বা অ্যালকোহল সেবন থেকে বিরত থাকুন",
        "রক্তদানের পরের ২৪ ঘন্টা গরম পানি দিয়ে গোসল করবেন না",
        "মাথা ঘুরানো বা দুর্বলতা অনুভব করলে সঙ্গে সঙ্গে শুয়ে পড়ুন"
    )
)

