package com.akash.beautifulbhaluka.presentation.components.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.presentation.components.common.CustomizableSearchBar

@Composable
fun Topbar(
    title: String = "বিউটিফুল ভালুকা",
    modifier: Modifier = Modifier,
    viewModel: TopbarViewModel = viewModel(),
    onNavigateToResult: ((SearchResult) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    fun searchbarVisibility(value: Boolean) {
        expanded = value
        if (!value) {
            viewModel.onAction(TopbarAction.ClearSearch)
        }
    }

    Box(
        modifier = modifier
            .drawBehind {
                val shadowColor = Color.Black.copy(alpha = 0.2f)
                val offsetX = 0.dp.toPx()
                val offsetY = 0.dp.toPx()
                val blur = 1.dp.toPx()

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = shadowColor
                        asFrameworkPaint().maskFilter =
                            android.graphics.BlurMaskFilter(
                                blur,
                                android.graphics.BlurMaskFilter.Blur.NORMAL
                            )
                    }
                    canvas.drawRoundRect(
                        left = offsetX,
                        top = offsetY,
                        right = size.width + offsetX,
                        bottom = size.height + offsetY,
                        radiusX = 0.dp.toPx(),
                        radiusY = 0.dp.toPx(),
                        paint = paint
                    )
                }
            }
            .background(Color.White)
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Row {
                IconButton(onClick = {
                    searchbarVisibility(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )
                }
            }
        }
    }

    Box {
        if (expanded) {
            CustomizableSearchBar(
                query = uiState.searchQuery,
                onQueryChange = { newQuery ->
                    viewModel.onAction(TopbarAction.SearchQueryChanged(newQuery))
                },
                onSearch = { searchQuery ->
                    viewModel.onAction(TopbarAction.SearchExecuted(searchQuery))
                },
                searchResults = uiState.searchResults.map { it.title }, // Convert to string list for compatibility
                onResultClick = { selectedTitle ->
                    // Find the full result object and handle navigation
                    uiState.searchResults.find { it.title == selectedTitle }?.let { result ->
                        viewModel.onAction(TopbarAction.SearchResultClicked(result))
                        onNavigateToResult?.invoke(result)
                    }
                    searchbarVisibility(false)
                },
                expanded = expanded,
                searchbarVisibility = { value -> searchbarVisibility(value) },
                isLoading = uiState.isSearching,
                suggestions = uiState.suggestions.map { it.text },
                recentSearches = uiState.recentSearches,
                onSuggestionClick = { suggestion ->
                    uiState.suggestions.find { it.text == suggestion }?.let { suggestionObj ->
                        viewModel.onAction(TopbarAction.SuggestionClicked(suggestionObj))
                    }
                }
            )
        }
    }
}
