package com.akash.beautifulbhaluka.presentation.screens.home

import com.akash.beautifulbhaluka.presentation.navigation.NavigationRoutes
import com.akash.beautifulbhaluka.R

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val carouselItems: List<CarouselItem> = getDefaultCarouselItems(),
    val linkSections: List<LinkSection> = getDefaultLinkSections()
)

data class CarouselItem(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String
)

data class LinkSection(
    val name: String,
    val values: List<LinkItem>
)

data class LinkItem(
    val id: String,
    val title: String,
    val icon: Int, // Changed to Int to represent drawable resource ID
    val route: String,
    val description: String = ""
)

sealed class HomeAction {
    object LoadData : HomeAction()
    data class NavigateToLink(val link: LinkItem) : HomeAction()
}

private fun getDefaultLinkSections(): List<LinkSection> {
    return listOf(
        LinkSection(
            name = "ভালুকার পরিচিতি",
            values = listOf(
                LinkItem(
                    id = "upazila",
                    title = "উপজেলা",
                    icon = R.drawable.government_seal_of_bangladesh,
                    route = NavigationRoutes.UPAZILA,
                    description = "উপজেলা প্রশাসন"
                ),
                LinkItem(
                    id = "pourashava",
                    title = "পৌরসভা",
                    icon = R.drawable.government_seal_of_bangladesh,
                    route = NavigationRoutes.POURASHAVA,
                    description = "পৌরসভা তথ্য"
                ),
                LinkItem(
                    id = "union",
                    title = "ইউনিয়ন",
                    icon = R.drawable.government_seal_of_bangladesh,
                    route = NavigationRoutes.UNION,
                    description = "ইউনিয়ন পরিষদ"
                )
            )
        ),
        LinkSection(
            name = "জরুরি সেবা",
            values = listOf(
                LinkItem(
                    id = "fire_service",
                    title = "ফায়ার সার্ভিস",
                    icon = R.drawable.a364617,
                    route = NavigationRoutes.FIRE_SERVICE,
                    description = "দমকল বাহিনী"
                ),
                LinkItem(
                    id = "police",
                    title = "থানা পুলিশ",
                    icon = R.drawable.b6998114,
                    route = NavigationRoutes.POLICE,
                    description = "পুলিশ সেবা"
                ),
                LinkItem(
                    id = "upazila_admin",
                    title = "উপজেলা প্রশাসন",
                    icon = R.drawable.government_seal_of_bangladesh,
                    route = NavigationRoutes.UPAZILA_ADMIN,
                    description = "প্রশাসনিক সেবা"
                )
            )
        ),
        LinkSection(
            name = "স্বাস্থ্য সেবা",
            values = listOf(
                LinkItem(
                    id = "doctor",
                    title = "ডাক্তার",
                    icon = R.drawable.a1735056863183,
                    route = NavigationRoutes.DOCTOR,
                    description = "চিকিৎসক তালিকা"
                ),
                LinkItem(
                    id = "ambulance",
                    title = "এ্যাম্বুলেন্স",
                    icon = R.drawable.ambulance,
                    route = NavigationRoutes.AMBULANCE,
                    description = "অ্যাম্বুলেন্স সেবা"
                ),
                LinkItem(
                    id = "hospital",
                    title = "হাসপাতাল",
                    icon = R.drawable.r1735380971650,
                    route = NavigationRoutes.HOSPITAL,
                    description = "হাসপাতাল ও ক্লিনিক"
                )
            )
        ),
        LinkSection(
            name = "জনসেবা",
            values = listOf(
                LinkItem(
                    id = "news",
                    title = "সংবাদ",
                    icon = R.drawable.y1735022072408,
                    route = NavigationRoutes.NEWS,
                    description = "খবর ও সংবাদ"
                ),
                LinkItem(
                    id = "book_buddy",
                    title = "Book Buddy",
                    icon = R.drawable.y1735022072408,
                    route = NavigationRoutes.BOOK_BUDDY,
                    description = "গল্প, কবিতা, উপন্যাস"
                ),
                LinkItem(
                    id = "lawyer",
                    title = "আইনজীবী",
                    icon = R.drawable.e1735317705899,
                    route = NavigationRoutes.LAWYER,
                    description = "আইনি সেবা"
                ),
                LinkItem(
                    id = "famous_person",
                    title = "প্রসিদ্ধ ব্যক্তি",
                    icon = R.drawable.e1735270158766,
                    route = NavigationRoutes.FAMOUS_PERSON,
                    description = "বিশিষ্ট ব্যক্তিত্ব"
                )
            )
        ),
        LinkSection(
            name = "প্রয়োজনীয় তালিকা",
            values = listOf(
                LinkItem(
                    id = "freedom_fighter",
                    title = "মুক্তিযোদ্ধার তালিকা",
                    icon = R.drawable.e1735580616030,
                    route = NavigationRoutes.FREEDOM_FIGHTER,
                    description = "মুক্তিযোদ্ধা তালিকা"
                ),
                LinkItem(
                    id = "voter_list",
                    title = "ভোটার তালিকা",
                    icon = R.drawable.e234987,
                    route = NavigationRoutes.VOTER_LIST,
                    description = "ভোটার তালিকা"
                ),
                LinkItem(
                    id = "school_college",
                    title = "স্কুল কলেজ",
                    icon = R.drawable.w1735381317722,
                    route = NavigationRoutes.SCHOOL_COLLEGE,
                    description = "শিক্ষা প্রতিষ্ঠান"
                )
            )
        ),
        LinkSection(
            name = "ভ্রমণ ও বাসস্থান",
            values = listOf(
                LinkItem(
                    id = "tourist_spots",
                    title = "দর্শনীয় স্থান",
                    icon = R.drawable.w1735058108543,
                    route = NavigationRoutes.PLACES,
                    description = "পর্যটন স্থান"
                ),
                LinkItem(
                    id = "restaurant",
                    title = "রেস্টুরেন্ট",
                    icon = R.drawable.w242452,
                    route = NavigationRoutes.RESTAURANTS,
                    description = "খাবারের দোকান"
                ),
                LinkItem(
                    id = "hotel",
                    title = "আবাসিক হোটেল",
                    icon = R.drawable.q235889,
                    route = NavigationRoutes.HOTELS,
                    description = "থাকার ব্যবস্থা"
                )
            )
        ),
        LinkSection(
            name = "কুরিয়ার ও ব্যাংক",
            values = listOf(
                LinkItem(
                    id = "courier",
                    title = "কুরিয়ার সার্ভিস",
                    icon = R.drawable.w1735059362286,
                    route = NavigationRoutes.COURIER,
                    description = "কুরিয়ার সেবা"
                ),
                LinkItem(
                    id = "bank",
                    title = "ব্যাংক",
                    icon = R.drawable.e2349892387,
                    route = NavigationRoutes.BANK,
                    description = "ব্যাংকিং সেবা"
                ),
                LinkItem(
                    id = "car_rent",
                    title = "গাড়ি ভাড়া",
                    icon = R.drawable.e2309439,
                    route = NavigationRoutes.CAR_RENT,
                    description = "যানবাহন ভাড়া"
                )
            )
        ),
        LinkSection(
            name = "গ্রাহক সেবা",
            values = listOf(
                LinkItem(
                    id = "broadband",
                    title = "ব্রডব্যান্ড সার্ভিস",
                    icon = R.drawable.e28438742398,
                    route = NavigationRoutes.BROADBAND,
                    description = "ইন্টারনেট সেবা"
                ),
                LinkItem(
                    id = "cleaner",
                    title = "পরিচ্ছন্নতা কর্মী",
                    icon = R.drawable.w39842384,
                    route = NavigationRoutes.CLEANER,
                    description = "পরিচ্ছন্নতা সেবা"
                ),
                LinkItem(
                    id = "electricity",
                    title = "বিদ্যুৎ",
                    icon = R.drawable.w1735483114815,
                    route = NavigationRoutes.ELECTRICITY,
                    description = "বিদ্যুৎ সেবা"
                )
            )
        ),
        LinkSection(
            name = "বিউটি এন্ড ফিট",
            values = listOf(
                LinkItem(
                    id = "gym",
                    title = "জিম",
                    icon = R.drawable.fitness,
                    route = NavigationRoutes.GYM,
                    description = "ফিটনেস সেন্টার"
                ),
                LinkItem(
                    id = "ladies_parlour",
                    title = "লেডিস পার্লার",
                    icon = R.drawable.make_up,
                    route = NavigationRoutes.LADIES_PARLOUR,
                    description = "মহিলা সৌন্দর্য চর্চা"
                ),
                LinkItem(
                    id = "gents_parlour",
                    title = "জেন্টস পার্লার",
                    icon = R.drawable.t1735417766060,
                    route = NavigationRoutes.GENTS_PARLOUR,
                    description = "পুরুষ সৌন্দর্য চর্চা"
                )
            )
        ),
        LinkSection(
            name = "অফিস ও হাটবাজার",
            values = listOf(
                LinkItem(
                    id = "office",
                    title = "অফিস",
                    icon = R.drawable.u1735483433122,
                    route = NavigationRoutes.DIRECTORY,
                    description = "কর্মক্ষেত্র"
                ),
                LinkItem(
                    id = "kazi_office",
                    title = "কাজী অফিস",
                    icon = R.drawable.y1735199734348,
                    route = NavigationRoutes.KAZI_OFFICE,
                    description = "বিবাহ নিবন্ধন"
                ),
                LinkItem(
                    id = "market",
                    title = "হাট বাজার",
                    icon = R.drawable.i1735483162735,
                    route = NavigationRoutes.SHOPPING,
                    description = "বাজার সমূহ"
                )
            )
        ),
        LinkSection(
            name = "এয়ার ট্রাভেল ও মিস্ত্রি",
            values = listOf(
                LinkItem(
                    id = "butcher_cook",
                    title = "কসাই ও বাবুর্চি",
                    icon = R.drawable.y3240923984,
                    route = NavigationRoutes.BUTCHER_COOK,
                    description = "রান্নার সেবা"
                ),
                LinkItem(
                    id = "craftsman",
                    title = "সকল মিস্ত্রি",
                    icon = R.drawable.o206854,
                    route = NavigationRoutes.CRAFTSMAN,
                    description = "কারিগরি সেবা"
                ),
                LinkItem(
                    id = "air_travel",
                    title = "এয়ার ট্রাভেল",
                    icon = R.drawable.plane,
                    route = NavigationRoutes.AIR_TRAVEL,
                    description = "বিমান যাত্রা"
                )
            )
        ),
        LinkSection(
            name = "ডিজাইন ও টিউশন ও ক্যালকুলেটর",
            values = listOf(
                LinkItem(
                    id = "press_graphics",
                    title = "প্রেস ও গ্রাফিক্স",
                    icon = R.drawable.pgivon,
                    route = NavigationRoutes.PRESS_GRAPHICS,
                    description = "ডিজাইন সেবা"
                ),
                LinkItem(
                    id = "tuition",
                    title = "টিউশন",
                    icon = R.drawable.tuition_2,
                    route = NavigationRoutes.TUITION,
                    description = "শিক্ষাদান সেবা"
                ),
                LinkItem(
                    id = "calculator",
                    title = "ক্যাশআউট চার্জ ক্যালকুলেটর",
                    icon = R.drawable.e20250820_083505,
                    route = NavigationRoutes.CALCULATOR,
                    description = "চার্জ গণনা"
                )
            )
        ),
        LinkSection(
            name = "আইটি ও বাদ্যযন্ত্র",
            values = listOf(
                LinkItem(
                    id = "cyber_expert",
                    title = "Cyber Expert",
                    icon = R.drawable.r248932487,
                    route = NavigationRoutes.CYBER_EXPERT,
                    description = "কম্পিউটার সেবা"
                ),
                LinkItem(
                    id = "video",
                    title = "ভিডিও",
                    icon = R.drawable.w20250203_235236,
                    route = NavigationRoutes.GALLERY,
                    description = "ভিডিও সেবা"
                ),
                LinkItem(
                    id = "band_party",
                    title = "ব্যান্ডপার্টি ও সাউন্ড সিস্টেম",
                    icon = R.drawable.drum,
                    route = NavigationRoutes.ENTERTAINMENT,
                    description = "বিনোদন সেবা"
                )
            )
        ),
        LinkSection(
            name = "বিবাহ",
            values = listOf(
                LinkItem(
                    id = "marriage",
                    title = "পাত্রপাত্রী",
                    icon = R.drawable.p284234908328,
                    route = NavigationRoutes.SOCIAL,
                    description = "বিবাহের জন্য"
                ),
                LinkItem(
                    id = "house_rent",
                    title = "বাসা ভাড়া",
                    icon = R.drawable.i9934823984,
                    route = NavigationRoutes.HOUSE_RENT,
                    description = "ভাড়া বাসা"
                ),
                LinkItem(
                    id = "kazi_office2",
                    title = "কাজী অফিস",
                    icon = R.drawable.p234098230984,
                    route = NavigationRoutes.KAZI_OFFICE,
                    description = "বিবাহ নিবন্ধন"
                )
            )
        ),
        LinkSection(
            name = "ভালুকার গর্ব",
            values = listOf(
                LinkItem(
                    id = "achiever",
                    title = "কৃতি সন্তান",
                    icon = R.drawable.e20250830_041338,
                    route = NavigationRoutes.ACHIEVER,
                    description = "সফল ব্যক্তিত্ব"
                ),
                LinkItem(
                    id = "anti_drug",
                    title = "মাদক নির্মুল",
                    icon = R.drawable.e1757344880741,
                    route = NavigationRoutes.SOCIAL,
                    description = "মাদকবিরোধী"
                ),
                LinkItem(
                    id = "famous_person2",
                    title = "প্রসিদ্ধ ব্যক্তি",
                    icon = R.drawable.i1735270158766,
                    route = NavigationRoutes.FAMOUS_PERSON,
                    description = "বিশিষ্ট ব্যক্তিত্ব"
                )
            )
        ),
        LinkSection(
            name = "ব্লাড, ক্রয়বিক্রয় ও চাকরি",
            values = listOf(
                LinkItem(
                    id = "blood_bank",
                    title = "ব্লাড ব্যাংক",
                    icon = R.drawable.u1735794593270,
                    route = NavigationRoutes.BLOOD_BANK,
                    description = "রক্ত দান সেবা"
                ),
                LinkItem(
                    id = "buy_sell",
                    title = "ক্রয়বিক্রয়",
                    icon = R.drawable.shopping_cart,
                    route = NavigationRoutes.SHOPS,
                    description = "কিনাকাটা"
                ),
                LinkItem(
                    id = "jobs",
                    title = "চাকরি",
                    icon = R.drawable.u1736314,
                    route = NavigationRoutes.JOBS,
                    description = "কর্মসংস্থান"
                )
            )
        )
    )
}

