package com.akash.beautifulbhaluka.presentation.screens.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val carouselItems: List<CarouselItem> = emptyList(),
    val linkSections: List<LinkSection> = emptyList(),
    val error: String? = null
)

data class CarouselItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String
)

data class LinkSection(
    val name: String,
    val values: List<LinkItem>
)

data class LinkItem(
    val link: String,
    val icon: Int,
    val label: String
)

sealed class HomeAction {
    object LoadData : HomeAction()
    object Refresh : HomeAction()
    data class NavigateToLink(val linkItem: LinkItem) : HomeAction()
}
