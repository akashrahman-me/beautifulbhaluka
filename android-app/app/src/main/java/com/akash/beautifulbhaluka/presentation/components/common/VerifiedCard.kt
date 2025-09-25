package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Business
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * Data class for card details with material icons and actions
 */
data class CardDetail(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val actionable: Boolean = false,
    val actionData: String? = null
) {
    companion object {
        fun phone(number: String) = CardDetail(
            icon = Icons.Default.Phone,
            label = "ফোন",
            value = number,
            actionable = true,
            actionData = number
        )

        fun email(address: String) = CardDetail(
            icon = Icons.Default.Email,
            label = "ইমেইল",
            value = address,
            actionable = true,
            actionData = address
        )

        fun location(address: String) = CardDetail(
            icon = Icons.Default.LocationOn,
            label = "ঠিকানা",
            value = address
        )

        fun schedule(time: String) = CardDetail(
            icon = Icons.Default.AccessTime,
            label = "সময়সূচী",
            value = time
        )

        fun organization(name: String) = CardDetail(
            icon = Icons.Default.Business,
            label = "প্রতিষ্ঠান",
            value = name
        )
    }
}
