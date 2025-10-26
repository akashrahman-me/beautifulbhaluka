package com.akash.beautifulbhaluka.presentation.screens.social.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.composables.icons.lucide.*

/**
 * Edit Profile Screen
 * Allows users to edit their profile information including:
 * - Profile picture
 * - Cover photo
 * - Bio
 * - Location
 * - Work
 * - Education
 * - Website
 * - Relationship status
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit = {},
    onSaveProfile: (ProfileEditData) -> Unit = {}
) {
    var bio by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var work by remember { mutableStateOf("") }
    var education by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var relationship by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onSaveProfile(
                                ProfileEditData(
                                    bio = bio,
                                    location = location,
                                    work = work,
                                    education = education,
                                    website = website,
                                    relationship = relationship
                                )
                            )
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.primary
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
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Profile Picture Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Profile Picture
                    Box(
                        modifier = Modifier.size(100.dp)
                    ) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/300",
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    TextButton(
                        onClick = { /* Change profile picture */ }
                    ) {
                        Icon(
                            imageVector = Lucide.Camera,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Change Profile Picture")
                    }
                }
            }

            // Cover Photo Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Cover Photo",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    TextButton(
                        onClick = { /* Change cover photo */ }
                    ) {
                        Icon(
                            imageVector = Lucide.Image,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Change Cover Photo")
                    }
                }
            }

            // Bio Section
            EditProfileTextField(
                value = bio,
                onValueChange = { bio = it },
                label = "Bio",
                icon = Lucide.FileText,
                placeholder = "Write something about yourself...",
                maxLines = 4
            )

            // Location
            EditProfileTextField(
                value = location,
                onValueChange = { location = it },
                label = "Location",
                icon = Lucide.MapPin,
                placeholder = "City, Country"
            )

            // Work
            EditProfileTextField(
                value = work,
                onValueChange = { work = it },
                label = "Work",
                icon = Lucide.Briefcase,
                placeholder = "Company or Position"
            )

            // Education
            EditProfileTextField(
                value = education,
                onValueChange = { education = it },
                label = "Education",
                icon = Lucide.GraduationCap,
                placeholder = "School or University"
            )

            // Website
            EditProfileTextField(
                value = website,
                onValueChange = { website = it },
                label = "Website",
                icon = Lucide.Link,
                placeholder = "https://example.com"
            )

            // Relationship Status
            EditProfileTextField(
                value = relationship,
                onValueChange = { relationship = it },
                label = "Relationship",
                icon = Lucide.Heart,
                placeholder = "Single, In a relationship, etc."
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun EditProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    placeholder: String,
    maxLines: Int = 1
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(12.dp),
            maxLines = maxLines,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
        )
    }
}

/**
 * Data class to hold profile edit information
 */
data class ProfileEditData(
    val bio: String,
    val location: String,
    val work: String,
    val education: String,
    val website: String,
    val relationship: String
)

