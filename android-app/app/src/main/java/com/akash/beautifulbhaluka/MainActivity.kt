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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.akash.beautifulbhaluka.presentation.components.layout.Bottombar
import com.akash.beautifulbhaluka.presentation.components.layout.NavigationDrawer
import com.akash.beautifulbhaluka.presentation.components.layout.Topbar
import com.akash.beautifulbhaluka.presentation.navigation.AppNavigation
import com.akash.beautifulbhaluka.presentation.theme.BeautifulBhalukaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
                            Bottombar(
                                navController = navController,
                                drawerState = drawerState
                            )
                        },
                        topBar = { Topbar() },
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavigation(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
