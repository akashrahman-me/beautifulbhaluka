package com.akash.beautifulbhaluka.domain.model

data class AmbulanceInfo(
    val title: String,
    val organization: String? = null,
    val phone: String? = null,
    val phones: List<String>? = null,
    val image: String
) {
    val phoneNumbers: List<String>
        get() = when {
            phones != null -> phones
            phone != null -> listOf(phone)
            else -> emptyList()
        }
}
