package com.akash.beautifulbhaluka.domain.model

data class UnionInfo(
    val title: String,
    val unions: List<UnionData>
)

data class UnionData(
    val title: String,
    val content: String
)
