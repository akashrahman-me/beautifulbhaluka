# Social Feature Implementation Guide

## Overview
This document describes the complete implementation of the Social feature for the Beautiful Bhaluka Android app, following clean MVVM architecture with Jetpack Compose.

## Architecture Summary

The Social feature follows the three-layer architecture pattern:

### 1. **Domain Layer** (Business Logic)
Located in `domain/model/`, `domain/repository/`, `domain/usecase/`

**Models:**
- `Post.kt` - Social post entity with likes, comments, shares
- `Comment.kt` - Comment entity for posts
- `SocialProfile.kt` - User profile information
- `PostPrivacy` - Privacy settings enum (PUBLIC, FRIENDS, ONLY_ME)

**Repository Interface:**
- `SocialRepository.kt` - Defines all social operations

**Use Cases:**
- `GetPostsUseCase.kt` - Fetch feed posts
- `CreatePostUseCase.kt` - Create new posts with validation
- `GetProfileUseCase.kt` - Fetch user profile

### 2. **Data Layer** (Data Access)
Located in `data/repository/`

**Repository Implementation:**
- `SocialRepositoryImpl.kt` - Mock implementation with sample data
  - Generates realistic Bengali content
  - Simulates network delays
  - Manages in-memory state

### 3. **Presentation Layer** (UI)
Located in `presentation/screens/social/`

**Main Screen:**
- `SocialScreen.kt` - Hub with tab navigation (Feed/Profile)

**Feed Feature:**
- `SocialFeedScreen.kt` - Posts feed with pull-to-refresh
- `SocialFeedViewModel.kt` - State management for feed
- `SocialFeedUiState.kt` - UI state and actions

**Create Post Feature:**
- `CreatePostScreen.kt` - Full-screen post creation dialog
- `CreatePostViewModel.kt` - Post creation logic
- `CreatePostUiState.kt` - Creation state management

**Profile Feature:**
- `SocialProfileScreen.kt` - User profile with tabs
- `SocialProfileViewModel.kt` - Profile state management
- `SocialProfileUiState.kt` - Profile state and actions

**Components:**
- `PostCard.kt` - Reusable post card component

## Features Implemented

### ✅ Feed Screen
- **View Posts:** Scrollable feed with all posts
- **Pull to Refresh:** Swipe down to reload
- **Like Posts:** Toggle like on any post
- **Comment Button:** Opens comments (placeholder)
- **Share Posts:** Share counter increments
- **Delete Posts:** Users can delete their own posts
- **Post Display:**
  - User profile image and name
  - Post timestamp (in Bengali, relative time)
  - Privacy indicator (Public/Friends/Only Me)
  - Text content
  - Multiple images (smart grid layout)
  - Location tag
  - Like, comment, share counts
  - Interactive action buttons

### ✅ Create Post
- **Text Content:** Multi-line text input
- **Image Upload:** Select up to 10 images from gallery
- **Privacy Settings:** Choose PUBLIC, FRIENDS, or ONLY_ME
- **Location Tag:** Add custom location
- **Preview Images:** View selected images before posting
- **Remove Images:** Delete individual images
- **Validation:** Prevents empty posts
- **Loading State:** Shows progress while posting

### ✅ Profile Screen
- **Cover Image:** Large header image
- **Profile Picture:** Circular avatar
- **User Info:** Name, bio, location, website
- **Statistics:** Posts, Friends, Followers, Following counts
- **Edit Profile:** Inline editing for bio, location, website
- **Follow/Unfollow:** For other users
- **Tabs:**
  - **Posts:** User's posts grid
  - **About:** Detailed information
  - **Friends:** Friends list (placeholder)
  - **Photos:** Photo gallery (placeholder)

## Key Features (Facebook-like)

### Implemented:
1. ✅ News Feed with posts
2. ✅ Create text posts with images
3. ✅ Like/Unlike posts
4. ✅ Comment count display
5. ✅ Share functionality
6. ✅ User profiles
7. ✅ Follow/Unfollow users
8. ✅ Profile editing
9. ✅ Privacy settings
10. ✅ Location tagging
11. ✅ Post timestamps
12. ✅ Pull-to-refresh
13. ✅ Delete own posts

