package com.akash.beautifulbhaluka.presentation.screens.webview

import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.composables.icons.lucide.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    title: String = "Article",
    onNavigateBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var currentUrl by remember { mutableStateOf(url) }
    var pageTitle by remember { mutableStateOf(title) }
    var progress by remember { mutableFloatStateOf(0f) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var webView by remember { mutableStateOf<WebView?>(null) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = pageTitle,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = currentUrl,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Close",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    actions = {
                        // Refresh button
                        IconButton(
                            onClick = { webView?.reload() }
                        ) {
                            Icon(
                                imageVector = Lucide.RefreshCw,
                                contentDescription = "Refresh",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Progress indicator
                AnimatedVisibility(
                    visible = isLoading && progress < 1f,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back button
                    IconButton(
                        onClick = { webView?.goBack() },
                        enabled = canGoBack
                    ) {
                        Icon(
                            imageVector = Lucide.ChevronLeft,
                            contentDescription = "Back",
                            tint = if (canGoBack)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }

                    // Forward button
                    IconButton(
                        onClick = { webView?.goForward() },
                        enabled = canGoForward
                    ) {
                        Icon(
                            imageVector = Lucide.ChevronRight,
                            contentDescription = "Forward",
                            tint = if (canGoForward)
                                MaterialTheme.colorScheme.onSurface
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }

                    // Home button
                    IconButton(
                        onClick = { webView?.loadUrl(url) }
                    ) {
                        Icon(
                            imageVector = Lucide.House,
                            contentDescription = "Home"
                        )
                    }

                    // Share button (optional - can open share sheet)
                    IconButton(
                        onClick = {
                            // TODO: Implement share functionality
                        }
                    ) {
                        Icon(
                            imageVector = Lucide.Share2,
                            contentDescription = "Share"
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            builtInZoomControls = true
                            displayZoomControls = false
                            setSupportZoom(true)
                        }

                        webViewClient = object : android.webkit.WebViewClient() {
                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                isLoading = true
                                currentUrl = url ?: ""
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                                progress = 1f
                                currentUrl = url ?: ""
                                pageTitle = view?.title ?: title
                                canGoBack = view?.canGoBack() ?: false
                                canGoForward = view?.canGoForward() ?: false
                            }
                        }

                        webChromeClient = object : android.webkit.WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                progress = newProgress / 100f
                            }

                            override fun onReceivedTitle(view: WebView?, title: String?) {
                                pageTitle = title ?: pageTitle
                            }
                        }

                        webView = this
                        loadUrl(url)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Loading overlay
            AnimatedVisibility(
                visible = isLoading && progress < 0.3f,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = "Loading article...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

