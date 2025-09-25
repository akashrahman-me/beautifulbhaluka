package com.akash.beautifulbhaluka.presentation.screens.upazila_admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
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
import com.akash.beautifulbhaluka.presentation.components.common.ContactCard
import com.akash.beautifulbhaluka.presentation.components.common.ModernTable

@Composable
fun UpazilaAdminScreen(
    viewModel: UpazilaAdminViewModel = viewModel(),
    onPhoneCall: (String) -> Unit = {},
    onEmailSend: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Set up callbacks
    LaunchedEffect(Unit) {
        viewModel.setPhoneCallback(onPhoneCall)
        viewModel.setEmailCallback(onEmailSend)
        viewModel.onAction(UpazilaAdminAction.LoadData)
    }

    UpazilaAdminContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun UpazilaAdminContent(
    uiState: UpazilaAdminUiState,
    onAction: (UpazilaAdminAction) -> Unit
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
        UpazilaAdminHeader()

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
                // Contact Cards Section
                uiState.contactCards.forEach { contactInfo ->
                    ContactCard(
                        title = contactInfo.title,
                        phone = contactInfo.phone,
                        email = contactInfo.email,
                        imageUrl = contactInfo.image,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                // Former Officers Table
                FormerOfficersTable(
                    officers = uiState.formerOfficers,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun UpazilaAdminHeader() {
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
                imageVector = Icons.Default.AccountBalance,
                contentDescription = "উপজেলা প্রশাসন",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "উপজেলা প্রশাসন",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun FormerOfficersTable(
    officers: List<FormerOfficer>,
    modifier: Modifier = Modifier
) {
    val tableRows = officers.map { officer ->
        listOf(officer.serialNo, officer.name, officer.fromDate, officer.toDate)
    }

    Spacer(Modifier.height(8.dp))
    ModernTable(
        title = "পূর্বতন উপজেলা নির্বাহী কর্মকর্তাগণ",
        headers = listOf("ক্রঃ নং", "নাম", "হতে", "পর্যন্ত"),
        rows = tableRows,
        modifier = modifier
    )
}
