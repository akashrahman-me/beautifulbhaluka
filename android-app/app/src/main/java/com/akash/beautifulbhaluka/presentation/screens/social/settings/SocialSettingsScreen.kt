package com.akash.beautifulbhaluka.presentation.screens.social.settings

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Lock
import com.composables.icons.lucide.Eye
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.CheckCheck
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.BellRing
import com.composables.icons.lucide.FileText
import com.composables.icons.lucide.MessageSquare
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Palette
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Play
import com.composables.icons.lucide.Database
import com.composables.icons.lucide.Shield
import com.composables.icons.lucide.UserX
import com.composables.icons.lucide.ShieldCheck
import com.composables.icons.lucide.FileCheck
import com.composables.icons.lucide.KeyRound
import com.composables.icons.lucide.Info
import com.composables.icons.lucide.CircleHelp
import com.composables.icons.lucide.Star
import com.composables.icons.lucide.Share2
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.ChevronRight

/**
 * Ultra-Modern Social Settings Screen
 *
 * Design principles:
 * - Icon-first design with Lucide Icons
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
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Customize your social experience",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Account Settings Section
        ModernSettingsSection(
            title = "Account",
            icon = Lucide.User
        ) {
            ModernSwitchItem(
                icon = Lucide.Lock,
                title = "Private Account",
                subtitle = "Only approved people can see your posts",
                checked = privateAccount,
                onCheckedChange = { privateAccount = it }
            )

            ModernSwitchItem(
                icon = Lucide.Eye,
                title = "Online Status",
                subtitle = "Others can see when you're online",
                checked = showOnlineStatus,
                onCheckedChange = { showOnlineStatus = it }
            )

            ModernSwitchItem(
                icon = Lucide.Tag,
                title = "Allow Tagging",
                subtitle = "Others can tag you in posts",
                checked = allowTagging,
                onCheckedChange = { allowTagging = it }
            )

            ModernSwitchItem(
                icon = Lucide.CheckCheck,
                title = "Read Receipts",
                subtitle = "Sender can see if you've read their message",
                checked = showReadReceipts,
                onCheckedChange = { showReadReceipts = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notification Settings Section
        ModernSettingsSection(
            title = "Notifications",
            icon = Lucide.Bell
        ) {
            ModernSwitchItem(
                icon = Lucide.BellRing,
                title = "All Notifications",
                subtitle = "Enable/disable all notifications",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it },
//                highlighted = true
            )

            if (notificationsEnabled) {
                ModernSwitchItem(
                    icon = Lucide.FileText,
                    title = "New Posts",
                    subtitle = "Notifications for new posts from friends",
                    checked = postNotifications,
                    onCheckedChange = { postNotifications = it }
                )

                ModernSwitchItem(
                    icon = Lucide.MessageSquare,
                    title = "Comments",
                    subtitle = "For new comments on your posts",
                    checked = commentNotifications,
                    onCheckedChange = { commentNotifications = it }
                )

                ModernSwitchItem(
                    icon = Lucide.Heart,
                    title = "Likes",
                    subtitle = "For likes on your posts",
                    checked = likeNotifications,
                    onCheckedChange = { likeNotifications = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Appearance & Performance Section
        ModernSettingsSection(
            title = "Appearance & Performance",
            icon = Lucide.Palette
        ) {
            ModernSwitchItem(
                icon = Lucide.Moon,
                title = "Dark Mode",
                subtitle = "Enable dark theme",
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )

            ModernSwitchItem(
                icon = Lucide.Play,
                title = "Auto-play Videos",
                subtitle = "Videos will play automatically",
                checked = autoPlayVideos,
                onCheckedChange = { autoPlayVideos = it }
            )

            ModernSwitchItem(
                icon = Lucide.Database,
                title = "Data Saver Mode",
                subtitle = "Compress images to use less data",
                checked = dataCompression,
                onCheckedChange = { dataCompression = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Privacy & Security Section
        ModernSettingsSection(
            title = "Privacy & Security",
            icon = Lucide.Shield
        ) {
            ModernNavigationItem(
                icon = Lucide.UserX,
                title = "Blocked Users",
                subtitle = "Manage your block list",
                onClick = { /* Navigate to blocked users */ }
            )

            ModernNavigationItem(
                icon = Lucide.ShieldCheck,
                title = "Privacy Policy",
                subtitle = "How we protect your information",
                onClick = { /* Navigate to privacy policy */ }
            )

            ModernNavigationItem(
                icon = Lucide.FileCheck,
                title = "Terms of Service",
                subtitle = "Service usage rules",
                onClick = { /* Navigate to terms */ }
            )

            ModernNavigationItem(
                icon = Lucide.KeyRound,
                title = "Change Password",
                subtitle = "Keep your account secure",
                onClick = { /* Navigate to change password */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // About Section
        ModernSettingsSection(
            title = "About",
            icon = Lucide.Info
        ) {
            ModernNavigationItem(
                icon = Lucide.Info,
                title = "About App",
                subtitle = "Version 1.0.0 • Updated: Oct 17, 2025",
                onClick = { /* Show about dialog */ }
            )

            ModernNavigationItem(
                icon = Lucide.CircleHelp,
                title = "Help & Support",
                subtitle = "FAQ and support center",
                onClick = { /* Navigate to help */ }
            )

            ModernNavigationItem(
                icon = Lucide.Star,
                title = "Rate Us",
                subtitle = "Rate us on Play Store",
                onClick = { /* Open Play Store */ }
            )

            ModernNavigationItem(
                icon = Lucide.Share2,
                title = "Share App",
                subtitle = "Share app with friends",
                onClick = { /* Share app */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        ModernDangerButton(
            icon = Lucide.LogOut,
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
            // Icon Container - Now consistent color
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

            // Navigation Arrow
            Icon(
                imageVector = Lucide.ChevronRight,
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
                        text = "Log Out",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Sign out from your account",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Arrow
                Icon(
                    imageVector = Lucide.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
                )
            }
        }
    }
}
