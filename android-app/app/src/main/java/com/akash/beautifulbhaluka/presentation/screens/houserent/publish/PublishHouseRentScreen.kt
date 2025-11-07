package com.akash.beautifulbhaluka.presentation.screens.houserent.publish

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akash.beautifulbhaluka.domain.model.Amenity
import com.akash.beautifulbhaluka.domain.model.PropertyType

@Composable
fun PublishHouseRentScreen(
    viewModel: PublishHouseRentViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    PublishHouseRentContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishHouseRentContent(
    uiState: PublishHouseRentUiState,
    onAction: (PublishHouseRentAction) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("বাসা ভাড়া যোগ করুন") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEF4444),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Color(0xFFEF4444)
                        )
                        Column {
                            Text(
                                text = "আপনার বাসা ভাড়ার তথ্য যোগ করুন",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "সব তথ্য সঠিকভাবে পূরণ করুন",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Basic Information Section
                SectionCard(title = "মূল তথ্য") {
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateTitle(it)) },
                        label = { Text("শিরোনাম *") },
                        placeholder = { Text("যেমন: আধুনিক ৩ বেডরুমের ফ্ল্যাট") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.description,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateDescription(it)) },
                        label = { Text("বর্ণনা *") },
                        placeholder = { Text("বাসার বিস্তারিত বর্ণনা লিখুন") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        maxLines = 6
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "সম্পত্তির ধরন *",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(PropertyType.entries) { type ->
                            FilterChip(
                                selected = uiState.propertyType == type,
                                onClick = { onAction(PublishHouseRentAction.UpdatePropertyType(type)) },
                                label = { Text(type.displayName) }
                            )
                        }
                    }
                }

                // Price Section
                SectionCard(title = "মূল্য তথ্য") {
                    OutlinedTextField(
                        value = uiState.monthlyRent,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateMonthlyRent(it)) },
                        label = { Text("মাসিক ভাড়া (৳) *") },
                        placeholder = { Text("১৫০০০") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Text("৳", style = MaterialTheme.typography.titleMedium)
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.advancePayment,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateAdvancePayment(it)) },
                        label = { Text("অগ্রিম পেমেন্ট (৳)") },
                        placeholder = { Text("৩০০০০") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Text("৳", style = MaterialTheme.typography.titleMedium)
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("দাম আলোচনা করা যাবে?")
                        Switch(
                            checked = uiState.isNegotiable,
                            onCheckedChange = { onAction(PublishHouseRentAction.ToggleNegotiable) }
                        )
                    }
                }

                // Property Details Section
                SectionCard(title = "সম্পত্তির বিবরণ") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.bedrooms,
                            onValueChange = { onAction(PublishHouseRentAction.UpdateBedrooms(it)) },
                            label = { Text("বেডরুম *") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = uiState.bathrooms,
                            onValueChange = { onAction(PublishHouseRentAction.UpdateBathrooms(it)) },
                            label = { Text("বাথরুম *") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.area,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateArea(it)) },
                        label = { Text("আয়তন (sqft) *") },
                        placeholder = { Text("১২০০") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            Text("sqft", style = MaterialTheme.typography.bodySmall)
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.floor,
                            onValueChange = { onAction(PublishHouseRentAction.UpdateFloor(it)) },
                            label = { Text("তলা") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = uiState.totalFloors,
                            onValueChange = { onAction(PublishHouseRentAction.UpdateTotalFloors(it)) },
                            label = { Text("মোট তলা") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }
                }

                // Amenities Section
                SectionCard(title = "সুবিধাসমূহ") {
                    Text(
                        text = "যেসব সুবিধা আছে সেগুলো নির্বাচন করুন",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Amenity.entries.chunked(2).forEach { rowAmenities ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowAmenities.forEach { amenity ->
                                    FilterChip(
                                        selected = amenity in uiState.selectedAmenities,
                                        onClick = {
                                            onAction(
                                                PublishHouseRentAction.ToggleAmenity(
                                                    amenity
                                                )
                                            )
                                        },
                                        label = { Text(amenity.displayName) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (rowAmenities.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                // Location Section
                SectionCard(title = "অবস্থান") {
                    OutlinedTextField(
                        value = uiState.location,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateLocation(it)) },
                        label = { Text("এলাকা *") },
                        placeholder = { Text("ভালুকা বাজার") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.detailedAddress,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateDetailedAddress(it)) },
                        label = { Text("বিস্তারিত ঠিকানা *") },
                        placeholder = { Text("রোড নং ৫, ভালুকা বাজার, ময়মনসিংহ") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 3
                    )
                }

                // Owner Information Section
                SectionCard(title = "মালিকের তথ্য") {
                    OutlinedTextField(
                        value = uiState.ownerName,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateOwnerName(it)) },
                        label = { Text("নাম *") },
                        placeholder = { Text("মোঃ করিম উদ্দিন") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.ownerContact,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateOwnerContact(it)) },
                        label = { Text("মোবাইল নম্বর *") },
                        placeholder = { Text("+880 1711-123456") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.ownerWhatsApp,
                        onValueChange = { onAction(PublishHouseRentAction.UpdateOwnerWhatsApp(it)) },
                        label = { Text("WhatsApp নম্বর") },
                        placeholder = { Text("+880 1711-123456") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true
                    )
                }

                // Image Upload Section
                SectionCard(title = "ছবি যোগ করুন") {
                    Button(
                        onClick = { /* Image picker */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ছবি আপলোড করুন")
                    }

                    Text(
                        text = "সর্বোচ্চ ৫টি ছবি যোগ করতে পারবেন",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Submit Button
                Button(
                    onClick = { onAction(PublishHouseRentAction.Submit) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = uiState.canSubmit && !uiState.isSubmitting,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (uiState.isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "প্রকাশ করুন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Bottom spacing
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Success/Error Snackbar would go here
    }
}

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEF4444)
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            content()
        }
    }
}

