package com.akash.beautifulbhaluka.componets.layout

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.R
import com.akash.beautifulbhaluka.views.home.components.CustomizableSearchBar
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun Topbar() {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    var results by rememberSaveable { mutableStateOf(listOf<String>()) }

    fun searchbarVisibility(value: Boolean) {
        expanded = value
    }


    var sampleData = listOf(
        "Apple", "Banana", "Cherry", "Date", "Grapes"
    )

    Box(
        modifier = Modifier
            .drawBehind {
                val shadowColor = Color.Black.copy(alpha = 0.2f)
                val offsetX = 0.dp.toPx()
                val offsetY = 0.dp.toPx()
                val blur = 1.dp.toPx()

                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = shadowColor
                        asFrameworkPaint().setMaskFilter(
                            BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL)
                        )
                    }
                    canvas.drawRoundRect(
                        left = offsetX,
                        top = offsetY,
                        right = size.width + offsetX,
                        bottom = size.height + offsetY,
                        radiusX = 0.dp.toPx(),
                        radiusY = 0.dp.toPx(),
                        paint = paint
                    )
                }
            }
            .background(Color.White)
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "বিউটিফুল ভালুকা",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Row {
                IconButton(onClick = {
                    searchbarVisibility(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                    )
                }
            }
        }
    }

    Box {
        if (expanded) {
            CustomizableSearchBar(
                query = query,
                onQueryChange = {
                    query = it
                    // Optionally update search results live
                    results = sampleData.filter { item ->
                        item.contains(it, ignoreCase = true)
                    }
                },
                onSearch = {
                    // Perform final search action here
                },
                searchResults = results,
                onResultClick = { selected ->
                    // Handle when user clicks a result
                },
                expanded = expanded,
                searchbarVisibility = { value -> searchbarVisibility(value) }
            )
        }
    }

}