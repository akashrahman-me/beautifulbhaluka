package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

/**
 * A modern, responsive table component for displaying tabular data with professional styling.
 *
 * Production-ready with advanced features like smart column sizing, horizontal scrolling,
 * text overflow handling, empty states, and full Material 3 theming support.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTable(
    title: String,
    headers: List<String>,
    rows: List<List<String>>,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true,
    alternateRowColors: Boolean = true,
    minColumnWidth: Dp = 80.dp,
    maxColumnWidth: Dp = 200.dp,
    horizontalPadding: Dp = 20.dp,
    verticalPadding: Dp = 14.dp,
    headerAlignment: TextAlign = TextAlign.Start,
    cellAlignment: TextAlign = TextAlign.Start,
    isLoading: Boolean = false,
    emptyMessage: String = "No data available"
) {
    val density = LocalDensity.current
    val scrollState = rememberScrollState()

    // Calculate optimal layout using improved algorithm
    data class TableLayoutInfo(
        val columnWidths: List<Dp>,
        val needsHorizontalScroll: Boolean,
        val totalRequiredWidth: Dp,
        val availableWidth: Dp
    )

    val layoutInfo = remember(headers, rows, minColumnWidth, maxColumnWidth) {
        with(density) {
            // More accurate character width estimation based on Material 3 typography
            val avgCharWidth = 7.5.dp // Empirically measured for bodyMedium
            val paddingPerCell = horizontalPadding * 2 + 16.dp // Account for cell padding

            // Calculate content-based widths
            val contentWidths = headers.mapIndexed { columnIndex, header ->
                val headerWidth = (header.length * avgCharWidth.value).dp + paddingPerCell
                val maxCellWidth = if (rows.isNotEmpty()) {
                    rows.maxOfOrNull { row ->
                        if (columnIndex < row.size) {
                            (row[columnIndex].length * avgCharWidth.value).dp + paddingPerCell
                        } else paddingPerCell
                    } ?: paddingPerCell
                } else paddingPerCell

                // Use the maximum of header and content, constrained by min/max
                max(headerWidth.value, maxCellWidth.value).dp
                    .coerceIn(minColumnWidth, maxColumnWidth)
            }

            val totalContentWidth = contentWidths.fold(0.dp) { acc, width -> acc + width }

            // Estimate available width (conservative approach)
            val screenWidthDp = 360.dp // Conservative mobile width
            val availableWidth = screenWidthDp - (horizontalPadding * 2)

            val needsScroll = totalContentWidth.value > availableWidth.value

            val finalWidths = if (needsScroll) {
                contentWidths // Use calculated widths for scrolling
            } else {
                // Distribute available width proportionally
                val totalWeight = contentWidths.fold(0f) { acc, width -> acc + width.value }
                contentWidths.map { width ->
                    (availableWidth.value * (width.value / totalWeight)).dp
                        .coerceAtLeast(minColumnWidth)
                }
            }

            TableLayoutInfo(
                columnWidths = finalWidths,
                needsHorizontalScroll = needsScroll,
                totalRequiredWidth = finalWidths.fold(0.dp) { acc, width -> acc + width },
                availableWidth = availableWidth
            )
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Table title with improved styling
        if (showTitle) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .width(4.dp)
                        .height(24.dp),
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(2.dp)
                ) {}

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.3).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                // Enhanced scroll indicator
                if (layoutInfo.needsHorizontalScroll) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Scroll →",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }

        // Main table container
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = CardDefaults.outlinedCardBorder()
        ) {
            when {
                isLoading -> {
                    // Loading state
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                strokeWidth = 3.dp
                            )
                            Text(
                                text = "Loading data...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                rows.isEmpty() -> {
                    // Empty state
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                            Text(
                                text = emptyMessage,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                else -> {
                    // Table content with scrolling support
                    val tableModifier = if (layoutInfo.needsHorizontalScroll) {
                        Modifier
                            .horizontalScroll(scrollState)
                            .width(layoutInfo.totalRequiredWidth)
                    } else {
                        Modifier.fillMaxWidth()
                    }

                    Column(modifier = tableModifier) {
                        // Enhanced header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                                )
                                .padding(
                                    horizontal = horizontalPadding,
                                    vertical = verticalPadding + 2.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            headers.forEachIndexed { index, header ->
                                Text(
                                    text = header,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.2.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = headerAlignment,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    modifier = Modifier
                                        .width(layoutInfo.columnWidths[index])
                                        .padding(horizontal = 4.dp)
                                )
                            }
                        }

                        // Table rows with improved styling
                        rows.forEachIndexed { rowIndex, row ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        when {
                                            alternateRowColors && rowIndex % 2 != 0 ->
                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f)

                                            else -> MaterialTheme.colorScheme.surface
                                        }
                                    )
                                    .padding(
                                        horizontal = horizontalPadding,
                                        vertical = verticalPadding
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                row.forEachIndexed { cellIndex, cell ->
                                    Text(
                                        text = cell,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            lineHeight = 20.sp,
                                            letterSpacing = 0.1.sp,
                                            fontWeight = when (cellIndex) {
                                                0 -> FontWeight.SemiBold // First column emphasis
                                                else -> FontWeight.Normal
                                            }
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = if (cellIndex == 0) 0.95f else 0.87f
                                        ),
                                        textAlign = cellAlignment,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 2,
                                        modifier = Modifier
                                            .width(
                                                if (cellIndex < layoutInfo.columnWidths.size)
                                                    layoutInfo.columnWidths[cellIndex]
                                                else minColumnWidth
                                            )
                                            .padding(horizontal = 4.dp)
                                    )
                                }
                            }

                            // Subtle row dividers
                            if (rowIndex < rows.size - 1) {
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                    thickness = 0.5.dp,
                                    modifier = Modifier.padding(horizontal = horizontalPadding)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Optional footer with table info
        if (!isLoading && rows.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${rows.size} rows • ${headers.size} columns",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}
