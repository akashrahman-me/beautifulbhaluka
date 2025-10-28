package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

/**
 * Hook to track scroll direction and visibility state for headers
 * Returns true when header should be visible (scrolling up or at top)
 */
@Composable
fun rememberScrollHeaderState(
    scrollState: LazyListState
): Boolean {
    var showHeader by remember { mutableStateOf(true) }
    var previousScrollPosition by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        snapshotFlow {
            scrollState.firstVisibleItemIndex * 10000 + scrollState.firstVisibleItemScrollOffset
        }.collect { currentScrollPosition ->
            // Always show at the very top
            if (scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset == 0) {
                showHeader = true
                previousScrollPosition = 0
                return@collect
            }

            // Detect scroll direction by comparing with previous position
            val isScrollingUp = currentScrollPosition < previousScrollPosition

            // Update header visibility based on scroll direction
            showHeader = isScrollingUp

            // Update previous position
            previousScrollPosition = currentScrollPosition
        }
    }

    return showHeader
}

/**
 * Animated visibility for scroll-based headers
 * Provides smooth slide, fade, and expand animations
 */
@Composable
fun ScrollAnimatedHeader(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 300),
            initialOffsetY = { -it }
        ) + fadeIn(
            animationSpec = tween(durationMillis = 300)
        ) + expandVertically(
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300),
            targetOffsetY = { -it }
        ) + fadeOut(
            animationSpec = tween(durationMillis = 300)
        ) + shrinkVertically(
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        content()
    }
}

