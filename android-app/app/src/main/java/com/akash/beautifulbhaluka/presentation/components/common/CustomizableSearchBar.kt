package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizableSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    searchResults: List<String>,
    onResultClick: (String) -> Unit,
    expanded: Boolean,
    searchbarVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "খোঁজ করুন...",
    isLoading: Boolean = false,
    suggestions: List<String> = emptyList(),
    recentSearches: List<String> = emptyList(),
    onSuggestionClick: ((String) -> Unit)? = null
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Show search overlay when expanded
    if (expanded) {
        Dialog(
            onDismissRequest = { searchbarVisibility(false) },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .statusBarsPadding()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                // Search Bar Header
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = CircleShape
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { searchbarVisibility(false) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }

                        OutlinedTextField(
                            value = query,
                            onValueChange = onQueryChange,
                            placeholder = { Text(placeholder) },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    onSearch(query)
                                    keyboardController?.hide()
                                }
                            ),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            )
                        )

                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = { onQueryChange("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                onSearch(query)
                                keyboardController?.hide()
                            }
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                    }
                }

                // Search Content
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        // Show search results if query is not empty and we have results
                        if (query.isNotEmpty() && searchResults.isNotEmpty()) {
                            item {
                                Text(
                                    text = "খোঁজার ফলাফল",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            items(searchResults) { result ->
                                SearchResultItem(
                                    text = result,
                                    onClick = { onResultClick(result) },
                                    icon = Icons.Default.Search
                                )
                            }
                        }
                        // Show no results message
                        else if (query.isNotEmpty() && searchResults.isEmpty() && !isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "কোন ফলাফল পাওয়া যায়নি",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "অন্য কিছু খোঁজ করে দেখুন",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                        // Show suggestions and recent searches when query is empty
                        else if (query.isEmpty()) {
                            // Recent searches
                            if (recentSearches.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "সাম্প্রতিক খোঁজ",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        ),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                items(recentSearches.take(5)) { recent ->
                                    SearchResultItem(
                                        text = recent,
                                        onClick = {
                                            onQueryChange(recent)
                                            onSearch(recent)
                                        },
                                        icon = Icons.Default.History
                                    )
                                }
                            }

                            // Popular suggestions
                            if (suggestions.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "জনপ্রিয় খোঁজ",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(
                                            horizontal = 16.dp,
                                            vertical = 8.dp
                                        ),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                items(suggestions.take(8)) { suggestion ->
                                    SearchResultItem(
                                        text = suggestion,
                                        onClick = {
                                            onSuggestionClick?.invoke(suggestion)
                                        },
                                        icon = Icons.Default.TrendingUp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Auto-focus when search opens
        LaunchedEffect(expanded) {
            if (expanded) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
private fun SearchResultItem(
    text: String,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
