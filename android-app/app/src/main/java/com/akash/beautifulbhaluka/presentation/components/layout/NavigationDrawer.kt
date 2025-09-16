package com.akash.beautifulbhaluka.presentation.components.layout

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
import com.akash.beautifulbhaluka.presentation.navigation.NavigationRoutes

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

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))

        // Navigation items
        val drawerItems = listOf(
            DrawerItem(
                route = NavigationRoutes.PROFILE,
                label = "Profile",
                icon = Icons.Default.AccountCircle
            ),
            DrawerItem(
                route = NavigationRoutes.SETTINGS,
                label = "Settings",
                icon = Icons.Default.Settings
            ),
            DrawerItem(
                route = NavigationRoutes.NOTIFICATIONS,
                label = "Notifications",
                icon = Icons.Default.Notifications
            ),
            DrawerItem(
                route = NavigationRoutes.ABOUT,
                label = "About",
                icon = Icons.Default.Info
            ),
            DrawerItem(
                route = NavigationRoutes.HELP,
                label = "Help & Support",
                icon = Icons.AutoMirrored.Filled.Help
            )
        )

        drawerItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.label) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                selected = false,
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                        navController.navigate(item.route)
                    }
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color(0xFF007BFF).copy(alpha = 0.1f),
                    selectedIconColor = Color(0xFF007BFF),
                    selectedTextColor = Color(0xFF007BFF)
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

data class DrawerItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
