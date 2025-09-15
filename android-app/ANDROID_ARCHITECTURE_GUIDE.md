# Android App Architecture Guide
## Kotlin + Jetpack Compose + Modern Standards

## 📱 Overview
This document provides a standard, scalable architecture for modern Android applications using **Kotlin** and **Jetpack Compose**. Follow this structure for maintainable, testable, and performant apps.

**Core Technologies:**
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture Pattern:** MVVM + Repository Pattern
- **Dependency Injection:** Hilt (recommended)
- **Navigation:** Navigation Compose
- **State Management:** StateFlow + Compose State

---

## 🏗️ Standard Project Structure

```
com.company.appname/
├── Application.kt                  # Application class with Hilt setup
├── MainActivity.kt                 # Single activity entry point
├── di/                            # Dependency injection modules
│   ├── DatabaseModule.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
├── data/                          # Data layer
│   ├── local/                     # Local data sources
│   │   ├── database/             # Room database
│   │   │   ├── AppDatabase.kt
│   │   │   ├── dao/              # Data access objects
│   │   │   └── entities/         # Database entities
│   │   └── preferences/          # SharedPreferences/DataStore
│   ├── remote/                    # Network data sources
│   │   ├── api/                  # API interfaces
│   │   ├── dto/                  # Data transfer objects
│   │   └── interceptors/         # Network interceptors
│   └── repository/                # Repository implementations
│       └── [Feature]Repository.kt
├── domain/                        # Business logic layer
│   ├── model/                     # Domain models (clean entities)
│   ├── repository/                # Repository interfaces
│   └── usecase/                   # Business use cases
│       └── [Feature]UseCase.kt
├── presentation/                  # UI layer
│   ├── navigation/                # Navigation setup
│   │   ├── AppNavigation.kt
│   │   ├── NavigationRoutes.kt
│   │   └── NavigationArgs.kt
│   ├── theme/                     # UI theming
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   ├── Type.kt
│   │   └── Dimension.kt
│   ├── components/                # Reusable UI components
│   │   ├── common/               # App-wide components
│   │   └── [feature]/            # Feature-specific components
│   └── screens/                   # Screen-level composables
│       └── [feature]/            # Feature modules
│           ├── [Feature]Screen.kt
│           ├── [Feature]ViewModel.kt
│           ├── [Feature]UiState.kt
│           └── components/        # Screen-specific components
└── utils/                         # Utility classes and extensions
    ├── Constants.kt
    ├── Extensions.kt
    └── NetworkUtils.kt
```

---

## 🏛️ Architecture Layers

### 1. **Presentation Layer** (`presentation/`)
- **Purpose:** Handle UI logic, user interactions, and state management
- **Components:** Composables, ViewModels, UI States
- **Dependencies:** Only domain layer (never data layer directly)

### 2. **Domain Layer** (`domain/`)
- **Purpose:** Business logic and rules, platform-independent
- **Components:** Use Cases, Domain Models, Repository Interfaces
- **Dependencies:** No dependencies on other layers

### 3. **Data Layer** (`data/`)
- **Purpose:** Data access from various sources (network, database, cache)
- **Components:** Repositories, Data Sources, DTOs, Entities
- **Dependencies:** Only domain interfaces

---

## 🎯 MVVM + Repository Pattern

### Screen Architecture Pattern
```kotlin
// 1. UI State
data class FeatureUiState(
    val isLoading: Boolean = false,
    val data: List<DomainModel> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)

// 2. ViewModel
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val useCase: FeatureUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FeatureUiState())
    val uiState: StateFlow<FeatureUiState> = _uiState.asStateFlow()
    
    fun onAction(action: FeatureAction) {
        when (action) {
            is FeatureAction.LoadData -> loadData()
            is FeatureAction.Refresh -> refresh()
        }
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            useCase.getData()
                .onSuccess { data ->
                    _uiState.update { 
                        it.copy(isLoading = false, data = data, error = null) 
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(isLoading = false, error = error.message) 
                    }
                }
        }
    }
}

// 3. Screen Composable
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    FeatureContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        navigateBack = navigateBack
    )
}

// 4. Content Composable (Stateless)
@Composable
fun FeatureContent(
    uiState: FeatureUiState,
    onAction: (FeatureAction) -> Unit,
    navigateBack: () -> Unit
) {
    // UI implementation
}
```

---

## 🗂️ Data Management

### Repository Pattern
```kotlin
// Domain Interface
interface FeatureRepository {
    suspend fun getData(): Result<List<DomainModel>>
    suspend fun saveData(data: DomainModel): Result<Unit>
    fun observeData(): Flow<List<DomainModel>>
}

// Data Implementation
@Singleton
class FeatureRepositoryImpl @Inject constructor(
    private val remoteDataSource: FeatureRemoteDataSource,
    private val localDataSource: FeatureLocalDataSource
) : FeatureRepository {
    
    override suspend fun getData(): Result<List<DomainModel>> {
        return try {
            // Try remote first
            val remoteData = remoteDataSource.fetchData()
            localDataSource.saveData(remoteData)
            Result.success(remoteData.toDomainModels())
        } catch (e: Exception) {
            // Fallback to local
            val localData = localDataSource.getData()
            Result.success(localData.toDomainModels())
        }
    }
}
```

