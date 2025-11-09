package com.akash.beautifulbhaluka.presentation.screens.social.comments

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.domain.model.Reaction

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
    viewModel: CommentsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Microphone permission launcher
    val microphonePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onAction(CommentsAction.OnMicrophonePermissionResult(isGranted))
    }

    // Handle permission request
    LaunchedEffect(uiState.needsMicrophonePermission) {
        if (uiState.needsMicrophonePermission) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                viewModel.onAction(CommentsAction.OnMicrophonePermissionResult(true))
            } else {
                microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

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
                selectedImages = uiState.selectedImages,
                isRecordingVoice = uiState.isRecordingVoice,
                recordingDuration = uiState.recordingDuration,
                onTextChange = { viewModel.onAction(CommentsAction.UpdateCommentText(it)) },
                onSubmit = { viewModel.onAction(CommentsAction.SubmitComment) },
                onCancelReply = { viewModel.onAction(CommentsAction.CancelReply) },
                onImagePick = { viewModel.onAction(CommentsAction.PickImage) },
                onRemoveImage = { viewModel.onAction(CommentsAction.RemoveImage(it)) },
                onStartVoiceRecording = { viewModel.onAction(CommentsAction.StartVoiceRecording) },
                onStopVoiceRecording = { viewModel.onAction(CommentsAction.StopVoiceRecording) },
                onCancelVoiceRecording = { viewModel.onAction(CommentsAction.CancelVoiceRecording) }
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
                        errorMessage = uiState.error ?: "An error occurred",
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
                                onReactionSelected = { reaction ->
                                    viewModel.onAction(
                                        CommentsAction.ReactToComment(
                                            comment.id,
                                            reaction
                                        )
                                    )
                                },
                                onCustomEmojiSelected = { emoji, label ->
                                    viewModel.onAction(
                                        CommentsAction.CustomReactToComment(
                                            comment.id,
                                            emoji,
                                            label
                                        )
                                    )
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
                                },
                                onReplyReactionSelected = { replyId, reaction ->
                                    viewModel.onAction(
                                        CommentsAction.ReactToComment(
                                            replyId,
                                            reaction
                                        )
                                    )
                                },
                                onReplyCustomEmojiSelected = { replyId, emoji, label ->
                                    viewModel.onAction(
                                        CommentsAction.CustomReactToComment(
                                            replyId,
                                            emoji,
                                            label
                                        )
                                    )
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
                    text = "Comments",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                if (commentsCount > 0) {
                    Text(
                        text = "$commentsCount comments",
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
                text = "Loading comments...",
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
                text = "Something went wrong",
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
                Text("Try again")
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
                text = "No comments yet",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Be the first to comment",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
