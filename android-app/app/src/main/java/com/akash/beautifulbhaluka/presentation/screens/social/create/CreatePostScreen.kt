package com.akash.beautifulbhaluka.presentation.screens.social.create

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.akash.beautifulbhaluka.domain.model.PostPrivacy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    onDismiss: () -> Unit,
    onPostCreated: () -> Unit,
    viewModel: CreatePostViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        viewModel.onAction(CreatePostAction.AddImages(uris))
    }

    LaunchedEffect(uiState.postSuccess) {
        if (uiState.postSuccess) {
            onPostCreated()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("পোস্ট তৈরি করুন") },
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "বন্ধ করুন")
                            }
                        },
                        actions = {
                            Button(
                                onClick = { viewModel.onAction(CreatePostAction.PublishPost) },
                                enabled = !uiState.isPosting,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                if (uiState.isPosting) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text("পোস্ট করুন")
                                }
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Content TextField
                    OutlinedTextField(
                        value = uiState.content,
                        onValueChange = { viewModel.onAction(CreatePostAction.UpdateContent(it)) },
                        placeholder = { Text("আপনার মনে কি আছে?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 150.dp),
                        maxLines = 10
                    )

                    if (uiState.error != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Selected Images
                    if (uiState.selectedImages.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.selectedImages) { uri ->
                                Box {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    IconButton(
                                        onClick = { viewModel.onAction(CreatePostAction.RemoveImage(uri)) },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Options
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            // Add Photos
                            ListItem(
                                headlineContent = { Text("ছবি/ভিডিও যোগ করুন") },
                                leadingContent = {
                                    Icon(Icons.Default.Image, contentDescription = null)
                                },
                                modifier = Modifier.clickable {
                                    imagePickerLauncher.launch("image/*")
                                }
                            )

                            HorizontalDivider()

                            // Privacy
                            ListItem(
                                headlineContent = { Text("গোপনীয়তা") },
                                supportingContent = { Text(uiState.selectedPrivacy.displayName) },
                                leadingContent = {
                                    Icon(
                                        imageVector = when (uiState.selectedPrivacy) {
                                            PostPrivacy.PUBLIC -> Icons.Default.Public
                                            PostPrivacy.FRIENDS -> Icons.Default.People
                                            PostPrivacy.ONLY_ME -> Icons.Default.Lock
                                        },
                                        contentDescription = null
                                    )
                                },
                                trailingContent = {
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                                },
                                modifier = Modifier.clickable {
                                    viewModel.onAction(CreatePostAction.ShowPrivacyDialog)
                                }
                            )

                            HorizontalDivider()

                            // Location
                            ListItem(
                                headlineContent = { Text("অবস্থান যোগ করুন") },
                                supportingContent = if (uiState.location.isNotBlank()) {
                                    { Text(uiState.location) }
                                } else null,
                                leadingContent = {
                                    Icon(Icons.Default.LocationOn, contentDescription = null)
                                },
                                trailingContent = {
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                                },
                                modifier = Modifier.clickable {
                                    viewModel.onAction(CreatePostAction.ShowLocationDialog)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Privacy Dialog
    if (uiState.showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onAction(CreatePostAction.HidePrivacyDialog) },
            title = { Text("গোপনীয়তা নির্বাচন করুন") },
            text = {
                Column {
                    PostPrivacy.entries.forEach { privacy ->
                        ListItem(
                            headlineContent = { Text(privacy.displayName) },
                            leadingContent = {
                                RadioButton(
                                    selected = uiState.selectedPrivacy == privacy,
                                    onClick = { viewModel.onAction(CreatePostAction.UpdatePrivacy(privacy)) }
                                )
                            },
                            modifier = Modifier.clickable {
                                viewModel.onAction(CreatePostAction.UpdatePrivacy(privacy))
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.onAction(CreatePostAction.HidePrivacyDialog) }) {
                    Text("বন্ধ করুন")
                }
            }
        )
    }

    // Location Dialog
    if (uiState.showLocationDialog) {
        var locationText by remember { mutableStateOf(uiState.location) }

        AlertDialog(
            onDismissRequest = { viewModel.onAction(CreatePostAction.HideLocationDialog) },
            title = { Text("অবস্থান যোগ করুন") },
            text = {
                OutlinedTextField(
                    value = locationText,
                    onValueChange = { locationText = it },
                    placeholder = { Text("আপনার অবস্থান লিখুন") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onAction(CreatePostAction.UpdateLocation(locationText))
                }) {
                    Text("যোগ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onAction(CreatePostAction.HideLocationDialog) }) {
                    Text("বাতিল")
                }
            }
        )
    }
}
