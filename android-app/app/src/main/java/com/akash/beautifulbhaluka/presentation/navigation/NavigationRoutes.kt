package com.akash.beautifulbhaluka.presentation.navigation

object NavigationRoutes {
    // Main screens
    const val HOME = "home"

    // Tourism screens - with title parameter support
    const val PLACES = "places"
    const val ATTRACTIONS = "attractions"
    const val TOURS = "tours"
    const val TOURISM = "tourism"
    const val MAPS = "maps"
    const val GUIDES = "guides"

    // Accommodation & Services
    const val HOTELS = "hotels"
    const val ACCOMMODATION = "accommodation"
    const val HOUSE_RENT = "house_rent"
    const val HOUSE_RENT_DETAILS = "house_rent_details/{propertyId}"
    const val HOUSE_RENT_PUBLISH = "house_rent_publish"
    const val RESTAURANTS = "restaurants"
    const val FOOD = "food"
    const val SERVICES = "services"

    // Culture & Heritage
    const val CULTURE = "culture"
    const val HERITAGE = "heritage"
    const val MUSEUMS = "museums"
    const val FESTIVALS = "festivals"
    const val HISTORY = "history"

    // Activities & Entertainment
    const val EVENTS = "events"
    const val ACTIVITIES = "activities"
    const val ENTERTAINMENT = "entertainment"

    // Practical Information
    const val TRANSPORT = "transport"
    const val WEATHER = "weather"
    const val EMERGENCY = "emergency"
    const val DIRECTORY = "directory"

    // Commerce & Media
    const val SHOPPING = "shopping"
    const val GALLERY = "gallery"
    const val REVIEWS = "reviews"
    const val BOOKINGS = "bookings"
    const val NEWS = "news"

    // Buy-Sell Marketplace
    const val BUY_SELL = "buy_sell"
    const val BUY_SELL_DETAILS = "buy_sell_details/{itemId}"
    const val BUY_SELL_PUBLISH = "buy_sell_publish"

    // Matchmaking & Marriage Platform
    const val MATCHMAKING = "matchmaking"
    const val MATCHMAKING_DETAILS = "matchmaking_details/{profileId}"
    const val MATCHMAKING_PUBLISH = "matchmaking_publish"
    const val MANAGE_MATCHMAKING_PROFILES = "manage_matchmaking_profiles"

    // Matchmaker (ঘটক) Routes
    const val MATCHMAKER_DETAILS = "matchmaker_details/{matchmakerId}"
    const val MATCHMAKER_PUBLISH = "matchmaker_publish"
    const val MANAGE_MATCHMAKER_PROFILES = "manage_matchmaker_profiles"

    // Existing screens
    const val JOBS = "jobs"
    const val PUBLISH_JOB = "publish_job"

    // Pattern route with argument placeholder
    const val JOB_DETAILS = "job_details/{jobId}"
    const val SHOPS = "shops"
    const val PRODUCT_DETAILS = "product_details/{productId}"
    const val PRODUCT_PUBLISH = "product_publish"
    const val SOCIAL = "social"
    const val SETTINGS = "settings"
    const val ABOUT = "about"

    // Administrative levels
    const val UPAZILA = "upazila"
    const val POURASHAVA = "pourashava"
    const val UNION = "union"

    // Emergency Services
    const val FIRE_SERVICE = "fire_service"
    const val POLICE = "police"
    const val AMBULANCE = "ambulance"
    const val BLOOD_BANK = "blood_bank"
    const val BLOOD_DONATION_GUIDELINES = "blood_donation_guidelines"
    const val BLOOD_BANK_PUBLISH = "blood_bank_publish"
    const val BLOOD_BANK_MANAGE = "blood_bank_manage"

    // Healthcare Services
    const val DOCTOR = "doctor"
    const val HOSPITAL = "hospital"

    // Legal & Administrative Services
    const val LAWYER = "lawyer"
    const val UPAZILA_ADMIN = "upazila_admin"
    const val VOTER_LIST = "voter_list"
    const val KAZI_OFFICE = "kazi_office"

    // Educational Services
    const val SCHOOL_COLLEGE = "school_college"
    const val TUITION = "tuition"

    // Financial Services
    const val BANK = "bank"
    const val COURIER = "courier"

    // Utility Services
    const val BROADBAND = "broadband"
    const val ELECTRICITY = "electricity"
    const val CLEANER = "cleaner"

    // Beauty & Wellness
    const val LADIES_PARLOUR = "ladies_parlour"
    const val GENTS_PARLOUR = "gents_parlour"
    const val GYM = "gym"

    // Professional Services
    const val CRAFTSMAN = "craftsman"
    const val BUTCHER_COOK = "butcher_cook"
    const val PRESS_GRAPHICS = "press_graphics"
    const val CYBER_EXPERT = "cyber_expert"
    const val CAR_RENT = "car_rent"
    const val AIR_TRAVEL = "air_travel"

    // Information Services
    const val FAMOUS_PERSON = "famous_person"
    const val FREEDOM_FIGHTER = "freedom_fighter"
    const val ACHIEVER = "achiever"
    const val CALCULATOR = "calculator"

    // Menu route for bottombar
    const val MENU = "menu"

    // Drawer routes
    const val PROFILE = "profile"
    const val NOTIFICATIONS = "notifications"
    const val HELP = "help"

    // Helper function to create route with title parameter
    fun createRouteWithTitle(baseRoute: String, title: String): String {
        return "$baseRoute?title=${java.net.URLEncoder.encode(title, "UTF-8")}"
    }

    // Helper builder for concrete detail routes (avoids hardcoding pattern everywhere)
    fun jobDetails(jobId: String) = "job_details/$jobId"
    fun productDetails(productId: String) = "product_details/$productId"
    fun buySellDetails(itemId: String) = "buy_sell_details/$itemId"
    fun matchmakingDetails(profileId: String) = "matchmaking_details/$profileId"
    fun matchmakerDetails(matchmakerId: String) = "matchmaker_details/$matchmakerId"
}
