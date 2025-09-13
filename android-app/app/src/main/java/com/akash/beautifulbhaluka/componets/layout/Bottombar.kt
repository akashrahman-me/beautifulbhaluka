package com.akash.beautifulbhaluka.componets.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@Composable
fun Bottombar(navController: NavHostController) {
    var selectedDestination by remember { mutableStateOf(0) }

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        Destination.entries.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedDestination == index,
                onClick = {
                    navController.navigate(route = destination.route)
                    selectedDestination = index
                },
                icon = {
                    Icon(
                        imageVector = if (selectedDestination == index) destination.icon2 else destination.icon,
                        contentDescription = destination.contentDescription
                    )
                },
                label = { Text(destination.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF007BFF),
                    selectedTextColor = Color(0xFF007BFF),
                    indicatorColor = Color.Transparent,
                )
            )
        }
    }
}


enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val icon2: ImageVector,
    val contentDescription: String
) {
    Home("home", "Home", Icons.Outlined.Home, Icons.Default.Home, "Home"),
    Social("social", "Social", Icons.Outlined.Public, Icons.Default.Public, "Social"),
    Jobs("jobs", "Jobs", Icons.Outlined.WorkOutline, Icons.Default.Work, "Jobs"),
    Menu("menu", "Menu", Icons.Outlined.Menu, Icons.Default.Menu, "Menu"),
}