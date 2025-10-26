package com.akash.beautifulbhaluka.presentation.screens.social.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.SocialProfile
import com.akash.beautifulbhaluka.domain.model.StoryHighlight
import com.akash.beautifulbhaluka.domain.model.Friend
import com.akash.beautifulbhaluka.domain.model.Photo
import com.akash.beautifulbhaluka.presentation.screens.social.components.PostCard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.UserPlus
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.Link as LinkIcon
import com.composables.icons.lucide.Briefcase
import com.composables.icons.lucide.GraduationCap
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.Image as ImageIcon
import com.composables.icons.lucide.Video
import java.util.Locale

/**
 * Ultra-Modern Social Profile Screen - Facebook-inspired Design
 *
 * Features:
 * - Cover photo with gradient overlay and camera options
 * - Profile picture with upload functionality
 * - Bio section with rich information (work, education, location, etc.)
 * - Interactive stats (posts, friends, followers, photos)
 * - Quick action buttons (Message, Add Friend, Share)
 * - Tabbed content (Posts, About, Friends, Photos, Videos)
 * - Create post/story shortcuts
 * - Modern Material 3 design with gradients
 * - Smooth animations and transitions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialProfileScreen(
    viewModel: SocialProfileViewModel,
    onNavigateToEditProfile: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMoreOptions by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                ModernLoadingState()
            }
            uiState.error != null -> {
                ModernErrorState(
                    errorMessage = uiState.error ?: "An error occurred",
                    onRetry = { viewModel.onAction(SocialProfileAction.LoadProfile("current_user_id")) }
                )
            }
            uiState.profile != null -> {
                val profile = uiState.profile!!
                val isOwnProfile = profile.userId == "current_user_id"

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Ultra-Modern Cover & Profile Section
                    item {
                        UltraModernCoverSection(
                            profile = profile,
                            isOwnProfile = isOwnProfile,
                            onCoverPhotoClick = { /* Upload cover */ },
                            onProfilePhotoClick = { /* Upload profile */ }
                        )
                    }

                    // Name & Bio Section
                    item {
                        NameAndBioSection(
                            profile = profile,
                            isOwnProfile = isOwnProfile
                        )
                    }

                    // Action Buttons Row
                    item {
                        ActionButtonsRow(
                            isOwnProfile = isOwnProfile,
                            isFollowing = profile.isFollowing,
                            onMessageClick = { /* Navigate to chat */ },
                            onFriendClick = {
                                if (profile.isFollowing) {
                                    viewModel.onAction(SocialProfileAction.UnfollowUser)
                                } else {
                                    viewModel.onAction(SocialProfileAction.FollowUser)
                                }
                            },
                            onMoreClick = { showMoreOptions = true },
                            onEditProfileClick = { viewModel.onAction(SocialProfileAction.ToggleEditMode) }
                        )
                    }

                    // Story Highlights Section (Square 2:4 shape)
                    if (isOwnProfile) {
                        item {
                            StoryHighlightsSection(highlights = uiState.storyHighlights)
                        }
                    }

                    // Intro Section (Work, Education, Location, etc.)
                    item {
                        IntroSection(
                            profile = profile,
                            onEditDetailsClick = onNavigateToEditProfile
                        )
                    }

                    // Friends Preview Section
                    item {
                        FriendsPreviewSection(
                            friendsCount = profile.friendsCount,
                            friends = uiState.friends
                        )
                    }

                    // Photos Preview Section
                    item {
                        PhotosPreviewSection(photos = uiState.photos)
                    }

                    // Create Post Section (if own profile)
                    if (isOwnProfile) {
                        item {
                            CreatePostSection(
                                profile = profile,
                                onCreatePost = { /* Handle create post */ },
                                onAddPhoto = { /* Handle add photo */ },
                                onGoLive = { /* Handle go live */ }
                            )
                        }
                    }

                    // Tabbed Content Section
                    item {
                        ContentTabsSection(
                            selectedTab = uiState.selectedTab,
                            onTabSelected = { viewModel.onAction(SocialProfileAction.SelectTab(it)) }
                        )
                    }

                    // Tab Content
                    when (uiState.selectedTab) {
                        ProfileTab.POSTS -> {
                            if (uiState.posts.isEmpty()) {
                                item {
                                    EmptyPostsState(isOwnProfile = isOwnProfile)
                                }
                            } else {
                                items(uiState.posts, key = { it.id }) { post ->
                                    PostCard(
                                        post = post,
                                        onLikeClick = {
                                            if (post.isLiked) {
                                                // Unlike the post by selecting LIKE again
                                                viewModel.onAction(SocialProfileAction.ReactToPost(post.id, com.akash.beautifulbhaluka.domain.model.Reaction.LIKE))
                                            } else {
                                                viewModel.onAction(SocialProfileAction.ReactToPost(post.id, com.akash.beautifulbhaluka.domain.model.Reaction.LIKE))
                                            }
                                        },
                                        onReactionSelected = { reaction ->
                                            viewModel.onAction(SocialProfileAction.ReactToPost(post.id, reaction))
                                        },
                                        onCustomEmojiSelected = { emoji, label ->
                                            viewModel.onAction(SocialProfileAction.ReactToPostWithCustomEmoji(post.id, emoji, label))
                                        },
                                        onCommentClick = { /* Handle comment */ },
                                        onShareClick = { /* Handle share */ },
                                        onProfileClick = { /* Handle profile */ }
                                    )
                                }
                            }
                        }
                        ProfileTab.ABOUT -> {
                            item {
                                DetailedAboutSection(profile = profile)
                            }
                        }
                        ProfileTab.FRIENDS -> {
                            item {
                                FriendsGridSection()
                            }
                        }
                        ProfileTab.PHOTOS -> {
                            item {
                                PhotosGridSection()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UltraModernCoverSection(
    profile: SocialProfile,
    isOwnProfile: Boolean,
    onCoverPhotoClick: () -> Unit,
    onProfilePhotoClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        // Cover Photo with Gradient Overlay
        Box {
            // Cover Image
            AsyncImage(
                model = profile.coverImage,
                contentDescription = "Cover Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f)
                            )
                        )
                    )
            )

            // Cover Photo Edit Button
            if (isOwnProfile) {
                FilledTonalIconButton(
                    onClick = onCoverPhotoClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    )
                ) {
                    Icon(
                        imageVector = Lucide.Camera,
                        contentDescription = "Edit Cover",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Profile Picture with Border & Badge
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp)
                .offset(y = 10.dp)
        ) {
            // Gradient Border
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                // Profile Image
                Card(
                    modifier = Modifier.fillMaxSize(),
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
            }

            // Camera Button for Profile Photo
            if (isOwnProfile) {
                Surface(
                    onClick = onProfilePhotoClick,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-6).dp, y = (-6).dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shadowElevation = 4.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Lucide.Camera,
                            contentDescription = "Change Photo",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NameAndBioSection(
    profile: SocialProfile,
    isOwnProfile: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top =36.dp, bottom = 16.dp)
    ) {
        // Name with verification badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = profile.userName,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            // Verified Badge
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Verified",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp),
                    tint = Color.White
                )
            }
        }

        // Bio
        if (profile.bio.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 24.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ActionButtonsRow(
    isOwnProfile: Boolean,
    isFollowing: Boolean,
    onMessageClick: () -> Unit,
    onFriendClick: () -> Unit,
    onMoreClick: () -> Unit,
    onEditProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isOwnProfile) {
            // Edit Profile Button
            Button(
                onClick = onEditProfileClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile", style = MaterialTheme.typography.labelLarge)
            }

            // Add Story Button
            FilledTonalButton(
                onClick = { /* Add story */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Lucide.Plus,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Story", style = MaterialTheme.typography.labelLarge)
            }
        } else {
            // Add Friend / Following Button
            Button(
                onClick = onFriendClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = if (isFollowing) {
                    ButtonDefaults.filledTonalButtonColors()
                } else {
                    ButtonDefaults.buttonColors()
                }
            ) {
                Icon(
                    imageVector = if (isFollowing) Icons.Filled.Check else Lucide.UserPlus,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (isFollowing) "Friends" else "Add Friend",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // Message Button
            FilledTonalButton(
                onClick = onMessageClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Lucide.MessageCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Message", style = MaterialTheme.typography.labelLarge)
            }
        }

        // More Options Button
        FilledTonalIconButton(
            onClick = onMoreClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Lucide.Ellipsis,
                contentDescription = "More",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun IntroSection(
    profile: SocialProfile,
    onEditDetailsClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Intro",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            // Work
            IntroItem(
                icon = Lucide.Briefcase,
                text = "Works at Tech Company"
            )

            // Education
            IntroItem(
                icon = Lucide.GraduationCap,
                text = "Studied Computer Science at University"
            )

            // Lives in
            IntroItem(
                icon = Lucide.House,
                text = "Lives in ${profile.location.ifBlank { "New York, USA" }}"
            )

            // From
            IntroItem(
                icon = Lucide.MapPin,
                text = "From California, USA"
            )

            // Relationship
            IntroItem(
                icon = Lucide.Heart,
                text = "Single"
            )

            // Joined
            IntroItem(
                icon = Lucide.Calendar,
                text = "Joined October 2023"
            )

            if (profile.website.isNotBlank()) {
                IntroItem(
                    icon = Lucide.LinkIcon,
                    text = profile.website
                )
            }

            // Edit Details Button
            TextButton(
                onClick = onEditDetailsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Details")
            }
        }
    }
}

@Composable
private fun IntroItem(
    icon: ImageVector,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun FriendsPreviewSection(friendsCount: Int, friends: List<Friend>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Friends",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "$friendsCount friends",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                TextButton(onClick = { /* See all friends */ }) {
                    Text("See All")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Friends Grid Preview (2x3) - Show actual friend data
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    friends.take(3).forEachIndexed { index, friend ->
                        FriendPreviewCard(
                            name = friend.userName,
                            profileImage = friend.profileImage,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty slots if less than 3 friends
                    repeat(maxOf(0, 3 - minOf(3, friends.size))) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
                if (friends.size > 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        friends.drop(3).take(3).forEachIndexed { index, friend ->
                            FriendPreviewCard(
                                name = friend.userName,
                                profileImage = friend.profileImage,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // Fill empty slots if less than 3 friends in second row
                        repeat(maxOf(0, 3 - minOf(3, friends.drop(3).size))) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FriendPreviewCard(
    name: String,
    profileImage: String = "",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            if (profileImage.isNotEmpty()) {
                AsyncImage(
                    model = profileImage,
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PhotosPreviewSection(photos: List<Photo>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Photos",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                TextButton(onClick = { /* See all photos */ }) {
                    Text("See All")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Photos Grid (3x3) - Show actual photo data
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(3) { colIndex ->
                            val photoIndex = rowIndex * 3 + colIndex
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                if (photoIndex < photos.size) {
                                    AsyncImage(
                                        model = photos[photoIndex].imageUrl,
                                        contentDescription = photos[photoIndex].caption,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.Photo,
                                            contentDescription = null,
                                            modifier = Modifier.size(32.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
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
}

@Composable
private fun CreatePostSection(
    profile: SocialProfile,
    onCreatePost: () -> Unit,
    onAddPhoto: () -> Unit,
    onGoLive: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Create Post Input
            Surface(
                onClick = onCreatePost,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape
                    ) {
                        AsyncImage(
                            model = profile.userProfileImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = "What's on your mind?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            // Quick Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CreateActionButton(
                    icon = Lucide.ImageIcon,
                    label = "Photo",
                    color = Color(0xFF45BD62),
                    onClick = onAddPhoto
                )

                CreateActionButton(
                    icon = Lucide.Video,
                    label = "Video",
                    color = Color(0xFFE7554C),
                    onClick = { /* Add video */ }
                )

                CreateActionButton(
                    icon = Icons.Filled.Videocam,
                    label = "Live",
                    color = Color(0xFFFC636B),
                    onClick = onGoLive
                )
            }
        }
    }
}

@Composable
private fun CreateActionButton(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ContentTabsSection(
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        divider = {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )
        }
    ) {
        ProfileTab.entries.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                modifier = Modifier.height(56.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = if (selectedTab == tab) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                    Text(
                        text = tab.displayName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (selectedTab == tab) {
                                FontWeight.SemiBold
                            } else {
                                FontWeight.Normal
                            }
                        ),
                        color = if (selectedTab == tab) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailedAboutSection(profile: SocialProfile) {
    // Implement detailed about with all info
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "About",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        // Detailed info cards
        // This can be expanded similar to IntroSection
    }
}

@Composable
private fun FriendsGridSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "All Friends",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Full friends grid
    }
}

@Composable
private fun PhotosGridSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "All Photos",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Full photos grid
    }
}

@Composable
private fun EmptyPostsState(isOwnProfile: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Article,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
            Text(
                text = if (isOwnProfile) {
                    "No posts yet\nShare your first post!"
                } else {
                    "No posts to show"
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun formatCount(count: Int): String {
    return when {
        count >= 1000000 -> "${count / 1000000}M"
        count >= 1000 -> String.format(Locale.US, "%.1fK", count / 1000.0)
        else -> count.toString()
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(56.dp),
                strokeWidth = 5.dp
            )
            Text(
                text = "Loading profile...",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ModernErrorState(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(32.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.15f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.error
                )

                Text(
                    text = "Oops!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.error
                )

                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = onRetry,
                    modifier = Modifier.height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Try Again")
                }
            }
        }
    }
}

@Composable
private fun StoryHighlightsSection(highlights: List<StoryHighlight>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Story Highlights",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Add New Highlight
            item {
                AddHighlightCard()
            }

            // Existing Highlights from UI State
            items(highlights) { highlight ->
                StoryHighlightCard(
                    title = highlight.title,
                    imageUrl = highlight.coverImage
                )
            }
        }
    }
}

@Composable
private fun AddHighlightCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            modifier = Modifier
                .width(80.dp)
                .height(160.dp), // 2:4 ratio (1:2 = width:height)
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Lucide.Plus,
                    contentDescription = "Add Highlight",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = "New",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StoryHighlightCard(
    title: String,
    imageUrl: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(160.dp) // 2:4 ratio (1:2 = width:height)
        ) {
            // Gradient Border
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    )
                    .padding(3.dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
