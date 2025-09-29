package com.akash.beautifulbhaluka.presentation.screens.bank

data class BankUiState(
    val isLoading: Boolean = false,
    val banks: List<Bank> = emptyList(),
    val error: String? = null
)

data class Bank(
    val name: String,
    val thumbnail: String,
    val address: String,
    val phone: String
)

sealed interface BankAction {
    object LoadData : BankAction
    data class OnBankClick(val bank: Bank) : BankAction
    data class OnPhoneClick(val phoneNumber: String) : BankAction
}
