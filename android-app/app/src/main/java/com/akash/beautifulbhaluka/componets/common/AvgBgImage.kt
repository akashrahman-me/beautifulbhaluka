package com.akash.beautifulbhaluka.componets.common

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.akash.beautifulbhaluka.R

@Composable
fun AverageBackgroundImage() {
    // Load bitmap from resource
    val context = androidx.compose.ui.platform.LocalContext.current
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.a239438498)

    var avgColor by remember { mutableStateOf(Color.LightGray) }

    // Extract average color using Palette
    LaunchedEffect(bitmap) {
        Palette.from(bitmap).generate { palette ->
            palette?.let {
                val dominant = it.getDominantColor(android.graphics.Color.LTGRAY)
                avgColor = Color(dominant)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.extraLarge)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.extraLarge)
            .background(avgColor)
    ) {
        Image(
            painter = painterResource(R.drawable.a239438498),
            contentDescription = "Jobs Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
