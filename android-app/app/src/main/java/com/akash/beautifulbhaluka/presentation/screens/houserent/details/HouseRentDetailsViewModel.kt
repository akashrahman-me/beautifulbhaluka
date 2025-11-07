package com.akash.beautifulbhaluka.presentation.screens.houserent.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akash.beautifulbhaluka.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class HouseRentDetailsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HouseRentDetailsUiState())
    val uiState: StateFlow<HouseRentDetailsUiState> = _uiState.asStateFlow()

    // Mock data - same as in HouseRentViewModel
    private val mockProperties = listOf(
        HouseRent(
            id = "1",
            title = "আধুনিক ৩ বেডরুমের ফ্ল্যাট",
            description = "সুন্দর এবং পরিচ্ছন্ন ৩ বেডরুমের ফ্ল্যাট। সব ধরনের সুবিধা সহ। পরিবারের জন্য আদর্শ। এই ফ্ল্যাটটি একটি আধুনিক আবাসিক ভবনে অবস্থিত এবং সব ধরনের নিরাপত্তা ব্যবস্থা রয়েছে। কাছাকাছি স্কুল, কলেজ, হাসপাতাল এবং বাজার রয়েছে।",
            monthlyRent = 15000.0,
            advancePayment = 30000.0,
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=1",
                "https://picsum.photos/400/300?random=2",
                "https://picsum.photos/400/300?random=3"
            ),
            propertyType = PropertyType.APARTMENT,
            bedrooms = 3,
            bathrooms = 2,
            area = 1200.0,
            floor = 4,
            totalFloors = 6,
            amenities = listOf(
                Amenity.LIFT,
                Amenity.GAS_LINE,
                Amenity.PARKING,
                Amenity.SECURITY,
                Amenity.WATER_SUPPLY,
                Amenity.GENERATOR
            ),
            location = "ভালুকা বাজার",
            detailedAddress = "রোড নং ৫, ভালুকা বাজার, ময়মনসিংহ",
            ownerName = "মোঃ করিম উদ্দিন",
            ownerContact = "+880 1711-123456",
            ownerWhatsApp = "+880 1711-123456",
            isVerified = true,
            rating = 4.5f,
            reviewCount = 12,
            isFeatured = true,
            isNegotiable = true
        ),
        HouseRent(
            id = "2",
            title = "সুন্দর ২ বেডরুমের বাড়ি",
            description = "শান্তিপূর্ণ এলাকায় সুন্দর ২ বেডরুমের বাড়ি। বাগান এবং গ্যারেজ সহ। পরিবেশ খুবই ভালো এবং নিরাপদ।",
            monthlyRent = 12000.0,
            advancePayment = 24000.0,
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=3",
                "https://picsum.photos/400/300?random=4"
            ),
            propertyType = PropertyType.HOUSE,
            bedrooms = 2,
            bathrooms = 2,
            area = 1500.0,
            amenities = listOf(
                Amenity.GARAGE,
                Amenity.GARDEN,
                Amenity.GAS_LINE,
                Amenity.WATER_SUPPLY
            ),
            location = "বালিয়া বাজার",
            detailedAddress = "বালিয়া বাজার, ভালুকা, ময়মনসিংহ",
            ownerName = "আব্দুল মান্নান",
            ownerContact = "+880 1712-234567",
            isVerified = true,
            rating = 4.3f,
            reviewCount = 8,
            isFeatured = true
        ),
        HouseRent(
            id = "3",
            title = "ব্যাচেলর মেস - সিঙ্গেল রুম",
            description = "কলেজ/বিশ্ববিদ্যালয় ছাত্রদের জন্য আদর্শ। ওয়াইফাই এবং সব সুবিধা সহ। পড়াশোনার জন্য উপযুক্ত পরিবেশ।",
            monthlyRent = 4000.0,
            imageUrls = listOf("https://picsum.photos/400/300?random=4"),
            propertyType = PropertyType.BACHELOR_MESS,
            bedrooms = 1,
            bathrooms = 1,
            area = 250.0,
            floor = 2,
            totalFloors = 3,
            amenities = listOf(Amenity.WIFI, Amenity.FURNISHED, Amenity.WATER_SUPPLY),
            location = "কলেজ রোড",
            detailedAddress = "কলেজ রোড, ভালুকা, ময়মনসিংহ",
            ownerName = "রফিকুল ইসলাম",
            ownerContact = "+880 1713-345678",
            rating = 4.0f,
            reviewCount = 15,
            isNegotiable = true
        ),
        HouseRent(
            id = "4",
            title = "ডুপ্লেক্স হাউস - লাক্সারি",
            description = "অত্যাধুনিক সুবিধা সহ বিলাসবহুল ডুপ্লেক্স বাড়ি। বড় পরিবারের জন্য উপযুক্ত। সব ধরনের আধুনিক সুবিধা রয়েছে।",
            monthlyRent = 35000.0,
            advancePayment = 70000.0,
            imageUrls = listOf(
                "https://picsum.photos/400/300?random=5",
                "https://picsum.photos/400/300?random=6",
                "https://picsum.photos/400/300?random=7"
            ),
            propertyType = PropertyType.DUPLEX,
            bedrooms = 5,
            bathrooms = 4,
            area = 3000.0,
            totalFloors = 2,
            amenities = listOf(
                Amenity.GARAGE,
                Amenity.LIFT,
                Amenity.GENERATOR,
                Amenity.GAS_LINE,
                Amenity.WIFI,
                Amenity.FURNISHED,
                Amenity.AC,
                Amenity.PARKING,
                Amenity.SECURITY,
                Amenity.GARDEN
            ),
            location = "ভালুকা শহর",
            detailedAddress = "আধুনিক আবাসিক এলাকা, ভালুকা",
            ownerName = "আহমেদ হোসেন",
            ownerContact = "+880 1714-456789",
            ownerWhatsApp = "+880 1714-456789",
            isVerified = true,
            rating = 4.8f,
            reviewCount = 20,
            isFeatured = true
        ),
        HouseRent(
            id = "5",
            title = "সাবলেট - ১ বেডরুম",
            description = "তাড়াতাড়ি ছাড়তে হবে। সব কিছু ঠিক আছে। দ্রুত যোগাযোগ করুন।",
            monthlyRent = 8000.0,
            advancePayment = 8000.0,
            imageUrls = listOf("https://picsum.photos/400/300?random=7"),
            propertyType = PropertyType.SUBLET,
            bedrooms = 1,
            bathrooms = 1,
            area = 600.0,
            floor = 3,
            totalFloors = 5,
            amenities = listOf(Amenity.SEMI_FURNISHED, Amenity.GAS_LINE),
            location = "স্টেশন রোড",
            detailedAddress = "স্টেশন রোড, ভালুকা",
            ownerName = "শাহিন আলম",
            ownerContact = "+880 1715-567890",
            rating = 3.8f,
            reviewCount = 5,
            isNegotiable = true
        ),
        HouseRent(
            id = "6",
            title = "ফ্যামিলি ফ্ল্যাট - ২ বেড",
            description = "পরিবারের জন্য উপযুক্ত সুন্দর ফ্ল্যাট। নিরাপদ এবং শান্ত এলাকা।",
            monthlyRent = 10000.0,
            advancePayment = 20000.0,
            imageUrls = listOf("https://picsum.photos/400/300?random=8"),
            propertyType = PropertyType.FAMILY_FLAT,
            bedrooms = 2,
            bathrooms = 2,
            area = 900.0,
            floor = 2,
            totalFloors = 4,
            amenities = listOf(
                Amenity.BALCONY,
                Amenity.GAS_LINE,
                Amenity.PARKING,
                Amenity.WATER_SUPPLY
            ),
            location = "হাসপাতাল রোড",
            detailedAddress = "হাসপাতাল রোড, ভালুকা, ময়মনসিংহ",
            ownerName = "নাসিমা বেগম",
            ownerContact = "+880 1716-678901",
            isVerified = true,
            rating = 4.2f,
            reviewCount = 10,
            isFeatured = false
        ),
        HouseRent(
            id = "7",
            title = "স্টুডিও অ্যাপার্টমেন্ট",
            description = "আধুনিক এবং কমপ্যাক্ট স্টুডিও অ্যাপার্টমেন্ট। একক ব্যক্তির জন্য আদর্শ।",
            monthlyRent = 6500.0,
            advancePayment = 13000.0,
            imageUrls = listOf("https://picsum.photos/400/300?random=9"),
            propertyType = PropertyType.STUDIO,
            bedrooms = 1,
            bathrooms = 1,
            area = 400.0,
            floor = 5,
            totalFloors = 8,
            amenities = listOf(Amenity.FURNISHED, Amenity.WIFI, Amenity.LIFT, Amenity.SECURITY),
            location = "বাস স্ট্যান্ড এলাকা",
            detailedAddress = "বাস স্ট্যান্ড রোড, ভালুকা",
            ownerName = "তানভীর রহমান",
            ownerContact = "+880 1717-789012",
            ownerWhatsApp = "+880 1717-789012",
            rating = 4.4f,
            reviewCount = 7,
            isFeatured = true
        ),
        HouseRent(
            id = "8",
            title = "শেয়ারিং রুম - ছাত্রদের জন্য",
            description = "২ জন ছাত্রের জন্য শেয়ারিং রুম। সব ধরনের সুবিধা সহ।",
            monthlyRent = 3000.0,
            imageUrls = listOf("https://picsum.photos/400/300?random=10"),
            propertyType = PropertyType.ROOM,
            bedrooms = 1,
            bathrooms = 1,
            area = 200.0,
            floor = 1,
            amenities = listOf(Amenity.WIFI, Amenity.WATER_SUPPLY),
            location = "কলেজ পাড়া",
            detailedAddress = "কলেজ পাড়া, ভালুকা",
            ownerName = "জামাল উদ্দিন",
            ownerContact = "+880 1718-890123",
            rating = 3.9f,
            reviewCount = 18,
            isNegotiable = true
        )
    )

    fun loadPropertyDetails(propertyId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            delay(500) // Simulate network delay

            val property = mockProperties.find { it.id == propertyId }
            if (property != null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    property = property
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "সম্পত্তি পাওয়া যায়নি"
                )
            }
        }
    }

    fun onAction(action: HouseRentDetailsAction) {
        when (action) {
            is HouseRentDetailsAction.ToggleFavorite -> {
                _uiState.value = _uiState.value.copy(
                    isFavorite = !_uiState.value.isFavorite
                )
            }

            is HouseRentDetailsAction.Share -> {
                // Handle share action
            }

            is HouseRentDetailsAction.Call -> {
                // Handle call action
            }

            is HouseRentDetailsAction.WhatsApp -> {
                // Handle WhatsApp action
            }
        }
    }
}

