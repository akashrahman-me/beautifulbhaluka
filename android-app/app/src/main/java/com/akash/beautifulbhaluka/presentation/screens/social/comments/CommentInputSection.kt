package com.akash.beautifulbhaluka.presentation.screens.social.comments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.Comment
import com.composables.icons.lucide.ImagePlus
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mic
import com.composables.icons.lucide.X

/**
 * Ultra-modern Comment Input Section
 *
 * Features:
 * - Clean, minimalistic design
 * - Reply indicator with cancel option
 * - Image upload support
 * - Voice comment recording (simple click-based)
 * - Smooth animations
 * - Professional appearance
 */
@Composable
fun CommentInputSection(
    commentText: String,
    isSubmitting: Boolean,
    replyingTo: Comment?,
    selectedImages: List<String> = emptyList(),
    isRecordingVoice: Boolean = false,
    recordingDuration: Long = 0L,
    onTextChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancelReply: () -> Unit,
    onImagePick: () -> Unit = {},
    onRemoveImage: (String) -> Unit = {},
    onStartVoiceRecording: () -> Unit = {},
    onStopVoiceRecording: () -> Unit = {},
    onCancelVoiceRecording: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
        )

        // Reply Indicator
        AnimatedVisibility(
            visible = replyingTo != null,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            replyingTo?.let {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    text = "Replying to ${it.userName}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = it.content.take(50) + if (it.content.length > 50) "..." else "",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 12.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }

                        IconButton(
                            onClick = onCancelReply,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Cancel reply",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Image Preview Section
        AnimatedVisibility(
            visible = selectedImages.isNotEmpty(),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedImages) { imageUrl ->
                    Box(
                        modifier = Modifier.size(80.dp)
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Selected image",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentScale = ContentScale.Crop
                        )

                        // Remove button
                        IconButton(
                            onClick = { onRemoveImage(imageUrl) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(24.dp)
                                .offset(x = 4.dp, y = (-4).dp)
                                .background(
                                    MaterialTheme.colorScheme.error,
                                    CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Remove image",
                                modifier = Modifier.size(14.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Voice Recording Indicator
        AnimatedVisibility(
            visible = isRecordingVoice,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Animated recording dot with pulsing effect
                        val infiniteTransition =
                            rememberInfiniteTransition(label = "recording_pulse")
                        val scale by infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = 1.3f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(600, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "pulse_scale"
                        )
                        val alpha by infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = 0.5f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(600, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "pulse_alpha"
                        )

                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .scale(scale)
                                .alpha(alpha)
                                .background(
                                    MaterialTheme.colorScheme.error,
                                    CircleShape
                                )
                        )

                        Column {
                            Text(
                                text = "Recording voice...",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = formatDuration(recordingDuration),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Cancel button
                        IconButton(
                            onClick = onCancelVoiceRecording,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Cancel recording",
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }

                        // Send/Stop button
                        FilledIconButton(
                            onClick = onStopVoiceRecording,
                            modifier = Modifier.size(36.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send voice",
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }

        // Input Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Input Box with Icons Inside
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left side icons (Image & Voice) - Always visible to prevent blinking
                    Row(
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy((-4).dp)
                    ) {
                        // Image Upload Button - Always rendered
                        IconButton(
                            onClick = onImagePick,
                            modifier = Modifier
                                .size(40.dp)
                                .alpha(if (isRecordingVoice) 0.3f else 1f),
                            enabled = !isSubmitting && !isRecordingVoice
                        ) {
                            Icon(
                                imageVector = Lucide.ImagePlus,
                                contentDescription = "Add image",
                                modifier = Modifier.size(20.dp),
                                tint = if (selectedImages.isNotEmpty()) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }

                        // Voice Recording Button - Simple click to start recording
                        val isVoiceEnabled =
                            commentText.isBlank() && selectedImages.isEmpty() && !isSubmitting

                        IconButton(
                            onClick = {
                                if (!isRecordingVoice) {
                                    onStartVoiceRecording()
                                }
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .alpha(
                                    when {
                                        isRecordingVoice -> 0.3f
                                        isVoiceEnabled -> 1f
                                        else -> 0.75f
                                    }
                                ),
                            enabled = isVoiceEnabled && !isRecordingVoice
                        ) {
                            Icon(
                                imageVector = Lucide.Mic,
                                contentDescription = "Record voice",
                                modifier = Modifier.size(20.dp),
                                tint = if (isVoiceEnabled) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                    }

                    // Text Input (without border)
                    BasicTextField(
                        value = commentText,
                        onValueChange = onTextChange,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                start = 4.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = 12.dp
                            ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (commentText.isNotBlank() && !isSubmitting) {
                                    onSubmit()
                                    keyboardController?.hide()
                                }
                            }
                        ),
                        enabled = !isSubmitting && !isRecordingVoice,
                        maxLines = 5,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (commentText.isEmpty()) {
                                    Text(
                                        text = if (replyingTo != null) "Write a reply..." else "Write a comment...",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 14.sp
                                        ),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                            alpha = 0.6f
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }

            // Send Button (always visible)
            FilledIconButton(
                onClick = {
                    if ((commentText.isNotBlank() || selectedImages.isNotEmpty()) && !isSubmitting) {
                        onSubmit()
                        keyboardController?.hide()
                    }
                },
                enabled = (commentText.isNotBlank() || selectedImages.isNotEmpty()) && !isSubmitting,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send comment",
                        modifier = Modifier.size(20.dp),
                        tint = if (commentText.isNotBlank() || selectedImages.isNotEmpty()) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

// Helper function to format recording duration
private fun formatDuration(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(java.util.Locale.getDefault(), "%02d:%02d", minutes, remainingSeconds)
}
