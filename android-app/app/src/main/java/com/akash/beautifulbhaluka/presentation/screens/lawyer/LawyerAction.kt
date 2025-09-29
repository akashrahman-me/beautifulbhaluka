package com.akash.beautifulbhaluka.presentation.screens.lawyer

sealed class LawyerAction {
    object LoadData : LawyerAction()
    data class CallNumber(val phoneNumber: String) : LawyerAction()
}
