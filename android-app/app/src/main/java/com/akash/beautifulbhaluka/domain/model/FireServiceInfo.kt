package com.akash.beautifulbhaluka.domain.model

data class FireServiceInfo(
    val title: String,
    val stations: List<FireStation>
)

data class FireStation(
    val avatarUrl: String,
    val stationName: String,
    val phoneNumbers: List<String>
)
