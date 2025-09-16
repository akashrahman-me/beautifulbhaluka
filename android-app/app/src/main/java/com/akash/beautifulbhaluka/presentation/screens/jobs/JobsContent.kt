package com.akash.beautifulbhaluka.presentation.screens.jobs

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.CircularProgressIndicator
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
import com.akash.beautifulbhaluka.presentation.components.common.AverageBackgroundImage

@Composable
fun JobsContent(
    uiState: JobsUiState,
    onAction: (JobsAction) -> Unit
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        uiState.jobs.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.jobs, key = { it.id }) { job ->
                    JobCard(
                        job = job,
                        onJobClick = { onAction(JobsAction.ViewJobDetails(job.id)) }
                    )
                }
            }
        }

        else -> {
            // Default implementation with static content (from original Jobs.kt)
            JobsStaticContent()
        }
    }
}

@Composable
private fun JobCard(
    job: JobItem,
    onJobClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onJobClick() }
            .padding(16.dp)
    ) {
        Text(
            text = job.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = job.company,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = job.location,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AttachMoney,
                contentDescription = "Salary",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = job.salary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Posted: ${job.postedDate}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun JobsStaticContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            AverageBackgroundImage()

            Text(
                text = "JOBS",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Black,
                    color = Color.White
                ),
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            JobInfoRow(
                icon = Icons.Default.Work,
                label = "Job Title",
                value = "Android Developer"
            )

            JobInfoRow(
                icon = Icons.Default.People,
                label = "Company",
                value = "Tech Company Ltd."
            )

            JobInfoRow(
                icon = Icons.Default.LocationOn,
                label = "Location",
                value = "Dhaka, Bangladesh"
            )

            JobInfoRow(
                icon = Icons.Default.AttachMoney,
                label = "Salary",
                value = "50,000 - 80,000 BDT"
            )

            JobInfoRow(
                icon = Icons.Default.Work,
                label = "Experience",
                value = "2-3 years required"
            )

            JobInfoRow(
                icon = Icons.Default.School,
                label = "Education",
                value = "Bachelor's in CSE/IT"
            )

            JobInfoRow(
                icon = Icons.Default.DateRange,
                label = "Posted",
                value = "2 days ago"
            )

            JobInfoRow(
                icon = Icons.Default.DateRange,
                label = "Deadline",
                value = "30th Sep, 2025"
            )

            JobInfoRow(
                icon = Icons.Default.Phone,
                label = "Contact",
                value = "hr@techcompany.com"
            )

            HorizontalDivider()

            Column {
                Text(
                    text = "Job Description",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                SelectionContainer {
                    Text(
                        text = """
                        We are looking for an experienced Android developer to join our growing team. The ideal candidate will have strong knowledge of Kotlin, Jetpack Compose, and modern Android development practices.
                        
                        Responsibilities:
                        • Develop and maintain Android applications
                        • Collaborate with cross-functional teams
                        • Write clean, maintainable code
                        • Participate in code reviews
                        • Stay updated with latest Android technologies
                        
                        Requirements:
                        • 2-3 years of Android development experience
                        • Strong knowledge of Kotlin
                        • Experience with Jetpack Compose
                        • Understanding of MVVM architecture
                        • Good communication skills
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun JobInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        VerticalDivider(
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
