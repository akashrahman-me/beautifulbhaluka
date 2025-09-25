package com.akash.beautifulbhaluka.presentation.screens.doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.DoctorCard
import com.akash.beautifulbhaluka.presentation.components.common.ModernTable

@Composable
fun DoctorScreen(
    viewModel: DoctorViewModel = viewModel(),
    onPhoneCall: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Set up callbacks
    LaunchedEffect(Unit) {
        viewModel.setPhoneCallback(onPhoneCall)
        viewModel.onAction(DoctorAction.LoadData)
    }

    DoctorContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun DoctorContent(
    uiState: DoctorUiState,
    onAction: (DoctorAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Page Title with Icon
        DoctorHeader()

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = uiState.error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            else -> {
                // Doctor Cards Section
                uiState.doctorCards.forEach { doctorInfo ->
                    DoctorCard(
                        name = doctorInfo.name,
                        specialist = doctorInfo.specialist,
                        phone = doctorInfo.phone,
                        imageUrl = doctorInfo.image,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Specialist Doctors Table
                SpecialistDoctorsTable(
                    doctors = uiState.specialistDoctors,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun DoctorHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalHospital,
                contentDescription = "ভালুকার ডাক্তারগন",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "ভালুকার ডাক্তারগন",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun SpecialistDoctorsTable(
    doctors: List<SpecialistDoctor>,
    modifier: Modifier = Modifier
) {
    val tableRows = doctors.map { doctor ->
        listOf(doctor.serialNo, doctor.name, doctor.specialist, doctor.phone)
    }

    Spacer(Modifier.height(8.dp))
    ModernTable(
        title = "বিশেষজ্ঞ ডাক্তারগন",
        headers = listOf("ক্রমিক", "ডাক্তারের নাম", "বিশেষজ্ঞ", "মোবাইল"),
        rows = tableRows,
        modifier = modifier
    )
}