### Use Cases
```kotlin
@Singleton
class GetFeatureDataUseCase @Inject constructor(
    private val repository: FeatureRepository
) {
    suspend operator fun invoke(): Result<List<DomainModel>> {
        return repository.getData()
    }
}
```

---

## 🧭 Navigation Architecture

### Navigation Setup
```kotlin
// Navigation Routes
object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{id}"
    const val PROFILE = "profile"
}

// Navigation Arguments
data class DetailArgs(val id: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        id = checkNotNull(savedStateHandle.get<String>("id"))
    )
}

// App Navigation
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = Routes.HOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                navigateToDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            DetailScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}
```

---

## 🎨 UI Components & Theming

### Design System Structure
```kotlin
// Theme Setup
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) 
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Reusable Components
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !loading
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(text)
        }
    }
}
```

---

## 📋 Best Practices & Standards

### 🎯 Compose Best Practices

#### State Management
```kotlin
// ✅ Lift state up - keep composables stateless
@Composable
fun StatelessComponent(
    data: List<Item>,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
)

// ✅ Use proper state holders
@Composable
fun ScreenWithState() {
    val viewModel: MyViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    MyContent(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}
```

#### Performance Optimizations
```kotlin
// ✅ Use remember for expensive calculations
@Composable
fun ExpensiveComponent(items: List<Item>) {
    val processedItems = remember(items) {
        items.map { /* expensive operation */ }
    }
}

// ✅ Use keys for dynamic lists
LazyColumn {
    items(items, key = { it.id }) { item ->
        ItemCard(item = item)
    }
}

// ✅ Use derivedStateOf for computed states
@Composable
fun FilteredList(items: List<Item>, query: String) {
    val filteredItems by remember {
        derivedStateOf {
            if (query.isBlank()) items
            else items.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}
```

### 🏗️ Architecture Standards

#### Dependency Injection
```kotlin
// Application Module
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}

// Network Module
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```

#### Error Handling
```kotlin
// Sealed class for results
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

// Extension functions
inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    if (this is Result.Error) action(exception)
    return this
}
```

### 🧪 Testing Structure

```kotlin
// Unit Test Example
class FeatureViewModelTest {
    
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private val mockUseCase = mockk<FeatureUseCase>()
    private lateinit var viewModel: FeatureViewModel
    
    @Before
    fun setup() {
        viewModel = FeatureViewModel(mockUseCase)
    }
    
    @Test
    fun `when load data succeeds, ui state is updated correctly`() = runTest {
        // Given
        val expectedData = listOf(mockk<DomainModel>())
        coEvery { mockUseCase.invoke() } returns Result.success(expectedData)
        
        // When
        viewModel.onAction(FeatureAction.LoadData)
        
        // Then
        val uiState = viewModel.uiState.value
        assertEquals(expectedData, uiState.data)
        assertEquals(false, uiState.isLoading)
        assertEquals(null, uiState.error)
    }
}

// Compose UI Test
class FeatureScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun `when loading state, progress indicator is shown`() {
        composeTestRule.setContent {
            FeatureContent(
                uiState = FeatureUiState(isLoading = true),
                onAction = {},
                navigateBack = {}
            )
        }
        
        composeTestRule
            .onNodeWithTag("loading_indicator")
            .assertIsDisplayed()
    }
}
```

---

## 🚀 Development Workflow

### Git Standards
```bash
# Branch naming
feature/user-authentication
fix/navigation-crash
refactor/repository-pattern
docs/architecture-update

# Commit messages
feat: add user authentication flow
fix: resolve navigation back stack issue
refactor: implement repository pattern
docs: update architecture documentation
test: add unit tests for login feature
```

### Code Review Checklist
- [ ] Follows MVVM pattern correctly
- [ ] UI state is properly managed
- [ ] Composables are stateless where possible
- [ ] Proper error handling implemented
- [ ] Unit tests added for business logic
- [ ] UI tests for critical user flows
- [ ] Performance optimizations applied
- [ ] Accessibility considerations met

---

## 🎯 Quick Start Checklist

### New Feature Implementation
1. **Define Domain Models** in `domain/model/`
2. **Create Repository Interface** in `domain/repository/`
3. **Implement Use Cases** in `domain/usecase/`
4. **Create Data Sources** in `data/remote/` or `data/local/`
5. **Implement Repository** in `data/repository/`
6. **Create UI State** in `presentation/screens/[feature]/`
7. **Build ViewModel** with state management
8. **Design Screen Composables** (stateless)
9. **Add Navigation Routes** in `navigation/`
10. **Write Tests** for business logic and UI

### Architecture Validation
✅ **Single Responsibility:** Each class has one clear purpose  
✅ **Dependency Inversion:** High-level modules don't depend on low-level modules  
✅ **Separation of Concerns:** UI, business logic, and data are properly separated  
✅ **Testability:** Business logic is easily testable without Android framework  
✅ **Scalability:** Structure supports growth and new features  

---

**This architecture provides a solid foundation for any Android project. Adapt the structure based on your specific requirements while maintaining these core principles.**
