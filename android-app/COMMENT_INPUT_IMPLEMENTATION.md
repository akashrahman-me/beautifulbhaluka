# Comment Input: Image Upload & Voice Recording Implementation

## Overview

Complete implementation of image upload and voice recording features for the comment input section,
following MVVM architecture pattern.

## Architecture Components

### 1. Domain Layer (`domain/model/`)

No changes needed - Comment model already supports rich content.

### 2. Domain Repository Interface (`domain/repository/SocialRepository.kt`)

**Updated Methods:**

```kotlin
suspend fun addComment(
    postId: String,
    content: String,
    images: List<String> = emptyList(),
    voiceUrl: String? = null
): Result<Comment>

suspend fun addReply(
    postId: String,
    parentCommentId: String,
    content: String,
    images: List<String> = emptyList(),
    voiceUrl: String? = null
): Result<Comment>
```

### 3. Data Layer (`data/repository/SocialRepositoryImpl.kt`)

**Updated Implementation:**

- `addComment()` - Now accepts images and voiceUrl parameters
- `addReply()` - Now accepts images and voiceUrl parameters
- Both methods maintain backward compatibility with default empty values

### 4. Presentation Layer

#### UI State (`presentation/screens/social/comments/CommentsUiState.kt`)

**Added State Fields:**

```kotlin
data class CommentsUiState(
    // ... existing fields
    val selectedImages: List<String> = emptyList(),
    val isRecordingVoice: Boolean = false,
    val recordingDuration: Long = 0L,
    val voiceRecordingUrl: String? = null
)
```

**Added Actions:**

```kotlin
sealed class CommentsAction {
    // ... existing actions

    // Image upload actions
    data class AddImage(val imageUrl: String) : CommentsAction()
    data class RemoveImage(val imageUrl: String) : CommentsAction()
    object PickImage : CommentsAction()

    // Voice recording actions
    object StartVoiceRecording : CommentsAction()
    object StopVoiceRecording : CommentsAction()
    object CancelVoiceRecording : CommentsAction()
    data class UpdateRecordingDuration(val duration: Long) : CommentsAction()
}
```

#### ViewModel (`presentation/screens/social/comments/CommentsViewModel.kt`)

**Added Methods:**

- `addImage(imageUrl: String)` - Add image to selection (max 5)
- `removeImage(imageUrl: String)` - Remove image from selection
- `pickImage()` - Trigger image picker (handled by UI layer)
- `startVoiceRecording()` - Begin audio recording
- `stopVoiceRecording()` - Stop and save recording
- `cancelVoiceRecording()` - Cancel and discard recording
- `updateRecordingDuration(duration: Long)` - Update recording time

**Updated Methods:**

- `submitComment()` - Now includes images and voice in submission
- Clear images and voice URL after successful submission

#### UI Component (`presentation/screens/social/comments/CommentInputSection.kt`)

**New Parameters:**

```kotlin
@Composable
fun CommentInputSection(
    commentText: String,
    isSubmitting: Boolean,
    replyingTo: Comment?,
    selectedImages: List<String> = emptyList(),
    isRecordingVoice: Boolean = false,
    recordingDuration: Long = 0L,
    onTextChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancelReply: () -> Unit,
    onImagePick: () -> Unit = {},
    onRemoveImage: (String) -> Unit = {},
    onStartVoiceRecording: () -> Unit = {},
    onStopVoiceRecording: () -> Unit = {},
    onCancelVoiceRecording: () -> Unit = {},
    modifier: Modifier = Modifier
)
```

**New UI Sections:**

1. **Image Preview Section**
    - Horizontal scrollable row of selected images
    - Each image has remove button
    - Shows up to 5 images
    - Smooth animations with `AnimatedVisibility`

2. **Voice Recording Indicator**
    - Red recording dot animation
    - Real-time duration display (MM:SS format)
    - Stop and Cancel buttons
    - Error container styling for visibility

3. **Enhanced Input Row**
    - Image upload button (shows when not recording)
    - Voice recording button (shows when text is empty and no images)
    - Send button (shows when has text or images)
    - Conditional button display based on state

#### Screen Integration (`presentation/screens/social/comments/CommentsScreen.kt`)

**Updated Component Usage:**

