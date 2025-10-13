package com.akash.beautifulbhaluka.presentation.screens.social.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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

/**
 * Ultra-modern Comments Screen
 *
 * Features:
 * - Clean, minimalistic design following Material 3 guidelines
 * - Nested replies with visual hierarchy
 * - Like/unlike comments and replies
 * - Professional spacing and typography
 * - Smooth animations and interactions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    viewModel: CommentsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CommentsTopBar(
                commentsCount = uiState.comments.size,
                onNavigateBack = onNavigateBack
            )
        },
        bottomBar = {
            CommentInputSection(
                commentText = uiState.commentText,
                isSubmitting = uiState.isSubmitting,
                replyingTo = uiState.replyingTo,
                onTextChange = { viewModel.onAction(CommentsAction.UpdateCommentText(it)) },
                onSubmit = { viewModel.onAction(CommentsAction.SubmitComment) },
                onCancelReply = { viewModel.onAction(CommentsAction.CancelReply) }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.comments.isEmpty() -> {
                    LoadingState()
                }
                uiState.error != null && uiState.comments.isEmpty() -> {
                    ErrorState(
                        errorMessage = uiState.error ?: "একটি ত্রুটি হয়েছে",
                        onRetry = { viewModel.onAction(CommentsAction.LoadComments) }
                    )
                }
                uiState.comments.isEmpty() -> {
                    EmptyCommentsState()
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(uiState.comments, key = { it.id }) { comment ->
                            CommentCard(
                                comment = comment,
                                onLikeClick = {
                                    if (comment.isLiked) {
                                        viewModel.onAction(CommentsAction.UnlikeComment(comment.id))
                                    } else {
                                        viewModel.onAction(CommentsAction.LikeComment(comment.id))
                                    }
                                },
                                onReplyClick = {
                                    viewModel.onAction(CommentsAction.StartReply(comment))
                                },
                                onDeleteClick = {
                                    viewModel.onAction(CommentsAction.DeleteComment(comment.id))
                                },
                                onReplyLikeClick = { replyId, isLiked ->
                                    if (isLiked) {
                                        viewModel.onAction(CommentsAction.UnlikeComment(replyId))
                                    } else {
                                        viewModel.onAction(CommentsAction.LikeComment(replyId))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommentsTopBar(
    commentsCount: Int,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "মন্তব্য",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                if (commentsCount > 0) {
                    Text(
                        text = "$commentsCount টি মন্তব্য",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
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
                text = "মন্তব্য লোড হচ্ছে...",
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
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )

            Text(
                text = "কিছু ভুল হয়েছে",
                style = MaterialTheme.typography.titleLarge.copy(
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
                modifier = Modifier.height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("পুনরায় চেষ্টা করুন")
            }
        }
    }
}

@Composable
private fun EmptyCommentsState() {
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
                imageVector = Icons.Outlined.ChatBubbleOutline,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )

            Text(
                text = "কোনো মন্তব্য নেই",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "প্রথম মন্তব্য করুন",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
