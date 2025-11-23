package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun RatingSection(
    averageRating: Float,
    ratingCount: Int,
    userRating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (ratingCount > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = String.format(Locale.US, "%.1f", averageRating),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "/ ৫.০",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "($ratingCount জন রেটিং)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            RatingStarsVisualization(averageRating)

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (userRating > 0) "আপনার রেটিং" else "রেটিং দিন",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            InteractiveRatingBar(
                rating = userRating,
                onRatingChange = onRatingChange
            )
        }
    }
}

@Composable
fun RatingStarsVisualization(
    rating: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val starRating = (rating - index).coerceIn(0f, 1f)
            Icon(
                imageVector = when {
                    starRating >= 1f -> Icons.Filled.Star
                    starRating > 0f -> Icons.Filled.Star
                    else -> Icons.Outlined.StarOutline
                },
                contentDescription = null,
                tint = when {
                    starRating >= 1f -> MaterialTheme.colorScheme.primary
                    starRating > 0f -> MaterialTheme.colorScheme.primary.copy(alpha = starRating)
                    else -> MaterialTheme.colorScheme.outlineVariant
                },
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun InteractiveRatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxStars: Int = 5
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            Surface(
                onClick = { onRatingChange(i) },
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(8.dp),
                color = if (i <= rating) {
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "রেটিং $i",
                        tint = if (i <= rating) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (rating > 0) {
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "$rating★",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    }
}

