package com.akash.beautifulbhaluka.presentation.screens.jobs.details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobItem

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

    JobDetailsContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack
    )

    // Application Dialog
    if (uiState.showApplicationDialog) {
        JobApplicationDialog(
            uiState = uiState,
            onAction = viewModel::onAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobDetailsContent(
    uiState: JobDetailsUiState,
    onAction: (JobDetailsAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = uiState.job?.title ?: "Job Details",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = { /* Retry logic */ }) {
                            Text("Retry")
                        }
                    }
                }
            }

            uiState.job != null -> {
                JobDetailsBody(
                    job = uiState.job,
                    onApplyClick = { onAction(JobDetailsAction.ShowApplicationDialog) }
                )
            }
        }
    }
}

@Composable
private fun JobDetailsBody(
    job: JobItem,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Job Image
            AsyncImage(
                model = job.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Job Details
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Company Name
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Job Details Cards - Using data from JobItem
                JobDetailCard(
                    title = "বেতন",
                    value = job.salary
                )

                JobDetailCard(
                    title = "পদ সংখ্যা",
                    value = job.positionCount
                )

                JobDetailCard(
                    title = "লোকেশন",
                    value = job.location
                )

                JobDetailCard(
                    title = "আবেদনের শেষ তারিখ",
                    value = job.deadline
                )

                JobDetailCard(
                    title = "ন্যূনতম শিক্ষাগত যোগ্যতা",
                    value = job.education
                )

                JobDetailCard(
                    title = "অভিজ্ঞতা",
                    value = job.experience
                )

                JobDetailCard(
                    title = "চাকরির ধরন",
                    value = job.jobType
                )

                JobDetailCard(
                    title = "কাজের সময়",
                    value = job.workingHours
                )

                JobDetailCard(
                    title = "কাজের স্থান",
                    value = job.workLocation
                )

                JobDetailCard(
                    title = "যোগাযোগ",
                    value = job.contactInfo
                )

                // Extra spacing at bottom
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // Apply Button (Fixed at bottom)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = onApplyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Apply Now",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun JobDetailCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun JobApplicationDialog(
    uiState: JobDetailsUiState,
    onAction: (JobDetailsAction) -> Unit
) {
    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onAction(JobDetailsAction.SelectPdf(uri))
    }

    Dialog(
        onDismissRequest = {
            if (uiState.applicationState != ApplicationState.Submitting) {
                onAction(JobDetailsAction.HideApplicationDialog)
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            when (uiState.applicationState) {
                ApplicationState.Success -> {
                    SuccessContent()
                }

                else -> {
                    ApplicationFormContent(
                        uiState = uiState,
                        onAction = onAction,
                        onSelectPdf = { pdfLauncher.launch("application/pdf") }
                    )
                }
            }
        }
    }
}

@Composable
private fun ApplicationFormContent(
    uiState: JobDetailsUiState,
    onAction: (JobDetailsAction) -> Unit,
    onSelectPdf: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "Apply for Job",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // PDF Upload Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Upload Resume/CV (PDF) *",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onSelectPdf,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.outline
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uiState.selectedPdfUri != null) "PDF Selected" else "Select PDF File"
                    )
                }

                if (uiState.selectedPdfUri != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "File selected successfully",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Optional Note Section
        Column {
            Text(
                text = "Additional Note (Optional)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.applicationNote,
                onValueChange = { onAction(JobDetailsAction.UpdateApplicationNote(it)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter any additional information...") },
                minLines = 3,
                maxLines = 5
            )
        }

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onAction(JobDetailsAction.HideApplicationDialog) },
                modifier = Modifier.weight(1f),
                enabled = uiState.applicationState != ApplicationState.Submitting
            ) {
                Text("Cancel")
            }

            Button(
                onClick = { onAction(JobDetailsAction.SubmitApplication) },
                modifier = Modifier.weight(1f),
                enabled = uiState.selectedPdfUri != null && uiState.applicationState != ApplicationState.Submitting
            ) {
                if (uiState.applicationState == ApplicationState.Submitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Submit")
                }
            }
        }

        // Error state
        if (uiState.applicationState == ApplicationState.Error) {
            Text(
                text = "Failed to submit application. Please try again.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SuccessContent() {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "Application Submitted Successfully!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Your application has been submitted. We will contact you soon.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
