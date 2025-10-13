package com.akash.beautifulbhaluka.presentation.screens.social.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.SocialProfile
import com.akash.beautifulbhaluka.presentation.screens.social.components.PostCard

/**
 * Ultra-modern Social Profile Screen
 *
 * Design principles:
 * - Ultra-clean, spacious profile header with gradient overlay
 * - Minimalistic stats display with subtle dividers
 * - Professional typography hierarchy
 * - Smooth spacing and visual breathing room
 * - Clear information architecture
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialProfileScreen(
    viewModel: SocialProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingState()
            }
            uiState.error != null -> {
                ErrorState(
                    errorMessage = uiState.error ?: "একটি ত্রুটি হয়েছে",
                    onRetry = { viewModel.onAction(SocialProfileAction.LoadProfile("current_user_id")) }
                )
            }
            uiState.profile != null -> {
                val profile = uiState.profile!!

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Modern Profile Header
                    item {
                        ModernProfileHeader(
                            profile = profile,
                            onEditClick = { viewModel.onAction(SocialProfileAction.ToggleEditMode) }
                        )
                    }

                    // Profile Info Section
                    item {
                        ProfileInfoSection(
                            profile = profile,
                            isEditMode = uiState.isEditMode,
                            editBio = uiState.editBio,
                            editLocation = uiState.editLocation,
                            editWebsite = uiState.editWebsite,
                            isSaving = uiState.isSaving,
                            onBioChange = { viewModel.onAction(SocialProfileAction.UpdateBio(it)) },
                            onLocationChange = { viewModel.onAction(SocialProfileAction.UpdateLocation(it)) },
                            onWebsiteChange = { viewModel.onAction(SocialProfileAction.UpdateWebsite(it)) },
                            onSave = { viewModel.onAction(SocialProfileAction.SaveProfile) },
                            onCancel = { viewModel.onAction(SocialProfileAction.ToggleEditMode) }
                        )
                    }

                    // Minimalist Stats Section
                    item {
                        MinimalistStatsSection(
                            postsCount = profile.postsCount,
                            friendsCount = profile.friendsCount,
                            followersCount = profile.followersCount,
                            followingCount = profile.followingCount
                        )
                    }

                    // Action Buttons (for other users)
                    if (profile.userId != "current_user_id") {
                        item {
                            ModernActionButtons(
                                isFollowing = profile.isFollowing,
                                onFollowClick = {
                                    if (profile.isFollowing) {
                                        viewModel.onAction(SocialProfileAction.UnfollowUser)
                                    } else {
                                        viewModel.onAction(SocialProfileAction.FollowUser)
                                    }
                                },
                                onMessageClick = { /* Handle message */ }
                            )
                        }
                    }

                    // Content Tabs
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        PrimaryTabRow(
                            selectedTabIndex = uiState.selectedTab.ordinal,
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {
                            ProfileTab.entries.forEach { tab ->
                                Tab(
                                    selected = uiState.selectedTab == tab,
                                    onClick = { viewModel.onAction(SocialProfileAction.SelectTab(tab)) },
                                    icon = {
                                        Icon(
                                            imageVector = tab.icon,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    text = {
                                        Text(
                                            tab.displayName,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = if (uiState.selectedTab == tab)
                                                    FontWeight.Bold else FontWeight.Normal,
                                                letterSpacing = 0.5.sp
                                            )
                                        )
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Tab Content
                    when (uiState.selectedTab) {
                        ProfileTab.POSTS -> {
                            if (uiState.posts.isEmpty()) {
                                item {
                                    EmptyStateCard(
                                        icon = Icons.Outlined.PostAdd,
                                        message = "এখনো কোনো পোস্ট নেই"
                                    )
                                }
                            } else {
                                items(uiState.posts, key = { it.id }) { post ->
                                    PostCard(
                                        post = post,
                                        onLikeClick = { /* Handle like */ },
                                        onCommentClick = { /* Handle comment */ },
                                        onShareClick = { /* Handle share */ },
                                        onProfileClick = { /* Handle profile */ }
                                    )
                                }
                            }
                        }
                        ProfileTab.ABOUT -> {
                            item {
                                ModernAboutSection(profile = profile)
                            }
                        }
                        ProfileTab.FRIENDS -> {
                            item {
                                EmptyStateCard(
                                    icon = Icons.Outlined.Group,
                                    message = "বন্ধুদের তালিকা শীঘ্রই আসছে"
                                )
                            }
                        }
                        ProfileTab.PHOTOS -> {
                            item {
                                EmptyStateCard(
                                    icon = Icons.Outlined.Photo,
                                    message = "ফটো গ্যালারি শীঘ্রই আসছে"
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
private fun ModernProfileHeader(
    profile: SocialProfile,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        // Cover Image with gradient overlay
        Box {
            AsyncImage(
                model = profile.coverImage,
                contentDescription = "Cover Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Subtle gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.15f)
                            )
                        )
                    )
            )
        }

        // Modern Profile Image with elevation
        Card(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.BottomStart)
                .offset(x = 24.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = profile.userProfileImage,
                contentDescription = "Profile Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Minimalist Edit Button
        if (profile.userId == "current_user_id") {
            FilledTonalIconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "প্রোফাইল সম্পাদনা",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoSection(
    profile: SocialProfile,
    isEditMode: Boolean,
    editBio: String,
    editLocation: String,
    editWebsite: String,
    isSaving: Boolean,
    onBioChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onWebsiteChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 20.dp)
    ) {
        // Name with cleaner typography
        Text(
            text = profile.userName,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isEditMode) {
            // View Mode - Clean and spacious
            if (profile.bio.isNotBlank()) {
                Text(
                    text = profile.bio,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Location and Website with better spacing
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (profile.location.isNotBlank()) {
                    InfoChip(
                        icon = Icons.Outlined.LocationOn,
                        text = profile.location,
                        iconTint = MaterialTheme.colorScheme.primary
                    )
                }

                if (profile.website.isNotBlank()) {
                    InfoChip(
                        icon = Icons.Outlined.Link,
                        text = profile.website,
                        iconTint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        } else {
            // Edit Mode with refined inputs
            OutlinedTextField(
                value = editBio,
                onValueChange = onBioChange,
                label = { Text("বায়ো") },
                placeholder = { Text("আপনার সম্পর্কে লিখুন...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = editLocation,
                onValueChange = onLocationChange,
                label = { Text("অবস্থান") },
                placeholder = { Text("শহর, দেশ") },
                leadingIcon = {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = editWebsite,
                onValueChange = onWebsiteChange,
                label = { Text("ওয়েবসাইট") },
                placeholder = { Text("https://example.com") },
                leadingIcon = {
                    Icon(Icons.Outlined.Link, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Modern Save/Cancel Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("বাতিল", style = MaterialTheme.typography.titleSmall)
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f).height(52.dp),
                    enabled = !isSaving,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("সংরক্ষণ করুন", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    iconTint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = iconTint
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun MinimalistStatsSection(
    postsCount: Int,
    friendsCount: Int,
    followersCount: Int,
    followingCount: Int
) {
    // Ultra-modern grid layout with 2x2 stats cards
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top Row: Posts and Friends
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UltraModernStatCard(
                label = "পোস্ট",
                count = postsCount,
                icon = Icons.Outlined.Article,
                modifier = Modifier.weight(1f)
            )

            UltraModernStatCard(
                label = "বন্ধু",
                count = friendsCount,
                icon = Icons.Outlined.Group,
                modifier = Modifier.weight(1f)
            )
        }

        // Bottom Row: Followers and Following
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UltraModernStatCard(
                label = "ফলোয়ার",
                count = followersCount,
                icon = Icons.Outlined.Favorite,
                modifier = Modifier.weight(1f)
            )

            UltraModernStatCard(
                label = "ফলোয়িং",
                count = followingCount,
                icon = Icons.Outlined.PersonAdd,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun UltraModernStatCard(
    label: String,
    count: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1.5f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Icon in top-right corner
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.TopEnd),
            )

            // Count and Label in bottom-left
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = formatCount(count),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatCount(count: Int): String {
    return when {
        count >= 1000000 -> "${count / 1000000}M"
        count >= 1000 -> "${count / 1000}K"
        else -> count.toString()
    }
}

@Composable
private fun ModernActionButtons(
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onFollowClick,
            modifier = Modifier.weight(1f).height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = if (isFollowing) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                ButtonDefaults.buttonColors()
            }
        ) {
            Icon(
                imageVector = if (isFollowing) Icons.Outlined.Check else Icons.Outlined.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                if (isFollowing) "ফলো করছেন" else "ফলো করুন",
                style = MaterialTheme.typography.titleSmall
            )
        }

        OutlinedButton(
            onClick = onMessageClick,
            modifier = Modifier.weight(1f).height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Message,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("বার্তা", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun ModernAboutSection(profile: SocialProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "সম্পর্কে",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (profile.bio.isNotBlank()) {
            ModernInfoCard(
                icon = Icons.Outlined.Info,
                title = "বায়ো",
                content = profile.bio
            )
        }

        if (profile.location.isNotBlank()) {
            ModernInfoCard(
                icon = Icons.Outlined.LocationOn,
                title = "অবস্থান",
                content = profile.location
            )
        }

        if (profile.website.isNotBlank()) {
            ModernInfoCard(
                icon = Icons.Outlined.Link,
                title = "ওয়েবসাইট",
                content = profile.website
            )
        }

        if (profile.bio.isBlank() && profile.location.isBlank() && profile.website.isBlank()) {
            EmptyStateCard(
                icon = Icons.Outlined.Info,
                message = "কোনো তথ্য যোগ করা হয়নি"
            )
        }
    }
}

@Composable
private fun ModernInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun EmptyStateCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    message: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun LoadingState() {
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
                strokeWidth = 4.dp
            )
            Text(
                text = "লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
        Card(
            modifier = Modifier.padding(32.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("পুনরায় চেষ্টা করুন")
                }
            }
        }
    }
}
