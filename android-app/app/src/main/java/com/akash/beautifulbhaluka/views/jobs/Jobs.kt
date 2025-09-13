package com.akash.beautifulbhaluka.views.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.componets.common.AverageBackgroundImage


@Composable
fun Jobs() {
    val scrollState = rememberScrollState()

    Column {
        // MAIN CONTENT
        Box(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
        ) {
            Column {
                Text(
                    text = "চাকরির খবর-নিয়োগ বিজ্ঞপ্তি",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF1C4A8A)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.large)
                        .border(1.dp, Color(0xFF007BFF), MaterialTheme.shapes.large)
                        .clickable() { }
                        .padding(horizontal = 12.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "\uD83D\uDCE2   প্রতিষ্ঠানের নিয়োগ বিজ্ঞপ্তি প্রকাশ করুন",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Start,
                        color = Color(0xFF007BFF)
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))

                Column(verticalArrangement = Arrangement.spacedBy(36.dp)) {
                    Column {
                        Text(
                            text = "মাহিনূর ফ্যাশন",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        AverageBackgroundImage()

                        Spacer(modifier = Modifier.height(24.dp))

                        // Sample realistic data
                        val tableData = listOf(
                            Triple("পদের নাম", "রিসেলার", Icons.Default.Work),
                            Triple("পদসংখ্যা", "100", Icons.Default.People),
                            Triple("শিক্ষাগত যোগ্যতা", "সর্বনিম্ন এস এস সি পাস", Icons.Default.School),
                            Triple("বেতন", "কমিশন ভিক্তিক", Icons.Default.AttachMoney),
                            Triple("আবেদনের শেষ তারিখ", "2025-03-12", Icons.Default.DateRange),
                            Triple("নাম্বার", "01902763880, 01745-920702 (What app)", Icons.Default.Phone),
                            Triple("ঠিকানা", "অনলাইন, হবিরবাড়ী,আমতলী, ভালুকা, ময়মনসিংহ", Icons.Default.LocationOn),
                            Triple(
                                "অভিজ্ঞতা ও বিস্তারিত",
                                "*ভালো ইংরেজি লিখা ও পড়া জানা থাকতে হবে। * ভালো মন মনসিকতা থাকতে হবে। * র্দীঘ সময় কাজ করার ধোর্য থাকা লাগবে।",
                                Icons.Default.Info
                            )
                        )

                        // Each cell of a column must have the same weight.
                        val column1Weight = .3f // 30%
                        val column2Weight = .7f // 70%


                        // Rows
                        HorizontalDivider()
                        tableData.forEachIndexed { index, (id, name, icon) ->

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                            ) {
                                VerticalDivider()
                                Column(
                                    modifier = Modifier
                                        .weight(column1Weight)
                                        .padding(8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = id,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
                                        )
                                        Text(
                                            text = id.toString(),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                }
                                VerticalDivider()
                                Column(
                                    modifier = Modifier
                                        .weight(column2Weight)
                                        .padding(8.dp)
                                ) {
                                    SelectionContainer {
                                        Text(
                                            text = name.toString(),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                VerticalDivider()
                            }
                            HorizontalDivider()
                        }
                    }
                    Column {
                        Text(
                            text = "মাহিনূর ফ্যাশন",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFF007BFF), MaterialTheme.shapes.extraLarge)
                                .background(Color(0xFF007BFF).copy(0.1f), shape = MaterialTheme.shapes.extraLarge)
                                .padding(vertical = 24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ImageNotSupported,
                                contentDescription = "not found",
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(end = 4.dp),
                                tint = Color(0xFF007BFF)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "সার্কুলারের ছবি যোগ করা হয়নি",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                color = Color(0xFF007BFF)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Sample realistic data
                        val tableData = listOf(
                            Triple("পদের নাম", "রিসেলার", Icons.Default.Work),
                            Triple("পদসংখ্যা", "100", Icons.Default.People),
                            Triple("শিক্ষাগত যোগ্যতা", "সর্বনিম্ন এস এস সি পাস", Icons.Default.School),
                            Triple("বেতন", "কমিশন ভিক্তিক", Icons.Default.AttachMoney),
                            Triple("আবেদনের শেষ তারিখ", "2025-03-12", Icons.Default.DateRange),
                            Triple("নাম্বার", "01902763880, 01745-920702 (What app)", Icons.Default.Phone),
                            Triple("ঠিকানা", "অনলাইন, হবিরবাড়ী,আমতলী, ভালুকা, ময়মনসিংহ", Icons.Default.LocationOn),
                            Triple(
                                "অভিজ্ঞতা ও বিস্তারিত",
                                "*ভালো ইংরেজি লিখা ও পড়া জানা থাকতে হবে। * ভালো মন মনসিকতা থাকতে হবে। * র্দীঘ সময় কাজ করার ধোর্য থাকা লাগবে।",
                                Icons.Default.Info
                            )
                        )

                        // Each cell of a column must have the same weight.
                        val column1Weight = .3f // 30%
                        val column2Weight = .7f // 70%


                        // Rows
                        HorizontalDivider()
                        tableData.forEachIndexed { index, (id, name, icon) ->

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min)
                            ) {
                                VerticalDivider()
                                Column(
                                    modifier = Modifier
                                        .weight(column1Weight)
                                        .padding(8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = id,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
                                        )
                                        Text(
                                            text = id.toString(),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                }
                                VerticalDivider()
                                Column(
                                    modifier = Modifier
                                        .weight(column2Weight)
                                        .padding(8.dp)
                                ) {
                                    SelectionContainer {
                                        Text(
                                            text = name.toString(),
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                VerticalDivider()
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
