package com.akash.beautifulbhaluka.domain.model

data class PourashavaInfo(
    val title: String,
    val sections: List<PourashavaSection>,
    val wardsTable: WardsTable
)

data class PourashavaSection(
    val title: String,
    val content: String
)

data class WardsTable(
    val title: String,
    val wards: List<Ward>
)

data class Ward(
    val number: String,
    val areas: String
)
