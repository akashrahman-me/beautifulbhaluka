package com.akash.beautifulbhaluka.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.LoadData -> loadData()
            is HomeAction.Refresh -> refresh()
            is HomeAction.NavigateToLink -> handleLinkNavigation(action.linkItem)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val carouselItems = getMockCarouselItems()
                val linkSections = getMockLinkSections()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        carouselItems = carouselItems,
                        linkSections = linkSections,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    private fun refresh() {
        loadData()
    }

    private fun handleLinkNavigation(linkItem: LinkItem) {
        // Handle navigation logic here
        // This could trigger navigation events or external URL opening
    }

    // Mock data - replace with actual repository calls
    private fun getMockCarouselItems(): List<CarouselItem> {
        return listOf(
            CarouselItem(
                id = "1",
                title = "Welcome to Beautiful Bhaluka",
                imageUrl = "",
                description = "Discover the beauty of our community"
            ),
            CarouselItem(
                id = "2",
                title = "Latest Updates",
                imageUrl = "",
                description = "Stay informed with recent developments"
            )
        )
    }

    private fun getMockLinkSections(): List<LinkSection> {
        return listOf(
            LinkSection(
                title = "Quick Actions",
                links = listOf(
                    LinkItem(
                        id = "1",
                        title = "Find Jobs",
                        description = "Browse available job opportunities",
                        route = "jobs"
                    ),
                    LinkItem(
                        id = "2",
                        title = "Social Network",
                        description = "Connect with the community",
                        route = "social"
                    ),
                    LinkItem(
                        id = "3",
                        title = "Local Shops",
                        description = "Discover local businesses",
                        route = "shops"
                    )
                )
            ),
            LinkSection(
                title = "Services",
                links = listOf(
                    LinkItem(
                        id = "4",
                        title = "Government Services",
                        description = "Access local government services",
                        externalUrl = "https://example.com"
                    ),
                    LinkItem(
                        id = "5",
                        title = "Emergency Contacts",
                        description = "Important contact information",
                        route = "emergency"
                    )
                )
            )
        )
    }
}
