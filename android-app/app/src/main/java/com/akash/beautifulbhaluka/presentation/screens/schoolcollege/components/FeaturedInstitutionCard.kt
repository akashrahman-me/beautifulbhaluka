package com.akash.beautifulbhaluka.presentation.screens.schoolcollege.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akash.beautifulbhaluka.presentation.screens.schoolcollege.FeaturedInstitution

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedInstitutionCard(
    institution: FeaturedInstitution,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // Thumbnail Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(institution.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = institution.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Institution Title
                Text(
                    text = institution.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Institution Details
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Founder
                    institution.founder?.let { founder ->
                        DetailRow(
                            icon = Icons.Default.Person,
                            label = "প্রতিষ্ঠাতা",
                            value = founder
                        )
                    }

                    // Established Date
                    DetailRow(
                        icon = Icons.Default.CalendarToday,
                        label = "প্রতিষ্ঠিত",
                        value = institution.established
                    )

                    // EIIN
                    DetailRow(
                        icon = Icons.Default.School,
                        label = "EIIN",
                        value = institution.eiin
                    )

                    // College Code (if available)
                    institution.collegeCode?.let { code ->
                        DetailRow(
                            icon = Icons.Default.School,
                            label = "কলেজ কোড",
                            value = code
                        )
                    }

                    // Website (if available)
                    institution.website?.let { website ->
                        DetailRow(
                            icon = Icons.Default.Web,
                            label = "ওয়েবসাইট",
                            value = website,
                            isLink = true
                        )
                    }
                }

                // Details/Description (if available)
                institution.details?.let { details ->
                    Spacer(modifier = Modifier.height(12.dp))

                    // Divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = details,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    isLink: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = if (isLink) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}
