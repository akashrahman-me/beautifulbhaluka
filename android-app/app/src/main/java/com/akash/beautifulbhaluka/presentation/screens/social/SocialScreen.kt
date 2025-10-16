package com.akash.beautifulbhaluka.presentation.screens.social

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.akash.beautifulbhaluka.presentation.screens.social.comments.CommentsScreen
import com.akash.beautifulbhaluka.presentation.screens.social.settings.SocialSettingsScreen

enum class SocialTab(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    FEED("ফিড", Icons.Default.Home),
    PROFILE("প্রোফাইল", Icons.Default.Person),
    SETTINGS("সেটিংস", Icons.Default.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialScreen(
    onExit: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(SocialTab.FEED) }
    var showCreatePost by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            "বিউটিফুল ভালুকা সোশ্যাল",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = onExit) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Exit Social",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )

                // Tab Row for navigation
                PrimaryTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    SocialTab.entries.forEach { tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            text = { Text(tab.title) },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                SocialTab.FEED -> {
                    NavHost(
                        navController = navController,
                        startDestination = "feed"
                    ) {
                        composable("feed") {
                            SocialFeedScreen(
                                onCreatePostClick = { showCreatePost = true },
                                onNavigateToComments = { postId ->
                                    navController.navigate("comments/$postId")
                                }
                            )
                        }

                        composable(
                            route = "comments/{postId}",
                            arguments = listOf(
                                navArgument("postId") { type = NavType.StringType }
                            )
                        ) {
                            CommentsScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }

                SocialTab.PROFILE -> {
                    val viewModel: com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileViewModel = viewModel()
                    LaunchedEffect(Unit) {
                        viewModel.onAction(
                            com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileAction.LoadProfile("current_user_id")
                        )
                    }
                    com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileScreen(viewModel = viewModel)
                }

                SocialTab.SETTINGS -> {
                    SocialSettingsScreen()
                }
            }
        }

        if (showCreatePost) {
            com.akash.beautifulbhaluka.presentation.screens.social.create.CreatePostScreen(
                onDismiss = { showCreatePost = false },
                onPostCreated = {
                    showCreatePost = false
                }
            )
        }
    }
}
