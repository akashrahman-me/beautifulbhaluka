package com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishMatchmakingScreen(
    onNavigateBack: () -> Unit,
    onProfilePublished: () -> Unit = {},
    viewModel: PublishMatchmakingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.onAction(PublishMatchmakingAction.SelectImage(it))
        }
    }

    LaunchedEffect(uiState.publishSuccess) {
        if (uiState.publishSuccess) {
            onProfilePublished()
        }
    }

    // Modern gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFF6B9D),
            Color(0xFFC06C84),
            MaterialTheme.colorScheme.surface
        ),
        startY = 0f,
        endY = 1000f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        if (uiState.isPublishing) {
            ModernLoadingState()
        } else {
            // Scrollable Content with Header
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Modern Header (scrolls with content)
                ModernPublishHeader(onNavigateBack = onNavigateBack)

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Profile Image Upload
                    ModernImageUploadCard(
                        selectedImageUri = uiState.selectedImageUri,
                        isUploading = uiState.isUploadingImage,
                        onSelectImage = { imagePickerLauncher.launch("image/*") }
                    )

                    // Personal Information Card
                    ModernInfoCard(
                        title = "Personal Information",
                        subtitle = "Basic details about yourself",
                        icon = Icons.Outlined.Person,
                        iconColor = Color(0xFFFF6B9D)
                    ) {
                        ModernTextField(
                            value = uiState.name,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateName(
                                        it
                                    )
                                )
                            },
                            label = "Full Name",
                            placeholder = "Enter your full name",
                            leadingIcon = Icons.Outlined.Badge,
                            isRequired = true,
                            error = uiState.validationErrors["name"]
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            ModernTextField(
                                value = uiState.age,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishMatchmakingAction.UpdateAge(
                                            it
                                        )
                                    )
                                },
                                label = "Age",
                                placeholder = "26",
                                leadingIcon = Icons.Outlined.Cake,
                                keyboardType = KeyboardType.Number,
                                isRequired = true,
                                error = uiState.validationErrors["age"],
                                modifier = Modifier.weight(1f)
                            )

                            ModernTextField(
                                value = uiState.height,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishMatchmakingAction.UpdateHeight(
                                            it
                                        )
                                    )
                                },
                                label = "Height",
                                placeholder = "5'6\"",
                                leadingIcon = Icons.Outlined.Height,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        ModernDropdownField(
                            value = uiState.gender,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateGender(
                                        it
                                    )
                                )
                            },
                            label = "Gender",
                            options = listOf("Male", "Female"),
                            leadingIcon = Icons.Outlined.Wc
                        )
                    }

                    // Professional & Education Card
                    ModernInfoCard(
                        title = "Professional & Education",
                        subtitle = "Career and academic background",
                        icon = Icons.Outlined.Work,
                        iconColor = Color(0xFF6366F1)
                    ) {
                        ModernTextField(
                            value = uiState.occupation,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateOccupation(
                                        it
                                    )
                                )
                            },
                            label = "Occupation",
                            placeholder = "Software Engineer",
                            leadingIcon = Icons.Outlined.Work,
                            isRequired = true,
                            error = uiState.validationErrors["occupation"]
                        )

                        ModernTextField(
                            value = uiState.education,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateEducation(
                                        it
                                    )
                                )
                            },
                            label = "Education",
                            placeholder = "B.Sc in Computer Science",
                            leadingIcon = Icons.Outlined.School,
                            isRequired = true,
                            error = uiState.validationErrors["education"]
                        )
                    }

                    // Location & Religious Info Card
                    ModernInfoCard(
                        title = "Location & Religious Info",
                        subtitle = "Where you live and religious background",
                        icon = Icons.Outlined.LocationOn,
                        iconColor = Color(0xFF10B981)
                    ) {
                        ModernTextField(
                            value = uiState.location,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateLocation(
                                        it
                                    )
                                )
                            },
                            label = "Location",
                            placeholder = "Bhaluka, Mymensingh",
                            leadingIcon = Icons.Outlined.LocationOn,
                            isRequired = true,
                            error = uiState.validationErrors["location"]
                        )

                        ModernDropdownField(
                            value = uiState.religion,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateReligion(
                                        it
                                    )
                                )
                            },
                            label = "Religion",
                            options = listOf(
                                "Islam",
                                "Hinduism",
                                "Buddhism",
                                "Christianity",
                                "Other"
                            ),
                            leadingIcon = Icons.Outlined.Star
                        )

                        ModernDropdownField(
                            value = uiState.maritalStatus,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateMaritalStatus(
                                        it
                                    )
                                )
                            },
                            label = "Marital Status",
                            options = listOf("Never Married", "Divorced", "Widowed"),
                            leadingIcon = Icons.Outlined.Favorite
                        )
                    }

                    // About Me Card
                    ModernInfoCard(
                        title = "About Me",
                        subtitle = "Tell us about yourself",
                        icon = Icons.Outlined.Description,
                        iconColor = Color(0xFFF59E0B)
                    ) {
                        ModernTextField(
                            value = uiState.bio,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateBio(
                                        it
                                    )
                                )
                            },
                            label = "Bio",
                            placeholder = "Write a brief description about yourself, your interests, and what you're looking for...",
                            leadingIcon = Icons.Outlined.Description,
                            minLines = 5,
                            maxLines = 8,
                            singleLine = false,
                            isRequired = true,
                            error = uiState.validationErrors["bio"]
                        )
                    }

                    // Interests & Hobbies Card
                    ModernInfoCard(
                        title = "Interests & Hobbies",
                        subtitle = "What do you enjoy doing?",
                        icon = Icons.Outlined.Psychology,
                        iconColor = Color(0xFFEC4899)
                    ) {
                        InterestsInput(
                            interests = uiState.interests,
                            currentInterest = uiState.currentInterest,
                            onCurrentInterestChange = {
                                viewModel.onAction(PublishMatchmakingAction.UpdateCurrentInterest(it))
                            },
                            onAddInterest = {
                                viewModel.onAction(PublishMatchmakingAction.AddInterest(it))
                            },
                            onRemoveInterest = {
                                viewModel.onAction(PublishMatchmakingAction.RemoveInterest(it))
                            }
                        )
                    }

                    // Contact Information Card
                    ModernInfoCard(
                        title = "Contact Information",
                        subtitle = "How can people reach you?",
                        icon = Icons.Outlined.ContactPhone,
                        iconColor = Color(0xFF8B5CF6)
                    ) {
                        ModernTextField(
                            value = uiState.phoneNumber,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdatePhoneNumber(
                                        it
                                    )
                                )
                            },
                            label = "Phone Number",
                            placeholder = "+880 1XXX-XXXXXX",
                            leadingIcon = Icons.Outlined.Phone,
                            keyboardType = KeyboardType.Phone,
                            error = uiState.validationErrors["phoneNumber"]
                        )

                        ModernTextField(
                            value = uiState.email,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishMatchmakingAction.UpdateEmail(
                                        it
                                    )
                                )
                            },
                            label = "Email",
                            placeholder = "your.email@example.com",
                            leadingIcon = Icons.Outlined.Email,
                            keyboardType = KeyboardType.Email,
                            error = uiState.validationErrors["email"]
                        )
                    }

                    // Error Display
                    if (uiState.error != null) {
                        val errorMsg = uiState.error ?: ""
                        ErrorCard(
                            message = errorMsg,
                            onDismiss = { viewModel.onAction(PublishMatchmakingAction.ClearError) }
                        )
                    }

                    // Publish Button
                    Button(
                        onClick = { viewModel.onAction(PublishMatchmakingAction.PublishProfile) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF6B9D)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Publish Profile",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernPublishHeader(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text("Create Profile") },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 4.dp)
    )
}

