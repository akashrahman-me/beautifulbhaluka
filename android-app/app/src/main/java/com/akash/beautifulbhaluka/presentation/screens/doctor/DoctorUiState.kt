package com.akash.beautifulbhaluka.presentation.screens.doctor

data class DoctorUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val verifiedDoctors: List<VerifiedDoctorInfo> = getDefaultVerifiedDoctors(),
    val doctorCards: List<DoctorInfo> = getDefaultDoctorCards(),
    val specialistDoctors: List<SpecialistDoctor> = getDefaultSpecialistDoctors()
)

data class DoctorInfo(
    val id: String,
    val name: String,
    val specialist: String,
    val phone: String,
    val image: String
)

data class SpecialistDoctor(
    val serialNo: String,
    val name: String,
    val specialist: String,
    val phone: String
)

data class VerifiedDoctorInfo(
    val id: String,
    val name: String,
    val specialist: String,
    val phone: String,
    val image: String
)

sealed class DoctorAction {
    object LoadData : DoctorAction()
    data class CallPhone(val phoneNumber: String) : DoctorAction()
}

private fun getDefaultDoctorCards(): List<DoctorInfo> {
    return listOf(
        DoctorInfo(
            id = "1",
            name = "ডাঃ মুশফিকুর রহমা",
            specialist = "নবজাতক ও শিশুরোগ বিশেষজ্ঞ",
            phone = "০১৭১২-২৮৮৪৫৫",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735056863183.png"
        ),
        DoctorInfo(
            id = "2",
            name = "ডাঃ রোকাইয়া আক্তার",
            specialist = "গাইনি ও প্রসূতি বিভাগ",
            phone = "০১৭১৪-০৭৭৪৮৬",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735056863183.png"
        ),
        DoctorInfo(
            id = "3",
            name = "ডাঃ মো. মোশাররফ হোসেন",
            specialist = "গাইনি ও প্রসূতি",
            phone = "০১৭১১-৮২৮৭৬৫",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735056863183.png"
        ),
        DoctorInfo(
            id = "4",
            name = "ডাঃ সোহেল রানা",
            specialist = "মেডিসিন ও হৃদরোগ বিশেষজ্ঞ",
            phone = "01716-943691",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735056863183.png"
        ),
        DoctorInfo(
            id = "5",
            name = "কেবিএম হাদিউজ্জামান সেলিম",
            specialist = "কিডনি রোগ বিশেষজ্ঞ",
            phone = "+8801711-386831",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2024/12/1735056863183.png"
        )
    )
}

