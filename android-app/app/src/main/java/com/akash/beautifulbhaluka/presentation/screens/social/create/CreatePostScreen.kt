package com.akash.beautifulbhaluka.presentation.screens.social.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.PostPrivacy

/**
 * Ultra-Modern Create Post Screen
 *
 * Design principles:
 * - Clean, spacious layout with breathing room
 * - Minimalistic UI with clear visual hierarchy
 * - Smooth rounded corners and consistent spacing
 * - Professional typography
 * - Intuitive action buttons with icons
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    onDismiss: () -> Unit,
    onPostCreated: () -> Unit,
    viewModel: CreatePostViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        viewModel.onAction(CreatePostAction.AddImages(uris))
    }

    LaunchedEffect(uiState.postSuccess) {
        if (uiState.postSuccess) {
            onPostCreated()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    ModernTopBar(
                        isPosting = uiState.isPosting,
                        onDismiss = onDismiss,
                        onPublish = { viewModel.onAction(CreatePostAction.PublishPost) }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Content Input Section
                    ModernContentSection(
                        content = uiState.content,
                        onContentChange = { viewModel.onAction(CreatePostAction.UpdateContent(it)) },
                        error = uiState.error
                    )

                    // Selected Images Preview
                    if (uiState.selectedImages.isNotEmpty()) {
                        ModernImagePreview(
                            images = uiState.selectedImages,
                            onRemoveImage = { uri ->
                                viewModel.onAction(CreatePostAction.RemoveImage(uri))
                            }
                        )
                    }

                    // Action Buttons Section
                    ModernActionButtons(
                        selectedPrivacy = uiState.selectedPrivacy,
                        location = uiState.location,
                        onAddPhotos = { imagePickerLauncher.launch("image/*") },
                        onPrivacyClick = { viewModel.onAction(CreatePostAction.ShowPrivacyDialog) },
                        onLocationClick = { viewModel.onAction(CreatePostAction.ShowLocationDialog) }
                    )
                }
            }
        }
    }

    // Modern Privacy Dialog
    if (uiState.showPrivacyDialog) {
        ModernPrivacyDialog(
            selectedPrivacy = uiState.selectedPrivacy,
            onPrivacySelected = { viewModel.onAction(CreatePostAction.UpdatePrivacy(it)) },
            onDismiss = { viewModel.onAction(CreatePostAction.HidePrivacyDialog) }
        )
    }

    // Modern Location Dialog
    if (uiState.showLocationDialog) {
        ModernLocationDialog(
            currentLocation = uiState.location,
            onLocationSet = { viewModel.onAction(CreatePostAction.UpdateLocation(it)) },
            onDismiss = { viewModel.onAction(CreatePostAction.HideLocationDialog) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernTopBar(
    isPosting: Boolean,
    onDismiss: () -> Unit,
    onPublish: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "পোস্ট তৈরি করুন",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "বন্ধ করুন",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            Button(
                onClick = onPublish,
                enabled = !isPosting,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                if (isPosting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "পোস্ট করুন",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun ModernContentSection(
    content: String,
    onContentChange: (String) -> Unit,
    error: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Large, clean text input
        TextField(
            value = content,
            onValueChange = onContentChange,
            placeholder = {
                Text(
                    text = "আপনার মনে কি আছে?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 180.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 26.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 12
        )

        // Error message
        if (error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun ModernImagePreview(
    images: List<Uri>,
    onRemoveImage: (Uri) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = "ছবি (${images.size})",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(images) { uri ->
                Card(
                    modifier = Modifier.size(140.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Remove button with modern styling
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.errorContainer,
                            tonalElevation = 4.dp
                        ) {
                            IconButton(
                                onClick = { onRemoveImage(uri) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "মুছে ফেলুন",
                                    modifier = Modifier.size(18.dp),
                                    tint = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernActionButtons(
    selectedPrivacy: PostPrivacy,
    location: String,
    onAddPhotos: () -> Unit,
    onPrivacyClick: () -> Unit,
    onLocationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "আপনার পোস্টে যোগ করুন",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        // Photo/Video Button
        ModernActionCard(
            icon = Icons.Outlined.Image,
            title = "ছবি/ভিডিও",
            subtitle = "গ্যালারি থেকে নির্বাচন করুন",
            iconTint = MaterialTheme.colorScheme.primary,
            onClick = onAddPhotos
        )

        // Privacy Button
        ModernActionCard(
            icon = when (selectedPrivacy) {
                PostPrivacy.PUBLIC -> Icons.Outlined.Public
                PostPrivacy.FRIENDS -> Icons.Outlined.Group
                PostPrivacy.ONLY_ME -> Icons.Outlined.Lock
            },
            title = "গোপনীয়তা",
            subtitle = selectedPrivacy.displayName,
            iconTint = MaterialTheme.colorScheme.secondary,
            onClick = onPrivacyClick
        )

        // Location Button
        ModernActionCard(
            icon = Icons.Outlined.LocationOn,
            title = "অবস্থান",
            subtitle = location.ifBlank { "অবস্থান যোগ করুন" },
            iconTint = MaterialTheme.colorScheme.tertiary,
            onClick = onLocationClick
        )
    }
}

@Composable
private fun ModernActionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconTint: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon in colored surface
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = iconTint.copy(alpha = 0.15f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = iconTint
                    )
                }
            }

            // Text content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Arrow icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun ModernPrivacyDialog(
    selectedPrivacy: PostPrivacy,
    onPrivacySelected: (PostPrivacy) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Security,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(
                text = "গোপনীয়তা নির্বাচন করুন",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PostPrivacy.entries.forEach { privacy ->
                    ModernPrivacyOption(
                        privacy = privacy,
                        isSelected = selectedPrivacy == privacy,
                        onSelect = {
                            onPrivacySelected(privacy)
                            onDismiss()
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("বন্ধ করুন")
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}

@Composable
private fun ModernPrivacyOption(
    privacy: PostPrivacy,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 2.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )

            Icon(
                imageVector = when (privacy) {
                    PostPrivacy.PUBLIC -> Icons.Outlined.Public
                    PostPrivacy.FRIENDS -> Icons.Outlined.Group
                    PostPrivacy.ONLY_ME -> Icons.Outlined.Lock
                },
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Text(
                text = privacy.displayName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
private fun ModernLocationDialog(
    currentLocation: String,
    onLocationSet: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var locationText by remember { mutableStateOf(currentLocation) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.tertiary
            )
        },
        title = {
            Text(
                text = "অবস্থান যোগ করুন",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            OutlinedTextField(
                value = locationText,
                onValueChange = { locationText = it },
                placeholder = { Text("যেমন: ঢাকা, বাংলাদেশ") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onLocationSet(locationText)
                    onDismiss()
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("বাতিল")
            }
        },
        shape = RoundedCornerShape(24.dp)
    )
}
