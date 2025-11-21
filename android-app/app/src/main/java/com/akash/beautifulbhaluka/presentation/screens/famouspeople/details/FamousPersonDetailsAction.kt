package com.akash.beautifulbhaluka.presentation.screens.famouspeople.details

sealed class FamousPersonDetailsAction {
    object NavigateBack : FamousPersonDetailsAction()
    object NavigateHome : FamousPersonDetailsAction()
}

