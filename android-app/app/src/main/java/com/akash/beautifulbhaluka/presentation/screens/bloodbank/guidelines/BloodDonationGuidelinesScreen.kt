package com.akash.beautifulbhaluka.presentation.screens.bloodbank.guidelines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodDonationGuidelinesScreen(
    viewModel: BloodDonationGuidelinesViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    BloodDonationGuidelinesContent(
        uiState = uiState,
        guidelineData = viewModel.guidelineData,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodDonationGuidelinesContent(
    uiState: BloodDonationGuidelinesUiState,
    guidelineData: GuidelineData,
    onAction: (GuidelinesAction) -> Unit,
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
                                            Color(0xFFE53935),
                                            Color(0xFFFF6B6B)
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
                            text = "রক্তদান নির্দেশিকা",
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

            // Before Donation Section
            ExpandableSection(
                title = "রক্তদানের পূর্বে করণীয়",
                icon = Icons.Outlined.EventAvailable,
                gradientColors = listOf(Color(0xFF2196F3), Color(0xFF42A5F5)),
                items = guidelineData.beforeDonation,
                expanded = uiState.expandedSections.contains(GuidelineSection.BEFORE_DONATION),
                onToggle = { onAction(GuidelinesAction.ToggleSection(GuidelineSection.BEFORE_DONATION)) }
            )

            // During Donation Section
            ExpandableSection(
                title = "রক্তদানের সময় করণীয়",
                icon = Icons.Outlined.LocalHospital,
                gradientColors = listOf(Color(0xFF4CAF50), Color(0xFF66BB6A)),
                items = guidelineData.duringDonation,
                expanded = uiState.expandedSections.contains(GuidelineSection.DURING_DONATION),
                onToggle = { onAction(GuidelinesAction.ToggleSection(GuidelineSection.DURING_DONATION)) }
            )

            // After Donation Section
            ExpandableSection(
                title = "রক্তদানের পরে করণীয়",
                icon = Icons.Outlined.CheckCircle,
                gradientColors = listOf(Color(0xFFFF9800), Color(0xFFFFB74D)),
                items = guidelineData.afterDonation,
                expanded = uiState.expandedSections.contains(GuidelineSection.AFTER_DONATION),
                onToggle = { onAction(GuidelinesAction.ToggleSection(GuidelineSection.AFTER_DONATION)) }
            )

            // Health Benefits Section
            HealthBenefitsSection()

            // Eligibility Criteria Section
            EligibilityCriteriaSection()

            // Patient Family Section
            PatientFamilyGuideSection()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun HeroCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color(0xFFE53935).copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(24.dp),
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
                            Color(0xFFE53935),
                            Color(0xFFD32F2F),
                            Color(0xFFC62828)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🩸",
                    fontSize = 48.sp
                )
                Text(
                    text = "রক্তদান করুন, জীবন বাঁচান",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "একটি রক্তদান তিনটি জীবন বাঁচাতে পারে",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ExpandableSection(
    title: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    items: List<String>,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.linearGradient(gradientColors),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = gradientColors[0].copy(alpha = 0.1f),
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = gradientColors[0],
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(rotationAngle)
                        )
                    }
                }
            }

            // Expandable Content
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        gradientColors[0].copy(alpha = 0.3f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )

                    items.forEachIndexed { index, item ->
                        ChecklistItem(
                            text = item,
                            number = index + 1,
                            accentColor = gradientColors[0]
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChecklistItem(
    text: String,
    number: Int,
    accentColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            color = accentColor.copy(alpha = 0.12f),
            shape = CircleShape,
            modifier = Modifier.size(28.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = accentColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 22.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun HealthBenefitsSection() {
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF9C27B0),
                                    Color(0xFFBA68C8)
                                )
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "রক্তদানের স্বাস্থ্য উপকারিতা",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BenefitCard(
                    icon = "❤️",
                    title = "হৃদযন্ত্রের স্বাস্থ্য",
                    description = "নিয়মিত রক্তদান হৃদযন্ত্রের স্বাস্থ্য উন্নত করে এবং হার্ট অ্যাটাকের ঝুঁকি কমায়।",
                    gradientColors = listOf(Color(0xFFE91E63), Color(0xFFF48FB1))
                )

                BenefitCard(
                    icon = "🛡️",
                    title = "রক্তের ক্যান্সার প্রতিরোধ",
                    description = "রক্তদান hemochromatosis (আয়রন ওভারলোড) প্রতিরোধে সাহায্য করে।",
                    gradientColors = listOf(Color(0xFF9C27B0), Color(0xFFBA68C8))
                )

                BenefitCard(
                    icon = "🔄",
                    title = "নতুন রক্তকণিকা উৎপাদন",
                    description = "রক্তদানের পর শরীর নতুন রক্তকণিকা উৎপাদন করে যা শরীরকে সতেজ ও সক্রিয় রাখে।",
                    gradientColors = listOf(Color(0xFF00BCD4), Color(0xFF4DD0E1))
                )

                BenefitCard(
                    icon = "🔥",
                    title = "ক্যালোরি বার্ন",
                    description = "প্রতি বার রক্তদানে প্রায় ৬৫০ ক্যালোরি খরচ হয় যা ওজন নিয়ন্ত্রণে সহায়ক।",
                    gradientColors = listOf(Color(0xFFFF9800), Color(0xFFFFB74D))
                )
            }
        }
    }
}

