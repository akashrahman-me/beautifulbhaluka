package com.akash.beautifulbhaluka.presentation.screens.schoolcollege

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ModernTable
import com.akash.beautifulbhaluka.presentation.screens.schoolcollege.components.FeaturedInstitutionCard

@Composable
fun SchoolCollegeScreen(
    viewModel: SchoolCollegeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SchoolCollegeContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun SchoolCollegeContent(
    uiState: SchoolCollegeUiState,
    onAction: (SchoolCollegeAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(24.dp)
        ) {
            // Modern Header Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Minimalist Title
                    Text(
                        text = "শিক্ষাপ্রতিষ্ঠান",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Light,
                            fontSize = 36.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Clean underline accent
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(2.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(1.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Subtitle with refined typography
                    Text(
                        text = "ভালুকার শিক্ষাপ্রতিষ্ঠানসমূহের বিস্তারিত তথ্য",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Loading/Error States
            if (uiState.isLoading) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }

            if (uiState.error != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = uiState.error,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            // Featured Institutions Cards Section
            if (uiState.featuredInstitutions.isNotEmpty()) {
                items(uiState.featuredInstitutions) { institution ->
                    FeaturedInstitutionCard(
                        institution = institution,
                        onClick = { onAction(SchoolCollegeAction.OnInstitutionClick(institution)) }
                    )
                }
            }

            // Clean section divider before tables
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                )
            }

            // Colleges Table Section
            if (uiState.colleges.isNotEmpty()) {
                item {
                    Column {
                        // Table title with modern styling
                        Text(
                            text = "কলেজ সমূহ",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        val collegeHeaders = listOf("ক্রমিক", "প্রতিষ্ঠানের নাম", "EIIN")
                        val collegeRows = uiState.colleges.map { college ->
                            listOf(college.serialNumber, college.name, college.eiin)
                        }

                        ModernTable(
                            title = "",
                            headers = collegeHeaders,
                            rows = collegeRows,
                            showTitle = false,
                            modifier = Modifier.fillMaxWidth(),
                            minColumnWidth = 60.dp,
                            maxColumnWidth = 300.dp,
                            alternateRowColors = true
                        )
                    }
                }
            }

            // High Schools Table Section
            if (uiState.highSchools.isNotEmpty()) {
                item {
                    Column {
                        // Table title with modern styling
                        Text(
                            text = "উচ্চ মাধ্যমিক বিদ্যালয়ের তালিকা",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 24.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        val highSchoolHeaders = listOf("ক্রমিক", "প্রতিষ্ঠানের নাম", "EIIN")
                        val highSchoolRows = uiState.highSchools.map { school ->
                            listOf(school.serialNumber, school.name, school.eiin)
                        }

                        ModernTable(
                            title = "",
                            headers = highSchoolHeaders,
                            rows = highSchoolRows,
                            showTitle = false,
                            modifier = Modifier.fillMaxWidth(),
                            minColumnWidth = 60.dp,
                            maxColumnWidth = 300.dp,
                            alternateRowColors = true
                        )
                    }
                }
            }
        }
    }
}
