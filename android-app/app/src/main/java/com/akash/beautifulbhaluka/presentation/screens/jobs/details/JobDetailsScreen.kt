package com.akash.beautifulbhaluka.presentation.screens.jobs.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.Job
import com.akash.beautifulbhaluka.presentation.screens.jobs.components.ApplicationDialog
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailsScreen(
    jobId: String,
    onNavigateBack: () -> Unit,
    viewModel: JobDetailsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(jobId) {
        viewModel.onAction(JobDetailsAction.LoadJobDetails(jobId))
    }

    // Modern gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        when {
            uiState.isLoading -> {
                ModernLoadingState()
            }

            uiState.error != null -> {
                ModernErrorState(
                    error = uiState.error ?: "Unknown error",
                    onRetry = { viewModel.onAction(JobDetailsAction.LoadJobDetails(jobId)) },
                    onNavigateBack = onNavigateBack
                )
            }

            uiState.job != null -> {
                val job = uiState.job!!
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Hero Section with Company Banner
                    item {
                        HeroJobSection(
                            job = job,
                            onNavigateBack = onNavigateBack
                        )
                    }

                    // Job Info Card
                    item {
                        JobInfoCard(job = job)
                    }

                    // Quick Stats
                    item {
                        QuickJobStats(job = job)
                    }

                    // Job Description
                    item {
                        ModernJobDescription(job = job)
                    }

                    // Requirements
                    item {
                        ModernRequirementsCard(job = job)
                    }

                    // Company Details
                    item {
                        ModernCompanyCard(job = job)
                    }

                    // Bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }

                // Floating Apply Button
                FloatingApplyButton(
                    onApplyClick = { viewModel.onAction(JobDetailsAction.ShowApplicationDialog) }
                )
            }
        }
    }

    // Application Dialog
    if (uiState.showApplicationDialog) {
        ApplicationDialog(
            uiState = uiState,
            onAction = viewModel::onAction
        )
    }
}

@Composable
private fun BoxScope.FloatingApplyButton(onApplyClick: () -> Unit) {
    Button(
        onClick = onApplyClick,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF6366F1),
                            Color(0xFF8B5CF6)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = Color.White
                )
                Text(
                    text = "আবেদন করুন",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun HeroJobSection(
    job: Job,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        // Company Image
        AsyncImage(
            model = job.imageUrl
                ?: "https://via.placeholder.com/600x300/6366F1/FFFFFF?text=${job.company.firstOrNull() ?: "C"}",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )

        // Back Button
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .background(Color.White.copy(alpha = 0.9f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Job Title & Company
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
        ) {
            Text(
                text = job.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Business,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.95f)
                )
            }
        }
    }
}

@Composable
private fun JobInfoCard(job: Job) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-20).dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoChip(
                icon = Icons.Outlined.LocationOn,
                text = job.location,
                color = Color(0xFF10B981)
            )

            InfoChip(
                icon = Icons.Outlined.AttachMoney,
                text = "${job.salary.min}-${job.salary.max} টাকা",
                color = Color(0xFF6366F1)
            )
        }
    }
}

@Composable
private fun InfoChip(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = color.copy(alpha = 0.1f),
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = color
                )
            }
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun QuickJobStats(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickStatItem(
                icon = Icons.Outlined.Work,
                value = job.jobType.banglaName,
                label = "ধরন"
            )

            VerticalDivider(
                modifier = Modifier.height(48.dp),
                thickness = 1.dp
            )

            QuickStatItem(
                icon = Icons.Outlined.Groups,
                value = job.positionCount,
                label = "পদ"
            )

            VerticalDivider(
                modifier = Modifier.height(48.dp),
                thickness = 1.dp
            )

            QuickStatItem(
                icon = Icons.Outlined.DateRange,
                value = job.deadline,
                label = "শেষ তারিখ"
            )
        }
    }
}

@Composable
private fun QuickStatItem(
    icon: ImageVector,
    value: Any,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF6366F1)
        )
        Text(
            text = when (value) {
                is Long -> {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("bn"))
                    sdf.format(Date(value))
                }

                is Int -> value.toString()
                else -> value.toString()
            },
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ModernJobDescription(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionHeader(
                icon = Icons.Outlined.Description,
                title = "কাজের বিবরণ",
                color = Color(0xFF6366F1)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            Text(
                text = job.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun ModernRequirementsCard(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionHeader(
                icon = Icons.Outlined.School,
                title = "যোগ্যতা",
                color = Color(0xFF10B981)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            DetailRow(
                icon = Icons.Outlined.School,
                label = "শিক্ষাগত যোগ্যতা",
                value = job.education.banglaName
            )

            DetailRow(
                icon = Icons.AutoMirrored.Outlined.TrendingUp,
                label = "অভিজ্ঞতা",
                value = job.experience.banglaName
            )

            DetailRow(
                icon = Icons.Outlined.Schedule,
                label = "কাজের সময়",
                value = job.workingHours
            )
        }
    }
}

@Composable
private fun ModernCompanyCard(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionHeader(
                icon = Icons.Outlined.Business,
                title = "কোম্পানির তথ্য",
                color = Color(0xFF8B5CF6)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            DetailRow(
                icon = Icons.Outlined.Business,
                label = "কোম্পানি",
                value = job.company
            )

            DetailRow(
                icon = Icons.Outlined.LocationOn,
                label = "কর্মস্থল",
                value = job.workLocation.banglaName
            )

            DetailRow(
                icon = Icons.Outlined.Phone,
                label = "যোগাযোগ",
                value = job.contactInfo.phone
            )
        }
    }
}

@Composable
private fun SectionHeader(
    icon: ImageVector,
    title: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = color.copy(alpha = 0.1f),
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = color
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ModernLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 4.dp,
                color = Color(0xFF6366F1)
            )
            Text(
                text = "লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ModernErrorState(
    error: String,
    onRetry: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )

            Text(
                text = "কিছু সমস্যা হয়েছে",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("ফিরে যান")
                }

                FilledTonalButton(
                    onClick = onRetry,
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("আবার চেষ্টা করুন")
                }
            }
        }
    }
}

