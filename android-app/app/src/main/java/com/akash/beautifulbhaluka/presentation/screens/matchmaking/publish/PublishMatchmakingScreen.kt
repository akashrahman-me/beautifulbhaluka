package com.akash.beautifulbhaluka.presentation.screens.matchmaking.publish

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishMatchmakingScreen(
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var occupation by remember { mutableStateOf("") }
    var education by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var religion by remember { mutableStateOf("Islam") }
    var maritalStatus by remember { mutableStateOf("Never Married") }
    var bio by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    val gradientColors = listOf(
        Color(0xFFFF6B9D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.horizontalGradient(gradientColors))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Create Your Profile",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Find your perfect match",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            // Personal Information Section
            item {
                SectionHeader(
                    icon = Icons.Default.Person,
                    title = "Personal Information"
                )
            }

            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name *") },
                    leadingIcon = { Icon(Icons.Default.Person, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age *") },
                        leadingIcon = { Icon(Icons.Default.Cake, null) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text("Height") },
                        leadingIcon = { Icon(Icons.Default.Height, null) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        placeholder = { Text("5'4\"") },
                        singleLine = true
                    )
                }
            }

            item {
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Gender *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        leadingIcon = { Icon(if (gender == "Male") Icons.Default.Male else Icons.Default.Female, null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Male", "Female").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    gender = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Professional Information
            item {
                SectionHeader(
                    icon = Icons.Default.Work,
                    title = "Professional & Educational"
                )
            }

            item {
                OutlinedTextField(
                    value = occupation,
                    onValueChange = { occupation = it },
                    label = { Text("Occupation *") },
                    leadingIcon = { Icon(Icons.Default.Work, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = education,
                    onValueChange = { education = it },
                    label = { Text("Education *") },
                    leadingIcon = { Icon(Icons.Default.School, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Location & Background
            item {
                SectionHeader(
                    icon = Icons.Default.LocationOn,
                    title = "Location & Background"
                )
            }

            item {
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location *") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            item {
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = religion,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Religion") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        leadingIcon = { Icon(Icons.Default.Star, null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Islam", "Hinduism", "Buddhism", "Christianity", "Other").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    religion = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = maritalStatus,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Marital Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        leadingIcon = { Icon(Icons.Default.Favorite, null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Never Married", "Divorced", "Widowed").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    maritalStatus = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Bio Section
            item {
                SectionHeader(
                    icon = Icons.Default.Description,
                    title = "About Yourself"
                )
            }

            item {
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio / Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 6,
                    placeholder = { Text("Tell us about yourself, your interests, and what you're looking for...") }
                )
            }

            // Contact Information
            item {
                SectionHeader(
                    icon = Icons.Default.ContactPhone,
                    title = "Contact Information"
                )
            }

            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    leadingIcon = { Icon(Icons.Default.Phone, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
            }

            // Submit Button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        isSubmitting = true
                        // Handle submission
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = name.isNotBlank() && age.isNotBlank() && occupation.isNotBlank() && !isSubmitting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B9D)
                    )
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Icon(Icons.Default.Check, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Create Profile",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Text(
                    text = "* Required fields",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

