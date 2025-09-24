package com.akash.beautifulbhaluka.presentation.screens.upazila.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModernMarkdownText(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val lines = text.split("\n")
        var i = 0

        while (i < lines.size) {
            val line = lines[i].trim()

            when {
                // Handle main headers (##)
                line.startsWith("## ") -> {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = line.removePrefix("## "),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.3).sp
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        )
                    }
                }

                // Handle sub headers (###)
                line.startsWith("### ") -> {
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
                            text = line.removePrefix("### "),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = (-0.2).sp
                            ),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                // Handle bullet points with modern styling
                line.startsWith("- ") -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
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
                        Text(
                            text = line.removePrefix("- "),
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
