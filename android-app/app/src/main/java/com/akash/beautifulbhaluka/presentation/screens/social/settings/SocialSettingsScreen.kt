package com.akash.beautifulbhaluka.presentation.screens.social.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SocialSettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var privateAccount by remember { mutableStateOf(false) }
    var showOnlineStatus by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Account Settings Section
        Text(
            text = "অ্যাকাউন্ট সেটিংস",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsItem(
            icon = Icons.Default.Lock,
            title = "ব্যক্তিগত অ্যাকাউন্ট",
            subtitle = "শুধুমাত্র অনুমোদিত ব্যক্তিরা আপনার পোস্ট দেখতে পারবে",
            checked = privateAccount,
            onCheckedChange = { privateAccount = it }
        )

        SettingsItem(
            icon = Icons.Default.Visibility,
            title = "অনলাইন স্ট্যাটাস দেখান",
            subtitle = "অন্যরা দেখতে পারবে আপনি অনলাইন আছেন কিনা",
            checked = showOnlineStatus,
            onCheckedChange = { showOnlineStatus = it }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Notification Settings Section
        Text(
            text = "নোটিফিকেশন সেটিংস",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsItem(
            icon = Icons.Default.Notifications,
            title = "নোটিফিকেশন সক্রিয়",
            subtitle = "পোস্ট, লাইক এবং কমেন্টের জন্য নোটিফিকেশন পান",
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Privacy Section
        Text(
            text = "গোপনীয়তা",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsClickableItem(
            icon = Icons.Default.Block,
            title = "ব্লক করা ব্যবহারকারী",
            subtitle = "আপনার ব্লক তালিকা দেখুন",
            onClick = { /* Navigate to blocked users */ }
        )

        SettingsClickableItem(
            icon = Icons.Default.Shield,
            title = "গোপনীয়তা নীতি",
            subtitle = "আমাদের গোপনীয়তা নীতি পড়ুন",
            onClick = { /* Navigate to privacy policy */ }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // About Section
        Text(
            text = "সম্পর্কে",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsClickableItem(
            icon = Icons.Default.Info,
            title = "অ্যাপ সম্পর্কে",
            subtitle = "সংস্করণ 1.0.0",
            onClick = { /* Show about dialog */ }
        )

        SettingsClickableItem(
            icon = Icons.AutoMirrored.Filled.Help,
            title = "সাহায্য ও সহায়তা",
            subtitle = "সাধারণ প্রশ্ন এবং সহায়তা",
            onClick = { /* Navigate to help */ }
        )
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsClickableItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
