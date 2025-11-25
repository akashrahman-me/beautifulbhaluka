package com.akash.beautifulbhaluka.presentation.screens.entertainment

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.ui.graphics.vector.ImageVector

data class EntertainmentUiState(
    val isLoading: Boolean = false,
    val entertainers: List<Entertainer> = emptyList(),
    val filteredEntertainers: List<Entertainer> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val selectedType: EntertainmentType = EntertainmentType.ALL,
    val isSearchExpanded: Boolean = false
)

data class Entertainer(
    val name: String,
    val number: String,
    val type: String,
    val address: String,
    val image: String
)

enum class EntertainmentType(
    val displayName: String,
    val value: String,
    val icon: ImageVector
) {
    ALL("সব", "", Icons.Default.MusicNote),
    SOUND_SYSTEM("সাউন্ড সিস্টেম", "সাউন্ড সিস্টেম", Icons.Default.VolumeUp),
    BAND_PARTY("ব্যান্ড পার্টি", "ব্যান্ড পার্টি", Icons.Default.MusicNote)
}

sealed interface EntertainmentAction {
    object LoadData : EntertainmentAction
    data class SearchQueryChanged(val query: String) : EntertainmentAction
    data class TypeFilterChanged(val type: EntertainmentType) : EntertainmentAction
    data class SearchExpandedChanged(val expanded: Boolean) : EntertainmentAction
    data class CallNumber(val number: String) : EntertainmentAction
    object NavigateToPublish : EntertainmentAction
}

