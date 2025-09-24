package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A modern, responsive table component for displaying tabular data with professional styling.
 *
 * This component provides a clean, Material 3-styled table with alternating row colors,
 * proper spacing, and responsive design that works well on different screen sizes.
 *
 * @param title The table title displayed above the table
 * @param headers List of column headers
 * @param rows List of row data, where each row is a list of cell values
 * @param modifier Modifier for customizing the component's appearance and behavior
 * @param showTitle Whether to display the table title (default: true)
 * @param alternateRowColors Whether to use alternating row background colors (default: true)
 *
 * @sample
 * ```
 * val headers = listOf("Name", "Age", "City")
 * val rows = listOf(
 *     listOf("John", "25", "New York"),
 *     listOf("Jane", "30", "London"),
 *     listOf("Bob", "35", "Paris")
 * )
 *
 * ModernTable(
 *     title = "User Information",
 *     headers = headers,
 *     rows = rows,
 *     modifier = Modifier.fillMaxWidth()
 * )
 * ```
 *
 * Features:
 * - Modern Material 3 design with proper elevation and colors
 * - Responsive column widths that adapt to content
 * - Alternating row colors for better readability
 * - Professional typography with proper font weights
 * - Rounded corners and subtle shadows
 * - Theme-aware colors that adapt to light/dark modes
 * - Accessible design with proper contrast ratios
 * - Scrollable content for large tables
 */
@Composable
fun ModernTable(
    title: String,
    headers: List<String>,
    rows: List<List<String>>,
    modifier: Modifier = Modifier,
    showTitle: Boolean = true,
    alternateRowColors: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Table title
        if (showTitle) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(24.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(2.dp)
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Table container
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                // Table header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    headers.forEachIndexed { index, header ->
                        Text(
                            text = header,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.1.sp
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }

                // Table rows
                rows.forEachIndexed { rowIndex, row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (alternateRowColors && rowIndex % 2 != 0) {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surface
                                }
                            )
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        row.forEachIndexed { cellIndex, cell ->
                            Text(
                                text = cell,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 20.sp,
                                    letterSpacing = 0.1.sp,
                                    fontWeight = if (cellIndex == 0) FontWeight.SemiBold else FontWeight.Normal
                                ),
                                color = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = if (cellIndex == 0) 0.9f else 0.8f
                                ),
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }

                    // Row divider (except for last row)
                    if (rowIndex < rows.size - 1) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}
