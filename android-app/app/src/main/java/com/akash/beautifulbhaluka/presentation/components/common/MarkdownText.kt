package com.akash.beautifulbhaluka.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A modern markdown text renderer component that converts markdown-formatted text into styled Compose UI.
 *
 * This component provides a professional way to display markdown content with custom styling
 * that follows Material 3 design principles. It supports various markdown elements with
 * enhanced visual presentation.
 *
 * @param text The markdown-formatted text content to be rendered
 * @param modifier Modifier for customizing the component's appearance and behavior
 * @param enableStyling Whether to apply enhanced styling (colored backgrounds, etc.) (default: true)
 *
 * @sample
 * ```
 * val markdownContent = """
 *     ## Main Header
 *     This is regular text content.
 *
 *     ### Sub Header
 *     - First bullet point
 *     - Second bullet point
 *
 *     **This is bold text**
 * """
 *
 * MarkdownText(
 *     text = markdownContent,
 *     modifier = Modifier.fillMaxWidth()
 * )
 * ```
 *
 * Supported Markdown Features:
 * - **Headers**: ## Main Header, ### Sub Header
 * - **Bold Text**: **bold content** (renders in colored containers)
 * - **Bullet Points**: - List item (renders with modern bullet dots)
 * - **Regular Text**: Plain text with justified alignment
 * - **Line Breaks**: Empty lines create proper spacing
 *
 * Styling Features:
 * - Headers get colored backgrounds and accent colors
 * - Sub headers have colored left border indicators
 * - Bullet points use modern rounded dots instead of traditional bullets
 * - Bold text is displayed in subtle colored containers
 * - Optimized typography with proper line heights and letter spacing
 * - Responsive design that adapts to theme colors
 *
 * Design Principles:
 * - Follows Material 3 color scheme
 * - Uses theme-aware colors that adapt to light/dark modes
 * - Proper text hierarchy with varied font weights and sizes
 * - Consistent spacing and padding throughout
 * - Accessible color contrasts and readable typography
 */
@Composable
fun MarkdownText(
    text: String,
    modifier: Modifier = Modifier,
    enableStyling: Boolean = true
) {
    Column(modifier = modifier) {
        val lines = text.split("\n")
        var i = 0

        while (i < lines.size) {
            val line = lines[i].trim()

            when {
                // Handle main headers (##)
                line.startsWith("## ") -> {
                    val headerText = line.removePrefix("## ")
                    if (enableStyling) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = headerText,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.3).sp
                                ),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            )
                        }
                    } else {
                        Text(
                            text = headerText,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                // Handle sub headers (###)
                line.startsWith("### ") -> {
                    val subHeaderText = line.removePrefix("### ")
                    if (enableStyling) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(20.dp)
                                    .background(
                                        MaterialTheme.colorScheme.secondary,
                                        RoundedCornerShape(1.5.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = subHeaderText,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = (-0.2).sp
                                ),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    } else {
                        Text(
                            text = subHeaderText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }

                // Handle bullet points with modern styling
                line.startsWith("- ") -> {
                    val bulletText = line.removePrefix("- ")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        if (enableStyling) {
                            // Modern bullet point
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                        RoundedCornerShape(3.dp)
                                    )
                                    .padding(top = 8.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        } else {
                            Text(
                                text = "• ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Text(
                            text = bulletText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 20.sp,
                                letterSpacing = 0.1.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Handle bold text (enhanced styling)
                line.contains("**") -> {
                    val processedText = line.replace("**", "")
                    if (enableStyling) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                        ) {
                            Text(
                                text = processedText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.1.sp
                                ),
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    } else {
                        Text(
                            text = processedText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Handle empty lines (spacing)
                line.isEmpty() -> {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Regular text with improved styling
                else -> {
                    if (line.isNotEmpty()) {
                        Text(
                            text = line,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp,
                                letterSpacing = 0.15.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            i++
        }
    }
}
