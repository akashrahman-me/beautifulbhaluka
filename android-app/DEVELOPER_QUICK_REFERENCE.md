# Developer Quick Reference

## Files Modified/Created

### Modified Files

1. ✏️ `ShopsContent.kt` - Changed layout from grid to category sections
2. ✏️ `ShopsUiState.kt` - Added NavigateToCategory action
3. ✏️ `ShopsScreen.kt` - Added category navigation parameter
4. ✏️ `NavigationRoutes.kt` - Added category products route
5. ✏️ `AppNavigation.kt` - Added category screen navigation

### New Files

1. ✨ `CategoryProductsScreen.kt` - Category detail screen
2. ✨ `CategoryProductsUiState.kt` - State for category screen
3. ✨ `CategoryProductsViewModel.kt` - ViewModel with factory
4. 📄 `CATEGORY_IMPLEMENTATION_SUMMARY.md` - Implementation summary
5. 📄 `UI_FLOW_DOCUMENTATION.md` - Visual UI documentation
6. 📄 `TESTING_GUIDE.md` - Testing checklist

## Quick Code Reference

### Navigate to Category Screen

```kotlin
onAction(ShopsAction.NavigateToCategory(category))
```

### Category Section Component

```kotlin
CategoryProductSection(
    category = category,
    products = products,
    onProductClick = { product -> /* handle click */ },
    onSeeMoreClick = { /* navigate to category */ }
)
```

### Product Card with Custom Width

```kotlin
ModernProductCard(
    product = product,
    onClick = { /* handle click */ },
    modifier = Modifier.width(180.dp)
)
```

### Navigation Routes

```kotlin
// Navigate to category screen
navController.navigate(NavigationRoutes.categoryProducts(categoryId))

// Navigate to product details
navController.navigate(NavigationRoutes.productDetails(productId))
```

## Architecture Pattern

```
┌─────────────────────────────────────────┐
│              ShopsScreen                 │
│  (Entry point with navigation params)   │
└────────────┬────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────┐
│          ShopsViewModel                 │
│    (Business logic & state)             │
└────────────┬───────────────────────────┘
             │
             ↓
┌────────────────────────────────────────┐
│          ShopsContent                   │
│  (UI composition & layout)              │
│                                         │
│  ┌─────────────────────────────────┐  │
│  │  CategoryProductSection         │  │
│  │  (Category header + horiz row)  │  │
│  └─────────────────────────────────┘  │
└─────────────────────────────────────────┘
             │
             │ Click "আরও দেখুন"
             ↓
┌─────────────────────────────────────────┐
│      CategoryProductsScreen              │
│  (Full category view with grid)         │
└────────────┬────────────────────────────┘
             │
             ↓
┌────────────────────────────────────────┐
│    CategoryProductsViewModel            │
│  (Category-specific logic)              │
└─────────────────────────────────────────┘
```

## Key Components Breakdown

### 1. CategoryProductSection

**Purpose**: Shows category header with horizontally scrollable products

**Props**:

- `category: ProductCategory` - Category info
- `products: List<Product>` - Products in category
- `onProductClick: (Product) -> Unit` - Product click handler
- `onSeeMoreClick: () -> Unit` - See more button handler

**Layout**:

```
[Icon] Category Name        [আরও দেখুন →]
       X টি পণ্য
       
← [Product] [Product] [Product] →
```

### 2. ModernProductCard

**Purpose**: Displays individual product card

**Props**:

- `product: Product` - Product data
- `onClick: () -> Unit` - Click handler
- `modifier: Modifier` - Custom modifier (for width control)

**Features**:

- Responsive image loading
- Featured/New badges
- Out of stock overlay
- Price display with strikethrough
- Rating and location

### 3. CategoryProductsScreen

**Purpose**: Full-screen category view

**Features**:

- Top app bar with back button
- Product grid (2 columns)
- Sort functionality
- Loading/Error/Empty states

