package com.akash.beautifulbhaluka.presentation.screens.social.comments

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.akash.beautifulbhaluka.domain.model.Comment
import java.text.SimpleDateFormat
import java.util.*

/**
 * Ultra-modern Comment Card Component
 *
 * Features:
 * - Clean, minimalistic design with Material 3
 * - Support for nested replies with visual hierarchy
 * - Show first 2 replies by default, expand to see all
 * - Like/unlike with smooth animations
 * - Reply functionality
 * - Delete option for own comments
 * - Professional spacing and typography
 */
@Composable
fun CommentCard(
    comment: Comment,
    onLikeClick: () -> Unit,
    onReplyClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isReply: Boolean = false,
    onReplyLikeClick: (commentId: String, isLiked: Boolean) -> Unit = { _, _ -> }
) {
    var showMenu by remember { mutableStateOf(false) }
    var showAllReplies by remember { mutableStateOf(false) }

    // Smooth like animation
    val likeColor by animateColorAsState(
        targetValue = if (comment.isLiked) Color(0xFFED4956) else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 200),
        label = "like_color"
    )

    val likeScale by animateFloatAsState(
        targetValue = if (comment.isLiked) 1.1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "like_scale"
    )

    // Determine which replies to show
    val repliesToShow = remember(comment.replies, showAllReplies) {
        if (showAllReplies || comment.replies.size <= 2) {
            comment.replies
        } else {
            comment.replies.take(2)
        }
    }

    val hasHiddenReplies = comment.replies.size > 2

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (isReply) 56.dp else 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Profile Image
            AsyncImage(
                model = comment.userProfileImage,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Comment Content Bubble
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        // User Name
                        Text(
                            text = comment.userName,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Comment Text
                        Text(
                            text = comment.content,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 20.sp,
                                fontSize = 14.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Action Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Time
                    Text(
                        text = formatTimeAgo(comment.createdAt),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Like Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clickable(
                                onClick = onLikeClick,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    ) {
                        Text(
                            text = if (comment.isLiked) "পছন্দ হয়েছে" else "পছন্দ করুন",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = likeColor,
                            modifier = Modifier.scale(likeScale)
                        )

                        if (comment.likes > 0) {
                            Text(
                                text = "(${comment.likes})",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Reply Button
                    if (!isReply) {
                        Text(
                            text = "উত্তর দিন",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.clickable(
                                onClick = onReplyClick,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // More Options
                    Box {
                        IconButton(
                            onClick = { showMenu = true },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.MoreHoriz,
                                contentDescription = "More options",
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("মুছে ফেলুন", fontSize = 14.sp) },
                                onClick = {
                                    showMenu = false
                                    onDeleteClick()
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("রিপোর্ট করুন", fontSize = 14.sp) },
                                onClick = { showMenu = false },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Flag,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Nested Replies Section
        if (!isReply && comment.replies.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                // Show first 2 or all replies based on state
                repliesToShow.forEach { reply ->
                    CommentCard(
                        comment = reply,
                        onLikeClick = {
                            // Call the reply-specific like handler with the reply's ID
                            onReplyLikeClick(reply.id, reply.isLiked)
                        },
                        onReplyClick = { },
                        onDeleteClick = onDeleteClick,
                        isReply = true,
                        onReplyLikeClick = onReplyLikeClick
                    )
                }

                // Show more/less toggle button
                if (hasHiddenReplies) {
                    Row(
                        modifier = Modifier
                            .padding(start = 56.dp, top = 8.dp, bottom = 4.dp)
                            .clickable(
                                onClick = { showAllReplies = !showAllReplies },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (showAllReplies)
                                Icons.Outlined.KeyboardArrowUp
                            else
                                Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = if (showAllReplies) {
                                "কম উত্তর দেখুন"
                            } else {
                                "${comment.replies.size - 2} টি আরও উত্তর দেখুন"
                            },
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Format timestamp to relative time
 */
private fun formatTimeAgo(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> "এখনই"
        diff < 3600_000 -> "${diff / 60_000} মিনিট আগে"
        diff < 86400_000 -> "${diff / 3600_000} ঘন্টা আগে"
        diff < 604800_000 -> "${diff / 86400_000} দিন আগে"
        diff < 2592000_000 -> "${diff / 604800_000} সপ্তাহ আগে"
        else -> SimpleDateFormat("dd MMM", Locale("bn")).format(Date(timestamp))
    }
}
