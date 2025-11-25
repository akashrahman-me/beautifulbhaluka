package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.details

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingDetailsScreen(
    profileId: String,
    onNavigateBack: () -> Unit,
    viewModel: MatchmakingDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(profileId) {
        viewModel.onAction(MatchmakingDetailsAction.LoadProfile(profileId))
    }

    val gradientColors = listOf(
        Color(0xFFFF6B9D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (uiState.isLoading) {
            ModernLoadingState()
        } else if (uiState.profile != null) {
            val profile = uiState.profile!!

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                // Hero Section with Gradient
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        // Gradient Background
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(gradientColors)
                                )
                        )

                        // Top Bar
                        TopAppBar(
                            title = { },
                            navigationIcon = {
                                IconButton(onClick = onNavigateBack) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "ফিরে যান",
                                        tint = Color.White
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { viewModel.onAction(MatchmakingDetailsAction.ToggleFavorite) }
                                ) {
                                    Icon(
                                        imageVector = if (uiState.isFavorite)
                                            Icons.Filled.Favorite
                                        else
                                            Icons.Outlined.FavoriteBorder,
                                        contentDescription = "পছন্দ",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            )
                        )

                        // Profile Avatar and Name
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = profile.name.first().toString(),
                                    style = MaterialTheme.typography.displayLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = gradientColors[0]
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = profile.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                if (profile.verified) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified",
                                        tint = Color.White,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                QuickInfoChip(
                                    icon = Icons.Default.Cake,
                                    text = "${profile.age} years"
                                )
                                QuickInfoChip(
                                    icon = Icons.Default.Height,
                                    text = profile.height
                                )
                                QuickInfoChip(
                                    icon = if (profile.gender == "Male") Icons.Default.Male else Icons.Default.Female,
                                    text = profile.gender
                                )
                            }
                        }
                    }
                }

                // About Me Section
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    ModernSectionCard(
                        title = "About Me",
                        icon = Icons.Outlined.Person,
                        iconColor = Color(0xFFFF6B9D)
                    ) {
                        Text(
                            text = profile.bio,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.5
                        )
                    }
                }

                // Personal Information
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    ModernSectionCard(
                        title = "Personal Information",
                        icon = Icons.Outlined.Info,
                        iconColor = Color(0xFF6366F1)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            DetailInfoRow(
                                icon = Icons.Outlined.Work,
                                label = "Occupation",
                                value = profile.occupation,
                                iconColor = Color(0xFF6366F1)
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                            DetailInfoRow(
                                icon = Icons.Outlined.School,
                                label = "Education",
                                value = profile.education,
                                iconColor = Color(0xFF10B981)
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                            DetailInfoRow(
                                icon = Icons.Outlined.LocationOn,
                                label = "Location",
                                value = profile.location,
                                iconColor = Color(0xFFF59E0B)
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                            DetailInfoRow(
                                icon = Icons.Outlined.Favorite,
                                label = "Marital Status",
                                value = profile.maritalStatus,
                                iconColor = Color(0xFFEC4899)
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                            DetailInfoRow(
                                icon = Icons.Outlined.Star,
                                label = "Religion",
                                value = profile.religion,
                                iconColor = Color(0xFF8B5CF6)
                            )
                        }
                    }
                }

                // Interests & Hobbies
                if (profile.interests.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        ModernSectionCard(
                            title = "Interests & Hobbies",
                            icon = Icons.Outlined.Psychology,
                            iconColor = Color(0xFFEC4899)
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(profile.interests) { interest ->
                                    AssistChip(
                                        onClick = { },
                                        label = {
                                            Text(
                                                interest,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Tag,
                                                contentDescription = null,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Contact Section
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    ContactInfoCard(
                        profile = profile,
                        showContactInfo = uiState.showContactInfo,
                        onToggleContactInfo = {
                            viewModel.onAction(MatchmakingDetailsAction.ToggleContactInfo)
                        },
                        onCallContact = {
                            viewModel.onAction(MatchmakingDetailsAction.CallContact)
                        },
                        onSendEmail = {
                            viewModel.onAction(MatchmakingDetailsAction.SendEmail)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        } else if (uiState.error != null) {
            val errorMsg = uiState.error ?: "Unknown error"
            ErrorState(
                errorMessage = errorMsg,
                onRetry = {
                    viewModel.onAction(MatchmakingDetailsAction.LoadProfile(profileId))
                }
            )
        }
    }
}

@Composable
private fun ModernLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 4.dp,
                color = Color(0xFFFF6B9D)
            )
            Text(
                text = "Loading profile...",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ErrorState(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            FilledTonalButton(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try again")
            }
        }
    }
}

@Composable
private fun QuickInfoChip(
    icon: ImageVector,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.White
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ModernSectionCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
private fun DetailInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ContactInfoCard(
    profile: com.akash.beautifulbhaluka.domain.model.MatchmakingProfile,
    showContactInfo: Boolean,
    onToggleContactInfo: () -> Unit,
    onCallContact: () -> Unit,
    onSendEmail: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF6B9D).copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContactPhone,
                    contentDescription = null,
                    tint = Color(0xFFFF6B9D),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Contact Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(
                visible = showContactInfo,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (profile.contactNumber != null) {
                        ContactInfoRow(
                            icon = Icons.Outlined.Phone,
                            label = "Phone",
                            value = profile.contactNumber,
                            onAction = onCallContact
                        )
                    }
                    if (profile.email != null) {
                        ContactInfoRow(
                            icon = Icons.Outlined.Email,
                            label = "Email",
                            value = profile.email,
                            onAction = onSendEmail
                        )
                    }
                }
            }

            Button(
                onClick = onToggleContactInfo,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B9D)
                )
            ) {
                Icon(
                    imageVector = if (showContactInfo) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (showContactInfo) "Hide Contact Info" else "View Contact Info",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ContactInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    onAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFF6B9D),
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        IconButton(onClick = onAction) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Action",
                tint = Color(0xFFFF6B9D)
            )
        }
    }
}
