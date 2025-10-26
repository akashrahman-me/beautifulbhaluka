package com.akash.beautifulbhaluka.presentation.screens.social

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.akash.beautifulbhaluka.presentation.screens.social.comments.CommentsScreen
import com.akash.beautifulbhaluka.presentation.screens.social.messenger.MessengerScreen
import com.akash.beautifulbhaluka.presentation.screens.social.messenger.chat.ChatScreen
import com.akash.beautifulbhaluka.presentation.screens.social.settings.SocialSettingsScreen
import com.akash.beautifulbhaluka.presentation.theme.RubikWetPaint
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.House
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.CircleUserRound
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.X
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

enum class SocialTab(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconOutlined: androidx.compose.ui.graphics.vector.ImageVector
) {
    FEED("Feed", Lucide.House, Lucide.House),
    MESSAGES("Messages", Lucide.MessageCircle, Lucide.MessageCircle),
    PROFILE("Profile", Lucide.CircleUserRound, Lucide.CircleUserRound),
    SETTINGS("Settings", Lucide.Settings, Lucide.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialScreen(
    onExit: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(SocialTab.FEED) }
    var showCreatePost by remember { mutableStateOf(false) }
    val feedNavController = rememberNavController()
    val messengerNavController = rememberNavController()
    val profileNavController = rememberNavController()

    // Track scroll state for feed tab
    val feedScrollState = rememberLazyListState()

    // Track header visibility state
    var showHeader by remember { mutableStateOf(true) }

    // Track previous scroll position
    var previousScrollPosition by remember { mutableStateOf(0) }

    // Detect scroll direction using snapshotFlow
    LaunchedEffect(selectedTab) {
        if (selectedTab != SocialTab.FEED) {
            showHeader = false
            return@LaunchedEffect
        }

        snapshotFlow {
            feedScrollState.firstVisibleItemIndex * 10000 + feedScrollState.firstVisibleItemScrollOffset
        }.collect { currentScrollPosition ->
            // Always show at the very top
            if (feedScrollState.firstVisibleItemIndex == 0 && feedScrollState.firstVisibleItemScrollOffset == 0) {
                showHeader = true
                previousScrollPosition = 0
                return@collect
            }

            // Detect scroll direction by comparing with previous position
            val isScrollingUp = currentScrollPosition < previousScrollPosition

            // Update header visibility based on scroll direction
            showHeader = isScrollingUp

            // Update previous position
            previousScrollPosition = currentScrollPosition
        }
    }

    // Reset header visibility when changing tabs
    LaunchedEffect(selectedTab) {
        showHeader = selectedTab == SocialTab.FEED
    }

    Scaffold{ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                // Modern Header Section with smooth transition
                AnimatedVisibility(
                    visible = showHeader,
                    enter = slideInVertically(
                        animationSpec = tween(durationMillis = 300),
                        initialOffsetY = { -it }
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = 300)
                    ) + expandVertically(
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = slideOutVertically(
                        animationSpec = tween(durationMillis = 300),
                        targetOffsetY = { -it }
                    ) + fadeOut(
                        animationSpec = tween(durationMillis = 300)
                    ) + shrinkVertically(
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 0.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            // Create gradient brush
                            val gradientBrush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(1000f, 100f)
                            )

                            Text(
                                text = "Need Book",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    brush = gradientBrush
                                ),
                                fontFamily = RubikWetPaint,
                            )

                            // Exit Button
                            FilledTonalIconButton(
                                onClick = onExit,
                                colors = IconButtonDefaults.filledTonalIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Lucide.X,
                                    contentDescription = "Exit Social",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Modern Tab Navigation
                PrimaryTabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    divider = {
                        HorizontalDivider(
                            thickness = 0.5.dp,
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                        )
                    }
                ) {
                    SocialTab.entries.forEach { tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            modifier = Modifier.height(56.dp)
                        ) {
                            Column(
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = if (selectedTab == tab) tab.icon else tab.iconOutlined,
                                    contentDescription = tab.title,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (selectedTab == tab) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            }
                        }
                    }
                }

                when (selectedTab) {
                    SocialTab.FEED -> {
                        NavHost(
                            navController = feedNavController,
                            startDestination = "feed"
                        ) {
                            composable("feed") {
                                SocialFeedScreen(
                                    scrollState = feedScrollState,
                                    onCreatePostClick = { showCreatePost = true },
                                    onNavigateToComments = { postId ->
                                        feedNavController.navigate("comments/$postId")
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
                                    onNavigateBack = { feedNavController.popBackStack() }
                                )
                            }
                        }
                    }

                    SocialTab.MESSAGES -> {
                        NavHost(
                            navController = messengerNavController,
                            startDestination = "messenger"
                        ) {
                            composable("messenger") {
                                MessengerScreen(
                                    onConversationClick = { conversationId ->
                                        messengerNavController.navigate("chat/$conversationId")
                                    },
                                    onNewMessageClick = {
                                        // TODO: Navigate to new message screen
                                    }
                                )
                            }

                            composable(
                                route = "chat/{conversationId}",
                                arguments = listOf(
                                    navArgument("conversationId") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
                                ChatScreen(
                                    conversationId = conversationId,
                                    onNavigateBack = { messengerNavController.popBackStack() }
                                )
                            }
                        }
                    }

                    SocialTab.PROFILE -> {
                        NavHost(
                            navController = profileNavController,
                            startDestination = "profile"
                        ) {
                            composable("profile") {
                                val viewModel: com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileViewModel = viewModel()
                                LaunchedEffect(Unit) {
                                    viewModel.onAction(
                                        com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileAction.LoadProfile("current_user_id")
                                    )
                                }
                                com.akash.beautifulbhaluka.presentation.screens.social.profile.SocialProfileScreen(
                                    viewModel = viewModel,
                                    onNavigateToEditProfile = {
                                        profileNavController.navigate("edit_profile")
                                    }
                                )
                            }

                            composable("edit_profile") {
                                com.akash.beautifulbhaluka.presentation.screens.social.profile.EditProfileScreen(
                                    onNavigateBack = { profileNavController.popBackStack() },
                                    onSaveProfile = { profileData ->
                                        // TODO: Save profile data via ViewModel
                                    }
                                )
                            }
                        }
                    }

                    SocialTab.SETTINGS -> {
                        SocialSettingsScreen()
                    }
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
