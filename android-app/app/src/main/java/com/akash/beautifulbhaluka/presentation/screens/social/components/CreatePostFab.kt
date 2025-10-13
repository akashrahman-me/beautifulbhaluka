package com.akash.beautifulbhaluka.presentation.screens.social.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Ultra-modern, minimalistic Floating Action Button for creating posts
 *
 * Design principles:
 * - Clean extended FAB design
 * - Professional elevation and shadows
 * - Smooth Material 3 styling
 * - Clear call-to-action
 */
@Composable
fun CreatePostFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp,
            hoveredElevation = 8.dp
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = "নতুন পোস্ট",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "পোস্ট করুন",
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        )
    }
}
