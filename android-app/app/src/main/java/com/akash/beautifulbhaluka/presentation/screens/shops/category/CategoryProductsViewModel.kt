package com.akash.beautifulbhaluka.presentation.screens.shops.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.screens.shops.Product
import com.akash.beautifulbhaluka.presentation.screens.shops.ProductCategory
import com.akash.beautifulbhaluka.presentation.screens.shops.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class CategoryProductsViewModel(
    private val categoryId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryProductsUiState())
    val uiState: StateFlow<CategoryProductsUiState> = _uiState.asStateFlow()

    // Mock categories (synced with ShopsViewModel)
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

    // TODO: ARCHITECTURE ISSUE - Mock data duplicated from ShopsViewModel
    // This violates DRY principle and Single Source of Truth
    // Proper solution: Create a shared Repository or DataSource class
    // Current issue: Products not synced with ShopsViewModel causing 0 items in category details
    // Temporary fix needed: Copy all 56 products from ShopsViewModel with correct category assignments

    // Mock products (synced with ShopsViewModel) - 56 products total across 8 categories
    private val allProducts = listOf(
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
            imageUrl = "https://picsum.photos/200/120?random=2",
            category = mockCategories[0],
            stock = 15,
            sellerName = "টেক স্টোর",
            sellerContact = "+880 1715 901234",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 28,
            isNew = true
        ),
        Product(
            id = "3",
            name = "USB কীবোর্ড",
            description = "মেকানিক্যাল USB কীবোর্ড, টেকসই এবং আরামদায়ক।",
            price = 1500.0,
            imageUrl = "https://picsum.photos/200/120?random=3",
            category = mockCategories[0],
            stock = 10,
            sellerName = "কম্পিউটার শপ",
            sellerContact = "+880 1716 234567",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 15
        ),
        Product(
            id = "4",
            name = "এইচডি ওয়েবক্যাম",
            description = "১০৮০পি HD ওয়েবক্যাম, অনলাইন মিটিং এর জন্য।",
            price = 3200.0,
            imageUrl = "https://picsum.photos/200/120?random=4",
            category = mockCategories[0],
            stock = 5,
            sellerName = "ডিজিটাল হাব",
            sellerContact = "+880 1717 345678",
            location = "ভালুকা শহর",
            rating = 4.6f,
            reviewCount = 22,
            isFeatured = true
        ),
        Product(
            id = "5",
            name = "পাওয়ার ব্যাংক ২০০০০mAh",
            description = "ফাস্ট চার্জিং পাওয়ার ব্যাংক, সব ডিভাইসের জন্য।",
            price = 2800.0,
            originalPrice = 3500.0,
            imageUrl = "https://picsum.photos/200/120?random=5",
            category = mockCategories[0],
            stock = 12,
            sellerName = "মোবাইল একসেসরিজ",
            sellerContact = "+880 1718 456789",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 35
        ),
        Product(
            id = "6",
            name = "ব্লুটুথ স্পিকার",
            description = "পোর্টেবল ব্লুটুথ স্পিকার, চমৎকার সাউন্ড কোয়ালিটি।",
            price = 1800.0,
            imageUrl = "https://picsum.photos/200/120?random=6",
            category = mockCategories[0],
            stock = 20,
            sellerName = "সাউন্ড সিস্টেম",
            sellerContact = "+880 1719 567890",
            location = "ভালুকা বাজার",
            rating = 4.4f,
            reviewCount = 18
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

        // পোশাক - Clothing (Category 1) - ADD ALL 7 PRODUCTS
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
            name = "জিন্স প্যান্ট",
            description = "স্টাইলিশ জিন্স প্যান্ট, সব সাইজে উপলব্ধ।",
            price = 2500.0,
            imageUrl = "https://picsum.photos/200/120?random=9",
            category = mockCategories[1],
            stock = 10,
            sellerName = "ফ্যাশন হাউস",
            sellerContact = "+880 1714 567890",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 25,
            isNew = true
        ),
        Product(
            id = "10",
            name = "টি শার্ট",
            description = "আরামদায়ক টি শার্ট, বিভিন্ন রঙে পাওয়া যায়।",
            price = 800.0,
            imageUrl = "https://picsum.photos/200/120?random=10",
            category = mockCategories[1],
            stock = 15,
            sellerName = "ক্লোথিং স্টোর",
            sellerContact = "+880 1715 678901",
            location = "ভালুকা বাজার",
            rating = 4.2f,
            reviewCount = 18
        ),
        Product(
            id = "11",
            name = "হুডি",
            description = "গরম হুডি, শীতকালীন ব্যবহারের জন্য উপযুক্ত।",
            price = 3000.0,
            imageUrl = "https://picsum.photos/200/120?random=11",
            category = mockCategories[1],
            stock = 8,
            sellerName = "আউটডোর গিয়ার",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 30,
            isFeatured = true
        ),
        Product(
            id = "12",
            name = "স্পোর্টস শু",
            description = "আরামদায়ক স্পোর্টস শু, দৌড়ানোর জন্য উপযুক্ত।",
            price = 4000.0,
            imageUrl = "https://picsum.photos/200/120?random=12",
            category = mockCategories[1],
            stock = 10,
            sellerName = "জুতা ঘর",
            sellerContact = "+880 1717 890123",
            location = "ভালুকা বাজার",
            rating = 4.6f,
            reviewCount = 22
        ),
        Product(
            id = "13",
            name = "সানগ্লাস",
            description = "স্টাইলিশ সানগ্লাস, UV রে প্রতিরোধক।",
            price = 1500.0,
            imageUrl = "https://picsum.photos/200/120?random=13",
            category = mockCategories[1],
            stock = 25,
            sellerName = "অপটিক্যাল স্টোর",
            sellerContact = "+880 1718 901234",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 10
        ),
        Product(
            id = "14",
            name = "বেল্ট",
            description = "ক্লাসিক লেদার বেল্ট, সব সাইজে পাওয়া যায়।",
            price = 900.0,
            imageUrl = "https://picsum.photos/200/120?random=14",
            category = mockCategories[1],
            stock = 18,
            sellerName = "লেদার শপ",
            sellerContact = "+880 1719 012345",
            location = "ভালুকা",
            rating = 4.1f,
            reviewCount = 8
        ),

        // খাবার - Food (Category 2) - ADD ALL 7 PRODUCTS
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
            name = "গরুর মাংস",
            description = "তাজা গরুর মাংস, স্বাস্থ্যকর এবং পুষ্টিকর।",
            price = 1200.0,
            imageUrl = "https://picsum.photos/200/120?random=5",
            category = mockCategories[2],
            stock = 5,
            sellerName = "মাংসের দোকান",
            sellerContact = "+880 1715 678901",
            location = "ভালুকা বাজার",
            rating = 4.5f,
            reviewCount = 20
        ),
        Product(
            id = "17",
            name = "মুরগির ডিম",
            description = "তাজা মুরগির ডিম, পুষ্টিকর এবং স্বাস্থ্যকর।",
            price = 150.0,
            imageUrl = "https://picsum.photos/200/120?random=6",
            category = mockCategories[2],
            stock = 50,
            sellerName = "ডিমের দোকান",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 15
        ),
        Product(
            id = "18",
            name = "দেশি হাঁস",
            description = "তাজা দেশি হাঁস, স্বাস্থ্যকর এবং সুস্বাদু।",
            price = 700.0,
            imageUrl = "https://picsum.photos/200/120?random=7",
            category = mockCategories[2],
            stock = 8,
            sellerName = "কৃষক আব্দুল",
            sellerContact = "+880 1717 890123",
            location = "গ্রাম: বালিয়া",
            rating = 4.6f,
            reviewCount = 18
        ),
        Product(
            id = "19",
            name = "মাছ",
            description = "তাজা মাছ, স্বাস্থ্যকর এবং পুষ্টিকর।",
            price = 300.0,
            imageUrl = "https://picsum.photos/200/120?random=8",
            category = mockCategories[2],
            stock = 12,
            sellerName = "মাছের দোকান",
            sellerContact = "+880 1718 901234",
            location = "ভালুকা বাজার",
            rating = 4.4f,
            reviewCount = 10
        ),
        Product(
            id = "20",
            name = "সবজি মিশ্রণ",
            description = "তাজা সবজি মিশ্রণ, স্বাস্থ্যকর এবং পুষ্টিকর।",
            price = 200.0,
            imageUrl = "https://picsum.photos/200/120?random=9",
            category = mockCategories[2],
            stock = 20,
            sellerName = "সবজি বাজার",
            sellerContact = "+880 1719 012345",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 12
        ),
        Product(
            id = "21",
            name = "ফল মিশ্রণ",
            description = "তাজা ফল মিশ্রণ, ভিটামিন সি সমৃদ্ধ।",
            price = 250.0,
            imageUrl = "https://picsum.photos/200/120?random=10",
            category = mockCategories[2],
            stock = 15,
            sellerName = "ফল বিক্রেতা",
            sellerContact = "+880 1720 123456",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 8
        ),

        // বই - Books (Category 3) - ADD ALL 7 PRODUCTS
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
            name = "শিক্ষা বিষয়ক বই",
            description = "শিক্ষা বিষয়ক জনপ্রিয় বই সমূহ।",
            price = 600.0,
            imageUrl = "https://picsum.photos/200/120?random=7",
            category = mockCategories[3],
            stock = 10,
            sellerName = "বইয়ের দোকান",
            sellerContact = "+880 1717 678901",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 18
        ),
        Product(
            id = "24",
            name = "কিশোর-কিশোরী উপন্যাস",
            description = "জনপ্রিয় কিশোর-কিশোরী উপন্যাসের সংগ্রহ।",
            price = 900.0,
            imageUrl = "https://picsum.photos/200/120?random=8",
            category = mockCategories[3],
            stock = 8,
            sellerName = "লিটল ফ্রি প্রেস",
            sellerContact = "+880 1718 789012",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 25,
            isFeatured = true
        ),
        Product(
            id = "25",
            name = "রহস্য ও থ্রিলার বই",
            description = "রহস্য ও থ্রিলার প্রেমীদের জন্য বিশেষ সংগ্রহ।",
            price = 1100.0,
            imageUrl = "https://picsum.photos/200/120?random=9",
            category = mockCategories[3],
            stock = 5,
            sellerName = "বেস্টসেলার পাবলিশার্স",
            sellerContact = "+880 1719 890123",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 30
        ),
        Product(
            id = "26",
            name = "ভ্রমণ গাইড বই",
            description = "বিশ্বের বিভিন্ন স্থানের ভ্রমণ গাইড বই।",
            price = 1300.0,
            imageUrl = "https://picsum.photos/200/120?random=10",
            category = mockCategories[3],
            stock = 7,
            sellerName = "ভ্রমণ বইঘর",
            sellerContact = "+880 1720 123456",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 12
        ),
        Product(
            id = "27",
            name = "শিশুদের জন্য শিক্ষণীয় বই",
            description = "শিশুদের জন্য মজার এবং শিক্ষণীয় বইয়ের সেট।",
            price = 700.0,
            imageUrl = "https://picsum.photos/200/120?random=11",
            category = mockCategories[3],
            stock = 15,
            sellerName = "শিশু বইঘর",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 20
        ),
        Product(
            id = "28",
            name = "বাংলা সাহিত্যের সেরা গল্প",
            description = "বাংলা সাহিত্যের সেরা গল্পের সংকলন।",
            price = 850.0,
            imageUrl = "https://picsum.photos/200/120?random=12",
            category = mockCategories[3],
            stock = 10,
            sellerName = "বইয়ের দোকান",
            sellerContact = "+880 1717 678901",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 15
        ),

        // মোবাইল - Mobile (Category 4) - ADD ALL 7 PRODUCTS
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
            name = "অপো রেনো ৭",
            description = "অপো রেনো ৭ স্মার্টফোন, ৮GB RAM, ১২৮GB স্টোরেজ।",
            price = 30000.0,
            originalPrice = 35000.0,
            imageUrl = "https://picsum.photos/200/120?random=2",
            category = mockCategories[4],
            stock = 10,
            sellerName = "সেলিম মোবাইল",
            sellerContact = "+880 1712 345678",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 18
        ),
        Product(
            id = "31",
            name = "শাওমি রেডমি নোট ১১",
            description = "শাওমি রেডমি নোট ১১, ৬GB RAM, ১২৮GB স্টোরেজ।",
            price = 25000.0,
            originalPrice = 30000.0,
            imageUrl = "https://picsum.photos/200/120?random=3",
            category = mockCategories[4],
            stock = 15,
            sellerName = "মোবাইল ওয়ার্ল্ড",
            sellerContact = "+880 1713 456789",
            location = "ভালুকা বাজার",
            rating = 4.4f,
            reviewCount = 20
        ),
        Product(
            id = "32",
            name = "ভিভো Y21",
            description = "ভিভো Y21 স্মার্টফোন, ৪GB RAM, ৬৪GB স্টোরেজ।",
            price = 15000.0,
            originalPrice = 20000.0,
            imageUrl = "https://picsum.photos/200/120?random=4",
            category = mockCategories[4],
            stock = 20,
            sellerName = "স্মার্টফোন শপ",
            sellerContact = "+880 1714 567890",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 15
        ),
        Product(
            id = "33",
            name = "নোকিয়া ৫.৪",
            description = "নোকিয়া ৫.৪ স্মার্টফোন, ৪GB RAM, ৬৪GB স্টোরেজ।",
            price = 12000.0,
            originalPrice = 15000.0,
            imageUrl = "https://picsum.photos/200/120?random=5",
            category = mockCategories[4],
            stock = 25,
            sellerName = "নোকিয়া স্টোর",
            sellerContact = "+880 1715 678901",
            location = "ভালুকা",
            rating = 4.0f,
            reviewCount = 10
        ),
        Product(
            id = "34",
            name = "আইফোন ১৩ প্রো ম্যাক্স",
            description = "আইফোন ১৩ প্রো ম্যাক্স, ১২৮GB স্টোরেজ, নতুন কন্ডিশনে।",
            price = 120000.0,
            originalPrice = 130000.0,
            imageUrl = "https://picsum.photos/200/120?random=6",
            category = mockCategories[4],
            stock = 2,
            sellerName = "অ্যাপল স্টোর",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.9f,
            reviewCount = 5,
            isFeatured = true
        ),
        Product(
            id = "35",
            name = "স্যামসাং গ্যালাক্সি Z ফ্লিপ 3",
            description = "স্যামসাং গ্যালাক্সি Z ফ্লিপ 3, ফোল্ডেবল স্মার্টফোন।",
            price = 150000.0,
            originalPrice = 160000.0,
            imageUrl = "https://picsum.photos/200/120?random=7",
            category = mockCategories[4],
            stock = 3,
            sellerName = "স্যামসাং স্টোর",
            sellerContact = "+880 1717 890123",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 8
        ),

        // গাড়ি - Vehicles (Category 5) - ADD ALL 7 PRODUCTS
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
            name = "ইজিপ্ট মোটরসাইকেল",
            description = "২০২১ মডেল ইজিপ্ট বাইক, খুবই ভালো কন্ডিশন।",
            price = 115000.0,
            imageUrl = "https://picsum.photos/200/120?random=3",
            category = mockCategories[5],
            stock = 2,
            sellerName = "মোঃ সেলিম",
            sellerContact = "+880 1713 456789",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 10
        ),
        Product(
            id = "38",
            name = "হোন্ডা সিটি ২০২০",
            description = "হোন্ডা সিটি ২০২০ মডেল, একদম নতুন কন্ডিশনে।",
            price = 300000.0,
            imageUrl = "https://picsum.photos/200/120?random=4",
            category = mockCategories[5],
            stock = 1,
            sellerName = "আব্দুল মালেক",
            sellerContact = "+880 1714 567890",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 5,
            isFeatured = true
        ),
        Product(
            id = "39",
            name = "টয়োটা করোলা ২০১৯",
            description = "টয়োটা করোলা ২০১৯ মডেল, খুবই ভালো কন্ডিশন।",
            price = 280000.0,
            imageUrl = "https://picsum.photos/200/120?random=5",
            category = mockCategories[5],
            stock = 2,
            sellerName = "মোঃ রফিকুল ইসলাম",
            sellerContact = "+880 1715 678901",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 8
        ),
        Product(
            id = "40",
            name = "হুন্ডাই টক্সন ২০২১",
            description = "হুন্ডাই টক্সন ২০২১ মডেল, নতুনের মতো।",
            price = 350000.0,
            imageUrl = "https://picsum.photos/200/120?random=6",
            category = mockCategories[5],
            stock = 1,
            sellerName = "মোঃ সোহেল",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 10
        ),
        Product(
            id = "41",
            name = "মিতসুবিশি আউটল্যান্ডার ২০২২",
            description = "মিতসুবিশি আউটল্যান্ডার ২০২২ মডেল, একদম নতুন।",
            price = 450000.0,
            imageUrl = "https://picsum.photos/200/120?random=7",
            category = mockCategories[5],
            stock = 1,
            sellerName = "মোঃ কামাল",
            sellerContact = "+880 1717 890123",
            location = "ভালুকা",
            rating = 4.9f,
            reviewCount = 5,
            isFeatured = true
        ),
        Product(
            id = "42",
            name = "ফোর্ড রেঞ্জার ২০২০",
            description = "ফোর্ড রেঞ্জার ২০২০ মডেল, খুবই ভালো কন্ডিশন।",
            price = 550000.0,
            imageUrl = "https://picsum.photos/200/120?random=8",
            category = mockCategories[5],
            stock = 1,
            sellerName = "মোঃ আলী",
            sellerContact = "+880 1718 901234",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 8
        ),

        // আসবাবপত্র - Furniture (Category 6) - ADD ALL 7 PRODUCTS
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
            name = "প্লাস্টিকের চেয়ার",
            description = "হালকা ওজনের প্লাস্টিকের চেয়ার, সহজে বহনযোগ্য।",
            price = 1500.0,
            imageUrl = "https://picsum.photos/200/120?random=8",
            category = mockCategories[6],
            stock = 10,
            sellerName = "চেয়ার শপ",
            sellerContact = "+880 1718 456789",
            location = "ভালুকা",
            rating = 4.2f,
            reviewCount = 15
        ),
        Product(
            id = "45",
            name = "কাপড়ের সোফা",
            description = "আরামদায়ক কাপড়ের সোফা, বাড়ির জন্য আদর্শ।",
            price = 25000.0,
            imageUrl = "https://picsum.photos/200/120?random=9",
            category = mockCategories[6],
            stock = 2,
            sellerName = "সোফা সেন্টার",
            sellerContact = "+880 1719 567890",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 10
        ),
        Product(
            id = "46",
            name = "টেবিল ল্যাম্প",
            description = "আধুনিক ডিজাইনের টেবিল ল্যাম্প, ঘরের শোভা বাড়ায়।",
            price = 3000.0,
            imageUrl = "https://picsum.photos/200/120?random=10",
            category = mockCategories[6],
            stock = 5,
            sellerName = "লাইটিং স্টোর",
            sellerContact = "+880 1720 123456",
            location = "ভালুকা",
            rating = 4.3f,
            reviewCount = 12
        ),
        Product(
            id = "47",
            name = "আধুনিক বইয়ের তাক",
            description = "আধুনিক ডিজাইনের বইয়ের তাক, ঘর সাজানোর জন্য।",
            price = 7000.0,
            imageUrl = "https://picsum.photos/200/120?random=11",
            category = mockCategories[6],
            stock = 4,
            sellerName = "বইয়ের তাক শপ",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 8
        ),
        Product(
            id = "48",
            name = "কফি টেবিল",
            description = "ছোট আকারের কফি টেবিল, লিভিং রুমের জন্য উপযুক্ত।",
            price = 4000.0,
            imageUrl = "https://picsum.photos/200/120?random=12",
            category = mockCategories[6],
            stock = 6,
            sellerName = "কফি টেবিল শপ",
            sellerContact = "+880 1717 890123",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 10
        ),
        Product(
            id = "49",
            name = "বেডরুম ফার্নিচার সেট",
            description = "বেডরুমের জন্য সম্পূর্ণ ফার্নিচার সেট।",
            price = 50000.0,
            imageUrl = "https://picsum.photos/200/120?random=13",
            category = mockCategories[6],
            stock = 2,
            sellerName = "ফার্নিচার গ্যালারি",
            sellerContact = "+880 1718 901234",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 5,
            isFeatured = true
        ),

        // স্বাস্থ্য - Health (Category 7) - ADD ALL 7 PRODUCTS
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
            name = "ভিটামিন ডি ট্যাবলেট",
            description = "ভিটামিন ডি সমৃদ্ধ ট্যাবলেট, হাড়ের স্বাস্থ্য উন্নত করে।",
            price = 400.0,
            imageUrl = "https://picsum.photos/200/120?random=9",
            category = mockCategories[7],
            stock = 20,
            sellerName = "ফার্মাসি স্টোর",
            sellerContact = "+880 1719 012345",
            location = "ভালুকা",
            rating = 4.6f,
            reviewCount = 30
        ),
        Product(
            id = "52",
            name = "জিঙ্ক ট্যাবলেট",
            description = "জিঙ্ক সমৃদ্ধ ট্যাবলেট, রোগ প্রতিরোধ ক্ষমতা বাড়ায়।",
            price = 250.0,
            imageUrl = "https://picsum.photos/200/120?random=10",
            category = mockCategories[7],
            stock = 15,
            sellerName = "নিউট্রিশন শপ",
            sellerContact = "+880 1720 123456",
            location = "ভালুকা",
            rating = 4.5f,
            reviewCount = 25
        ),
        Product(
            id = "53",
            name = "ম্যাগনেসিয়াম ট্যাবলেট",
            description = "ম্যাগনেসিয়াম সমৃদ্ধ ট্যাবলেট, পেশীর স্বাস্থ্যের জন্য উপকারী।",
            price = 450.0,
            imageUrl = "https://picsum.photos/200/120?random=11",
            category = mockCategories[7],
            stock = 10,
            sellerName = "হেলথ কেয়ার",
            sellerContact = "+880 1716 789012",
            location = "ভালুকা",
            rating = 4.4f,
            reviewCount = 18
        ),
        Product(
            id = "54",
            name = "অ্যাসিডোফিলাস ট্যাবলেট",
            description = "অ্যাসিডোফিলাস সমৃদ্ধ ট্যাবলেট, পাচনতন্ত্রের স্বাস্থ্যের জন্য।",
            price = 550.0,
            imageUrl = "https://picsum.photos/200/120?random=12",
            category = mockCategories[7],
            stock = 8,
            sellerName = "ডায়েট শপ",
            sellerContact = "+880 1717 890123",
            location = "ভালুকা",
            rating = 4.7f,
            reviewCount = 20
        ),
        Product(
            id = "55",
            name = "মাল্টিভিটামিন ট্যাবলেট",
            description = "কমপ্লিট মাল্টিভিটামিন সাপ্লিমেন্ট, দৈনিক ব্যবহারের জন্য।",
            price = 700.0,
            imageUrl = "https://picsum.photos/200/120?random=13",
            category = mockCategories[7],
            stock = 12,
            sellerName = "ভিটামিন স্টোর",
            sellerContact = "+880 1718 901234",
            location = "ভালুকা",
            rating = 4.8f,
            reviewCount = 35
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
        loadCategoryProducts()
    }

    fun onAction(action: CategoryProductsAction) {
        when (action) {
            is CategoryProductsAction.SortProducts -> sortProducts(action.sortOption)
            CategoryProductsAction.Refresh -> loadCategoryProducts()
        }
    }

    private fun loadCategoryProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Simulate network delay
            delay(500)

            try {
                val category = mockCategories.find { it.id == categoryId }
                val products = allProducts.filter { it.category.id == categoryId }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        category = category,
                        products = products
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "পণ্য লোড করতে সমস্যা হয়েছে"
                    )
                }
            }
        }
    }

    private fun sortProducts(sortOption: SortOption) {
        val sortedProducts = when (sortOption) {
            SortOption.NEWEST -> _uiState.value.products.sortedByDescending { it.createdAt }
            SortOption.PRICE_LOW_TO_HIGH -> _uiState.value.products.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> _uiState.value.products.sortedByDescending { it.price }
            SortOption.RATING -> _uiState.value.products.sortedByDescending { it.rating }
            SortOption.NAME -> _uiState.value.products.sortedBy { it.name }
        }

        _uiState.update { it.copy(products = sortedProducts) }
    }
}

// ViewModelFactory to pass categoryId
class CategoryProductsViewModelFactory(
    private val categoryId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryProductsViewModel::class.java)) {
            return CategoryProductsViewModel(categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
