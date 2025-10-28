package com.akash.beautifulbhaluka.presentation.screens.matchmaking.details

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akash.beautifulbhaluka.domain.model.MatchmakingProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingDetailsScreen(
    profileId: String,
    onNavigateBack: () -> Unit
) {
    // Mock data - In real app, fetch from ViewModel
    val profile = remember {
        MatchmakingProfile(
            id = profileId,
            name = "Fatima Rahman",
            age = 26,
            gender = "Female",
            occupation = "Software Engineer",
            education = "B.Sc in CSE, BUET",
            location = "Bhaluka, Mymensingh",
            height = "5'4\"",
            religion = "Islam",
            maritalStatus = "Never Married",
            bio = "I'm a passionate software engineer who loves creating innovative solutions. Looking for a life partner who values education, family, and personal growth. I enjoy reading, traveling, and exploring new cuisines.",
            interests = listOf("Reading", "Traveling", "Cooking", "Technology", "Photography", "Music"),
            verified = true,
            contactNumber = "+880 1XXX-XXXXXX",
            email = "contact@example.com"
        )
    }

    val gradientColors = listOf(
        Color(0xFFFF6B9D),
        Color(0xFFC06C84),
        Color(0xFF6C5B7B)
    )

    var showContactInfo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Details") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Hero Section
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Brush.horizontalGradient(gradientColors)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = profile.name.first().toString(),
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Bold,
                                color = gradientColors[0]
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = profile.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            if (profile.verified) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Quick Info Cards
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickInfoCard(
                        icon = Icons.Default.Cake,
                        label = "Age",
                        value = "${profile.age} years",
                        modifier = Modifier.weight(1f)
                    )
                    QuickInfoCard(
                        icon = Icons.Default.Height,
                        label = "Height",
                        value = profile.height,
                        modifier = Modifier.weight(1f)
                    )
                    QuickInfoCard(
                        icon = if (profile.gender == "Male") Icons.Default.Male else Icons.Default.Female,
                        label = "Gender",
                        value = profile.gender,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Bio Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionCard(
                    title = "About Me",
                    icon = Icons.Default.Person
                ) {
                    Text(
                        text = profile.bio,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.5
                    )
                }
            }

            // Personal Information
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionCard(
                    title = "Personal Information",
                    icon = Icons.Default.Info
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        InfoRow(
                            icon = Icons.Default.Work,
                            label = "Occupation",
                            value = profile.occupation
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        InfoRow(
                            icon = Icons.Default.School,
                            label = "Education",
                            value = profile.education
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        InfoRow(
                            icon = Icons.Default.LocationOn,
                            label = "Location",
                            value = profile.location
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        InfoRow(
                            icon = Icons.Default.Favorite,
                            label = "Marital Status",
                            value = profile.maritalStatus
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        InfoRow(
                            icon = Icons.Default.Star,
                            label = "Religion",
                            value = profile.religion
                        )
                    }
                }
            }

            // Interests
            if (profile.interests.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    SectionCard(
                        title = "Interests & Hobbies",
                        icon = Icons.Default.Psychology
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(profile.interests) { interest ->
                                AssistChip(
                                    onClick = { },
                                    label = { Text(interest) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Tag,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Contact Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContactPhone,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Contact Information",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedVisibility(
                            visible = showContactInfo,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                if (profile.contactNumber != null) {
                                    ContactInfoRow(
                                        icon = Icons.Default.Phone,
                                        label = "Phone",
                                        value = profile.contactNumber
                                    )
                                }
                                if (profile.email != null) {
                                    ContactInfoRow(
                                        icon = Icons.Default.Email,
                                        label = "Email",
                                        value = profile.email
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { showContactInfo = !showContactInfo },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = if (showContactInfo) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (showContactInfo) "Hide Contact Info" else "Show Contact Info")
                        }
                    }
                }
            }

            // Action Buttons
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Share profile */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share")
                    }
                    Button(
                        onClick = { /* Send interest */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF6B9D)
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Express Interest")
                    }
                }
            }
        }
    }
}

@Composable
fun QuickInfoCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ContactInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

