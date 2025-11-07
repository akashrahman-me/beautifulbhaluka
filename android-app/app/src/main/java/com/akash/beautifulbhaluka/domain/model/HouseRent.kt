package com.akash.beautifulbhaluka.domain.model

data class HouseRent(
    val id: String,
    val title: String,
    val description: String,
    val monthlyRent: Double,
    val advancePayment: Double? = null,
    val imageUrls: List<String> = emptyList(),
    val propertyType: PropertyType,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double, // in square feet
    val floor: Int? = null,
    val totalFloors: Int? = null,
    val amenities: List<Amenity> = emptyList(),
    val location: String,
    val detailedAddress: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val ownerName: String,
    val ownerContact: String,
    val ownerWhatsApp: String? = null,
    val isAvailable: Boolean = true,
    val availableFrom: Long? = null,
    val isVerified: Boolean = false,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val isFeatured: Boolean = false,
    val isNegotiable: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class PropertyType(val displayName: String, val displayNameEn: String) {
    APARTMENT("ফ্ল্যাট", "Apartment"),
    HOUSE("বাড়ি", "House"),
    ROOM("রুম", "Room"),
    SUBLET("সাবলেট", "Sublet"),
    BACHELOR_MESS("ব্যাচেলর মেস", "Bachelor Mess"),
    FAMILY_FLAT("পরিবার ফ্ল্যাট", "Family Flat"),
    STUDIO("স্টুডিও", "Studio"),
    DUPLEX("ডুপ্লেক্স", "Duplex")
}

enum class Amenity(val displayName: String) {
    GARAGE("গ্যারেজ"),
    BALCONY("বারান্দা"),
    LIFT("লিফট"),
    GENERATOR("জেনারেটর"),
    GAS_LINE("গ্যাস লাইন"),
    WIFI("ওয়াইফাই"),
    FURNISHED("ফার্নিশড"),
    SEMI_FURNISHED("সেমি ফার্নিশড"),
    AC("এসি"),
    PARKING("পার্কিং"),
    SECURITY("সিকিউরিটি"),
    WATER_SUPPLY("পানি সরবরাহ"),
    PLAYGROUND("খেলার মাঠ"),
    GARDEN("বাগান")
}

data class RentCategory(
    val id: String,
    val name: String,
    val nameEn: String,
    val propertyType: PropertyType,
    val icon: Int,
    val propertyCount: Int = 0
)

enum class RentSortOption(val displayName: String) {
    NEWEST("নতুন আগে"),
    RENT_LOW_TO_HIGH("ভাড়া: কম থেকে বেশি"),
    RENT_HIGH_TO_LOW("ভাড়া: বেশি থেকে কম"),
    AREA_LARGE_TO_SMALL("এরিয়া: বড় থেকে ছোট"),
    RATING("রেটিং"),
    POPULAR("জনপ্রিয়")
}

data class PriceRange(
    val min: Double = 0.0,
    val max: Double = 100000.0
)