```kotlin
CommentInputSection(
    commentText = uiState.commentText,
    isSubmitting = uiState.isSubmitting,
    replyingTo = uiState.replyingTo,
    selectedImages = uiState.selectedImages,
    isRecordingVoice = uiState.isRecordingVoice,
    recordingDuration = uiState.recordingDuration,
    onTextChange = { viewModel.onAction(CommentsAction.UpdateCommentText(it)) },
    onSubmit = { viewModel.onAction(CommentsAction.SubmitComment) },
    onCancelReply = { viewModel.onAction(CommentsAction.CancelReply) },
    onImagePick = { viewModel.onAction(CommentsAction.PickImage) },
    onRemoveImage = { viewModel.onAction(CommentsAction.RemoveImage(it)) },
    onStartVoiceRecording = { viewModel.onAction(CommentsAction.StartVoiceRecording) },
    onStopVoiceRecording = { viewModel.onAction(CommentsAction.StopVoiceRecording) },
    onCancelVoiceRecording = { viewModel.onAction(CommentsAction.CancelVoiceRecording) }
)
```

## Features Implemented

### Image Upload

✅ **Image Picker Button** - Lucide `ImagePlus` icon
✅ **Image Preview** - Horizontal scrollable carousel
✅ **Remove Images** - Individual delete button per image
✅ **Limit** - Maximum 5 images per comment
✅ **Visual Feedback** - Icon highlights when images selected
✅ **Smooth Animations** - Expand/shrink animations

### Voice Recording

✅ **Record Button** - Lucide `Mic` icon (shows when no text/images)
✅ **Recording Indicator** - Animated red dot
✅ **Duration Display** - Real-time MM:SS format
✅ **Stop Recording** - Saves and auto-submits
✅ **Cancel Recording** - Discards audio file
✅ **State Management** - Disables text input while recording
✅ **Visual Feedback** - Error container for visibility

### UI/UX Enhancements

✅ **Conditional Display** - Smart button showing based on state
✅ **Keyboard Actions** - ImeAction.Send support
✅ **Loading States** - Progress indicator during submission
✅ **Error Handling** - Proper error states
✅ **Accessibility** - Content descriptions for all icons
✅ **Modern Icons** - Lucide icons for professional look

## TODO: Platform Integration

### Image Picker (Android)

```kotlin
// In your Activity or Fragment
val imagePickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
) { uris ->
    uris.forEach { uri ->
        // Upload image and get URL
        val imageUrl = uploadImage(uri)
        viewModel.onAction(CommentsAction.AddImage(imageUrl))
    }
}

// Trigger from PickImage action
when (action) {
    is CommentsAction.PickImage -> imagePickerLauncher.launch(
        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
    )
}
```

### Voice Recording (Android)

```kotlin
// MediaRecorder setup
private val mediaRecorder = MediaRecorder().apply {
    setAudioSource(MediaRecorder.AudioSource.MIC)
    setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
    setOutputFile(outputFile.absolutePath)
}

// Start recording
mediaRecorder.prepare()
mediaRecorder.start()

// Timer for duration
recordingTimer = Timer().apply {
    scheduleAtFixedRate(0, 1000) {
        viewModel.onAction(
            CommentsAction.UpdateRecordingDuration(currentDuration)
        )
    }
}

// Stop recording
mediaRecorder.stop()
mediaRecorder.release()
recordingTimer?.cancel()
```

### Permissions Required

```xml

<manifest>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
</manifest>
```

## Testing Checklist

### Unit Tests

- [ ] ViewModel image addition (max 5 limit)
- [ ] ViewModel image removal
- [ ] ViewModel voice recording state
- [ ] Comment submission with images
- [ ] Comment submission with voice
- [ ] Comment submission with text + images

### UI Tests

- [ ] Image preview appears
- [ ] Remove image button works
- [ ] Voice recording indicator shows
- [ ] Duration updates correctly
- [ ] Buttons show/hide conditionally
- [ ] Submit works with different content types

### Integration Tests

- [ ] Image picker integration
- [ ] MediaRecorder integration
- [ ] File upload to server
- [ ] Comment creation with media

## Benefits

1. **Architecture Compliance** ✅
    - Clean MVVM separation
    - Single source of truth (UI State)
    - Unidirectional data flow
    - Repository pattern

2. **User Experience** ✅
    - Intuitive interface
    - Visual feedback
    - Smooth animations
    - Error handling

3. **Code Quality** ✅
    - Type-safe actions
    - Reusable components
    - Well-documented
    - Follows Kotlin best practices

4. **Maintainability** ✅
    - Clear separation of concerns
    - Easy to test
    - Easy to extend
    - Consistent patterns

## Notes

- Image URLs are stored as strings (can be local file:// URIs or remote URLs)
- Voice recording uses mock URL until actual MediaRecorder integration
- Maximum 5 images per comment (configurable in ViewModel)
- Recording duration is tracked in milliseconds
- Auto-submit after voice recording stops
- All animations use Material3 AnimatedVisibility
- Icons use Lucide icon pack for consistency

