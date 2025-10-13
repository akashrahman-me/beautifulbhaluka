package com.akash.beautifulbhaluka.presentation.screens.social

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.akash.beautifulbhaluka.presentation.screens.social.comments.CommentsScreen

enum class SocialTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    FEED("ফিড", Icons.Default.Home),
    PROFILE("প্রোফাইল", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialScreen() {
    var selectedTab by remember { mutableStateOf(SocialTab.FEED) }
    var showCreatePost by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("সোশ্যাল") }
            )
        },
        bottomBar = {
            NavigationBar {
                SocialTab.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = null) },
                        label = { Text(tab.title) },
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "feed"
            ) {
                composable("feed") {
                    when (selectedTab) {
                        SocialTab.FEED -> SocialFeedScreen(
                            onCreatePostClick = { showCreatePost = true },
                            onNavigateToComments = { postId ->
                                navController.navigate("comments/$postId")
                            }
                        )
                        SocialTab.PROFILE -> {
                            val viewModel: com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileViewModel = viewModel()
                            LaunchedEffect(Unit) {
                                viewModel.onAction(
                                    com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileAction.LoadProfile("current_user_id")
                                )
                            }
                            com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileScreen(viewModel = viewModel)
                        }
                    }
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
