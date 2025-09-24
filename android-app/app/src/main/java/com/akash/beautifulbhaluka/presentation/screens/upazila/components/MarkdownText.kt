package com.akash.beautifulbhaluka.presentation.screens.upazila.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MarkdownText(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val lines = text.split("\n")
        var i = 0

        while (i < lines.size) {
            val line = lines[i].trim()

            when {
                // Handle headers
                line.startsWith("### ") -> {
                    Text(
                        text = line.removePrefix("### "),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                    )
                }

                line.startsWith("## ") -> {
                    Text(
                        text = line.removePrefix("## "),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
                    )
                }
                // Handle bullet points
                line.startsWith("- ") -> {
                    Text(
                        text = "• ${line.removePrefix("- ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(start = 8.dp)
                    )
                }
                // Handle bold text (simple **text** format)
                line.contains("**") -> {
                    val processedText = line.replace("**", "")
                    Text(
                        text = processedText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                // Handle empty lines
                line.isEmpty() -> {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                // Regular text
                else -> {
                    if (line.isNotEmpty()) {
                        Text(
                            text = line,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 2.dp),
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
            i++
        }
    }
}
