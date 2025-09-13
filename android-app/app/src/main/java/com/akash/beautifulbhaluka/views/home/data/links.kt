package com.akash.beautifulbhaluka.views.home.data

import com.akash.beautifulbhaluka.R

data class LinkItem(
    val link: String,
    val icon: Int,
    val label: String
)

data class LinkSection(
    val name: String,
    val values: List<LinkItem>
)

val linkSections = listOf(
    LinkSection(
        name = "ভালুকার পরিচিতি",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.government_seal_of_bangladesh,
                label = "উপজেলা"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.government_seal_of_bangladesh,
                label = "পৌরসভা"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.government_seal_of_bangladesh,
                label = "ইউনিয়ন"
            )
        )
    ),
    LinkSection(
        name = "জরুরি সেবা",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.a364617,
                label = "ফায়ার সার্ভিস"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.b6998114,
                label = "থানা পুলিশ"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.government_seal_of_bangladesh,
                label = "উপজেলা প্রশাসন"
            )
        )
    ),
    LinkSection(
        name = "স্বাস্থ্য সেবা",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.a1735056863183,
                label = "ডাক্তার"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.ambulance,
                label = "এ্যাম্বুলেন্স"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.r1735380971650,
                label = "হাসপাতাল"
            )
        )
    ),
    LinkSection(
        name = "জনসেবা",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.y1735022072408,
                label = "সাংবাদিক"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e1735317705899,
                label = "আইনজীবী"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e1735270158766,
                label = "প্রসিদ্ধ ব্যক্তি"
            )
        )
    ),
    LinkSection(
        name = "প্রয়োজনীয় তালিকা",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.e1735580616030,
                label = "মুক্তিযোদ্ধার তালিকা"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e234987,
                label = "ভোটার তালিকা"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.w1735381317722,
                label = "স্কুল কলেজ"
            )
        )
    ),
    LinkSection(
        name = "ভ্রমণ ও বাসস্থান",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.w1735058108543,
                label = "দর্শনীয় স্থান"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.w242452,
                label = "রেস্টুরেন্ট"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.q235889,
                label = "আবাসিক হোটেল"
            )
        )
    ),
    LinkSection(
        name = "ভকুরিয়ার ও ব্যাংক",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.w1735059362286,
                label = "কুরিয়ার সার্ভিস"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e2349892387,
                label = "ব্যাংক"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e2309439,
                label = "গাড়ি ভাড়া"
            )
        )
    ),
    LinkSection(
        name = "গ্রাহক সেবা",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.e28438742398,
                label = "ব্রডব্যান্ড সার্ভিস"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.w39842384,
                label = "পরিচ্ছন্নতা কর্মী"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.w1735483114815,
                label = "বিদ্যুৎ"
            )
        )
    ),
    LinkSection(
        name = "বিউটি এন্ড ফিট",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.fitness,
                label = "জিম"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.make_up,
                label = "লেডিস পার্লার"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.t1735417766060,
                label = "জেন্টস পার্লার"
            )
        )
    ),
    LinkSection(
        name = "অফিস ও হাটবাজার",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.u1735483433122,
                label = "অফিস"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.y1735199734348,
                label = "কাজী অফিস"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.i1735483162735,
                label = "হাট বাজার"
            )
        )
    ),
    LinkSection(
        name = "এয়ার ট্রাভেলস্ এন্ড মিস্ত্রি",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.y3240923984,
                label = "কসাই ও বাবুর্চি"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.o206854,
                label = "সকল মিস্ত্রি"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.plane,
                label = "এয়ার ট্রাভেল"
            )
        )
    ),
    LinkSection(
        name = "ডিজাইন ও টিউশন ও ক্যালকুলেটর",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.pgivon,
                label = "প্রেস ও গ্রাফিক্স"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.tuition_2,
                label = "টিউশন"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e20250820_083505,
                label = "ক্যাশআউট চার্জ ক্যালকুলেটর"
            )
        )
    ),
    LinkSection(
        name = "আইটি ও বাদ্যযন্ত্র",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.r248932487,
                label = "Cyber Expert"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.w20250203_235236,
                label = "ভিডিও"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.drum,
                label = "ব্যান্ডপার্টি ও সাউন্ড সিস্টেম"
            )
        )
    ),
    LinkSection(
        name = "বিবাহ",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.p284234908328,
                label = "পাত্রপাত্রী"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.i9934823984,
                label = "বাসা ভাড়া"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.p234098230984,
                label = "কাজী অফিস"
            )
        )
    ),
    LinkSection(
        name = "ভালুকার গর্ব",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.e20250830_041338,
                label = "কৃতি সন্তান"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.e1757344880741,
                label = "মাদক নির্মুল"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.i1735270158766,
                label = "প্রসিদ্ধ ব্যক্তি"
            )
        )
    ),
    LinkSection(
        name = "ব্লাড, ক্রয়বিক্রয় ও চাকরি",
        values = listOf(
            LinkItem(
                link = "",
                icon = R.drawable.u1735794593270,
                label = "ব্লাড ব্যাংক"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.shopping_cart,
                label = "ক্রয়বিক্রয়"
            ),
            LinkItem(
                link = "",
                icon = R.drawable.u1736314,
                label = "চাকরি"
            )
        )
    )
)
