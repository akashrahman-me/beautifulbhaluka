package com.akash.beautifulbhaluka.domain.model

data class UpazilaInfo(
    val title: String,
    val description: String,
    val imageUrl: String,
    val sections: List<UpazilaSection>
)

data class UpazilaSection(
    val title: String,
    val content: String
)
