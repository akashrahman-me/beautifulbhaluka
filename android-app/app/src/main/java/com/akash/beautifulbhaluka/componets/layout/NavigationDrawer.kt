package com.akash.beautifulbhaluka.componets.layout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    navController: NavHostController,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = modifier.width(300.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        // Header
        Text(
            text = "Beautiful Bhaluka",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Drawer Items
        DrawerMenuItem.entries.forEach { item ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription
                    )
                },
                label = { Text(item.label) },
                selected = false,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                        // Navigate to the route if needed
                        if (item.route.isNotEmpty()) {
                            navController.navigate(item.route)
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 12.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFF007BFF).copy(alpha = 0.1f),
                    selectedTextColor = Color(0xFF007BFF),
                    selectedIconColor = Color(0xFF007BFF)
                )
            )
        }
    }
}

enum class DrawerMenuItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    Profile("profile", "Profile", Icons.Default.AccountCircle, "Profile"),
    Settings("settings", "Settings", Icons.Default.Settings, "Settings"),
    Notifications("notifications", "Notifications", Icons.Default.Notifications, "Notifications"),
    About("about", "About", Icons.Default.Info, "About"),
    Help("help", "Help & Support", Icons.AutoMirrored.Filled.Help, "Help & Support"),
}
