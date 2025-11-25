package com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.domain.model.Matchmaker
import com.composables.icons.lucide.*
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakerDetailsScreen(
    matchmakerId: String,
    viewModel: MatchmakerDetailsViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(matchmakerId) {
        viewModel.loadMatchmaker(matchmakerId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ম্যাচমেকার প্রোফাইল") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Lucide.ArrowLeft,
                            contentDescription = "ফিরে যান"
                        )
                    }
                }
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
                    CircularProgressIndicator()
                }
            }

            uiState.matchmaker != null -> {
                MatchmakerDetailsContent(
                    matchmaker = uiState.matchmaker!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error ?: "Error loading matchmaker",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun MatchmakerDetailsContent(
    matchmaker: Matchmaker,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // Header with gradient
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF667EEA),
                                Color(0xFF764BA2)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Lucide.UserRound,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp),
                            tint = Color.White
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = matchmaker.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        if (matchmaker.verified) {
                            Icon(
                                imageVector = Lucide.BadgeCheck,
                                contentDescription = "Verified",
                                modifier = Modifier.size(24.dp),
                                tint = Color(0xFF4CAF50)
                            )
                        }
                    }

                    Text(
                        text = "${matchmaker.experience} অভিজ্ঞতা • ${matchmaker.age} বছর",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Stats Cards
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    icon = Lucide.Star,
                    value = String.format(Locale.getDefault(), "%.1f", matchmaker.rating),
                    label = "রেটিং",
                    color = Color(0xFFFFC107),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = Lucide.Heart,
                    value = matchmaker.successfulMatches.toString(),
                    label = "ম্যাচ",
                    color = Color(0xFFE91E63),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    icon = if (matchmaker.available) Lucide.Check else Lucide.X,
                    value = if (matchmaker.available) "হ্যাঁ" else "না",
                    label = "উপলভ্য",
                    color = if (matchmaker.available) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // About Section
        item {
            SectionCard(title = "আমার সম্পর্কে", icon = Lucide.FileText) {
                Text(
                    text = matchmaker.bio,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Contact Information
        item {
            SectionCard(title = "যোগাযোগের তথ্য", icon = Lucide.Phone) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoRow(
                        icon = Lucide.Phone,
                        label = "ফোন",
                        value = matchmaker.contactNumber
                    )
                    matchmaker.whatsapp?.let {
                        InfoRow(
                            icon = Lucide.MessageCircle,
                            label = "হোয়াটসঅ্যাপ",
                            value = it
                        )
                    }
                    matchmaker.email?.let {
                        InfoRow(
                            icon = Lucide.Mail,
                            label = "ইমেইল",
                            value = it
                        )
                    }
                    InfoRow(
                        icon = Lucide.MapPin,
                        label = "ঠিকানা",
                        value = matchmaker.location
                    )
                    matchmaker.workingHours?.let {
                        InfoRow(
                            icon = Lucide.Clock,
                            label = "কর্মঘন্টা",
                            value = it
                        )
                    }
                }
            }
        }

        // Specializations
        item {
            SectionCard(title = "বিশেষ দক্ষতা", icon = Lucide.Award) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    matchmaker.specialization.forEach { spec ->
                        AssistChip(
                            onClick = { },
                            label = { Text(spec) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }

        // Services Offered
        item {
            SectionCard(title = "প্রদত্ত সেবা", icon = Lucide.Briefcase) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    matchmaker.servicesOffered.forEach { service ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Lucide.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF4CAF50)
                            )
                            Text(
                                text = service,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        // Languages
        item {
            SectionCard(title = "ভাষা", icon = Lucide.Languages) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    matchmaker.languages.forEach { language ->
                        AssistChip(
                            onClick = { },
                            label = { Text(language) }
                        )
                    }
                }
            }
        }

        // Consultation Fee
        matchmaker.consultationFee?.let { fee ->
            item {
                SectionCard(title = "পরামর্শ ফি", icon = Lucide.Wallet) {
                    Text(
                        text = fee,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Testimonials
        if (matchmaker.testimonials.isNotEmpty()) {
            item {
                SectionCard(title = "প্রশংসাপত্র", icon = Lucide.MessageSquare) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        matchmaker.testimonials.forEach { testimonial ->
                            TestimonialCard(testimonial = testimonial)
                        }
                    }
                }
            }
        }

        // Social Media
        matchmaker.socialMedia?.let { social ->
            item {
                SectionCard(title = "সোশ্যাল মিডিয়া", icon = Lucide.Share2) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        social.facebook?.let {
                            InfoRow(icon = Lucide.Facebook, label = "ফেসবুক", value = it)
                        }
                        social.instagram?.let {
                            InfoRow(icon = Lucide.Instagram, label = "ইনস্টাগ্রাম", value = it)
                        }
                        social.linkedin?.let {
                            InfoRow(icon = Lucide.Linkedin, label = "লিংকডইন", value = it)
                        }
                        social.website?.let {
                            InfoRow(icon = Lucide.Globe, label = "ওয়েবসাইট", value = it)
                        }
                    }
                }
            }
        }
    }

    // Bottom Contact Buttons
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilledTonalButton(
                    onClick = { /* Call action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
                    )
                ) {
                    Icon(
                        imageVector = Lucide.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("কল করুন")
                }

                Button(
                    onClick = { /* WhatsApp action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366)
                    )
                ) {
                    Icon(
                        imageVector = Lucide.MessageCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("হোয়াটসঅ্যাপ")
                }
            }
        }
    }
}

@Composable
fun StatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = color
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
fun TestimonialCard(
    testimonial: com.akash.beautifulbhaluka.domain.model.Testimonial
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = testimonial.clientName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Lucide.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFC107)
                    )
                    Text(
                        text = String.format(Locale.getDefault(), "%.1f", testimonial.rating),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                text = testimonial.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
