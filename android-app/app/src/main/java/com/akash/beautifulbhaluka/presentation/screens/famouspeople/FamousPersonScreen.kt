package com.akash.beautifulbhaluka.presentation.screens.famouspeople

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.presentation.components.common.FamousPersonCard
import com.akash.beautifulbhaluka.presentation.components.common.ScreenTopBar

@Composable
fun FamousPersonScreen(
    viewModel: FamousPersonViewModel = viewModel(),
    navigateBack: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    FamousPersonContent(
        uiState = uiState,
        onViewPerson = viewModel::onViewPerson,
        onRetry = viewModel::loadFamousPeopleData,
        onNavigateBack = navigateBack,
        onNavigateHome = navigateToHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamousPersonContent(
    uiState: FamousPersonUiState,
    onViewPerson: (com.akash.beautifulbhaluka.domain.model.FamousPerson) -> Unit,
    onRetry: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit
) {
    Scaffold(
        topBar = {
            ScreenTopBar(
                title = "প্রসিদ্ধ ব্যক্তিত্ব",
                onNavigateBack = onNavigateBack,
                onNavigateHome = onNavigateHome
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 3.dp
                    )
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = uiState.error,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = onRetry
                        ) {
                            Text("পুনরায় চেষ্টা করুন")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = paddingValues.calculateTopPadding() + 24.dp,
                        bottom = paddingValues.calculateBottomPadding() + 24.dp,
                        start = 24.dp,
                        end = 24.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(
                        items = uiState.famousPeople,
                        key = { it.title }
                    ) { person ->
                        FamousPersonCard(
                            title = person.title,
                            thumbnail = person.thumbnail,
                            author = person.author,
                            date = person.date,
                            excerpt = person.excerpt,
                            category = person.category,
                            onClick = { onViewPerson(person) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
