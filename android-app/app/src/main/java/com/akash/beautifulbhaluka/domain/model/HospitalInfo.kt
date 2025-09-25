package com.akash.beautifulbhaluka.domain.model

data class HospitalInfo(
    val title: String,
    val address: String? = null,
    val phones: List<String>,
    val image: String
)
