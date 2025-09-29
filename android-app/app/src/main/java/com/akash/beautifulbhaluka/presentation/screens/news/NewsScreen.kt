package com.akash.beautifulbhaluka.presentation.screens.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.ModernTable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NewsContent(uiState = uiState)
}

@Composable
fun NewsContent(
    uiState: NewsUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header Section
        HeaderSection()

        // Journalists Table
        JournalistTable(
            journalists = uiState.journalists,
            isLoading = uiState.isLoading
        )
    }
}

@Composable
private fun HeaderSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "সাংবাদিক",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun JournalistTable(
    journalists: List<com.akash.beautifulbhaluka.domain.model.Journalist>,
    isLoading: Boolean
) {
    val headers = listOf("ক্র.নং", "সাংবাদিকের নাম", "কর্মরত গণমাধ্যম", "মোবাইল নাম্বার")

    val rows = journalists.map { journalist ->
        listOf(
            journalist.serialNumber,
            journalist.name,
            journalist.media,
            journalist.mobile
        )
    }

    ModernTable(
        title = "ভালুকার সাংবাদিকগন",
        headers = headers,
        rows = rows,
        isLoading = isLoading,
        emptyMessage = "কোন সাংবাদিকের তথ্য পাওয়া যায়নি",
        minColumnWidth = 100.dp,
        maxColumnWidth = 250.dp,
        alternateRowColors = true,
        headerAlignment = TextAlign.Center,
        cellAlignment = TextAlign.Start
    )
}
