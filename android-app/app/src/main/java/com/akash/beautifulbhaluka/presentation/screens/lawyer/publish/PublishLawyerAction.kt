package com.akash.beautifulbhaluka.presentation.screens.lawyer.publish

sealed class PublishLawyerAction {
    data class UpdateName(val name: String) : PublishLawyerAction()
    data class UpdateDesignation(val designation: String) : PublishLawyerAction()
    data class UpdatePhone(val phone: String) : PublishLawyerAction()
    data class UpdateImage(val image: String) : PublishLawyerAction()
    object Submit : PublishLawyerAction()
    object ClearSuccess : PublishLawyerAction()
}

