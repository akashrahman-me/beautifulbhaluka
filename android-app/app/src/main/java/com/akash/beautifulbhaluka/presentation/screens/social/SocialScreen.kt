package com.akash.beautifulbhaluka.presentation.screens.social

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SocialScreen() {
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
