package com.akash.beautifulbhaluka.views.home.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.R
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel() {
    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescription: String
    )

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.carousel1, "cupcake"),
            CarouselItem(1, R.drawable.carousel2, "donut"),
            CarouselItem(2, R.drawable.carousel3, "eclair"),
            CarouselItem(3, R.drawable.carousel4, "froyo"),
            CarouselItem(4, R.drawable.carousel5, "gingerbread"),
        )
    }

    // Create CarouselState (itemCount provider)
    val carouselState = rememberCarouselState { items.size }
    val coroutineScope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .fillMaxWidth()
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
            val item = items[i]
            Image(
                modifier = Modifier
                    .height(205.dp)
                    .fillMaxWidth()
                    .maskClip(MaterialTheme.shapes.extraLarge),
                painter = painterResource(id = item.imageResId),
                contentDescription = item.contentDescription,
                contentScale = ContentScale.Crop,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items.forEachIndexed { index, _ ->
                val isSelected = carouselState.currentItem == index

                val dotWidth by animateDpAsState(targetValue = if (isSelected) 24.dp else 8.dp)
                val dotHeight by animateDpAsState(targetValue = 8.dp)

                // animate color change
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