private fun getDefaultSpecialistDoctors(): List<SpecialistDoctor> {
    return listOf(
        SpecialistDoctor(
            "01",
            "অধ্যাপক (সহঃ) ডাঃ মুশফিকুর রহমান",
            "নবজাতক ও শিশু রোগ বিশেষজ্ঞ",
            "01712-288455"
        ),
        SpecialistDoctor(
            "02",
            "অধ্যাপক (সহঃ) ডাঃ আশীষ কুমার রায়",
            "হৃদরোগ ও মেডিসিন বিশেষজ্ঞ",
            "01710-077111"
        ),
        SpecialistDoctor(
            "03",
            "অধ্যাপক (সহঃ) ডাঃ সোহেল রানা",
            "মেডিসিন ও হৃদরোগ বিশেষজ্ঞ",
            "01716-943691"
        ),
        SpecialistDoctor(
            "04",
            "অধ্যাপক (সহঃ) ডাঃ সুস্থির সরকার",
            "চর্ম, যৌন ও এলাজি বিশেষজ্ঞ",
            "01712-386065"
        ),
        SpecialistDoctor(
            "05",
            "অধ্যাপক (সহঃ) ডাঃ ইকবাল মনির",
            "নাক-কান-গলা বিশেষজ্ঞ",
            "01711-361346"
        ),
        SpecialistDoctor("06", "ডাঃ ফাইজা চৌধুরী", "গাইনী ও প্রসূতি রোগ বিশেষজ্ঞ", "01717-620818"),
        SpecialistDoctor(
            "07",
            "ডাঃ সায়াদ উল্লাহ",
            "মেডিসিন ও গ্যাস্ট্রোলিভার বিশেষজ্ঞ",
            "01711-444299"
        ),
        SpecialistDoctor("08", "ডাঃ সালমা আতিয়া", "গাইনী ও প্রসূতি রোগ বিশেষজ্ঞ", "01712-590573"),
        SpecialistDoctor(
            "09",
            "ডাঃ রিপন কুমার রায়",
            "হাড়জোড়া বিশেষজ্ঞ (অর্থোপেডিক্স সার্জন)",
            "01712-778144"
        ),
        SpecialistDoctor(
            "10",
            "ডাঃ শরীফুল ইসলাম খান",
            "মেডিসিন ও গ্যাস্ট্রোলিভার বিশেষজ্ঞ",
            "01818-656797"
        ),
        SpecialistDoctor("11", "ডাঃ পংকজ দাস", "নিউরোমেডিসিন বিশেষজ্ঞ", "01771-085823"),
        SpecialistDoctor(
            "12",
            "ডাঃ ফাতেমা মাজাহার",
            "গাইনী ও প্রসূতি রোগ বিশেষজ্ঞ",
            "01712-000552"
        ),
        SpecialistDoctor("13", "ডাঃ সাদিকুর রহমান", "নাক-কান-গলা বিশেষজ্ঞ", "01729-271072"),
        SpecialistDoctor("14", "ডাঃ মোস্তাফিজুর রহমান", "কিডনী বিশেষজ্ঞ", "01716-200058"),
        SpecialistDoctor("15", "ডাঃ সালেকিন মামুন", "মেডিসিন ও হরমোন বিশেষজ্ঞ", "01670-550133"),
        SpecialistDoctor("16", "ডাঃ রুকুনুজ্জামান", "চর্ম, যৌন ও এলাজি বিশেষজ্ঞ", "01920-607280"),
        SpecialistDoctor("17", "ডাঃ হেনরি অধিকারি", "আল্ট্রাসনো ও এক্সরে বিশেষজ্ঞ", "01722-882324"),
        SpecialistDoctor("18", "ডাঃ জুনায়েদ হোসেন", "দন্তরোগ বিশেষজ্ঞ", "01825-001780"),
        SpecialistDoctor(
            "19",
            "ডাঃ জেরিন সোলাইমান রিজা",
            "গাইনী ও প্রসূতি রোগ বিশেষজ্ঞ",
            "01984-445985"
        ),
        SpecialistDoctor("20", "ডাঃ মোর্শেদুল ইসলাম", "আবাসিক চিকিৎসক", "01980-749428"),
        SpecialistDoctor("21", "ডাঃ আনোয়ারুল হক", "আবাসিক চিকিৎসক", "01521-405347"),
        SpecialistDoctor("22", "ডাঃ শামীম রেজা", "আবাসিক চিকিৎসক", "01521-404424"),
        SpecialistDoctor("23", "ডাঃ আবু নাইম", "আবাসিক চিকিৎসক", "01568-658636"),
        SpecialistDoctor("24", "ডাঃ বিন-ইয়ামিন", "আবাসিক চিকিৎসক", "01752-063936")
    )
}

private fun getDefaultVerifiedDoctors(): List<VerifiedDoctorInfo> {
    return listOf(
        VerifiedDoctorInfo(
            id = "1",
            name = "ডাঃ জাকারিয়া রুবেল",
            specialist = "ওরাল অ্যান্ড ডেন্টাল সার্জন",
            phone = "01952-532442",
            image = "https://beautifulbhaluka.com/wp-content/uploads/2025/01/IMG-20250102-WA01002.jpg"
        )
    )
}
