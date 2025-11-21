package com.akash.beautifulbhaluka.presentation.screens.ambulance

sealed class AmbulanceAction {
    data class DialPhone(val phoneNumber: String) : AmbulanceAction()
}

