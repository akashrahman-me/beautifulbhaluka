package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A modern, animated collapsible section component that can expand and collapse content.
 *
 * This component provides a professional UI pattern for organizing content in expandable sections
 * with smooth animations, status indicators, and modern Material 3 styling.
 *
 * @param title The header text displayed in the collapsible section
 * @param content The content to be displayed when the section is expanded (supports any Composable)
 * @param isExpanded Whether the section is currently expanded or collapsed
 * @param onToggle Callback function invoked when the user taps to expand/collapse the section
 * @param modifier Modifier for customizing the component's appearance and behavior
 * @param enabled Whether the section can be interacted with (default: true)
 * @param showStatusIndicator Whether to show the colored status dot indicator (default: true)
 * @param animationDurationMs Duration of the expand/collapse animation in milliseconds (default: 300)
 *
 * @sample
 * ```
 * var isExpanded by remember { mutableStateOf(false) }
 *
 * CollapsibleSection(
 *     title = "Section Title",
 *     content = {
 *         Text("This is the expandable content")
 *     },
 *     isExpanded = isExpanded,
 *     onToggle = { isExpanded = !isExpanded }
 * )
 * ```
 *
 * Features:
 * - Smooth expand/collapse animations with customizable duration
 * - Modern Material 3 design with dynamic colors
 * - Status indicator dot that changes color based on expansion state
 * - Animated arrow icon rotation
 * - Gradient divider when expanded
 * - Accessibility support with proper content descriptions
 * - Fully customizable content area
 */
@Composable
fun CollapsibleSection(
    title: String,
    content: @Composable () -> Unit,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showStatusIndicator: Boolean = true,
    animationDurationMs: Int = 300
) {
    // Animated rotation for the arrow icon
    val arrowRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = animationDurationMs),
        label = "arrow_rotation"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isExpanded) 3.dp else 2.dp
        )
    ) {
        Column {
            // Header section with title and toggle button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = enabled) { onToggle() }
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side with optional status indicator and title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Optional status indicator
                    if (showStatusIndicator) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (isExpanded) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = (-0.2).sp
                        ),
                        color = if (isExpanded) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }

                // Expand/collapse icon button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isExpanded) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse section" else "Expand section",
                        modifier = Modifier
                            .padding(6.dp)
                            .rotate(arrowRotation),
                        tint = if (isExpanded) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        }
                    )
                }
            }

            // Expandable content area with smooth animation
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(animationDurationMs)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(animationDurationMs)) + fadeOut()
            ) {
                Column {
                    // Gradient divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(end = 20.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    )
                                )
                            )
                    )

                    // Content area
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}
