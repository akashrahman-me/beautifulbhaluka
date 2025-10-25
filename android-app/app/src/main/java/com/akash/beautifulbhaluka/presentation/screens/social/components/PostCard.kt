package com.akash.beautifulbhaluka.presentation.screens.social.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.Post
import com.akash.beautifulbhaluka.domain.model.PostPrivacy
import com.akash.beautifulbhaluka.domain.model.Reaction
import com.akash.beautifulbhaluka.presentation.screens.social.comments.ReactionPicker
import java.text.SimpleDateFormat
import java.util.*

/**
 * Ultra-modern, minimalistic Post Card Component
 *
 * Design principles:
 * - Clean spacing with consistent 16dp rhythm
 * - Subtle elevation without heavy shadows
 * - Smooth micro-interactions
 * - Clear visual hierarchy
 * - Professional typography with proper weights
 */
@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    post: Post,
    onLikeClick: () -> Unit,
    onReactionSelected: (Reaction) -> Unit = {},
    onCustomEmojiSelected: (String, String) -> Unit = { _, _ -> },
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onProfileClick: () -> Unit,
    onDeleteClick: (() -> Unit)? = null
) {
    var showMenu by remember { mutableStateOf(false) }

    // Smooth like animation
    val likeScale by animateFloatAsState(
        targetValue = if (post.isLiked) 1f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "like_scale"
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // Header Section
            PostHeader(
                post = post,
                showMenu = showMenu,
                onShowMenuChange = { showMenu = it },
                onProfileClick = onProfileClick,
                onDeleteClick = onDeleteClick
            )

            // Content Section
            if (post.content.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        lineHeight = 24.sp,
                        letterSpacing = 0.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Location
            if (post.location != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = post.location,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Images
            if (post.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                PostImagesGrid(images = post.images)
            }

            // Stats Section
            if (post.likes > 0 || post.comments > 0 || post.shares > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                PostStats(
                    likes = post.likes,
                    comments = post.comments,
                    shares = post.shares,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Divider
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            PostActions(
                post = post,
                onLikeClick = onLikeClick,
                onReactionSelected = onReactionSelected,
                onCustomEmojiSelected = onCustomEmojiSelected,
                onCommentClick = onCommentClick,
                onShareClick = onShareClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }

    // Add subtle bottom divider between posts
    HorizontalDivider(
        thickness = 8.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    )
}

@Composable
private fun PostHeader(
    post: Post,
    showMenu: Boolean,
    onShowMenuChange: (Boolean) -> Unit,
    onProfileClick: () -> Unit,
    onDeleteClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable(
                    onClick = onProfileClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            AsyncImage(
                model = post.userProfileImage,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = post.userName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = formatTimeAgo(post.createdAt),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Icon(
                        imageVector = when (post.privacy) {
                            PostPrivacy.PUBLIC -> Icons.Outlined.Public
                            PostPrivacy.FRIENDS -> Icons.Outlined.People
                            PostPrivacy.ONLY_ME -> Icons.Outlined.Lock
                        },
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Menu Button
        Box {
            IconButton(
                onClick = { onShowMenuChange(true) },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More options",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { onShowMenuChange(false) }
            ) {
                if (onDeleteClick != null) {
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            onShowMenuChange(false)
                            onDeleteClick()
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Delete, contentDescription = null)
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text("Save") },
                    onClick = { onShowMenuChange(false) },
                    leadingIcon = {
                        Icon(Icons.Outlined.BookmarkBorder, contentDescription = null)
                    }
                )
                DropdownMenuItem(
                    text = { Text("Report") },
                    onClick = { onShowMenuChange(false) },
                    leadingIcon = {
                        Icon(Icons.Outlined.Flag, contentDescription = null)
                    }
                )
            }
        }
    }
}

@Composable
private fun PostStats(
    likes: Int,
    comments: Int,
    shares: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (likes > 0) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text = formatCount(likes),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (comments > 0) {
                Text(
                    text = "$comments comments",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (shares > 0) {
                Text(
                    text = "$shares shares",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PostActions(
    post: Post,
    onLikeClick: () -> Unit,
    onReactionSelected: (Reaction) -> Unit,
    onCustomEmojiSelected: (String, String) -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showReactionPicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Like button with reaction support
        Box(modifier = Modifier.weight(1f)) {
            PostActionButtonWithReaction(
                post = post,
                onLikeClick = onLikeClick,
                onLongPress = { showReactionPicker = true },
                modifier = Modifier.fillMaxWidth()
            )

            // Reaction Picker Popup
            if (showReactionPicker) {
                ReactionPicker(
                    onReactionSelected = { reaction ->
                        onReactionSelected(reaction)
                    },
                    onCustomEmojiSelected = { emoji, label ->
                        onCustomEmojiSelected(emoji, label)
                    },
                    onDismiss = { showReactionPicker = false }
                )
            }
        }

        PostActionButton(
            icon = Icons.Outlined.ChatBubbleOutline,
            text = "Comment",
            onClick = onCommentClick,
            modifier = Modifier.weight(1f)
        )

        PostActionButton(
            icon = Icons.Outlined.Share,
            text = "Share",
            onClick = onShareClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostActionButtonWithReaction(
    post: Post,
    onLikeClick: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    // Determine the color based on reaction
    val iconColor by animateColorAsState(
        targetValue = when {
            post.customReactionEmoji != null -> MaterialTheme.colorScheme.primary
            post.userReaction != null -> when (post.userReaction) {
                Reaction.LOVE -> Color(0xFFED4956)
                Reaction.HAHA -> Color(0xFFF7B125)
                Reaction.WOW -> Color(0xFFF7B125)
                Reaction.SAD -> Color(0xFFF7B125)
                Reaction.ANGRY -> Color(0xFFF05545)
                else -> if (post.isLiked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            }
            post.isLiked -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(200),
        label = "icon_color"
    )

    val scale by animateFloatAsState(
        targetValue = if (post.userReaction != null || post.isLiked || post.customReactionEmoji != null) 1.1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "reaction_scale"
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .combinedClickable(
                onClick = onLikeClick,
                onLongClick = onLongPress,
                indication = null,
                interactionSource = interactionSource
            )
            .padding(vertical = 8.dp)
    ) {
        // Show reaction emoji or thumbs up icon
        when {
            post.customReactionEmoji != null -> {
                Text(
                    text = post.customReactionEmoji,
                    fontSize = 20.sp,
                    modifier = Modifier.scale(scale)
                )
            }
            post.userReaction != null -> {
                Text(
                    text = post.userReaction.emoji,
                    fontSize = 20.sp,
                    modifier = Modifier.scale(scale)
                )
            }
            else -> {
                Icon(
                    imageVector = if (post.isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUpOffAlt,
                    contentDescription = "Like",
                    modifier = Modifier.size(20.dp).scale(scale),
                    tint = iconColor
                )
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = when {
                post.customReactionLabel != null -> post.customReactionLabel
                post.userReaction != null -> post.userReaction.label
                else -> "Like"
            },
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            color = iconColor
        )
    }
}

@Composable
fun PostActionButton(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(100),
        label = "button_scale"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isActive) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(200),
        label = "icon_color"
    )

    Surface(
        onClick = onClick,
        modifier = modifier.scale(scale),
        color = Color.Transparent,
        interactionSource = interactionSource
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(20.dp),
                tint = iconColor
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = iconColor
            )
        }
    }
}

@Composable
fun PostImagesGrid(images: List<String>) {
    when (images.size) {
        1 -> {
            AsyncImage(
                model = images[0],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .clip(RoundedCornerShape(0.dp)),
                contentScale = ContentScale.Crop
            )
        }
        2 -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                images.forEach { image ->
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        3 -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                AsyncImage(
                    model = images[0],
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    AsyncImage(
                        model = images[1],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(124.dp),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = images[2],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(124.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                AsyncImage(
                    model = images[0],
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    images.drop(1).take(2).forEachIndexed { index, image ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(150.dp)
                        ) {
                            AsyncImage(
                                model = image,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            if (index == 1 && images.size > 3) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.6f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+${images.size - 3}",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = Color.White
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

// Helper Functions
fun formatTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> "Just now"
        diff < 3600_000 -> "${diff / 60_000}m"
        diff < 86400_000 -> "${diff / 3600_000}h"
        diff < 604800_000 -> "${diff / 86400_000}d"
        else -> {
            val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

fun formatCount(count: Int): String {
    return when {
        count < 1000 -> count.toString()
        count < 10000 -> String.format("%.1fK", count / 1000.0)
        count < 1000000 -> "${count / 1000}K"
        else -> String.format("%.1fM", count / 1000000.0)
    }
}
