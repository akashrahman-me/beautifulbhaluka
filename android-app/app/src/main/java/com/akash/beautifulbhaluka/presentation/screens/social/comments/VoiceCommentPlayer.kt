package com.akash.beautifulbhaluka.presentation.screens.social.comments

import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pause
import com.composables.icons.lucide.Play
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.util.Locale

/**
 * Beautiful Voice Comment Player
 *
 * Features:
 * - Modern waveform-style visualization
 * - Play/Pause control
 * - Duration display
 * - Gradient background
 * - Smooth animations
 */
@Composable
fun VoiceCommentPlayer(
    voiceUrl: String,
    duration: Long,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0L) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var actualDuration by remember { mutableStateOf(duration) }

    // Waveform animation
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val waveformAnimations = List(20) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 400 + (index * 50),
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "wave_$index"
        )
    }

    // Update progress while playing
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (isActive && isPlaying) {
                mediaPlayer?.let { player ->
                    currentPosition = player.currentPosition.toLong()
                    // Update actual duration from MediaPlayer
                    if (actualDuration == duration && player.duration > 0) {
                        actualDuration = player.duration.toLong()
                    }
                    if (currentPosition >= actualDuration) {
                        isPlaying = false
                        currentPosition = 0L
                        player.seekTo(0)
                    }
                }
                delay(100)
            }
        }
    }

    // Cleanup
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play/Pause Button
                FilledIconButton(
                    onClick = {
                        if (isPlaying) {
                            mediaPlayer?.pause()
                            isPlaying = false
                        } else {
                            if (mediaPlayer == null) {
                                try {
                                    mediaPlayer = MediaPlayer().apply {
                                        setDataSource(voiceUrl)
                                        prepare()
                                        start()
                                    }
                                    isPlaying = true
                                } catch (_: Exception) {
                                    // Handle error - could show a snackbar
                                }
                            } else {
                                mediaPlayer?.start()
                                isPlaying = true
                            }
                        }
                    },
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (isPlaying) Lucide.Pause else Lucide.Play,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                // Waveform Visualization
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    waveformAnimations.forEachIndexed { index, animation ->
                        val progress = if (actualDuration > 0) {
                            currentPosition.toFloat() / actualDuration.toFloat()
                        } else {
                            0f
                        }
                        val isPast = (index.toFloat() / waveformAnimations.size) < progress

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(
                                    if (isPlaying) animation.value * 0.8f else 0.5f
                                )
                                .clip(RoundedCornerShape(2.dp))
                                .background(
                                    if (isPast) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                                    }
                                )
                        )
                    }
                }

                // Duration
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formatDuration(if (isPlaying) currentPosition else actualDuration),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (!isPlaying) {
                        Text(
                            text = "Voice",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

private fun formatDuration(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format(Locale.getDefault(), "%d:%02d", minutes, remainingSeconds)
}

