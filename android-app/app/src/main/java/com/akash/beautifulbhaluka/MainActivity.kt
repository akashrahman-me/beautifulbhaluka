package com.akash.beautifulbhaluka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akash.beautifulbhaluka.presentation.components.layout.Bottombar
import com.akash.beautifulbhaluka.presentation.components.layout.NavigationDrawer
import com.akash.beautifulbhaluka.presentation.components.layout.Topbar
import com.akash.beautifulbhaluka.presentation.navigation.AppNavigation
import com.akash.beautifulbhaluka.presentation.navigation.NavigationRoutes
import com.akash.beautifulbhaluka.presentation.theme.BeautifulBhalukaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeautifulBhalukaApp()
        }
    }
}

@Composable
fun BeautifulBhalukaApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Observe current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Show bars only on Home screen
    val shouldShowBars = currentRoute == NavigationRoutes.HOME

    BeautifulBhalukaTheme(dynamicColor = false) {
        MainScaffold(
            navController = navController,
            drawerState = drawerState,
            shouldShowBars = shouldShowBars
        )
    }
}

@Composable
private fun MainScaffold(
    navController: NavHostController,
    drawerState: androidx.compose.material3.DrawerState,
    shouldShowBars: Boolean
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                navController = navController,
                drawerState = drawerState
            )
        }
    ) {
        if (shouldShowBars) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                bottomBar = {
                    AnimatedVisibility(
                        visible = shouldShowBars,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                    ) {
                        Bottombar(
                            navController = navController,
                            drawerState = drawerState
                        )
                    }
                },
                topBar = {
                    AnimatedVisibility(
                        visible = shouldShowBars,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
                    ) {
                        Topbar()
                    }
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    AppNavigation(navController = navController)
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AppNavigation(navController = navController)
            }
        }
    }
}
