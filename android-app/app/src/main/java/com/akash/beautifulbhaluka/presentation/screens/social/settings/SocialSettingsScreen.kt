package com.akash.beautifulbhaluka.presentation.screens.social.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Ultra-Modern Social Settings Screen
 *
 * Design principles:
 * - Icon-first design with Material Icons
 * - Segmented sections with clear visual separation
 * - Smooth animations and transitions
 * - Professional color usage and spacing
 * - Card-based layout for better organization
 */
@Composable
fun SocialSettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var postNotifications by remember { mutableStateOf(true) }
    var commentNotifications by remember { mutableStateOf(true) }
    var likeNotifications by remember { mutableStateOf(false) }

    var privateAccount by remember { mutableStateOf(false) }
    var showOnlineStatus by remember { mutableStateOf(true) }
    var allowTagging by remember { mutableStateOf(true) }
    var showReadReceipts by remember { mutableStateOf(true) }

    var darkMode by remember { mutableStateOf(false) }
    var autoPlayVideos by remember { mutableStateOf(true) }
    var dataCompression by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "সেটিংস",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "আপনার সোশ্যাল অভিজ্ঞতা কাস্টমাইজ করুন",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Account Settings Section
        ModernSettingsSection(
            title = "অ্যাকাউন্ট",
            icon = Icons.Outlined.AccountCircle
        ) {
            ModernSwitchItem(
                icon = Icons.Outlined.Lock,
                title = "ব্যক্তিগত অ্যাকাউন্ট",
                subtitle = "শুধুমাত্র অনুমোদিত ব্যক্তিরা আপনার পোস্ট দেখতে পারবে",
                checked = privateAccount,
                onCheckedChange = { privateAccount = it }
            )

            ModernSwitchItem(
                icon = Icons.Outlined.Visibility,
                title = "অনলাইন স্ট্যাটাস",
                subtitle = "অন্যরা দেখতে পারবে আপনি অনলাইন আছেন কিনা",
                checked = showOnlineStatus,
                onCheckedChange = { showOnlineStatus = it }
            )

            ModernSwitchItem(
                icon = Icons.Outlined.Label,
                title = "ট্যাগিং অনুমতি দিন",
                subtitle = "অন্যরা আপনাকে পোস্টে ট্যাগ করতে পারবে",
                checked = allowTagging,
                onCheckedChange = { allowTagging = it }
            )

            ModernSwitchItem(
                icon = Icons.Outlined.DoneAll,
                title = "পঠিত রসিদ",
                subtitle = "প্রেরক দেখতে পারবে আপনি বার্তা পড়েছেন কিনা",
                checked = showReadReceipts,
                onCheckedChange = { showReadReceipts = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notification Settings Section
        ModernSettingsSection(
            title = "নোটিফিকেশন",
            icon = Icons.Outlined.Notifications
        ) {
            ModernSwitchItem(
                icon = Icons.Outlined.NotificationsActive,
                title = "সব নোটিফিকেশন",
                subtitle = "সকল নোটিফিকেশন সক্রিয়/নিষ্ক্রিয় করুন",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it },
                highlighted = true
            )

            if (notificationsEnabled) {
                ModernSwitchItem(
                    icon = Icons.Outlined.PostAdd,
                    title = "নতুন পোস্ট",
                    subtitle = "বন্ধুদের নতুন পোস্টের জন্য নোটিফিকেশন",
                    checked = postNotifications,
                    onCheckedChange = { postNotifications = it }
                )

                ModernSwitchItem(
                    icon = Icons.Outlined.Comment,
                    title = "কমেন্ট",
                    subtitle = "আপনার পোস্টে নতুন কমেন্টের জন্য",
                    checked = commentNotifications,
                    onCheckedChange = { commentNotifications = it }
                )

                ModernSwitchItem(
                    icon = Icons.Outlined.FavoriteBorder,
                    title = "লাইক",
                    subtitle = "আপনার পোস্টে লাইকের জন্য",
                    checked = likeNotifications,
                    onCheckedChange = { likeNotifications = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Appearance & Performance Section
        ModernSettingsSection(
            title = "চেহারা ও পারফরম্যান্স",
            icon = Icons.Outlined.Palette
        ) {
            ModernSwitchItem(
                icon = Icons.Outlined.DarkMode,
                title = "ডার্ক মোড",
                subtitle = "গাঢ় থিম সক্রিয় করুন",
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )

            ModernSwitchItem(
                icon = Icons.Outlined.PlayCircle,
                title = "অটো-প্লে ভিডিও",
                subtitle = "ভিডিও স্বয়ংক্রিয়ভাবে চালু হবে",
                checked = autoPlayVideos,
                onCheckedChange = { autoPlayVideos = it }
            )

            ModernSwitchItem(
                icon = Icons.Outlined.DataUsage,
                title = "ডেটা সংরক্ষণ মোড",
                subtitle = "কম ডেটা ব্যবহার করার জন্য ছবি সংকুচিত করুন",
                checked = dataCompression,
                onCheckedChange = { dataCompression = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Privacy & Security Section
        ModernSettingsSection(
            title = "গোপনীয়তা ও নিরাপত্তা",
            icon = Icons.Outlined.Security
        ) {
            ModernNavigationItem(
                icon = Icons.Outlined.Block,
                title = "ব্লক করা ব্যবহারকারী",
                subtitle = "আপনার ব্লক তালিকা পরিচালনা করুন",
                onClick = { /* Navigate to blocked users */ }
            )

            ModernNavigationItem(
                icon = Icons.Outlined.Shield,
                title = "গোপনীয়তা নীতি",
                subtitle = "আমরা কীভাবে আপনার তথ্য সুরক্ষিত করি",
                onClick = { /* Navigate to privacy policy */ }
            )

            ModernNavigationItem(
                icon = Icons.Outlined.Policy,
                title = "ব্যবহারের শর্তাবলী",
                subtitle = "সেবা ব্যবহারের নিয়মাবলী",
                onClick = { /* Navigate to terms */ }
            )

            ModernNavigationItem(
                icon = Icons.Outlined.Password,
                title = "পাসওয়ার্ড পরিবর্তন করুন",
                subtitle = "আপনার অ্যাকাউন্ট সুরক্ষিত রাখুন",
                onClick = { /* Navigate to change password */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // About Section
        ModernSettingsSection(
            title = "সম্পর্কে",
            icon = Icons.Outlined.Info
        ) {
            ModernNavigationItem(
                icon = Icons.Outlined.Info,
                title = "অ্যাপ সম্পর্কে",
                subtitle = "সংস্করণ 1.0.0 • আপডেট: ১৩ অক্টোবর, ২০২৫",
                onClick = { /* Show about dialog */ }
            )

            ModernNavigationItem(
                icon = Icons.AutoMirrored.Filled.Help,
                title = "সাহায্য ও সহায়তা",
                subtitle = "FAQ এবং সহায়তা কেন্দ্র",
                onClick = { /* Navigate to help */ }
            )

            ModernNavigationItem(
                icon = Icons.Outlined.Reviews,
                title = "রিভিউ দিন",
                subtitle = "Play Store-এ আমাদের রেটিং দিন",
                onClick = { /* Open Play Store */ }
            )

            ModernNavigationItem(
                icon = Icons.Outlined.Share,
                title = "শেয়ার করুন",
                subtitle = "বন্ধুদের সাথে অ্যাপ শেয়ার করুন",
                onClick = { /* Share app */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        ModernDangerButton(
            icon = Icons.AutoMirrored.Outlined.Logout,
            title = "লগ আউট",
            subtitle = "আপনার অ্যাকাউন্ট থেকে সাইন আউট করুন",
            onClick = { /* Handle logout */ }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ModernSettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.3.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Content Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(vertical = 8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun ModernSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    highlighted: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = if (highlighted) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        } else {
            MaterialTheme.colorScheme.surface.copy(alpha = 0f)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon Container
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Text Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Modern Switch
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

@Composable
private fun ModernNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon Container
            Surface(
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Text Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Navigation Arrow
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun ModernDangerButton(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Surface(
            onClick = onClick,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon Container
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }

                // Text Content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Arrow
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                )
            }
        }
    }
}