### Simplified (for MVP):
- Comments are shown as count only (detailed view not implemented)
- Friends list is placeholder
- Photo gallery is placeholder
- Video posts not implemented (can be added)
- Reactions limited to single "Like"
- No notifications
- No messaging
- No stories

## Technical Details

### State Management
- Uses Kotlin StateFlow for reactive state
- Follows unidirectional data flow
- Actions pattern for user interactions

### UI Components
- Material 3 Design
- Jetpack Compose
- Coil for image loading
- Accompanist SwipeRefresh

### Data Flow
```
User Action → ViewModel → Repository → Update StateFlow → Recompose UI
```

### Image Grid Logic
- 1 image: Full width, max 400dp height
- 2 images: Side by side
- 3+ images: Large top image + 2 bottom images with "+N" overlay

## Bengali Localization
All UI text is in Bengali (বাংলা):
- Tab labels
- Button text
- Placeholder text
- Error messages
- Timestamps (relative time in Bengali)

## File Structure
```
presentation/screens/social/
├── SocialScreen.kt                 # Main hub
├── SocialFeedScreen.kt            # Feed view
├── SocialFeedViewModel.kt         # Feed logic
├── SocialFeedUiState.kt           # Feed state
├── create/
│   ├── CreatePostScreen.kt        # Post creation UI
│   ├── CreatePostViewModel.kt     # Creation logic
│   └── CreatePostUiState.kt       # Creation state
├── profile/
│   ├── SocialProfileScreen.kt     # Profile UI
│   ├── SocialProfileViewModel.kt  # Profile logic
│   └── SocialProfileUiState.kt    # Profile state
└── components/
    └── PostCard.kt                # Reusable post component

domain/
├── model/
│   └── Post.kt                    # Domain models
├── repository/
│   └── SocialRepository.kt        # Repository interface
└── usecase/
    ├── GetPostsUseCase.kt
    ├── CreatePostUseCase.kt
    └── GetProfileUseCase.kt

data/repository/
└── SocialRepositoryImpl.kt        # Mock implementation
```

## Usage

### Navigate to Social Screen
```kotlin
navController.navigate("social")
```

### Create Post
1. Click "+" button in Feed tab
2. Enter content
3. Optionally add images, location, privacy
4. Click "পোস্ট করুন" (Post)

### View Profile
1. Click "প্রোফাইল" tab
2. View your posts and info
3. Click edit icon to modify profile

## Next Steps (Future Enhancements)

1. **Comments System:**
   - Create CommentBottomSheet
   - Implement nested replies
   - Add comment likes

2. **Real Backend Integration:**
   - Replace mock repository with API calls
   - Implement image upload to server
   - Add authentication

3. **Advanced Features:**
   - Video posts
   - Stories (24h posts)
   - Live reactions
   - Post editing
   - Post reporting
   - User blocking

4. **Notifications:**
   - Push notifications
   - In-app notification center
   - Notification badges

5. **Performance:**
   - Pagination for feed
   - Image caching optimization
   - Lazy loading

## Dependencies Added
```gradle
implementation("io.coil-kt:coil-compose:2.4.0")
implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")
```

## Testing the Feature

1. **Build and Run** the app
2. **Navigate** to Social section
3. **View Feed** - See 5 mock posts
4. **Create Post** - Click +, add content, post
5. **Like Posts** - Toggle likes
6. **View Profile** - Switch to Profile tab
7. **Edit Profile** - Click edit icon, modify info
8. **Pull to Refresh** - Swipe down on feed

## Mock Data
The repository includes realistic Bengali mock data:
- 5 sample posts with Bengali text
- User profiles with Bengali names
- Random likes, comments, shares counts
- Sample images from Unsplash

All data is stored in-memory and persists during app session.

---

**Implementation Status:** ✅ Complete and Ready for Testing
**Architecture Compliance:** ✅ Follows MVVM + Clean Architecture
**UI Framework:** ✅ Jetpack Compose with Material 3
**Localization:** ✅ Bengali (বাংলা)

