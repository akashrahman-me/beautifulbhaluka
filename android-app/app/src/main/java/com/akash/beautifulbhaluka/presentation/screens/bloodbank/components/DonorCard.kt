package com.akash.beautifulbhaluka.presentation.screens.bloodbank.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akash.beautifulbhaluka.presentation.screens.bloodbank.DonorInfo
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Phone
import com.composables.icons.lucide.MapPin
import com.composables.icons.lucide.Clock
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.Facebook
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.PencilLine
import com.composables.icons.lucide.Trash2

@Composable
fun DonorCard(
    donor: DonorInfo,
    onCallClick: () -> Unit,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    showActions: Boolean = true,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(100, easing = LinearEasing),
        label = "scale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color(0xFFE53935).copy(alpha = 0.15f)
            )
            .animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Section with Profile and Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Gradient Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = CircleShape,
                                    spotColor = Color(0xFFE53935).copy(alpha = 0.2f)
                                )
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFFFF6B6B),
                                            Color(0xFFE53935)
                                        )
                                    ),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Lucide.User,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(36.dp)
                                    .align(Alignment.Center)
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = donor.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            // Status Badge with Icon
                            val isEligible = donor.status == "সময় হয়েছে"
                            val statusColor =
                                if (isEligible) Color(0xFF4CAF50) else Color(0xFFE53935)
                            val statusTextColor =
                                if (isEligible) Color(0xFF2E7D32) else Color(0xFFB71C1C)

                            Surface(
                                color = statusColor.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.wrapContentWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    ),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(
                                                statusColor,
                                                CircleShape
                                            )
                                    )
                                    Text(
                                        text = donor.status,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = statusTextColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }

                    // Edit icon button at top right (only shown when onEditClick is provided)
                    if (onEditClick != null) {
                        IconButton(
                            onClick = { onEditClick.invoke() },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Lucide.PencilLine,
                                contentDescription = "Edit",
                                tint = Color(0xFF2196F3),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }


                // Info Grid Section
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Blood Group - Prominent Display
                    Surface(
                        color = Color(0xFFE53935).copy(alpha = 0.08f),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🩸",
                                fontSize = 28.sp
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "রক্তের গ্রুপ",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = donor.bloodGroup,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = Color(0xFFE53935),
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }

                    // Contact Info
                    InfoRow(
                        icon = Lucide.Phone,
                        label = "যোগাযোগ",
                        value = donor.phone,
                        iconTint = Color(0xFF4CAF50)
                    )

                    // Location Info
                    InfoRow(
                        icon = Lucide.MapPin,
                        label = "ঠিকানা",
                        value = donor.location,
                        iconTint = Color(0xFF2196F3)
                    )

                    // Last Donation Info
                    InfoRow(
                        icon = Lucide.Clock,
                        label = "সর্বশেষ রক্তদান",
                        value = "${donor.lastDonation} আগে",
                        iconTint = Color(0xFFFF9800)
                    )

                    // Last Donation Date
                    InfoRow(
                        icon = Lucide.Calendar,
                        label = "রক্তদান তারিখ",
                        value = donor.lastDonationDate,
                        iconTint = Color(0xFF9C27B0)
                    )

                    // Social Links (if available)
                    if (!donor.facebookLink.isNullOrEmpty() || !donor.whatsappNumber.isNullOrEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (!donor.facebookLink.isNullOrEmpty()) {
                                SocialChip(
                                    icon = Lucide.Facebook,
                                    label = "Facebook",
                                    color = Color(0xFF1877F2),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (!donor.whatsappNumber.isNullOrEmpty()) {
                                SocialChip(
                                    icon = Lucide.MessageCircle,
                                    label = "WhatsApp",
                                    color = Color(0xFF25D366),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // Add spacer if only one social link to maintain grid layout
                            if (donor.facebookLink.isNullOrEmpty() || donor.whatsappNumber.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                // Action Buttons with Gradient
                if (showActions) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Call Button with Gradient
                        Button(
                            onClick = onCallClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 2.dp
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF4CAF50),
                                                Color(0xFF66BB6A)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Lucide.Phone,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = Color.White
                                    )
                                    Text(
                                        text = "Call Now",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }

                        // Delete Button (only shown when onDeleteClick is provided)
                        if (onDeleteClick != null) {
                            OutlinedButton(
                                onClick = { onDeleteClick.invoke() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFFE53935)
                                ),
                                border = androidx.compose.foundation.BorderStroke(
                                    width = 1.5.dp,
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFFE53935), Color(0xFFFF6B6B))
                                    )
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Lucide.Trash2,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Delete",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = iconTint.copy(alpha = 0.12f),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(40.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SocialChip(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

