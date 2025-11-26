package com.akash.beautifulbhaluka.presentation.screens.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ShopsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ShopsUiState())
    val uiState: StateFlow<ShopsUiState> = _uiState.asStateFlow()

    private val mockCategories = listOf(
        ProductCategory(
            "1",
            "ইলেকট্রনিক্স",
            "Electronics",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=101"
        ),
        ProductCategory(
            "2",
            "পোশাক",
            "Clothing",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=102"
        ),
        ProductCategory(
            "3",
            "খাবার",
            "Food",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=103"
        ),
        ProductCategory(
            "4",
            "বই",
            "Books",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=104"
        ),
        ProductCategory(
            "5",
            "মোবাইল",
            "Mobile",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=105"
        ),
        ProductCategory(
            "6",
            "গাড়ি",
            "Vehicles",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=106"
        ),
        ProductCategory(
            "7",
            "আসবাবপত্র",
            "Furniture",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=107"
        ),
        ProductCategory(
            "8",
            "স্বাস্থ্য",
            "Health",
            R.drawable.ic_launcher_foreground,
            productCount = 7,
            imageUrl = "https://picsum.photos/400/300?random=108"
        )
    )

    private val mockProducts = listOf(
        // ইলেকট্রনিক্স - Electronics (Category 0)
        Product(
            id = "1",
            name = "ল্যাপটপ ব্যাগ",
            description = "উচ্চ মানের ল্যাপটপ ব্যাগ, সব সাইজের ল্যাপটপের জন্য।",
            price = 2500.0,
            imageUrl = "https://picsum.photos/200/120?random=5",
            category = mockCategories[0],
            stock = 8,
            sellerName = "টেক স্টোর",
            sellerContact = "+880 1715 901234",
            location = "ভালুকা কলেজ গেট",
            rating = 4.3f,
            reviewCount = 12
        ),
        Product(
            id = "2",
            name = "ওয়্যারলেস মাউস",
            description = "হাই প্রিসিশন ওয়্যারলেস মাউস, দীর্ঘ ব্যাটারি লাইফ।",
            price = 850.0,
            originalPrice = 1200.0,
            imageUrl = "https://picsum.photos/200/120?random=9",
            category = mockCategories[0],
            stock = 15,
            sellerName = "টেক স্টোর",
            sellerContact = "+880 1715 901234",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 18,
            isNew = true
        ),
        Product(
            id = "3",
            name = "USB কীবোর্ড",
            description = "মেকানিক্যাল USB কীবোর্ড, আরামদায়ক টাইপিং।",
            price = 1800.0,
            imageUrl = "https://picsum.photos/200/120?random=10",
            category = mockCategories[0],
            stock = 12,
            sellerName = "ডিজিটাল ওয়ার্ল্ড",
            sellerContact = "+880 1716 111222",
            location = "ভালুকা সদর",
            rating = 4.4f,
            reviewCount = 9
        ),
        Product(
            id = "4",
            name = "এলইডি মনিটর ২৪ ইঞ্চি",
            description = "ফুল HD LED মনিটর, চমৎকার ডিসপ্লে কোয়ালিটি।",
            price = 12000.0,
            originalPrice = 15000.0,
            imageUrl = "https://picsum.photos/200/120?random=11",
            category = mockCategories[0],
            stock = 4,
            sellerName = "কম্পিউটার হাব",
            sellerContact = "+880 1717 333444",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 14,
            isFeatured = true
        ),
        Product(
            id = "5",
            name = "পাওয়ার ব্যাংক ২০০০০mAh",
            description = "বড় ক্যাপাসিটির পাওয়ার ব্যাংক, ফাস্ট চার্জিং সাপোর্ট।",
            price = 1500.0,
            imageUrl = "https://picsum.photos/200/120?random=12",
            category = mockCategories[0],
            stock = 20,
            sellerName = "মোবাইল এক্সেসরিজ",
            sellerContact = "+880 1718 555666",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 22
        ),
        Product(
            id = "6",
            name = "ব্লুটুথ স্পিকার",
            description = "উচ্চ মানের সাউন্ড কোয়ালিটি সহ পোর্টেবল স্পিকার।",
            price = 2200.0,
            originalPrice = 2800.0,
            imageUrl = "https://picsum.photos/200/120?random=13",
            category = mockCategories[0],
            stock = 10,
            sellerName = "সাউন্ড সিস্টেম",
            sellerContact = "+880 1719 777888",
            location = "ভালুকা শহর",
            rating = 4.7f,
            reviewCount = 31,
            isNew = true,
            isFeatured = true
        ),
        Product(
            id = "7",
            name = "HDMI ক্যাবল ৫ মিটার",
            description = "হাই স্পিড HDMI ক্যাবল, 4K সাপোর্ট।",
            price = 450.0,
            imageUrl = "https://picsum.photos/200/120?random=14",
            category = mockCategories[0],
            stock = 30,
            sellerName = "ক্যাবল শপ",
            sellerContact = "+880 1711 999000",
            location = "ভালুকা",
            rating = 4.0f,
            reviewCount = 6
        ),

        // পোশাক - Clothing (Category 1)
        Product(
            id = "8",
            name = "কটন শার্ট",
            description = "১০০% কটন শার্ট, সব সাইজ পাওয়া যায়।",
            price = 1200.0,
            originalPrice = 1500.0,
            imageUrl = "https://picsum.photos/200/120?random=3",
            category = mockCategories[1],
            stock = 20,
            sellerName = "ফারুক টেইলার্স",
            sellerContact = "+880 1713 789012",
            location = "ভালুকা শহর",
            rating = 4.0f,
            reviewCount = 15
        ),
        Product(
            id = "9",
            name = "পাঞ্জাবি",
            description = "ঈদ স্পেশাল পাঞ্জাবি, সুন্দর ডিজাইন।",
            price = 2500.0,
            imageUrl = "https://picsum.photos/200/120?random=15",
            category = mockCategories[1],
            stock = 12,
            sellerName = "রাজধানী ফ্যাশন",
            sellerContact = "+880 1712 123123",
            location = "ভালুকা বাজার",
            rating = 4.5f,
            reviewCount = 28,
            isFeatured = true
        ),
        Product(
            id = "10",
            name = "জিন্স প্যান্ট",
            description = "স্ট্রেচেবল জিন্স, কমফর্টেবল ফিট।",
            price = 1800.0,
            originalPrice = 2200.0,
            imageUrl = "https://picsum.photos/200/120?random=16",
            category = mockCategories[1],
            stock = 15,
            sellerName = "ডেনিম হাউস",
            sellerContact = "+880 1713 234234",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 19
        ),
        Product(
            id = "11",
            name = "টি-শার্ট",
            description = "কটন টি-শার্ট, বিভিন্ন কালার।",
            price = 450.0,
            imageUrl = "https://picsum.photos/200/120?random=17",
            category = mockCategories[1],
            stock = 40,
            sellerName = "ইয়ুথ ফ্যাশন",
            sellerContact = "+880 1714 345345",
            location = "ভালুকা সদর",
            rating = 4.1f,
            reviewCount = 42
        ),
        Product(
            id = "12",
            name = "শাড়ি",
            description = "কটন শাড়ি, ট্রেডিশনাল ডিজাইন।",
            price = 3500.0,
            imageUrl = "https://picsum.photos/200/120?random=18",
            category = mockCategories[1],
            stock = 8,
            sellerName = "সাজ গোজ",
            sellerContact = "+880 1715 456456",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 21,
            isNew = true
        ),
        Product(
            id = "13",
            name = "সালোয়ার কামিজ",
            description = "থ্রি পিস সালোয়ার কামিজ সেট।",
            price = 2800.0,
            originalPrice = 3500.0,
            imageUrl = "https://picsum.photos/200/120?random=19",
            category = mockCategories[1],
            stock = 10,
            sellerName = "লেডিস কর্নার",
            sellerContact = "+880 1716 567567",
            location = "ভালুকা বাজার",
            rating = 4.4f,
            reviewCount = 17
        ),
        Product(
            id = "14",
            name = "জ্যাকেট",
            description = "শীতের জ্যাকেট, ওয়াটারপ্রুফ।",
            price = 3200.0,
            imageUrl = "https://picsum.photos/200/120?random=20",
            category = mockCategories[1],
            stock = 6,
            sellerName = "উইন্টার কালেকশন",
            sellerContact = "+880 1717 678678",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 13,
            isFeatured = true
        ),

        // খাবার - Food (Category 2)
        Product(
            id = "15",
            name = "দেশি মুরগি",
            description = "সম্পূর্ণ দেশি মুরগি, তাজা এবং স্বাস্থ্যকর।",
            price = 500.0,
            imageUrl = "https://picsum.photos/200/120?random=4",
            category = mockCategories[2],
            stock = 10,
            sellerName = "কৃষক আব্দুল মজিদ",
            sellerContact = "+880 1714 345678",
            location = "গ্রাম: বালিয়া",
            rating = 4.8f,
            reviewCount = 32,
            isNew = true
        ),
        Product(
            id = "16",
            name = "কাঁচা মধু",
            description = "খাঁটি কাঁচা মধু, সুন্দরবনের।",
            price = 1200.0,
            imageUrl = "https://picsum.photos/200/120?random=21",
            category = mockCategories[2],
            stock = 15,
            sellerName = "প্রকৃতি ভান্ডার",
            sellerContact = "+880 1718 789789",
            location = "ভালুকা",
            rating = 4.9f,
            reviewCount = 48,
            isFeatured = true
        ),
        Product(
            id = "17",
            name = "তেলের ঘানি চাল",
            description = "প্রিমিয়াম মিনিকেট চাল, সুগন্ধি।",
            price = 850.0,
            imageUrl = "https://picsum.photos/200/120?random=22",
            category = mockCategories[2],
            stock = 50,
            sellerName = "চাল ভান্ডার",
            sellerContact = "+880 1719 890890",
            location = "ভালুকা বাজার",
            rating = 4.4f,
            reviewCount = 25
        ),
        Product(
            id = "18",
            name = "তাজা শাকসবজি",
            description = "অর্গানিক শাকসবজি, প্রতিদিন তাজা।",
            price = 200.0,
            imageUrl = "https://picsum.photos/200/120?random=23",
            category = mockCategories[2],
            stock = 30,
            sellerName = "সবজি বাজার",
            sellerContact = "+880 1711 901901",
            location = "ভালুকা হাট",
            rating = 4.3f,
            reviewCount = 56
        ),
        Product(
            id = "19",
            name = "দেশি ডিম",
            description = "মুক্ত-পরিসরের মুরগির ডিম।",
            price = 180.0,
            imageUrl = "https://picsum.photos/200/120?random=24",
            category = mockCategories[2],
            stock = 40,
            sellerName = "পোল্ট্রি ফার্ম",
            sellerContact = "+880 1712 012012",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 39,
            isNew = true
        ),
        Product(
            id = "20",
            name = "গরুর দুধ",
            description = "খাঁটি গরুর দুধ, প্রতিদিন সকালে ডেলিভারি।",
            price = 120.0,
            imageUrl = "https://picsum.photos/200/120?random=25",
            category = mockCategories[2],
            stock = 20,
            sellerName = "ডেইরি ফার্ম",
            sellerContact = "+880 1713 123234",
            location = "ভালুকা গ্রাম",
            rating = 4.6f,
            reviewCount = 44
        ),
        Product(
            id = "21",
            name = "সরিষার তেল",
            description = "ঘানিতে ভাঙা খাঁটি সরিষার তেল।",
            price = 320.0,
            imageUrl = "https://picsum.photos/200/120?random=26",
            category = mockCategories[2],
            stock = 25,
            sellerName = "তেল ঘানি",
            sellerContact = "+880 1714 234345",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 33
        ),

        // বই - Books (Category 3)
        Product(
            id = "22",
            name = "গল্পের বই সেট",
            description = "জনপ্রিয় লেখকদের গল্পের বইয়ের সেট।",
            price = 800.0,
            originalPrice = 1000.0,
            imageUrl = "https://picsum.photos/200/120?random=6",
            category = mockCategories[3],
            stock = 15,
            sellerName = "বুক কর্নার",
            sellerContact = "+880 1716 567890",
            location = "ভালুকা লাইব্রেরি",
            rating = 4.6f,
            reviewCount = 28
        ),
        Product(
            id = "23",
            name = "উপন্যাস সমগ্র",
            description = "হুমায়ূন আহমেদের উপন্যাস সমগ্র।",
            price = 2500.0,
            imageUrl = "https://picsum.photos/200/120?random=27",
            category = mockCategories[3],
            stock = 8,
            sellerName = "জ্ঞান ভান্ডার",
            sellerContact = "+880 1715 345456",
            location = "ভালুকা",
            rating = 4.9f,
            reviewCount = 67,
            isFeatured = true
        ),
        Product(
            id = "24",
            name = "ইংরেজি গ্রামার বই",
            description = "সম্পূর্ণ ইংরেজি গ্রামার গাইড।",
            price = 450.0,
            imageUrl = "https://picsum.photos/200/120?random=28",
            category = mockCategories[3],
            stock = 20,
            sellerName = "এডুকেশন পয়েন্ট",
            sellerContact = "+880 1716 456567",
            location = "ভালুকা বাজার",
            rating = 4.3f,
            reviewCount = 18
        ),
        Product(
            id = "25",
            name = "গণিত সমাধান",
            description = "নবম-দশম শ্রেণির গণিত সমাধান।",
            price = 350.0,
            imageUrl = "https://picsum.photos/200/120?random=29",
            category = mockCategories[3],
            stock = 30,
            sellerName = "স্টুডেন্ট বুকস",
            sellerContact = "+880 1717 567678",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 22
        ),
        Product(
            id = "26",
            name = "ইসলামিক বই",
            description = "কোরআন তাফসীর ও হাদিস বই।",
            price = 600.0,
            imageUrl = "https://picsum.photos/200/120?random=30",
            category = mockCategories[3],
            stock = 12,
            sellerName = "ইসলামিক বুক হাউস",
            sellerContact = "+880 1718 678789",
            location = "ভালুকা মসজিদ রোড",
            rating = 4.8f,
            reviewCount = 41,
            isNew = true
        ),
        Product(
            id = "27",
            name = "শিশুতোষ বই",
            description = "রঙিন ছবি সহ শিশুদের বই।",
            price = 250.0,
            imageUrl = "https://picsum.photos/200/120?random=31",
            category = mockCategories[3],
            stock = 25,
            sellerName = "কিডস বুকস",
            sellerContact = "+880 1719 789890",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 34
        ),
        Product(
            id = "28",
            name = "রান্নার বই",
            description = "বাংলা ও চাইনিজ রান্নার রেসিপি।",
            price = 400.0,
            originalPrice = 550.0,
            imageUrl = "https://picsum.photos/200/120?random=32",
            category = mockCategories[3],
            stock = 10,
            sellerName = "কুক বুক শপ",
            sellerContact = "+880 1711 890901",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 15
        ),

        // মোবাইল - Mobile (Category 4)
        Product(
            id = "29",
            name = "স্যামসাং গ্যালাক্সি A54",
            description = "একদম নতুন কন্ডিশনে স্যামসাং গ্যালাক্সি A54। সব ফিচার সহ।",
            price = 35000.0,
            originalPrice = 40000.0,
            imageUrl = "https://picsum.photos/200/120?random=1",
            category = mockCategories[4],
            stock = 5,
            sellerName = "মোঃ রহিম উদ্দিন",
            sellerContact = "+880 1711 123456",
            location = "ভালুকা, ময়মনসিংহ",
            rating = 4.5f,
            reviewCount = 23,
            isNew = true,
            isFeatured = true
        ),
        Product(
            id = "30",
            name = "আইফোন ১৩",
            description = "ব্যবহৃত আইফোন ১৩, চমৎকার কন্ডিশন।",
            price = 55000.0,
            originalPrice = 65000.0,
            imageUrl = "https://picsum.photos/200/120?random=33",
            category = mockCategories[4],
            stock = 2,
            sellerName = "আইফোন সেন্টার",
            sellerContact = "+880 1712 901012",
            location = "ভালুকা শহর",
            rating = 4.7f,
            reviewCount = 19,
            isFeatured = true
        ),
        Product(
            id = "31",
            name = "শাওমি রেডমি নোট ১২",
            description = "নতুন শাওমি রেডমি নোট ১২, ওয়ারেন্টি সহ।",
            price = 22000.0,
            imageUrl = "https://picsum.photos/200/120?random=34",
            category = mockCategories[4],
            stock = 8,
            sellerName = "মোবাইল পয়েন্ট",
            sellerContact = "+880 1713 012123",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 27,
            isNew = true
        ),
        Product(
            id = "32",
            name = "রিয়েলমি C55",
            description = "বাজেট ফোন রিয়েলমি C55, ভালো পারফরম্যান্স।",
            price = 15000.0,
            imageUrl = "https://picsum.photos/200/120?random=35",
            category = mockCategories[4],
            stock = 10,
            sellerName = "ডিজিটাল মোবাইল",
            sellerContact = "+880 1714 123234",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 16
        ),
        Product(
            id = "33",
            name = "অপো রেনো ৮",
            description = "অপো রেনো ৮, দুর্দান্ত ক্যামেরা।",
            price = 38000.0,
            originalPrice = 42000.0,
            imageUrl = "https://picsum.photos/200/120?random=36",
            category = mockCategories[4],
            stock = 4,
            sellerName = "অপো শোরুম",
            sellerContact = "+880 1715 234345",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 21,
            isFeatured = true
        ),
        Product(
            id = "34",
            name = "ভিভো Y36",
            description = "ভিভো Y36, দীর্ঘ ব্যাটারি লাইফ।",
            price = 26000.0,
            imageUrl = "https://picsum.photos/200/120?random=37",
            category = mockCategories[4],
            stock = 6,
            sellerName = "ভিভো স্টোর",
            sellerContact = "+880 1716 345456",
            location = "ভালুকা সদর",
            rating = 4.3f,
            reviewCount = 14,
            isNew = true
        ),
        Product(
            id = "35",
            name = "ইনফিনিক্স হট ৩০",
            description = "গেমিং ফোন ইনফিনিক্স হট ৩০।",
            price = 18000.0,
            imageUrl = "https://picsum.photos/200/120?random=38",
            category = mockCategories[4],
            stock = 12,
            sellerName = "গেমার মোবাইল",
            sellerContact = "+880 1717 456567",
            location = "ভালুকা",
            rating = 4.1f,
            reviewCount = 9
        ),

        // গাড়ি - Vehicles (Category 5)
        Product(
            id = "36",
            name = "হোন্ডা মোটরসাইকেল",
            description = "২০২২ মডেল হোন্ডা বাইক, খুবই ভালো কন্ডিশন।",
            price = 125000.0,
            imageUrl = "https://picsum.photos/200/120?random=2",
            category = mockCategories[5],
            stock = 1,
            sellerName = "আব্দুল করিম",
            sellerContact = "+880 1712 654321",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 8,
            isFeatured = true
        ),
        Product(
            id = "37",
            name = "ইয়ামাহা FZ",
            description = "২০২১ মডেল ইয়ামাহা FZ, স্পোর্টস বাইক।",
            price = 180000.0,
            originalPrice = 200000.0,
            imageUrl = "https://picsum.photos/200/120?random=39",
            category = mockCategories[5],
            stock = 1,
            sellerName = "বাইক শোরুম",
            sellerContact = "+880 1718 567678",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 12,
            isFeatured = true
        ),
        Product(
            id = "38",
            name = "হিরো স্প্লেন্ডর",
            description = "ফুয়েল এফিশিয়েন্ট হিরো স্প্লেন্ডর।",
            price = 95000.0,
            imageUrl = "https://picsum.photos/200/120?random=40",
            category = mockCategories[5],
            stock = 2,
            sellerName = "হিরো ডিলার",
            sellerContact = "+880 1719 678789",
            location = "ভালুকা সদর",
            rating = 4.3f,
            reviewCount = 15
        ),
        Product(
            id = "39",
            name = "সাইকেল",
            description = "মাউন্টেন বাইক, গিয়ার সহ।",
            price = 12000.0,
            imageUrl = "https://picsum.photos/200/120?random=41",
            category = mockCategories[5],
            stock = 5,
            sellerName = "সাইকেল হাউস",
            sellerContact = "+880 1711 789890",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 24,
            isNew = true
        ),
        Product(
            id = "40",
            name = "ব্যাটারি চালিত রিকশা",
            description = "ইলেকট্রিক রিকশা, কম খরচ।",
            price = 85000.0,
            imageUrl = "https://picsum.photos/200/120?random=42",
            category = mockCategories[5],
            stock = 1,
            sellerName = "ইকো ট্রান্সপোর্ট",
            sellerContact = "+880 1712 890901",
            location = "ভালুকা বাজার",
            rating = 4.0f,
            reviewCount = 6
        ),
        Product(
            id = "41",
            name = "স্কুটার",
            description = "অটো স্কুটার, সহজে চালানো যায়।",
            price = 65000.0,
            originalPrice = 75000.0,
            imageUrl = "https://picsum.photos/200/120?random=43",
            category = mockCategories[5],
            stock = 3,
            sellerName = "স্কুটার সেন্টার",
            sellerContact = "+880 1713 901012",
            location = "ভালুকা",
            rating = 4.2f,
            reviewCount = 11
        ),
        Product(
            id = "42",
            name = "টয়োটা করোলা",
            description = "২০১৮ মডেল টয়োটা করোলা, চমৎকার কন্ডিশন।",
            price = 1800000.0,
            imageUrl = "https://picsum.photos/200/120?random=44",
            category = mockCategories[5],
            stock = 1,
            sellerName = "প্রিমিয়াম কার",
            sellerContact = "+880 1714 012123",
            location = "ভালুকা শহর",
            rating = 4.8f,
            reviewCount = 5,
            isFeatured = true
        ),

        // আসবাবপত্র - Furniture (Category 6)
        Product(
            id = "43",
            name = "কাঠের টেবিল",
            description = "শক্ত কাঠের তৈরি পড়ার টেবিল, দীর্ঘস্থায়ী।",
            price = 8500.0,
            imageUrl = "https://picsum.photos/200/120?random=7",
            category = mockCategories[6],
            stock = 3,
            sellerName = "কারিগর আসবাবপত্র",
            sellerContact = "+880 1717 234567",
            location = "ভালুকা বাস স্ট্যান্ড",
            rating = 4.4f,
            reviewCount = 7
        ),
        Product(
            id = "44",
            name = "সোফা সেট",
            description = "৫ সিটার সোফা সেট, আরামদায়ক।",
            price = 35000.0,
            originalPrice = 42000.0,
            imageUrl = "https://picsum.photos/200/120?random=45",
            category = mockCategories[6],
            stock = 2,
            sellerName = "ফার্নিচার ওয়ার্ল্ড",
            sellerContact = "+880 1715 123234",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 18,
            isFeatured = true
        ),
        Product(
            id = "45",
            name = "বিছানা",
            description = "ডাবল বেড, ম্যাট্রেস সহ।",
            price = 25000.0,
            imageUrl = "https://picsum.photos/200/120?random=46",
            category = mockCategories[6],
            stock = 4,
            sellerName = "বেডরুম ফার্নিচার",
            sellerContact = "+880 1716 234345",
            location = "ভালুকা সদর",
            rating = 4.5f,
            reviewCount = 13,
            isNew = true
        ),
        Product(
            id = "46",
            name = "আলমারি",
            description = "৬ ফুট আলমারি, ৩ দরজা।",
            price = 18000.0,
            imageUrl = "https://picsum.photos/200/120?random=47",
            category = mockCategories[6],
            stock = 3,
            sellerName = "ওয়ারড্রোব হাউস",
            sellerContact = "+880 1717 345456",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 9
        ),
        Product(
            id = "47",
            name = "খাবার টেবিল",
            description = "৬ চেয়ার সহ ডাইনিং টেবিল।",
            price = 22000.0,
            originalPrice = 28000.0,
            imageUrl = "https://picsum.photos/200/120?random=48",
            category = mockCategories[6],
            stock = 2,
            sellerName = "ডাইনিং সেট",
            sellerContact = "+880 1718 456567",
            location = "ভালুকা বাজার",
            rating = 4.7f,
            reviewCount = 16
        ),
        Product(
            id = "48",
            name = "বুক শেলফ",
            description = "কাঠের বুক শেলফ, ৫ তাক।",
            price = 6500.0,
            imageUrl = "https://picsum.photos/200/120?random=49",
            category = mockCategories[6],
            stock = 8,
            sellerName = "শেলফ মার্ট",
            sellerContact = "+880 1719 567678",
            location = "ভালুকা",
            rating = 4.2f,
            reviewCount = 11
        ),
        Product(
            id = "49",
            name = "কম্পিউটার টেবিল",
            description = "মডার্ন কম্পিউটার টেবিল।",
            price = 7500.0,
            imageUrl = "https://picsum.photos/200/120?random=50",
            category = mockCategories[6],
            stock = 6,
            sellerName = "অফিস ফার্নিচার",
            sellerContact = "+880 1711 678789",
            location = "ভালুকা সদর",
            rating = 4.4f,
            reviewCount = 8,
            isNew = true
        ),

        // স্বাস্থ্য - Health (Category 7)
        Product(
            id = "50",
            name = "ভিটামিন সি ট্যাবলেট",
            description = "প্রাকৃতিক ভিটামিন সি ট্যাবলেট, রোগ প্রতিরোধ ক্ষমতা বৃদ্ধি করে।",
            price = 350.0,
            imageUrl = "https://picsum.photos/200/120?random=8",
            category = mockCategories[7],
            stock = 25,
            sellerName = "হেলথ ফার্মেসি",
            sellerContact = "+880 1718 890123",
            location = "ভালুকা হাসপাতাল রোড",
            rating = 4.7f,
            reviewCount = 45,
            isNew = true
        ),
        Product(
            id = "51",
            name = "ওমেগা-৩ ক্যাপসুল",
            description = "হার্ট ও ব্রেইনের জন্য ওমেগা-৩।",
            price = 850.0,
            imageUrl = "https://picsum.photos/200/120?random=51",
            category = mockCategories[7],
            stock = 20,
            sellerName = "মেডিকেয়ার",
            sellerContact = "+880 1712 789890",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 37,
            isFeatured = true
        ),
        Product(
            id = "52",
            name = "ব্লাড প্রেসার মনিটর",
            description = "ডিজিটাল BP মনিটর, সহজে ব্যবহারযোগ্য।",
            price = 2500.0,
            originalPrice = 3200.0,
            imageUrl = "https://picsum.photos/200/120?random=52",
            category = mockCategories[7],
            stock = 10,
            sellerName = "মেডিক্যাল ইকুইপমেন্ট",
            sellerContact = "+880 1713 890901",
            location = "ভালুকা হাসপাতাল",
            rating = 4.5f,
            reviewCount = 28
        ),
        Product(
            id = "53",
            name = "থার্মোমিটার",
            description = "ডিজিটাল থার্মোমিটার, দ্রুত রিডিং।",
            price = 450.0,
            imageUrl = "https://picsum.photos/200/120?random=53",
            category = mockCategories[7],
            stock = 30,
            sellerName = "হেলথ কেয়ার",
            sellerContact = "+880 1714 901012",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 22
        ),
        Product(
            id = "54",
            name = "হুইলচেয়ার",
            description = "ফোল্ডেবল হুইলচেয়ার, লাইটওয়েট।",
            price = 8500.0,
            imageUrl = "https://picsum.photos/200/120?random=54",
            category = mockCategories[7],
            stock = 3,
            sellerName = "মেডিক্যাল সাপ্লাই",
            sellerContact = "+880 1715 012123",
            location = "ভালুকা সদর",
            rating = 4.4f,
            reviewCount = 11,
            isNew = true
        ),
        Product(
            id = "55",
            name = "গ্লুকোমিটার",
            description = "ব্লাড সুগার টেস্ট মেশিন।",
            price = 1800.0,
            originalPrice = 2300.0,
            imageUrl = "https://picsum.photos/200/120?random=55",
            category = mockCategories[7],
            stock = 12,
            sellerName = "ডায়াবেটিস কেয়ার",
            sellerContact = "+880 1716 123234",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 34
        ),
        Product(
            id = "56",
            name = "মাল্টিভিটামিন",
            description = "কমপ্লিট মাল্টিভিটামিন সাপ্লিমেন্ট।",
            price = 650.0,
            imageUrl = "https://picsum.photos/200/120?random=56",
            category = mockCategories[7],
            stock = 40,
            sellerName = "নিউট্রিশন স্টোর",
            sellerContact = "+880 1717 234345",
            location = "ভালুকা বাজার",
            rating = 4.5f,
            reviewCount = 52,
            isFeatured = true
        )
    )

    init {
        loadInitialData()
    }

    fun onAction(action: ShopsAction) {
        when (action) {
            is ShopsAction.SearchProducts -> {
                _uiState.value = _uiState.value.copy(
                    searchQuery = action.query,
                    filteredProducts = filterProducts(action.query, _uiState.value.selectedCategory)
                )
            }

            is ShopsAction.SelectCategory -> {
                _uiState.value = _uiState.value.copy(
                    selectedCategory = action.category,
                    filteredProducts = filterProducts(_uiState.value.searchQuery, action.category)
                )
            }

            is ShopsAction.NavigateToCategory -> {
                // Navigation is handled in ShopsScreen, no state change needed here
            }

            is ShopsAction.ToggleFavorite -> {
                // Handle favorite toggle - could update local storage or backend
            }

            is ShopsAction.SortProducts -> {
                // Handle product sorting
                val sortedProducts = when (action.sortOption) {
                    SortOption.PRICE_LOW_TO_HIGH -> _uiState.value.filteredProducts.sortedBy { it.price }
                    SortOption.PRICE_HIGH_TO_LOW -> _uiState.value.filteredProducts.sortedByDescending { it.price }
                    SortOption.NAME -> _uiState.value.filteredProducts.sortedBy { it.name }
                    SortOption.NEWEST -> _uiState.value.filteredProducts.sortedByDescending { it.createdAt }
                    SortOption.RATING -> _uiState.value.filteredProducts.sortedByDescending { it.rating }
                }
                _uiState.value = _uiState.value.copy(filteredProducts = sortedProducts)
            }

            ShopsAction.Refresh -> {
                loadInitialData()
            }

            ShopsAction.ClearFilters -> {
                _uiState.value = _uiState.value.copy(
                    selectedCategory = null,
                    searchQuery = "",
                    filteredProducts = _uiState.value.products
                )
            }
        }
    }

    private fun filterProducts(
        searchQuery: String,
        selectedCategory: ProductCategory?
    ): List<Product> {
        var filtered = _uiState.value.products

        // Filter by category
        if (selectedCategory != null) {
            filtered = filtered.filter { it.category.id == selectedCategory.id }
        }

        // Filter by search query
        if (searchQuery.isNotEmpty()) {
            filtered = filtered.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true) ||
                        it.location.contains(searchQuery, ignoreCase = true)
            }
        }

        return filtered
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Simulate network delay
            delay(1000)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                products = mockProducts,
                categories = mockCategories,
                filteredProducts = mockProducts
            )
        }
    }
}
