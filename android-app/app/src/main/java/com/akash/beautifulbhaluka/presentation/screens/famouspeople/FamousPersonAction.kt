package com.akash.beautifulbhaluka.presentation.screens.famouspeople

sealed class FamousPersonAction {
    object LoadData : FamousPersonAction()
    data class ViewPerson(val person: com.akash.beautifulbhaluka.domain.model.FamousPerson) :
        FamousPersonAction()
}
