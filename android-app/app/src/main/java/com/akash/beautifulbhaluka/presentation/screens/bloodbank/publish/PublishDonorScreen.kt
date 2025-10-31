package com.akash.beautifulbhaluka.presentation.screens.bloodbank.publish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishDonorScreen(
    viewModel: PublishDonorViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onPublishSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setPublishSuccessCallback(onPublishSuccess)
    }

    // Show success and navigate back
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onPublishSuccess()
        }
    }

    PublishDonorContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishDonorContent(
    uiState: PublishDonorUiState,
    onAction: (PublishDonorAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF4CAF50),
                                            Color(0xFF66BB6A)
                                        )
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🩸",
                                fontSize = 18.sp
                            )
                        }
                        Text(
                            text = "রক্তদাতা প্রকাশ করুন",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero Card
            HeroCard()

            // Error Message
            if (uiState.error != null) {
                ErrorCard(
                    error = uiState.error,
                    onDismiss = { onAction(PublishDonorAction.ClearError) }
                )
            }

            // Personal Information Section
            SectionCard(
                title = "ব্যক্তিগত তথ্য",
                icon = Icons.Outlined.Person,
                gradientColors = listOf(Color(0xFFE53935), Color(0xFFFF6B6B))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ModernTextField(
                        value = uiState.fullName,
                        onValueChange = { onAction(PublishDonorAction.UpdateFullName(it)) },
                        label = "পূর্ণ নাম",
                        placeholder = "আপনার পূর্ণ নাম লিখুন",
                        leadingIcon = Icons.Outlined.Badge,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        error = uiState.validationErrors.fullName
                    )

                    ModernTextField(
                        value = uiState.mobileNumber,
                        onValueChange = { onAction(PublishDonorAction.UpdateMobileNumber(it)) },
                        label = "মোবাইল নম্বর",
                        placeholder = "01XXXXXXXXX",
                        leadingIcon = Icons.Outlined.Phone,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        error = uiState.validationErrors.mobileNumber
                    )

                    BloodGroupDropdown(
                        selectedBloodGroup = uiState.bloodGroup,
                        onBloodGroupSelected = { onAction(PublishDonorAction.UpdateBloodGroup(it)) },
                        expanded = uiState.isBloodGroupDropdownExpanded,
                        onExpandedChange = {
                            onAction(
                                PublishDonorAction.SetBloodGroupDropdownExpanded(
                                    it
                                )
                            )
                        },
                        bloodGroups = getBloodGroups(),
                        error = uiState.validationErrors.bloodGroup
                    )

                    ModernTextField(
                        value = uiState.address,
                        onValueChange = { onAction(PublishDonorAction.UpdateAddress(it)) },
                        label = "বর্তমান ঠিকানা",
                        placeholder = "আপনার সম্পূর্ণ ঠিকানা লিখুন",
                        leadingIcon = Icons.Outlined.LocationOn,
                        minLines = 3,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        error = uiState.validationErrors.address
                    )

                    ModernTextField(
                        value = uiState.lastDonationDate,
                        onValueChange = { onAction(PublishDonorAction.UpdateLastDonationDate(it)) },
                        label = "সর্বশেষ রক্তদান তারিখ",
                        placeholder = "DD/MM/YYYY",
                        leadingIcon = Icons.Outlined.CalendarToday,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        error = uiState.validationErrors.lastDonationDate
                    )
                }
            }

            // Social Links Section
            SectionCard(
                title = "সোশ্যাল মিডিয়া লিংক (ঐচ্ছিক)",
                icon = Icons.Outlined.Share,
                gradientColors = listOf(Color(0xFF2196F3), Color(0xFF42A5F5))
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ModernTextField(
                        value = uiState.facebookLink,
                        onValueChange = { onAction(PublishDonorAction.UpdateFacebookLink(it)) },
                        label = "Facebook প্রোফাইল লিংক",
                        placeholder = "https://facebook.com/...",
                        leadingIcon = Icons.Outlined.Link,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
                    )

                    ModernTextField(
                        value = uiState.whatsappNumber,
                        onValueChange = { onAction(PublishDonorAction.UpdateWhatsAppNumber(it)) },
                        label = "WhatsApp নম্বর",
                        placeholder = "01XXXXXXXXX",
                        leadingIcon = Icons.Outlined.ChatBubble,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }
            }

            // Submit Button
            SubmitButton(
                isLoading = uiState.isLoading,
                enabled = uiState.fullName.isNotEmpty() &&
                        uiState.mobileNumber.isNotEmpty() &&
                        uiState.bloodGroup.isNotEmpty() &&
                        uiState.address.isNotEmpty() &&
                        uiState.lastDonationDate.isNotEmpty(),
                onClick = { onAction(PublishDonorAction.Submit) }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ErrorCard(
    error: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
private fun SubmitButton(
    isLoading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled && !isLoading
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF4CAF50),
                            Color(0xFF66BB6A)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = "প্রকাশ করুন",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF4CAF50),
                            Color(0xFF45A049),
                            Color(0xFF388E3C)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🩸",
                    fontSize = 40.sp
                )
                Text(
                    text = "রক্তদাতা হিসাবে নিবন্ধন করুন",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Text(
                    text = "আপনার তথ্য শেয়ার করুন এবং জীবন বাঁচাতে সাহায্য করুন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            brush = Brush.linearGradient(gradientColors),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    error: String? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            },
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant
            ),
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            isError = error != null
        )

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BloodGroupDropdown(
    selectedBloodGroup: String,
    onBloodGroupSelected: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    bloodGroups: List<String>,
    error: String? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "রক্তের গ্রুপ",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedBloodGroup,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = "রক্তের গ্রুপ নির্বাচন করুন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                leadingIcon = {
                    Text(
                        text = "🩸",
                        fontSize = 20.sp
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant
                ),
                isError = error != null
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                bloodGroups.forEach { group ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🩸")
                                Text(
                                    text = group,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE53935)
                                )
                            }
                        },
                        onClick = {
                            onBloodGroupSelected(group)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }

        if (error != null) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


