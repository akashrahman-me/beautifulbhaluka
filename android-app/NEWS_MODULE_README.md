# News Module

A modern, ultra-clean news feed module where users can submit and browse news articles from external
websites.

## Features

- **Submit Article**: Users can submit news articles by providing a web URL
- **Article Preview**: Displays title, content excerpt, and thumbnail for each article
- **Clean UI**: Modern design with gradient accents, smooth spacing, and Lucide icons
- **MVVM Architecture**: Follows the app's architecture standards

## Module Structure

```
news/
├── domain/
│   ├── model/
│   │   └── NewsArticle.kt           # Domain model
│   ├── repository/
│   │   └── NewsRepository.kt        # Repository interface
│   └── usecase/
│       ├── GetNewsArticlesUseCase.kt
│       └── SubmitNewsArticleUseCase.kt
├── data/
│   └── repository/
│       └── NewsRepositoryImpl.kt    # Repository implementation
└── presentation/
    └── screens/
        └── news/
            ├── NewsScreen.kt        # Main screen composable
            ├── NewsViewModel.kt     # View model with state management
            ├── NewsUiState.kt       # UI state data class
            └── components/
                ├── NewsArticleCard.kt    # Article card component
                └── SubmitNewsDialog.kt   # Submit dialog component
```

## Navigation

The news screen is accessible via:

```kotlin
NavigationRoutes.NEWS
```

## Design Highlights

- **Gradient Headers**: Primary to tertiary color gradients for visual appeal
- **Card-based Layout**: Each article in a rounded card with shadow
- **Modern Typography**: Clear hierarchy with bold titles and subtle metadata
- **Lucide Icons**: Consistent iconography throughout
- **Smooth Spacing**: 20dp between cards, generous padding
- **Floating Action Button**: Extended FAB for article submission
- **Empty State**: Encouraging UI when no articles exist
- **Loading State**: Elegant loading indicator with gradient background
- **Error State**: Clear error messaging with retry option

## Usage

From HomeScreen or any navigation point:

```kotlin
navController.navigate(NavigationRoutes.NEWS)
```

## Key Components

### NewsArticleCard

- Displays article with thumbnail, title, excerpt
- Shows source domain badge
- User name and time ago
- "Read" button to open external URL
- Optional delete functionality

### SubmitNewsDialog

- Modern dialog with gradient icon
- URL input field with validation
- Info card explaining the feature
- Submit button with loading state
- Error handling

## Color Scheme

- Uses MaterialTheme color scheme
- Primary and tertiary colors for gradients
- Surface colors for cards
- Variant colors for secondary text

## Future Enhancements

- Web scraping for auto-extracting metadata
- Bookmark functionality
- Share feature
- Categories/tags
- Search and filter