private fun getDefaultCarouselItems(): List<CarouselItem> {
    return listOf(
        CarouselItem(
            id = "carousel_1",
            title = "ভালুকায় স্বাগতম",
            description = "ময়মনসিংহের ঐতিহ্যবাহী উপজেলা",
            imageUrl = "https://picsum.photos/seed/bhaluka1/800/400"
        ),
        CarouselItem(
            id = "carousel_2",
            title = "পর্যটন ও দর্শনীয় স্থান",
            description = "প্রাকৃতিক সৌন্দর্যে ভরপুর",
            imageUrl = "https://picsum.photos/seed/bhaluka2/800/400"
        ),
        CarouselItem(
            id = "carousel_3",
            title = "শিক্ষা ও সংস্কৃতি",
            description = "জ্ঞান ও ঐতিহ্যের কেন্দ্র",
            imageUrl = "https://picsum.photos/seed/bhaluka3/800/400"
        ),
        CarouselItem(
            id = "carousel_4",
            title = "ব্যবসা ও বাণিজ্য",
            description = "স্থানীয় অর্থনীতির হৃদয়",
            imageUrl = "https://picsum.photos/seed/bhaluka4/800/400"
        ),
        CarouselItem(
            id = "carousel_5",
            title = "সামাজিক সেবা",
            description = "সকলের জন্য উন্নত সেবা",
            imageUrl = "https://picsum.photos/seed/bhaluka5/800/400"
        )
    )
}

