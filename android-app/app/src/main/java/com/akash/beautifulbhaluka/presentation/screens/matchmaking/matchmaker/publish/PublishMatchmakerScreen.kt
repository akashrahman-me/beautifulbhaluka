package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.publish

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.composables.icons.lucide.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishMatchmakerScreen(
    onNavigateBack: () -> Unit,
    viewModel: PublishMatchmakerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.publishSuccess) {
        if (uiState.publishSuccess) {
            viewModel.onAction(PublishMatchmakerAction.ResetPublishSuccess)
            onNavigateBack()
        }
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF667EEA),
            Color(0xFF764BA2),
            MaterialTheme.colorScheme.surface
        ),
        startY = 0f,
        endY = 1200f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Modern Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                Column {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Create Matchmaker Profile",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Share your matchmaking expertise",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                    imageVector = Lucide.ArrowLeft,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                    LinearProgressIndicator(
                        progress = {
                            val totalFields = 6
                            val filledFields = listOf(
                                uiState.name,
                                uiState.age,
                                uiState.experience,
                                uiState.location,
                                uiState.contactNumber,
                                uiState.bio
                            ).count { it.isNotBlank() }
                            filledFields.toFloat() / totalFields
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Basic Information
                SectionCard(
                    title = "Basic Information",
                    subtitle = "Essential details about you",
                    icon = Lucide.User,
                    iconColor = Color(0xFF667EEA)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ModernTextField(
                            value = uiState.name,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateName(
                                        it
                                    )
                                )
                            },
                            label = "Full Name *",
                            placeholder = "Enter your full name",
                            icon = Lucide.UserRound,
                            error = uiState.nameError
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            ModernTextField(
                                value = uiState.age,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishMatchmakerAction.UpdateAge(
                                            it
                                        )
                                    )
                                },
                                label = "Age *",
                                placeholder = "35",
                                icon = Lucide.Cake,
                                keyboardType = KeyboardType.Number,
                                error = uiState.ageError,
                                modifier = Modifier.weight(1f)
                            )

                            ModernTextField(
                                value = uiState.experience,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishMatchmakerAction.UpdateExperience(
                                            it
                                        )
                                    )
                                },
                                label = "Experience *",
                                placeholder = "10 years",
                                icon = Lucide.Award,
                                error = uiState.experienceError,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        ModernTextField(
                            value = uiState.location,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateLocation(
                                        it
                                    )
                                )
                            },
                            label = "Location *",
                            placeholder = "Bhaluka, Mymensingh",
                            icon = Lucide.MapPin,
                            error = uiState.locationError
                        )
                    }
                }

                // Contact Information
                SectionCard(
                    title = "Contact Information",
                    subtitle = "How clients can reach you",
                    icon = Lucide.Phone,
                    iconColor = Color(0xFF10B981)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ModernTextField(
                            value = uiState.contactNumber,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateContactNumber(
                                        it
                                    )
                                )
                            },
                            label = "Contact Number *",
                            placeholder = "01XXXXXXXXX",
                            icon = Lucide.Phone,
                            keyboardType = KeyboardType.Phone,
                            error = uiState.contactNumberError
                        )

                        ModernTextField(
                            value = uiState.whatsapp,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateWhatsApp(
                                        it
                                    )
                                )
                            },
                            label = "WhatsApp (Optional)",
                            placeholder = "01XXXXXXXXX",
                            icon = Lucide.MessageCircle,
                            keyboardType = KeyboardType.Phone
                        )

                        ModernTextField(
                            value = uiState.email,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateEmail(
                                        it
                                    )
                                )
                            },
                            label = "Email (Optional)",
                            placeholder = "matchmaker@example.com",
                            icon = Lucide.Mail,
                            keyboardType = KeyboardType.Email
                        )

                        ModernTextField(
                            value = uiState.workingHours,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateWorkingHours(
                                        it
                                    )
                                )
                            },
                            label = "Working Hours (Optional)",
                            placeholder = "9 AM - 6 PM",
                            icon = Lucide.Clock
                        )
                    }
                }

                // Professional Information
                SectionCard(
                    title = "Professional Details",
                    subtitle = "Your expertise and services",
                    icon = Lucide.Briefcase,
                    iconColor = Color(0xFFF59E0B)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Specializations
                        MultiSelectSection(
                            title = "Specializations",
                            icon = Lucide.Target,
                            options = listOf(
                                "Muslim Marriages",
                                "Hindu Marriages",
                                "Intercultural Matches",
                                "Professional Matches",
                                "NRI Matches",
                                "Remarriage"
                            ),
                            selectedOptions = uiState.selectedSpecializations,
                            onToggle = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.ToggleSpecialization(
                                        it
                                    )
                                )
                            }
                        )

                        // Services
                        MultiSelectSection(
                            title = "Services Offered",
                            icon = Lucide.ListChecks,
                            options = listOf(
                                "Profile Creation",
                                "Partner Search",
                                "Background Verification",
                                "Wedding Planning",
                                "Pre-marriage Counseling",
                                "Post-marriage Support"
                            ),
                            selectedOptions = uiState.selectedServices,
                            onToggle = { viewModel.onAction(PublishMatchmakerAction.ToggleService(it)) }
                        )

                        // Languages
                        MultiSelectSection(
                            title = "Languages Spoken",
                            icon = Lucide.Languages,
                            options = listOf("Bengali", "English", "Hindi", "Urdu", "Arabic"),
                            selectedOptions = uiState.selectedLanguages,
                            onToggle = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.ToggleLanguage(
                                        it
                                    )
                                )
                            }
                        )

                        ModernTextField(
                            value = uiState.consultationFee,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateConsultationFee(
                                        it
                                    )
                                )
                            },
                            label = "Consultation Fee (Optional)",
                            placeholder = "৳ 1000",
                            icon = Lucide.Banknote,
                            keyboardType = KeyboardType.Number
                        )
                    }
                }

                // About/Bio
                SectionCard(
                    title = "About You",
                    subtitle = "Tell clients about your experience",
                    icon = Lucide.FileText,
                    iconColor = Color(0xFFEC4899)
                ) {
                    ModernTextField(
                        value = uiState.bio,
                        onValueChange = { viewModel.onAction(PublishMatchmakerAction.UpdateBio(it)) },
                        label = "Bio *",
                        placeholder = "Share your experience, success stories, and approach to matchmaking... (min 50 characters)",
                        icon = Lucide.FileText,
                        minLines = 6,
                        singleLine = false,
                        error = uiState.bioError
                    )
                }

                // Social Media (Optional)
                SectionCard(
                    title = "Social Media & Website",
                    subtitle = "Build trust with your online presence",
                    icon = Lucide.Globe,
                    iconColor = Color(0xFF3B82F6)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ModernTextField(
                            value = uiState.facebook,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateFacebook(
                                        it
                                    )
                                )
                            },
                            label = "Facebook (Optional)",
                            placeholder = "facebook.com/yourprofile",
                            icon = Lucide.Facebook
                        )

                        ModernTextField(
                            value = uiState.instagram,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateInstagram(
                                        it
                                    )
                                )
                            },
                            label = "Instagram (Optional)",
                            placeholder = "instagram.com/yourprofile",
                            icon = Lucide.Instagram
                        )

                        ModernTextField(
                            value = uiState.linkedin,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateLinkedIn(
                                        it
                                    )
                                )
                            },
                            label = "LinkedIn (Optional)",
                            placeholder = "linkedin.com/in/yourprofile",
                            icon = Lucide.Linkedin
                        )

                        ModernTextField(
                            value = uiState.website,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakerAction.UpdateWebsite(
                                        it
                                    )
                                )
                            },
                            label = "Website (Optional)",
                            placeholder = "www.yourwebsite.com",
                            icon = Lucide.Globe
                        )
                    }
                }

                // Error Message
                AnimatedVisibility(
                    visible = uiState.error != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFEE2E2)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Lucide.TriangleAlert,
                                contentDescription = null,
                                tint = Color(0xFFDC2626)
                            )
                            Text(
                                text = uiState.error ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFDC2626)
                            )
                        }
                    }
                }
            }
        }

        // Floating Publish Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { viewModel.onAction(PublishMatchmakerAction.Publish) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !uiState.isPublishing,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF667EEA)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                if (uiState.isPublishing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color(0xFF667EEA),
                        strokeWidth = 3.dp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Publishing...",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Icon(
                        imageVector = Lucide.Send,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Publish Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            content()
        }
    }
}

@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1,
    singleLine: Boolean = true,
    error: String? = null
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (error != null) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            minLines = minLines,
            isError = error != null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF667EEA),
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        )

        AnimatedVisibility(visible = error != null) {
            Text(
                text = error ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
private fun MultiSelectSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    options: List<String>,
    selectedOptions: Set<String>,
    onToggle: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }

        // Chips
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedOptions.contains(option)
                FilterChip(
                    selected = isSelected,
                    onClick = { onToggle(option) },
                    label = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Lucide.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF667EEA),
                        selectedLabelColor = Color.White,
                        selectedLeadingIconColor = Color.White
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) Color.Transparent
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}

