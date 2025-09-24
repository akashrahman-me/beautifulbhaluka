package com.akash.beautifulbhaluka.domain.model

data class EmergencyServiceInfo(
    val title: String,
    val services: List<EmergencyService>
)

data class EmergencyService(
    val avatarUrl: String?,
    val serviceName: String,
    val location: String,
    val phoneNumbers: List<String>
)