@Composable
fun ModernLoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFFFF6B9D),
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun ModernImageUploadCard(
    selectedImageUri: Uri?,
    isUploading: Boolean,
    onSelectImage: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = !isUploading) { onSelectImage() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected profile image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder - gray box with icon
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Add photo",
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Add Profile Photo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Loading overlay
            if (isUploading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            // Edit button (shows on both placeholder and selected image)
            if (!isUploading) {
                IconButton(
                    onClick = { onSelectImage() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color(0xFFFF6B9D), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Add photo",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ModernInfoCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            HorizontalDivider()

            content()
        }
    }
}

@Composable
fun ModernTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector? = null,
    isRequired: Boolean = false,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    minLines: Int = 1,
    maxLines: Int = 1,
    singleLine: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            leadingIcon = leadingIcon?.let { { Icon(it, null) } },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFFFF6B9D),
                unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f),
                focusedLabelColor = Color(0xFFFF6B9D),
                unfocusedLabelColor = Color.Gray
            ),
            isError = error != null
        )

        if (isRequired) {
            Text(
                text = "* Required",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        error?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernDropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            leadingIcon = leadingIcon?.let { { Icon(it, null) } },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            shape = RoundedCornerShape(12.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun InterestsInput(
    interests: List<String>,
    currentInterest: String,
    onCurrentInterestChange: (String) -> Unit,
    onAddInterest: (String) -> Unit,
    onRemoveInterest: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Display interests as simple chips in a wrapping layout
        if (interests.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                interests.chunked(3).forEach { rowInterests ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowInterests.forEach { interest ->
                            AssistChip(
                                onClick = { onRemoveInterest(interest) },
                                label = { Text(interest) },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove",
                                        modifier = Modifier.size(16.dp)
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFFEC4899),
                                    labelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = currentInterest,
                onValueChange = onCurrentInterestChange,
                placeholder = { Text("Enter your interest") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFFF6B9D),
                    unfocusedIndicatorColor = Color.Gray.copy(alpha = 0.5f),
                    focusedLabelColor = Color(0xFFFF6B9D),
                    unfocusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .weight(1f)
                    .border(
                        1.dp,
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFF6B9D), Color(0xFFC06C84))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            )

            Button(
                onClick = {
                    if (currentInterest.isNotBlank()) {
                        onAddInterest(currentInterest)
                        onCurrentInterestChange("")
                    }
                },
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6B9D)
                )
            ) {
                Text("Add")
            }
        }
    }
}

@Composable
fun ErrorCard(
    message: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFE3E3)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = Color(0xFFB00020),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB00020)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = Color(0xFFB00020)
                )
            }
        }
    }
}
