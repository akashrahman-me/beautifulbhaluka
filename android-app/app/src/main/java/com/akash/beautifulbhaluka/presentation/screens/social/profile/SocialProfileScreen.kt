package com.akash.beautifulbhaluka.presentation.screens.social.profile

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
 * - Clean, spacious profile header
 * - Minimalistic stats display
 * - Professional typography
 * - Smooth edit mode transitions
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
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    // Cover and Profile Image Section
                    item {
                        ProfileHeader(
                            profile = profile,
                            onEditClick = { viewModel.onAction(SocialProfileAction.ToggleEditMode) }
                        )
                    }

                    // Profile Info Section
                    item {
                        ProfileInfo(
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

                    // Stats Section
                    item {
                        ProfileStats(
                            postsCount = profile.postsCount,
                            friendsCount = profile.friendsCount,
                            followersCount = profile.followersCount,
                            followingCount = profile.followingCount
                        )
                    }

                    // Action Buttons (for other users)
                    if (profile.userId != "current_user_id") {
                        item {
                            ProfileActionButtons(
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

                    // Tabs
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        PrimaryTabRow(
                            selectedTabIndex = uiState.selectedTab.ordinal,
                            containerColor = MaterialTheme.colorScheme.surface
                        ) {
                            ProfileTab.entries.forEach { tab ->
                                Tab(
                                    selected = uiState.selectedTab == tab,
                                    onClick = { viewModel.onAction(SocialProfileAction.SelectTab(tab)) },
                                    text = {
                                        Text(
                                            tab.displayName,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = if (uiState.selectedTab == tab)
                                                    FontWeight.Bold else FontWeight.Medium
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Tab Content
                    when (uiState.selectedTab) {
                        ProfileTab.POSTS -> {
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
                        ProfileTab.ABOUT -> {
                            item {
                                AboutSection(profile = profile)
                            }
                        }
                        ProfileTab.FRIENDS -> {
                            item {
                                Text(
                                    "Friends feature coming soon",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        ProfileTab.PHOTOS -> {
                            item {
                                Text(
                                    "Photos feature coming soon",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
private fun ProfileHeader(
    profile: SocialProfile,
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        // Cover Image
        AsyncImage(
            model = profile.coverImage,
            contentDescription = "Cover",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        // Profile Image with border
        Surface(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.BottomStart)
                .offset(x = 20.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            AsyncImage(
                model = profile.userProfileImage,
                contentDescription = "Profile",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        // Edit Button
        if (profile.userId == "current_user_id") {
            FilledTonalIconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "Edit Profile",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun ProfileInfo(
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
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp)
    ) {
        // Name
        Text(
            text = profile.userName,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (!isEditMode) {
            // View Mode
            if (profile.bio.isNotBlank()) {
                Text(
                    text = profile.bio,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 22.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Location and Website
            if (profile.location.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = profile.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            if (profile.website.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Outlined.Link,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = profile.website,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        } else {
            // Edit Mode
            OutlinedTextField(
                value = editBio,
                onValueChange = onBioChange,
                label = { Text("বায়ো") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = editLocation,
                onValueChange = onLocationChange,
                label = { Text("অবস্থান") },
                leadingIcon = {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = editWebsite,
                onValueChange = onWebsiteChange,
                label = { Text("ওয়েবসাইট") },
                leadingIcon = {
                    Icon(Icons.Outlined.Link, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Save/Cancel Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("বাতিল")
                }

                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f).height(48.dp),
                    enabled = !isSaving
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Text("সংরক্ষণ করুন")
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileStats(
    postsCount: Int,
    friendsCount: Int,
    followersCount: Int,
    followingCount: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("পোস্ট", postsCount)
            VerticalDivider(modifier = Modifier.height(40.dp))
            StatItem("বন্ধু", friendsCount)
            VerticalDivider(modifier = Modifier.height(40.dp))
            StatItem("অনুসরণকারী", followersCount)
            VerticalDivider(modifier = Modifier.height(40.dp))
            StatItem("অনুসরণ", followingCount)
        }
    }
}

@Composable
private fun StatItem(label: String, count: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProfileActionButtons(
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onMessageClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onFollowClick,
            modifier = Modifier.weight(1f).height(48.dp),
            colors = if (isFollowing) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            } else {
                ButtonDefaults.buttonColors()
            }
        ) {
            Icon(
                imageVector = if (isFollowing) Icons.Outlined.PersonRemove else Icons.Outlined.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isFollowing) "আনফলো" else "ফলো করুন")
        }

        OutlinedButton(
            onClick = onMessageClick,
            modifier = Modifier.weight(1f).height(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Message,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("বার্তা")
        }
    }
}

@Composable
private fun AboutSection(
    profile: SocialProfile
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "সম্পর্কে",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        if (profile.bio.isNotBlank()) {
            InfoCard(
                icon = Icons.Outlined.Info,
                title = "বায়ো",
                content = profile.bio
            )
        }

        if (profile.location.isNotBlank()) {
            InfoCard(
                icon = Icons.Outlined.LocationOn,
                title = "অবস্থান",
                content = profile.location
            )
        }

        if (profile.website.isNotBlank()) {
            InfoCard(
                icon = Icons.Outlined.Link,
                title = "ওয়েবসাইট",
                content = profile.website
            )
        }
    }
}

@Composable
private fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
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
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            strokeWidth = 4.dp
        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            Button(onClick = onRetry) {
                Text("পুনরায় চেষ্টা করুন")
            }
        }
    }
}
