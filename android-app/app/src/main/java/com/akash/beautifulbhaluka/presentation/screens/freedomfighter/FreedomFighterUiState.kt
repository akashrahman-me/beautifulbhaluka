package com.akash.beautifulbhaluka.presentation.screens.freedomfighter

data class FreedomFighterUiState(
    val isLoading: Boolean = false,
    val freedomFighters: List<FreedomFighter> = emptyList(),
    val error: String? = null
)

data class FreedomFighter(
    val serialNumber: String,
    val identificationNumber: String,
    val name: String,
    val fatherName: String,
    val village: String,
    val postOffice: String,
    val certificateDetails: String
)

sealed interface FreedomFighterAction {
    object LoadData : FreedomFighterAction
    object DownloadPdf : FreedomFighterAction
}
