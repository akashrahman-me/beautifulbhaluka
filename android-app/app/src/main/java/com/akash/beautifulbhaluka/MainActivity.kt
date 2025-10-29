package com.akash.beautifulbhaluka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            // Observe current destination
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Hide bars when in Social screen or Buy-Sell screen
            val shouldShowBars = currentRoute != NavigationRoutes.SOCIAL &&
                    currentRoute != NavigationRoutes.BUY_SELL &&
                    currentRoute != NavigationRoutes.MATCHMAKING &&
                    currentRoute != NavigationRoutes.MATCHMAKING_DETAILS &&
                    currentRoute != NavigationRoutes.MATCHMAKING_PUBLISH


            BeautifulBhalukaTheme(dynamicColor = false) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        NavigationDrawer(
                            navController = navController,
                            drawerState = drawerState
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        bottomBar = {
                            if (shouldShowBars) {
                                Bottombar(
                                    navController = navController,
                                    drawerState = drawerState
                                )
                            }
                        },
                        topBar = {
                            if (shouldShowBars) {
                                Topbar()
                            }
                        },
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .then(
                                    if (shouldShowBars) {
                                        Modifier.padding(innerPadding)
                                    } else {
                                        Modifier
                                    }
                                )
                        ) {
                            AppNavigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
