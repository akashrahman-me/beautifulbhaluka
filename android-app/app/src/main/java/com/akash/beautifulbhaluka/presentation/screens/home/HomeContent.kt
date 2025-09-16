package com.akash.beautifulbhaluka.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.presentation.components.common.Carousel
import com.akash.beautifulbhaluka.presentation.components.common.LinkCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeContent(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        else -> {
            HomeContentLoaded(
                uiState = uiState,
                onAction = onAction
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HomeContentLoaded(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current

    Column {
        Box(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Carousel section
                Carousel(
                    items = uiState.carouselItems,
                    onItemClick = { carouselItem ->
                        // Handle carousel item click - could navigate or show details
                        // For now, we'll just trigger a generic action
                        onAction(HomeAction.LoadData) // Or create a specific CarouselItemClicked action
                    }
                )

                // Link sections
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.linkSections.forEach { section ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = section.name,
                                style = MaterialTheme.typography.titleLarge,
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                            val itemWidth =
                                (screenWidth - (16.dp) - 20.dp * 2) / 3 // padding + gaps

                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                section.values.forEach { link ->
                                    LinkCard(
                                        linkItem = link,
                                        onClick = { onAction(HomeAction.NavigateToLink(link)) },
                                        modifier = Modifier.width(itemWidth - 6.dp) // force equal width
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
