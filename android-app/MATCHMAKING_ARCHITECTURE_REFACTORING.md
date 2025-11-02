# Matchmaking Module Architecture Refactoring

## Overview

The matchmaking module has been refactored to follow the proper architecture guidelines as defined
in `Architecture.md`. The module now has a clear separation between the two main tabs: **Bridegroom
Profiles** and **Matchmakers**.

## Changes Made

### Directory Structure

**Before:**

```
matchmaking/
├── MatchmakingScreen.kt (main screen with 2 tabs)
├── MatchmakingViewModel.kt
├── MatchmakingUiState.kt
├── MatchmakingContent.kt
├── details/          (for bridegroom profiles - at root level)
├── interested/       (for bridegroom profiles - at root level)
├── manage/           (for bridegroom profiles - at root level)
├── publish/          (for bridegroom profiles - at root level)
└── matchmaker/       (for matchmakers - properly nested)
    ├── details/
    ├── manage/
    └── publish/
```

**After:**

```
matchmaking/
├── MatchmakingScreen.kt (main screen with 2 tabs)
├── MatchmakingViewModel.kt
├── MatchmakingUiState.kt
├── MatchmakingContent.kt
├── bridegroom/       (Tab 1: Bride/Groom Profiles)
│   ├── details/
│   │   ├── MatchmakingDetailsScreen.kt
│   │   ├── MatchmakingDetailsViewModel.kt
│   │   └── MatchmakingDetailsUiState.kt
│   ├── interested/
│   │   ├── InterestedUsersViewModel.kt
│   │   └── InterestedUsersUiState.kt
│   ├── manage/
│   │   ├── ManageProfilesScreen.kt
│   │   ├── ManageProfilesViewModel.kt
│   │   └── ManageProfilesUiState.kt
│   └── publish/
│       ├── PublishMatchmakingScreen.kt
│       ├── PublishMatchmakingViewModel.kt
│       └── PublishMatchmakingUiState.kt
└── matchmaker/       (Tab 2: Matchmakers)
    ├── details/
    │   ├── MatchmakerDetailsScreen.kt
    │   ├── MatchmakerDetailsViewModel.kt
    │   └── MatchmakerDetailsUiState.kt
    ├── manage/
    │   ├── ManageMatchmakerScreen.kt
    │   ├── ManageMatchmakerViewModel.kt
    │   └── ManageMatchmakerUiState.kt
    └── publish/
        ├── PublishMatchmakerScreen.kt
        ├── PublishMatchmakerViewModel.kt
        └── PublishMatchmakerUiState.kt
```

### Package Updates

All files have been updated with the correct package declarations:

#### Bridegroom Tab Files

- **Details**: `com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.details`
- **Interested**:
  `com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.interested`
- **Manage**: `com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.manage`
- **Publish**: `com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.publish`

#### Matchmaker Tab Files

- **Details**: `com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.details`
- **Manage**: `com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.manage`
- **Publish**: `com.akash.beautifulbhaluka.presentation.screens.matchmaking.matchmaker.publish`

### Import Updates

The following files have been updated with correct imports:

1. **AppNavigation.kt**
    -
    `import com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.details.MatchmakingDetailsScreen`
    -
    `import com.akash.beautifulbhaluka.presentation.screens.matchmaking.bridegroom.publish.PublishMatchmakingScreen`
    - Fully qualified reference to `ManageProfilesScreen` updated

## Architecture Benefits

1. **Clear Separation**: The two tabs now have their own dedicated directories under `matchmaking/`
2. **Scalability**: Easy to add new features specific to either bridegroom profiles or matchmakers
3. **Maintainability**: Clear ownership and responsibility for each module
4. **Consistency**: Follows the same pattern used throughout the application

## Files Modified

### Bridegroom Directory Files (9 files)

1. `details/MatchmakingDetailsScreen.kt`
2. `details/MatchmakingDetailsViewModel.kt`
3. `details/MatchmakingDetailsUiState.kt`
4. `publish/PublishMatchmakingScreen.kt`
5. `publish/PublishMatchmakingViewModel.kt`
6. `publish/PublishMatchmakingUiState.kt`
7. `manage/ManageProfilesScreen.kt`
8. `manage/ManageProfilesViewModel.kt`
9. `manage/ManageProfilesUiState.kt`

### Navigation Files (1 file)

1. `AppNavigation.kt` - Updated imports and references

## Next Steps

### File System Reorganization

The package declarations have been updated, but the physical files still need to be moved to match
the new package structure:

1. **Move** `matchmaking/details/` → `matchmaking/bridegroom/details/`
2. **Move** `matchmaking/interested/` → `matchmaking/bridegroom/interested/`
3. **Move** `matchmaking/manage/` → `matchmaking/bridegroom/manage/`
4. **Move** `matchmaking/publish/` → `matchmaking/bridegroom/publish/`

### IDE Actions Required

After moving the files:

1. **Rebuild Project**: Build > Rebuild Project
2. **Invalidate Caches**: File > Invalidate Caches / Restart
3. **Clean Build**: ./gradlew clean build

## Architecture Compliance

This refactoring ensures the matchmaking module follows the standard architecture pattern:

```
[feature]/
├── [Feature]Screen.kt
├── [Feature]ViewModel.kt
├── [Feature]UiState.kt
└── [sub-feature]/
    ├── [SubFeature]Screen.kt
    ├── [SubFeature]ViewModel.kt
    └── [SubFeature]UiState.kt
```

In our case:

- **Feature**: matchmaking
- **Sub-features**: bridegroom, matchmaker
- **Sub-sub-features**: details, manage, publish, interested

This provides a clear hierarchical structure that matches the UI flow and user journey through the
application.

