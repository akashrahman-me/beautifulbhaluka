package com.akash.beautifulbhaluka.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akash.beautifulbhaluka.presentation.screens.home.HomeScreen
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobsScreen
import com.akash.beautifulbhaluka.presentation.screens.social.SocialScreen
import com.akash.beautifulbhaluka.presentation.screens.shops.ShopsScreen
import com.akash.beautifulbhaluka.presentation.screens.profile.ProfileScreen
import com.akash.beautifulbhaluka.presentation.screens.settings.SettingsScreen
import com.akash.beautifulbhaluka.presentation.screens.notifications.NotificationsScreen
import com.akash.beautifulbhaluka.presentation.screens.about.AboutScreen
import com.akash.beautifulbhaluka.presentation.screens.help.HelpScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = NavigationRoutes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Bottom navigation destinations
        composable(NavigationRoutes.HOME) {
            HomeScreen()
        }

        composable(NavigationRoutes.SOCIAL) {
            SocialScreen()
        }

        composable(NavigationRoutes.JOBS) {
            JobsScreen()
        }

        composable(NavigationRoutes.SHOPS) {
            ShopsScreen()
        }

        // Drawer navigation destinations
        composable(NavigationRoutes.PROFILE) {
            ProfileScreen()
        }

        composable(NavigationRoutes.SETTINGS) {
            SettingsScreen()
        }

        composable(NavigationRoutes.NOTIFICATIONS) {
            NotificationsScreen()
        }

        composable(NavigationRoutes.ABOUT) {
            AboutScreen()
        }

        composable(NavigationRoutes.HELP) {
            HelpScreen()
        }
    }
}
