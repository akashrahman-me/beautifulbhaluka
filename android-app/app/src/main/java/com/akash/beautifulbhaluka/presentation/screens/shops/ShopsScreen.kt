package com.akash.beautifulbhaluka.presentation.screens.shops

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
fun ShopsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Shops is in development".uppercase(),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Black,
            ),
            textAlign = TextAlign.Center,
        )
    }
}
