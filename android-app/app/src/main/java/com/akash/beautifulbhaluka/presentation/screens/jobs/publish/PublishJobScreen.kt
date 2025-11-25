package com.akash.beautifulbhaluka.presentation.screens.jobs.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishJobScreen(
    onNavigateBack: () -> Unit,
    onJobPublished: () -> Unit,
    viewModel: PublishJobViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.publishSuccess) {
        if (uiState.publishSuccess) {
            onJobPublished()
        }
    }

    // Modern gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6366F1),
            MaterialTheme.colorScheme.surface
        ),
        startY = 0f,
        endY = 800f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Modern Header
            ModernPublishHeader(onNavigateBack = onNavigateBack)

            if (uiState.isLoading) {
                ModernLoadingState()
            } else {
                // Scrollable Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Company Image Upload
                    ModernImageUploadCard(
                        selectedImageUri = uiState.selectedImageUri,
                        onSelectImage = { viewModel.onAction(PublishJobAction.SelectImage(it)) }
                    )

                    // Job Information Card
                    ModernInfoCard(
                        title = "চাকরির তথ্য",
                        subtitle = "চাকরির বিস্তারিত তথ্য দিন",
                        icon = Icons.Outlined.Work,
                        iconColor = Color(0xFF6366F1)
                    ) {
                        ModernTextField(
                            value = uiState.title,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateTitle(it)) },
                            label = "চাকরির শিরোনাম",
                            placeholder = "যেমন: সিনিয়র সফটওয়্যার ইঞ্জিনিয়ার",
                            leadingIcon = Icons.Outlined.Badge,
                            isRequired = true
                        )

                        ModernTextField(
                            value = uiState.company,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateCompany(it)) },
                            label = "কোম্পানির নাম",
                            placeholder = "যেমন: টেক ইনোভেশনস লিমিটেড",
                            leadingIcon = Icons.Outlined.Business,
                            isRequired = true
                        )

                        ModernTextField(
                            value = uiState.description,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishJobAction.UpdateDescription(
                                        it
                                    )
                                )
                            },
                            label = "কাজের বিবরণ",
                            placeholder = "চাকরির দায়িত্ব ও কাজের বিস্তারিত লিখুন...",
                            leadingIcon = Icons.Outlined.Description,
                            minLines = 4,
                            maxLines = 6,
                            singleLine = false
                        )
                    }

                    // Location & Compensation Card
                    ModernInfoCard(
                        title = "লোকেশন ও বেতন",
                        subtitle = "স্থান এবং বেতনের তথ্য",
                        icon = Icons.Outlined.LocationOn,
                        iconColor = Color(0xFF10B981)
                    ) {
                        ModernTextField(
                            value = uiState.location,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateLocation(it)) },
                            label = "কর্মস্থল",
                            placeholder = "যেমন: ঢাকা, বাংলাদেশ",
                            leadingIcon = Icons.Outlined.LocationOn,
                            isRequired = true
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            ModernTextField(
                                value = uiState.salary,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishJobAction.UpdateSalary(
                                            it
                                        )
                                    )
                                },
                                label = "বেতন",
                                placeholder = "৳৫০,০০০ - ৳৭০,০০০",
                                leadingIcon = Icons.Outlined.AttachMoney,
                                isRequired = true,
                                modifier = Modifier.weight(1f)
                            )

                            ModernTextField(
                                value = uiState.positionCount,
                                onValueChange = {
                                    viewModel.onAction(
                                        PublishJobAction.UpdatePositionCount(
                                            it
                                        )
                                    )
                                },
                                label = "পদ সংখ্যা",
                                placeholder = "৫",
                                leadingIcon = Icons.Outlined.Groups,
                                isRequired = true,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        ModernTextField(
                            value = uiState.deadline,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateDeadline(it)) },
                            label = "আবেদনের শেষ তারিখ",
                            placeholder = "৩১ ডিসেম্বর, ২০২৫",
                            leadingIcon = Icons.Outlined.DateRange,
                            isRequired = true
                        )
                    }

                    // Requirements Card
                    ModernInfoCard(
                        title = "যোগ্যতা",
                        subtitle = "প্রার্থীর যোগ্যতার মানদণ্ড",
                        icon = Icons.Outlined.School,
                        iconColor = Color(0xFF8B5CF6)
                    ) {
                        ModernTextField(
                            value = uiState.education,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateEducation(it)) },
                            label = "শিক্ষাগত যোগ্যতা",
                            placeholder = "যেমন: স্নাতক (কম্পিউটার সায়েন্স)",
                            leadingIcon = Icons.Outlined.School,
                            isRequired = true
                        )

                        ModernTextField(
                            value = uiState.experience,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishJobAction.UpdateExperience(
                                        it
                                    )
                                )
                            },
                            label = "অভিজ্ঞতা",
                            placeholder = "যেমন: ৩-৫ বছর",
                            leadingIcon = Icons.AutoMirrored.Outlined.TrendingUp,
                            isRequired = true
                        )
                    }

                    // Work Details Card
                    ModernInfoCard(
                        title = "কাজের বিস্তারিত",
                        subtitle = "কাজের ধরন ও সময়",
                        icon = Icons.Outlined.AccessTime,
                        iconColor = Color(0xFFF59E0B)
                    ) {
                        ModernTextField(
                            value = uiState.jobType,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateJobType(it)) },
                            label = "চাকরির ধরন",
                            placeholder = "যেমন: ফুল-টাইম",
                            leadingIcon = Icons.Outlined.Work
                        )

                        ModernTextField(
                            value = uiState.workingHours,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishJobAction.UpdateWorkingHours(
                                        it
                                    )
                                )
                            },
                            label = "কাজের সময়",
                            placeholder = "যেমন: ৮ ঘন্টা",
                            leadingIcon = Icons.Outlined.Schedule
                        )

                        ModernTextField(
                            value = uiState.workLocation,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishJobAction.UpdateWorkLocation(
                                        it
                                    )
                                )
                            },
                            label = "কাজের ধরন",
                            placeholder = "যেমন: অন-সাইট / রিমোট",
                            leadingIcon = Icons.Outlined.Place
                        )
                    }

                    // Contact Information Card
                    ModernInfoCard(
                        title = "যোগাযোগ",
                        subtitle = "যোগাযোগের তথ্য",
                        icon = Icons.Outlined.ContactPhone,
                        iconColor = Color(0xFFEF4444)
                    ) {
                        ModernTextField(
                            value = uiState.phoneNumber,
                            onValueChange = {
                                viewModel.onAction(
                                    PublishJobAction.UpdatePhoneNumber(
                                        it
                                    )
                                )
                            },
                            label = "ফোন নম্বর",
                            placeholder = "+৮৮০ ১XXX XXXXXX",
                            leadingIcon = Icons.Outlined.Phone,
                            isRequired = true
                        )

                        ModernTextField(
                            value = uiState.email,
                            onValueChange = { viewModel.onAction(PublishJobAction.UpdateEmail(it)) },
                            label = "ইমেইল",
                            placeholder = "hr@company.com",
                            leadingIcon = Icons.Outlined.Email,
                            isRequired = true
                        )
                    }

                    // Error Display
                    AnimatedVisibility(
                        visible = uiState.error != null,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        ModernErrorCard(error = uiState.error ?: "")
                    }

                    // Gradient Publish Button
                    GradientPublishButton(
                        isPublishing = uiState.isPublishing,
                        onPublish = { viewModel.onAction(PublishJobAction.PublishJob) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ModernPublishHeader(onNavigateBack: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "চাকরি পোস্ট করুন",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "চাকরির বিজ্ঞপ্তি তৈরি করুন",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
private fun ModernImageUploadCard(
    selectedImageUri: Uri?,
    onSelectImage: (Uri?) -> Unit
) {
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onSelectImage(uri)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFF59E0B)
                        )
                    }
                }

                Column {
                    Text(
                        text = "কোম্পানির লোগো",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "একটি ছবি আপলোড করুন",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (selectedImageUri == null) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clickable { imageLauncher.launch("image/*") },
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF59E0B).copy(alpha = 0.1f),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.AddPhotoAlternate,
                                    contentDescription = null,
                                    modifier = Modifier.size(28.dp),
                                    tint = Color(0xFFF59E0B)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "ছবি যোগ করুন",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    OutlinedButton(
                        onClick = { imageLauncher.launch("image/*") },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                    ) {
                        Text("পরিবর্তন করুন")
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernInfoCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
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
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = iconColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = iconColor
                        )
                    }
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = content
            )
        }
    }
}

@Composable
private fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = if (isRequired) "$label *" else label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = minLines,
        maxLines = if (singleLine) 1 else maxLines,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    )
}

@Composable
private fun ModernErrorCard(error: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEF4444).copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = Color(0xFFEF4444),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFEF4444)
            )
        }
    }
}

@Composable
private fun GradientPublishButton(
    isPublishing: Boolean,
    onPublish: () -> Unit
) {
    Button(
        onClick = onPublish,
        enabled = !isPublishing,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
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
                    brush = if (!isPublishing) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6366F1),
                                Color(0xFF8B5CF6)
                            )
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isPublishing) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                    Text(
                        text = "প্রকাশ হচ্ছে...",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Publish,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.White
                    )
                    Text(
                        text = "চাকরি প্রকাশ করুন",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
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
                color = Color.White
            )
            Text(
                text = "লোড হচ্ছে...",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}
