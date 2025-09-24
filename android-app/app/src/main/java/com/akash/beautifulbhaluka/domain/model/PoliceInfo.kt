package com.akash.beautifulbhaluka.domain.model

data class PoliceInfo(
    val title: String,
    val stations: List<PoliceStation>
)

data class PoliceStation(
    val avatarUrl: Any?, // Supports String URL, Int drawable resource, or null
    val officerName: String,
    val location: String,
    val phoneNumbers: List<String>
)
