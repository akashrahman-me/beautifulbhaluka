# Testing Guide for Category-Based Product Display

## Manual Testing Checklist

### 1. Main Shop Screen (ShopsScreen)

#### Category Display

- [ ] Products are grouped by category
- [ ] Each category section shows category icon and name
- [ ] Product count is displayed correctly for each category
- [ ] "আরও দেখুন" (See More) button appears on the right side of each category header

#### Horizontal Scrolling

- [ ] Products in each category scroll horizontally
- [ ] Smooth swipe gesture works left and right
- [ ] Product cards are properly sized (180dp width)
- [ ] Spacing between cards is consistent (12dp)
- [ ] No products are cut off at edges

#### Category Chips (Top Section)

- [ ] "সব" (All) chip shows all products
- [ ] Clicking a category chip filters by that category
- [ ] Selected chip has visual feedback (scale animation, different color)
- [ ] Category count displays correctly

#### Search Functionality

- [ ] Search bar filters products across all categories
- [ ] Categories with no matching products are hidden
- [ ] Clear button (X) appears when typing
- [ ] Clear button resets search

### 2. Category Products Screen (CategoryProductsScreen)

#### Navigation

- [ ] Clicking "আরও দেখুন" navigates to category screen
- [ ] Correct category name appears in top bar
- [ ] Product count is accurate
- [ ] Back button returns to main screen

#### Product Grid

- [ ] Products display in 2-column grid
- [ ] All products of the category are shown
- [ ] Proper spacing (12dp) between cards
- [ ] Cards are responsive to screen width

#### Sort Functionality

- [ ] Sort button opens bottom sheet
- [ ] All sort options are available:
    - [ ] নতুন প্রথম (Newest)
    - [ ] দাম: কম থেকে বেশি (Price: Low to High)
    - [ ] দাম: বেশি থেকে কম (Price: High to Low)
    - [ ] রেটিং (Rating)
    - [ ] নাম (Name)
- [ ] Products resort correctly after selection
- [ ] Bottom sheet closes after selection

#### Product Details Navigation

- [ ] Clicking a product card navigates to details screen
- [ ] Correct product ID is passed
- [ ] Back navigation works properly

### 3. Product Cards

#### Visual Elements

- [ ] Product image loads correctly
- [ ] Placeholder appears for missing images
- [ ] Featured badge shows for featured products
- [ ] New badge shows for new products
- [ ] Out of stock overlay appears when stock is 0
- [ ] Product name displays (max 2 lines)
- [ ] Price displays in Bangla numerals with ৳ symbol
- [ ] Original price shows with strikethrough when available
- [ ] Rating displays with star icon
- [ ] Location shows with location icon

#### Interactions

- [ ] Card has elevation animation on press
- [ ] Tap opens product details
- [ ] Long press (if implemented) shows context menu

### 4. Edge Cases

#### Empty States

- [ ] Empty state shows when no products exist
- [ ] Empty state shows appropriate message
- [ ] Empty state shows add product button (if user has permission)

#### Loading States

- [ ] Loading indicator appears during data fetch
- [ ] Loading message displays in Bangla
- [ ] UI is blocked during loading

#### Error States

- [ ] Error message displays in Bangla
- [ ] Retry button works correctly
- [ ] Error icon appears

#### Data Scenarios

- [ ] Category with 1 product displays correctly
- [ ] Category with many products (10+) scrolls properly
- [ ] Categories appear in correct order
- [ ] Product with missing data handles gracefully

### 5. Performance Tests

#### Scrolling Performance

- [ ] Vertical scroll in main screen is smooth
- [ ] Horizontal scroll in category rows is smooth
- [ ] No lag when switching between screens
- [ ] Images load progressively without blocking UI

#### Memory

- [ ] No memory leaks when navigating back and forth
- [ ] Images are properly cached
- [ ] Large product lists don't cause crashes

### 6. Responsive Design

#### Different Screen Sizes

- [ ] Works on small phones (320dp width)
- [ ] Works on regular phones (360dp - 420dp width)
- [ ] Works on large phones (420dp+ width)
- [ ] Works on tablets
- [ ] Adapts to landscape orientation

#### Text Scaling

- [ ] UI remains usable with large text settings
- [ ] No text overflow issues
- [ ] Proper text wrapping

### 7. Localization

#### Bangla Support

- [ ] All UI text displays in Bangla correctly
- [ ] Numbers format correctly in Bangla
- [ ] Currency symbol (৳) appears correctly
- [ ] RTL layout (if needed) works properly

### 8. Accessibility

- [ ] Content descriptions are provided
- [ ] Buttons are properly labeled
- [ ] Touch targets are at least 48dp
- [ ] Color contrast meets WCAG standards
- [ ] Screen reader compatibility

## Automated Testing Suggestions

### Unit Tests (ViewModel)

```kotlin
@Test
fun `products are grouped by category correctly`() {
    // Test that products are properly grouped
}

@Test
fun `sort by price low to high works`() {
    // Test sorting functionality
}

@Test
fun `filter by category works`() {
    // Test category filtering
}

@Test
fun `search filters products correctly`() {
    // Test search functionality
}
```

### UI Tests (Compose)

```kotlin
@Test
fun `clicking see more navigates to category screen`() {
    // Test navigation
}

@Test
fun `horizontal scroll works in category section`() {
    // Test scrolling
}

@Test
fun `product card displays all information`() {
    // Test card rendering
}

@Test
fun `back button returns to main screen`() {
    // Test back navigation
}
```

## Test Data Setup

### Minimum Test Data

- At least 3 categories with products
- Category with 1 product
- Category with 5+ products
- Products with all fields populated
- Products with missing optional fields
- Featured products
- New products
- Out of stock products

### Test Accounts

- User with products
- User without products
- Guest user (if applicable)

## Known Issues/Limitations to Test

1. **Image Loading**: Test with slow network to verify loading states
2. **Large Lists**: Test with 50+ products to check performance
3. **Orientation Change**: Verify state is preserved
4. **Process Death**: Verify state recovery after app restart

## Regression Testing

After any code changes, verify:

- [ ] Existing product details screen still works
- [ ] Product publish screen still works
- [ ] Navigation from home screen works
- [ ] Search and filter continue to work
- [ ] Sort functionality remains intact

## Device Testing Matrix

| Device      | Screen Size | Android Version | Status |
|-------------|-------------|-----------------|--------|
| Pixel 4     | Normal      | API 30          | [ ]    |
| Pixel 6     | Normal      | API 33          | [ ]    |
| Samsung S21 | Normal      | API 31          | [ ]    |
| Small Phone | Small       | API 29          | [ ]    |
| Tablet      | Large       | API 33          | [ ]    |

## Report Template

```
Test Date: ___________
Tester: ___________
Device: ___________
Android Version: ___________

Issues Found:
1. _________________
2. _________________

Screenshots:
- Attach relevant screenshots

Notes:
_________________
```

