package com.akash.beautifulbhaluka.presentation.screens.social.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Modern post creation bar with avatar, input field, and image icon
 *
 * Design:
 * - Transparent background
 * - Avatar on the left
 * - Input field hint in the middle
 * - Image icon on the right
 * - No borders or background colors on the button itself
 */
@Composable
fun CreatePostBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "আ",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Input field (fake - just for visual)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "আপনার মনের কথা শেয়ার করুন...",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 15.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }

        // Image icon
        Icon(
            imageVector = Icons.Outlined.Image,
            contentDescription = "ছবি যোগ করুন",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