## State Management

### ShopsUiState

```kotlin
data class ShopsUiState(
    val isLoading: Boolean,
    val products: List<Product>,
    val categories: List<ProductCategory>,
    val selectedCategory: ProductCategory?,
    val searchQuery: String,
    val filteredProducts: List<Product>,
    val error: String?
)
```

### CategoryProductsUiState

```kotlin
data class CategoryProductsUiState(
    val isLoading: Boolean,
    val category: ProductCategory?,
    val products: List<Product>,
    val error: String?
)
```

## Actions

### ShopsAction

```kotlin
sealed class ShopsAction {
    data class SearchProducts(val query: String)
    data class SelectCategory(val category: ProductCategory?)
    data class NavigateToCategory(val category: ProductCategory) // NEW
    data class ToggleFavorite(val productId: String)
    data class SortProducts(val sortOption: SortOption)
    object Refresh
    object ClearFilters
}
```

### CategoryProductsAction

```kotlin
sealed class CategoryProductsAction {
    data class SortProducts(val sortOption: SortOption)
    object Refresh
}
```

## Common Customizations

### Change Product Card Width

In `CategoryProductSection`, modify:

```kotlin
ModernProductCard(
    product = product,
    onClick = { onProductClick(product) },
    modifier = Modifier.width(200.dp) // Change from 180.dp
)
```

### Change Grid Columns in Category Screen

In `CategoryProductsContent`, modify:

```kotlin
LazyVerticalGrid(
    columns = GridCells.Fixed(3), // Change from 2
    // ...
)
```

### Add Category Icon Mapping

In `CategoryProductSection`, enhance the header:

```kotlin
Icon(
    imageVector = getCategoryIcon(category.id), // Custom function
    contentDescription = null,
    modifier = Modifier.size(24.dp),
    tint = MaterialTheme.colorScheme.primary
)
```

### Customize Spacing

```kotlin
// Horizontal spacing in category row
LazyRow(
    horizontalArrangement = Arrangement.spacedBy(16.dp), // Change from 12.dp
    // ...
)

// Vertical spacing between categories
LazyColumn(
    verticalArrangement = Arrangement.spacedBy(20.dp), // Change from 16.dp
    // ...
)
```

## Troubleshooting

### Products Not Grouping by Category

✅ Check: `productsByCategory = filteredProducts.groupBy { it.category }`

### Category Screen Not Opening

✅ Check: Navigation route matches in `NavigationRoutes.kt` and `AppNavigation.kt`
✅ Check: `onNavigateToCategory` is passed from parent

### ViewModelFactory Error

✅ Check: Using correct factory in `CategoryProductsScreen`:

```kotlin
viewModel: CategoryProductsViewModel = viewModel(
factory = CategoryProductsViewModelFactory(categoryId)
)
```

### Products Not Displaying

✅ Check: Mock data in `CategoryProductsViewModel` matches `ShopsViewModel`
✅ Check: Category IDs match between products and categories

## Performance Tips

1. **Image Loading**: Use Coil's placeholder and caching
2. **LazyRow**: Use `key = { it.id }` for better recomposition
3. **State Hoisting**: Keep state in ViewModel, not composables
4. **Remember**: Use `remember` for computed values
5. **Derivation**: Use `derivedStateOf` for derived states

## Next Steps / Future Enhancements

1. 🔄 Add pull-to-refresh in category screen
2. 🔍 Add search in category screen
3. 📊 Add category analytics
4. 💾 Implement real repository/API
5. 🎨 Add category-specific color themes
6. 📱 Add share functionality for categories
7. ⭐ Add favorite categories
8. 🔔 Add category-based notifications

## Need Help?

- Review `Architecture.md` for overall app structure
- Check existing screens like `BloodBankScreen.kt` for patterns
- Refer to `TESTING_GUIDE.md` for testing approach
- See `UI_FLOW_DOCUMENTATION.md` for UI specs