@Composable
private fun BenefitCard(
    icon: String,
    title: String,
    description: String,
    gradientColors: List<Color>
) {
    Surface(
        color = gradientColors[0].copy(alpha = 0.08f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = icon,
                fontSize = 32.sp
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = gradientColors[0]
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun EligibilityCriteriaSection() {
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF00BCD4),
                                    Color(0xFF4DD0E1)
                                )
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "রক্তদানের যোগ্যতা",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EligibilityItem(
                    icon = Icons.Outlined.Cake,
                    text = "বয়স ১৮ থেকে ৬০ বছর (কিছু ক্ষেত্রে ১৬-১৭ বছর বয়সীদের বাবা-মায়ের সম্মতি নিতে হয়)",
                    color = Color(0xFF00BCD4)
                )
                EligibilityItem(
                    icon = Icons.Outlined.FitnessCenter,
                    text = "শারীরিক ওজন কমপক্ষে ৪৫ কেজি",
                    color = Color(0xFF4CAF50)
                )
                EligibilityItem(
                    icon = Icons.Outlined.HealthAndSafety,
                    text = "সুস্থ শারীরিক অবস্থা এবং কোনো গুরুতর রোগ না থাকা",
                    color = Color(0xFFE91E63)
                )
                EligibilityItem(
                    icon = Icons.Outlined.Science,
                    text = "Hemoglobin পুরুষদের জন্য 12.5 g/dL এবং মহিলাদের জন্য 12.0 g/dL",
                    color = Color(0xFF9C27B0)
                )
                EligibilityItem(
                    icon = Icons.Outlined.MedicalServices,
                    text = "রক্তদানের আগে ৩ মাসের মধ্যে কোনো বড় অস্ত্রোপচার না করা",
                    color = Color(0xFFFF9800)
                )
                EligibilityItem(
                    icon = Icons.Outlined.Block,
                    text = "গত ৬ মাসে নতুন ট্যাটু বা পিয়ারসিং না করা",
                    color = Color(0xFFF44336)
                )
            }
        }
    }
}

@Composable
private fun EligibilityItem(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            color = color.copy(alpha = 0.12f),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(36.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 22.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun PatientFamilyGuideSection() {
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF673AB7),
                                    Color(0xFF9575CD)
                                )
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FamilyRestroom,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "রোগীর স্বজনের করণীয়",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Surface(
                color = Color(0xFF673AB7).copy(alpha = 0.08f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "রক্তদাতার প্রতি সম্মান ও কৃতজ্ঞতা প্রকাশ করা উচিত। মনে রাখবেন, তারা আপনার প্রিয়জনের জীবন বাঁচাতে সাহায্য করছেন।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Text(
                text = "রক্তদাতার সাথে যোগাযোগের সময়:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF673AB7)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GuideItem("সদাচরণ ও কৃতজ্ঞতা প্রকাশ করুন")
                GuideItem("রক্তদানের জন্য সময় দেওয়ার জন্য ধন্যবাদ জানান")
                GuideItem("প্রয়োজন হলে রক্তদান কেন্দ্র পর্যন্ত যাতায়াতের ব্যবস্থা করুন")
                GuideItem("রক্তদানের পরে দাতার খোঁজখবর নিন")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "রক্তদাতাকে সহায়তা করা:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF673AB7)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GuideItem("রক্তদানের প্রক্রিয়া সম্পর্কে প্রয়োজনীয় তথ্য প্রদান করুন")
                GuideItem("রক্তদানের পরে হালকা খাবার ও পানীয়ের ব্যবস্থা করুন")
                GuideItem("প্রয়োজনে রক্তদাতার যাতায়াতের ব্যবস্থা করতে সাহায্য করুন")
                GuideItem("রক্তদান শেষে দাতাকে পর্যাপ্ত বিশ্রাম নিতে উৎসাহিত করুন")
            }
        }
    }
}

@Composable
private fun GuideItem(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .offset(y = 8.dp)
                .background(
                    Color(0xFF673AB7),
                    CircleShape
                )
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 22.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

