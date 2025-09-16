package com.akash.beautifulbhaluka.presentation.components.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.akash.beautifulbhaluka.presentation.screens.home.CarouselItem
import com.akash.beautifulbhaluka.R

// Architecture-compliant Carousel component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    items: List<CarouselItem>,
    modifier: Modifier = Modifier,
    onItemClick: ((CarouselItem) -> Unit)? = null
) {
    // If no items provided, show default fallback
    if (items.isEmpty()) {
        DefaultCarousel(modifier = modifier)
        return
    }

    // Map domain CarouselItem to drawable resources
    val carouselItems = items.mapNotNull { item ->
        getDrawableResourceId(item.id)?.let { drawableId ->
            LocalCarouselItem(
                id = item.id.toIntOrNull() ?: 0,
                imageResId = drawableId,
                contentDescription = item.description,
                originalItem = item
            )
        }
    }

    if (carouselItems.isEmpty()) {
        DefaultCarousel(modifier = modifier)
        return
    }

    val carouselState = rememberCarouselState { carouselItems.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 12.dp),
            preferredItemWidth = (LocalConfiguration.current.screenWidthDp * 0.8f).dp,
            itemSpacing = 8.dp,
        ) { i ->
            val item = carouselItems[i]

            Image(
                modifier = Modifier
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .height(205.dp)
                    .fillMaxWidth()
                    .clickable {
                        onItemClick?.invoke(item.originalItem)
                    },
                painter = painterResource(id = item.imageResId),
                contentDescription = item.contentDescription,
                contentScale = ContentScale.Crop,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            carouselItems.forEachIndexed { index, _ ->
                val isSelected = carouselState.currentItem == index

                val dotWidth by animateDpAsState(targetValue = if (isSelected) 24.dp else 8.dp)
                val dotHeight by animateDpAsState(targetValue = 8.dp)

                val dotColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.36f)
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .width(dotWidth)
                        .height(dotHeight)
                        .clip(CircleShape)
                        .background(dotColor)
                        .clickable {
                            coroutineScope.launch {
                                carouselState.animateScrollToItem(index)
                            }
                        }
                )
            }
        }
    }
}

// Internal data class for carousel display
private data class LocalCarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescription: String,
    val originalItem: CarouselItem
)

// Default fallback carousel when no data available
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultCarousel(modifier: Modifier = Modifier) {
    val defaultItems = listOf(
        LocalCarouselItem(
            0,
            R.drawable.carousel1,
            "Beautiful Bhaluka",
            CarouselItem("0", "Welcome", "", "Beautiful Bhaluka")
        ),
        LocalCarouselItem(
            1,
            R.drawable.carousel2,
            "Community",
            CarouselItem("1", "Community", "", "Community")
        ),
        LocalCarouselItem(
            2,
            R.drawable.carousel3,
            "Services",
            CarouselItem("2", "Services", "", "Services")
        ),
        LocalCarouselItem(
            3,
            R.drawable.carousel4,
            "Culture",
            CarouselItem("3", "Culture", "", "Culture")
        ),
        LocalCarouselItem(
            4,
            R.drawable.carousel5,
            "Development",
            CarouselItem("4", "Development", "", "Development")
        ),
    )

    val carouselState = rememberCarouselState { defaultItems.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalMultiBrowseCarousel(
            state = carouselState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 12.dp),
            preferredItemWidth = (LocalConfiguration.current.screenWidthDp * 0.8f).dp,
            itemSpacing = 8.dp,
        ) { i ->
            val item = defaultItems[i]
            Image(
                modifier = Modifier
                    .height(205.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge),
                painter = painterResource(id = item.imageResId),
                contentDescription = item.contentDescription,
                contentScale = ContentScale.Crop,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            defaultItems.forEachIndexed { index, _ ->
                val isSelected = carouselState.currentItem == index

                val dotWidth by animateDpAsState(targetValue = if (isSelected) 24.dp else 8.dp)
                val dotHeight by animateDpAsState(targetValue = 8.dp)

                val dotColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.36f)
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .width(dotWidth)
                        .height(dotHeight)
                        .clip(CircleShape)
                        .background(dotColor)
                        .clickable {
                            coroutineScope.launch {
                                carouselState.animateScrollToItem(index)
                            }
                        }
                )
            }
        }
    }
}

// Helper function to map carousel item IDs to drawable resources
private fun getDrawableResourceId(itemId: String): Int? {
    return when (itemId) {
        "0", "1" -> R.drawable.carousel1
        "2", "3" -> R.drawable.carousel2
        "4", "5" -> R.drawable.carousel3
        "6", "7" -> R.drawable.carousel4
        "8", "9" -> R.drawable.carousel5
        else -> null
    }
}
