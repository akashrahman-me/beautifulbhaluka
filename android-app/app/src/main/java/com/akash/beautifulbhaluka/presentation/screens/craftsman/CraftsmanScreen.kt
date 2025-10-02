package com.akash.beautifulbhaluka.presentation.screens.craftsman

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.screens.craftsman.components.CraftsmanCard

@Composable
fun CraftsmanScreen(
    viewModel: CraftsmanViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CraftsmanContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CraftsmanContent(
    uiState: CraftsmanUiState,
    onAction: (CraftsmanAction) -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            // Modern Header Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Minimalist Title
                    Text(
                        text = "সকল মিস্ত্রি",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = 36.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Clean underline accent
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(2.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(1.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Subtitle with refined typography
                    Text(
                        text = "কারিগরি সেবা ও দক্ষ কারিগর",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Search and Filter Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Search Bar
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = { onAction(CraftsmanAction.SearchQueryChanged(it)) },
                        placeholder = { Text("নাম, ঠিকানা বা ধরন দিয়ে খোঁজ করুন...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "খোঁজ করুন"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Type Filter Dropdown
                    ExposedDropdownMenuBox(
                        expanded = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = uiState.selectedType.displayName,
                            onValueChange = { },
                            readOnly = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = uiState.selectedType.icon,
                                    contentDescription = "কারিগরি ধরন আইকন",
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "ড্রপডাউন"
                                )
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        ExposedDropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            CraftsmanType.values().forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type.displayName) },
                                    onClick = {
                                        onAction(CraftsmanAction.TypeFilterChanged(type))
                                        dropdownExpanded = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = type.icon,
                                            contentDescription = "${type.displayName} আইকন",
                                            tint = if (type == uiState.selectedType)
                                                MaterialTheme.colorScheme.primary
                                            else
                                                MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    trailingIcon = {
                                        if (type == uiState.selectedType) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "নির্বাচিত",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Results Count - Only show when search or filter is active
            if (uiState.searchQuery.isNotEmpty() || uiState.selectedType != CraftsmanType.ALL) {
                item {
                    Text(
                        text = "${uiState.filteredCraftsmen.size}টি ফলাফল পাওয়া গেছে",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Content Section
            when {
                uiState.isLoading -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Text(
                                        text = "তথ্য লোড করা হচ্ছে...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                uiState.error != null -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "ত্রুটি: ${uiState.error}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                uiState.filteredCraftsmen.isEmpty() && !uiState.isLoading -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = "🔍",
                                        style = MaterialTheme.typography.displayMedium
                                    )
                                    Text(
                                        text = "কোনো ফলাফল পাওয়া যায়নি",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "অন্য কিওয়ার্ড দিয়ে খোঁজ করে দেখুন",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    // Cards List
                    items(
                        items = uiState.filteredCraftsmen,
                        key = { "${it.name}_${it.number}" }
                    ) { craftsman ->
                        CraftsmanCard(
                            craftsman = craftsman,
                            onCallClick = { number ->
                                onAction(CraftsmanAction.CallNumber(number))
                            }
                        )
                    }
                }
            }
        }
    }
}
