package com.akash.beautifulbhaluka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akash.beautifulbhaluka.componets.layout.Bottombar
import com.akash.beautifulbhaluka.componets.layout.Destination
import com.akash.beautifulbhaluka.componets.layout.Topbar
import com.akash.beautifulbhaluka.ui.theme.BeautifulBhalukaTheme
import com.akash.beautifulbhaluka.views.home.Home
import com.akash.beautifulbhaluka.views.jobs.Jobs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val startDestination = Destination.Home

            BeautifulBhalukaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { Bottombar(navController) },
                    topBar = { Topbar() },
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(navController, startDestination)
                    }
                }
            }
        }
    }
}


@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: Destination,
) {
    NavHost(navController = navController, startDestination.route) {
//        composable("home") { Home(navController) }
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.Home -> Home()
                    Destination.Social -> Social()
                    Destination.Jobs -> Jobs()
                    Destination.Menu -> Home()
                }
            }
        }
    }
}


@Composable
fun Social() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Social is in development".uppercase(),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Black,
            ),
            textAlign = TextAlign.Center,
        )
    }
}

