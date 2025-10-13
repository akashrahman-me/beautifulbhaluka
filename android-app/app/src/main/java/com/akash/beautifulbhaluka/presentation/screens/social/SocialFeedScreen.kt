package com.akash.beautifulbhaluka.presentation.screens.social

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.screens.social.components.CreatePostFab
import com.akash.beautifulbhaluka.presentation.screens.social.components.PostCard

/**
 * Ultra-modern Social Feed Screen
 *
 * Features:
 * - Clean, minimalistic design
 * - Elegant loading and empty states
 * - Professional spacing and typography
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialFeedScreen(
    viewModel: SocialFeedViewModel = viewModel(),
    onCreatePostClick: () -> Unit = {},
    onNavigateToComments: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            CreatePostFab(
                onClick = onCreatePostClick
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.posts.isEmpty() -> {
                    LoadingState()
                }
                uiState.error != null && uiState.posts.isEmpty() -> {
                    ErrorState(
                        errorMessage = uiState.error ?: "একটি ত্রুটি হয়েছে",
                        onRetry = { viewModel.onAction(SocialFeedAction.LoadPosts) }
                    )
                }
                uiState.posts.isEmpty() -> {
                    EmptyState(
                        onCreatePost = onCreatePostClick
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 88.dp)
                    ) {
                        // Refresh indicator
                        if (uiState.isRefreshing) {
                            item {
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        items(uiState.posts, key = { it.id }) { post ->
                            PostCard(
                                post = post,
                                onLikeClick = {
                                    if (post.isLiked) {
                                        viewModel.onAction(SocialFeedAction.UnlikePost(post.id))
                                    } else {
                                        viewModel.onAction(SocialFeedAction.LikePost(post.id))
                                    }
                                },
                                onCommentClick = {
                                    onNavigateToComments(post.id)
                                },
                                onShareClick = {
                                    viewModel.onAction(SocialFeedAction.SharePost(post.id))
                                },
                                onProfileClick = {
                                    viewModel.onAction(SocialFeedAction.NavigateToProfile(post.userId))
                                },
                                onDeleteClick = if (post.userId == "current_user_id") {
                                    { viewModel.onAction(SocialFeedAction.DeletePost(post.id)) }
                                } else null
                            )
                        }
                    }
                }
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 4.dp
            )
            Text(
                text = "পোস্ট লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
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
                text = "কিছু ভুল হয়েছে",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            FilledTonalButton(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "পুনরায় চেষ্টা করুন",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Composable
private fun EmptyState(
    onCreatePost: () -> Unit
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
                imageVector = Icons.Outlined.DynamicFeed,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )

            Text(
                text = "কোনো পোস্ট নেই",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "এখনও কোনো পোস্ট শেয়ার করা হয়নি।\nপ্রথম পোস্টটি আপনিই করুন!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 24.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCreatePost,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(52.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "নতুন পোস্ট করুন",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
