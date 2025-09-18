package com.akash.beautifulbhaluka.presentation.screens.jobs.publish

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.presentation.screens.jobs.JobCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishJobScreen(
    onNavigateBack: () -> Unit,
    onJobPublished: () -> Unit,
    viewModel: PublishJobViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle success navigation
    LaunchedEffect(uiState.publishSuccess) {
        if (uiState.publishSuccess) {
            onJobPublished()
        }
    }

    PublishJobContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublishJobContent(
    uiState: PublishJobUiState,
    onAction: (PublishJobAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onAction(PublishJobAction.SelectImage(uri))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Publish New Job",
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

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Form content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Image Selection Section
                    ImageSelectionCard(
                        selectedImageUri = uiState.selectedImageUri,
                        onSelectImage = { imageLauncher.launch("image/*") }
                    )

                    // Job Title
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { onAction(PublishJobAction.UpdateTitle(it)) },
                        label = { Text("Job Title *") },
                        placeholder = { Text("e.g., Android Developer") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Company Name
                    OutlinedTextField(
                        value = uiState.company,
                        onValueChange = { onAction(PublishJobAction.UpdateCompany(it)) },
                        label = { Text("Company Name *") },
                        placeholder = { Text("e.g., Tech Corp Ltd.") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Category Selection
                    CategorySelectionCard(
                        categories = uiState.categories,
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelect = { onAction(PublishJobAction.SelectCategory(it)) }
                    )

                    // Location
                    LocationDropdown(
                        selectedLocation = uiState.location,
                        onLocationSelect = { onAction(PublishJobAction.UpdateLocation(it)) }
                    )

                    // Salary
                    OutlinedTextField(
                        value = uiState.salary,
                        onValueChange = { onAction(PublishJobAction.UpdateSalary(it)) },
                        label = { Text("বেতন *") },
                        placeholder = { Text("e.g., ১৯,০০০-২৫,০০০ টাকা") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Position Count
                    OutlinedTextField(
                        value = uiState.positionCount,
                        onValueChange = { onAction(PublishJobAction.UpdatePositionCount(it)) },
                        label = { Text("পদ সংখ্যা *") },
                        placeholder = { Text("e.g., ১০০ টি") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Application Deadline
                    OutlinedTextField(
                        value = uiState.deadline,
                        onValueChange = { onAction(PublishJobAction.UpdateDeadline(it)) },
                        label = { Text("আবেদনের শেষ তারিখ *") },
                        placeholder = { Text("e.g., ২৬/০৯/২০২৫") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Education Requirement
                    EducationDropdown(
                        selectedEducation = uiState.education,
                        onEducationSelect = { onAction(PublishJobAction.UpdateEducation(it)) }
                    )

                    // Experience Requirement
                    ExperienceDropdown(
                        selectedExperience = uiState.experience,
                        onExperienceSelect = { onAction(PublishJobAction.UpdateExperience(it)) }
                    )

                    // Job Type
                    JobTypeDropdown(
                        selectedJobType = uiState.jobType,
                        onJobTypeSelect = { onAction(PublishJobAction.UpdateJobType(it)) }
                    )

                    // Working Hours
                    WorkingHoursDropdown(
                        selectedWorkingHours = uiState.workingHours,
                        onWorkingHoursSelect = { onAction(PublishJobAction.UpdateWorkingHours(it)) }
                    )

                    // Work Location
                    WorkLocationDropdown(
                        selectedWorkLocation = uiState.workLocation,
                        onWorkLocationSelect = { onAction(PublishJobAction.UpdateWorkLocation(it)) }
                    )

                    // Contact Information - Phone Number
                    OutlinedTextField(
                        value = uiState.phoneNumber,
                        onValueChange = { onAction(PublishJobAction.UpdatePhoneNumber(it)) },
                        label = { Text("ফোন নম্বর *") },
                        placeholder = { Text("e.g., +০১৮১২৩৪৫৬৭৮৯") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Contact Information - Email
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { onAction(PublishJobAction.UpdateEmail(it)) },
                        label = { Text("ইমেইল *") },
                        placeholder = { Text("e.g., admin@example.com") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Error Display
                    if (uiState.error != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = uiState.error,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Bottom spacing
                    Spacer(modifier = Modifier.height(80.dp))
                }

                // Publish Button (Fixed at bottom)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 8.dp
                ) {
                    Button(
                        onClick = { onAction(PublishJobAction.PublishJob) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !uiState.isPublishing
                    ) {
                        if (uiState.isPublishing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 3.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Publish,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Publish Job",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageSelectionCard(
    selectedImageUri: Uri?,
    onSelectImage: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onSelectImage() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Job thumbnail",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Overlay with edit icon
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Change image",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Select Job Thumbnail *",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Tap to choose an image",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelectionCard(
    categories: List<JobCategory>,
    selectedCategory: JobCategory?,
    onCategorySelect: (JobCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedCategory?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Job Category *") },
            placeholder = { Text("Select a category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelect(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

// Continue with dropdown components...
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDropdown(
    selectedLocation: String,
    onLocationSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val locations =
        listOf("ঢাকা", "চট্টগ্রাম", "সিলেট", "রাজশাহী", "খুলনা", "বরিশাল", "রংপুর", "ময়মনসিংহ")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedLocation,
            onValueChange = {},
            readOnly = true,
            label = { Text("লোকেশন *") },
            placeholder = { Text("Select location") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            locations.forEach { location ->
                DropdownMenuItem(
                    text = { Text(location) },
                    onClick = {
                        onLocationSelect(location)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EducationDropdown(
    selectedEducation: String,
    onEducationSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val educationLevels = listOf("এস এস সি/ সমমান", "এইচ এস সি/ সমমান", "স্নাতক", "স্নাতকোত্তর")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedEducation,
            onValueChange = {},
            readOnly = true,
            label = { Text("ন্যূনতম শিক্ষাগত যোগ্যতা *") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            educationLevels.forEach { education ->
                DropdownMenuItem(
                    text = { Text(education) },
                    onClick = {
                        onEducationSelect(education)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExperienceDropdown(
    selectedExperience: String,
    onExperienceSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val experienceLevels = listOf("ফ্রেশার", "১-২ বছর", "২-৩ বছর", "৩-৫ বছর", "৫+ বছর")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedExperience,
            onValueChange = {},
            readOnly = true,
            label = { Text("অভিজ্ঞতা *") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            experienceLevels.forEach { experience ->
                DropdownMenuItem(
                    text = { Text(experience) },
                    onClick = {
                        onExperienceSelect(experience)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobTypeDropdown(
    selectedJobType: String,
    onJobTypeSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val jobTypes = listOf("ফুল-টাইম", "পার্ট-টাইম", "চুক্তিভিত্তিক", "ইন্টার্নশিপ")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedJobType,
            onValueChange = {},
            readOnly = true,
            label = { Text("চাকরির ধরন *") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            jobTypes.forEach { jobType ->
                DropdownMenuItem(
                    text = { Text(jobType) },
                    onClick = {
                        onJobTypeSelect(jobType)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkingHoursDropdown(
    selectedWorkingHours: String,
    onWorkingHoursSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val workingHours = listOf("৮ ঘন্টা", "৬ ঘন্টা", "১০ ঘন্টা", "ফ্লেক্সিবল")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedWorkingHours,
            onValueChange = {},
            readOnly = true,
            label = { Text("কাজের সময় *") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            workingHours.forEach { hours ->
                DropdownMenuItem(
                    text = { Text(hours) },
                    onClick = {
                        onWorkingHoursSelect(hours)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkLocationDropdown(
    selectedWorkLocation: String,
    onWorkLocationSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val workLocations = listOf("অন-সাইট", "রিমোট", "হাইব্রিড")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedWorkLocation,
            onValueChange = {},
            readOnly = true,
            label = { Text("কাজের স্থান *") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            workLocations.forEach { workLocation ->
                DropdownMenuItem(
                    text = { Text(workLocation) },
                    onClick = {
                        onWorkLocationSelect(workLocation)
                        expanded = false
                    }
                )
            }
        }
    }
}
