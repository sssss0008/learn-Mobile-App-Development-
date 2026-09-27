package com.example.data.repository

import com.example.data.model.*

object AcademyRepository {

    val creator = CreatorProfile(
        name = "Awiskar Acharya",
        title = "Mobile App Developer | Educator | Creator",
        tagline = "Learn. Build. Debug. Ship.",
        bio = "Lead mobile engineer and educator passionate about bringing accessible, production-grade mobile engineering to developers worldwide. I specialize in Kotlin, Jetpack Compose, Modern Android Architecture, and Cross-Platform frameworks.",
        teachingPhilosophy = "Real developers don't just copy code; they build mental models. The loop: Learn -> See -> Code -> Run -> Debug -> Practice -> Build -> Test -> Ship is how engineering mastery is achieved.",
        linkedInUrl = "https://www.linkedin.com/in/awiskaracharya/",
        totalStudents = "48,500+",
        completedProjectsCount = 120,
        featuredArticles = listOf(
            CreatorArticle(
                title = "The State-to-UI Mental Model in Jetpack Compose",
                readTime = "6 min read",
                category = "Architecture",
                summary = "Why imperative UI habits hold developers back and how unidirectional data flow unlocks declarative simplicity."
            ),
            CreatorArticle(
                title = "Mastering Kotlin Coroutines & Structured Concurrency",
                readTime = "8 min read",
                category = "Concurrency",
                summary = "Avoiding memory leaks, managing cancellation propagation, and writing rock-solid async code."
            ),
            CreatorArticle(
                title = "10 Architecture Mistakes Beginners Make in Android",
                readTime = "10 min read",
                category = "Best Practices",
                summary = "From fat ViewModels to skipping repository abstractions: how to build maintainable apps."
            )
        )
    )

    val skillTreeNodes = listOf(
        RoadmapNode("node_1", 1, "Programming Fundamentals", "Variables, Data Types, Control Flow, OOP", true, false),
        RoadmapNode("node_2", 2, "Kotlin Language Mastery", "Null Safety, Lambdas, Collections, Coroutines", true, false, listOf("Programming Fundamentals")),
        RoadmapNode("node_3", 3, "Android Basics & Manifest", "Activity Lifecycle, Intents, App Storage", true, false, listOf("Kotlin Language Mastery")),
        RoadmapNode("node_4", 4, "Jetpack Compose Modern UI", "Composables, State, Modifiers, M3 Design", true, true, listOf("Android Basics & Manifest")),
        RoadmapNode("node_5", 5, "MVVM & Clean Architecture", "UI Layer, ViewModels, Repository Pattern", false, false, listOf("Jetpack Compose Modern UI")),
        RoadmapNode("node_6", 6, "Networking & REST APIs", "Retrofit, OkHttp, JSON Serialization", false, false, listOf("MVVM & Clean Architecture")),
        RoadmapNode("node_7", 7, "Room Database & SQLite", "Entities, DAOs, Migrations, Offline Cache", false, false, listOf("Networking & REST APIs")),
        RoadmapNode("node_8", 8, "Authentication & Security", "Tokens, Biometrics, Secure DataStore", false, false, listOf("Room Database & SQLite")),
        RoadmapNode("node_9", 9, "Testing & Code Quality", "Unit Tests, Robolectric, Compose Testing", false, false, listOf("Authentication & Security")),
        RoadmapNode("node_10", 10, "Production Engineering & Release", "ProGuard, App Bundles, Play Console, CI/CD", false, false, listOf("Testing & Code Quality"))
    )

    val courses = listOf(
        Course(
            id = "course_kotlin",
            title = "Kotlin Language Academy",
            category = "Kotlin",
            level = "Beginner to Advanced",
            description = "From basic syntax to advanced Coroutines, Flow, Generics, and functional idioms.",
            durationHours = 18,
            progressPercent = 80,
            iconName = "Code",
            accentColorHex = 0xFF7C3AED,
            modules = listOf(
                CourseModule(
                    id = "mod_k_basics",
                    title = "1. Kotlin Core & Null Safety",
                    description = "val vs var, smart casts, nullability, safe calls, and elvis operators.",
                    lessons = listOf(
                        Lesson(
                            id = "k_lesson_1",
                            title = "Variables, Types & Immutability",
                            durationMinutes = 12,
                            isCompleted = true,
                            summary = "Understand the fundamental difference between val and var in Kotlin.",
                            learnTheory = "Kotlin enforces immutability by default using 'val' (read-only reference). Use 'var' only when reassignment is strictly required. Kotlin's compiler performs type inference, eliminating redundant boilerplate.",
                            visualConcept = "val (Immutable) ──> Fixed pointer\nvar (Mutable)   ──> Reassignable pointer",
                            sampleCode = """
fun main() {
    val developer = "Awiskar Acharya"
    var lessonsCompleted = 5
    lessonsCompleted += 1
    println("Developer: ${'$'}developer, Completed: ${'$'}lessonsCompleted")
}
                            """.trimIndent(),
                            expectedOutput = "Developer: Awiskar Acharya, Completed: 6",
                            codeExplanation = "val creates a read-only variable whose reference cannot be mutated after assignment. var allows reassignment.",
                            practiceTask = "Declare an immutable val for your appName and a mutable var for downloads count. Print both.",
                            practiceStarterCode = """
fun main() {
    // Write your code here
}
                            """.trimIndent(),
                            practiceSolution = """
fun main() {
    val appName = "MobileDev Academy"
    var downloads = 1000
    downloads += 250
    println("${'$'}appName has ${'$'}downloads downloads")
}
                            """.trimIndent(),
                            debuggingSample = """
fun main() {
    val score = 10
    score = score + 5 // Compilation error: Val cannot be reassigned
    println(score)
}
                            """.trimIndent(),
                            debuggingFix = "Change 'val score = 10' to 'var score = 10' because score is being reassigned.",
                            miniProjectIdea = "Create a user stats tracker tracking level, xp, and streak.",
                            keyTakeaway = "Always prefer 'val' over 'var' to prevent accidental side effects and race conditions in concurrent apps."
                        ),
                        Lesson(
                            id = "k_lesson_2",
                            title = "Null Safety & The Elvis Operator",
                            durationMinutes = 15,
                            isCompleted = true,
                            summary = "Eradicate NullPointerExceptions with Kotlin's type system, ?., and ?:",
                            learnTheory = "In Kotlin, references are non-nullable by default (String). Nullable types are suffixed with '?' (String?). Safe call operator (?.) executes only if non-null, and Elvis operator (?:) provides a graceful default value.",
                            visualConcept = "Nullable Object\n   ├── Non-Null ──> Execute block\n   └── Null     ──> Evaluate fallback (?: default)",
                            sampleCode = """
fun main() {
    val username: String? = null
    val displayName = username ?: "Guest Developer"
    println("Welcome, ${'$'}displayName! Length: ${'$'}{username?.length ?: 0}")
}
                            """.trimIndent(),
                            expectedOutput = "Welcome, Guest Developer! Length: 0",
                            codeExplanation = "username?.length returns null because username is null. The Elvis operator ?: catches the null and defaults to 0.",
                            practiceTask = "Safely read an optional email and provide 'no-email@dev.io' fallback.",
                            practiceStarterCode = """
fun main() {
    val userEmail: String? = null
    // Assign fallback using ?:
}
                            """.trimIndent(),
                            practiceSolution = """
fun main() {
    val userEmail: String? = null
    val safeEmail = userEmail ?: "no-email@dev.io"
    println("Contact: ${'$'}safeEmail")
}
                            """.trimIndent(),
                            debuggingSample = """
fun main() {
    val input: String? = null
    println(input.length) // ERROR: Only safe (?.) or non-null asserted (!!.) calls are allowed
}
                            """.trimIndent(),
                            debuggingFix = "Use input?.length or input?.length ?: 0 to safely access the property without crashing.",
                            miniProjectIdea = "Build a profile validator that cleanses optional user inputs.",
                            keyTakeaway = "Avoid the '!!' operator in production code. Always use safe calls (?.) and Elvis (?:)."
                        )
                    )
                ),
                CourseModule(
                    id = "mod_k_coroutines",
                    title = "2. Coroutines & Flow",
                    description = "Suspend functions, dispatchers, structured concurrency, StateFlow, and SharedFlow.",
                    lessons = listOf(
                        Lesson(
                            id = "k_lesson_3",
                            title = "Suspend Functions & Coroutine Dispatchers",
                            durationMinutes = 20,
                            isCompleted = false,
                            summary = "Asynchronous programming without blocking the main UI thread.",
                            learnTheory = "A suspend function can be paused and resumed without blocking the underlying thread. Dispatchers.Main controls UI, Dispatchers.IO handles network/database disk calls, and Dispatchers.Default handles CPU-intensive computation.",
                            visualConcept = "Main Thread ───[UI Updates]───────────────────────────────>\n                 │ launch(Dispatchers.IO)\nIO Thread   ─────└──[Fetch Network API]──> return data ─┘",
                            sampleCode = """
// Simulated Coroutine Execution
suspend fun fetchUserProfile(userId: String): String {
    // Simulated network delay
    return "User Profile for ID: ${'$'}userId (Loaded from API)"
}

fun main() {
    println("Thread: Dispatchers.Main -> Request started")
    println("Simulating suspend call...")
    val result = "Loaded Profile: Awiskar Acharya"
    println("Thread: Dispatchers.Main -> ${'$'}result")
}
                            """.trimIndent(),
                            expectedOutput = "Thread: Dispatchers.Main -> Request started\nSimulating suspend call...\nThread: Dispatchers.Main -> Loaded Profile: Awiskar Acharya",
                            codeExplanation = "Suspend functions yield thread control while waiting for I/O operations, ensuring butter-smooth 60/120fps UI.",
                            practiceTask = "Create a suspend function that simulates fetching user score from a local database.",
                            practiceStarterCode = """
suspend fun getScore(): Int {
    // return score
    return 0
}
                            """.trimIndent(),
                            practiceSolution = """
suspend fun getScore(): Int {
    // In real code: delay(500)
    return 980
}
fun main() {
    println("Score: 980")
}
                            """.trimIndent(),
                            debuggingSample = """
fun main() {
    // Calling suspend function from non-coroutine context
    // fetchUserProfile("123") // ERROR: Suspend function should be called only from a coroutine
}
                            """.trimIndent(),
                            debuggingFix = "Wrap the call in coroutineScope, viewModelScope.launch, or runBlocking for tests.",
                            miniProjectIdea = "Design an async image loader simulation with IO dispatcher switching.",
                            keyTakeaway = "Never perform network or database queries on Dispatchers.Main—it causes ANR (Application Not Responding) crashes."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course_compose",
            title = "Jetpack Compose Modern UI",
            category = "Compose",
            level = "All Levels",
            description = "Declarative Android UI mental model, state hoisting, recomposition, animations, and Material 3 design systems.",
            durationHours = 24,
            progressPercent = 65,
            iconName = "Layers",
            accentColorHex = 0xFF0284C7,
            modules = listOf(
                CourseModule(
                    id = "mod_compose_state",
                    title = "1. Declarative Mental Model & State",
                    description = "State-to-UI equation: UI = f(State). Recomposition, remember, and mutableStateOf.",
                    lessons = listOf(
                        Lesson(
                            id = "comp_lesson_1",
                            title = "The State -> UI Mental Model",
                            durationMinutes = 18,
                            isCompleted = true,
                            summary = "Stop manipulating views directly. Describe what the UI looks like for any given state.",
                            learnTheory = "In traditional Android (XML), you mutate views imperatively: textView.setText('Hello'). In Jetpack Compose, UI is declarative: UI = f(State). When state changes, Compose automatically recomposes only the functions whose inputs changed.",
                            visualConcept = "User Action (Click) ──> State Mutation (count++) ──> Recomposition ──> Fresh UI Render",
                            sampleCode = """
@Composable
fun CounterButton() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("Clicks: ${'$'}count")
    }
}
                            """.trimIndent(),
                            expectedOutput = "Button rendered with initial text 'Clicks: 0'. Tapping updates text to 'Clicks: 1'.",
                            codeExplanation = "remember caches the value across recompositions. mutableStateOf creates an observable state holder that triggers UI redraws when modified.",
                            practiceTask = "Create a Composable toggle that switches between 'Dark Mode: ON' and 'Dark Mode: OFF'.",
                            practiceStarterCode = """
@Composable
fun ModeToggle() {
    // Implement toggle with remember { mutableStateOf(false) }
}
                            """.trimIndent(),
                            practiceSolution = """
@Composable
fun ModeToggle() {
    var isDark by remember { mutableStateOf(false) }
    Button(onClick = { isDark = !isDark }) {
        Text(if (isDark) "Dark Mode: ON" else "Dark Mode: OFF")
    }
}
                            """.trimIndent(),
                            debuggingSample = """
@Composable
fun BrokenCounter() {
    var count = 0 // ERROR: Will reset to 0 on every single recomposition!
    Button(onClick = { count++ }) {
        Text("Count: ${'$'}count")
    }
}
                            """.trimIndent(),
                            debuggingFix = "Wrap count in: var count by remember { mutableStateOf(0) } so state survives recomposition.",
                            miniProjectIdea = "Build a dynamic theme switcher with animated color transitions.",
                            keyTakeaway = "Compose is not XML with Kotlin syntax. You never 'modify' a composable; you emit a new description based on state."
                        ),
                        Lesson(
                            id = "comp_lesson_2",
                            title = "LazyColumn & List Performance",
                            durationMinutes = 15,
                            isCompleted = false,
                            summary = "Recycle and render infinite items efficiently without OOM memory crashes.",
                            learnTheory = "LazyColumn is Compose's equivalent to RecyclerView. It emits only the items currently visible in the viewport. Always provide a stable, unique key in items(items, key = { it.id }) to optimize recomposition performance.",
                            visualConcept = "Viewport Window (Screen)\n ┌───────────────────────────┐\n │ Item #12 (Rendered in RAM)│\n │ Item #13 (Rendered in RAM)│\n │ Item #14 (Rendered in RAM)│\n └───────────────────────────┘\n (Items #1-#11 and #15+ are recycled)",
                            sampleCode = """
@Composable
fun StudentList(students: List<String>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(students) { student ->
            Text(
                text = student,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
                            """.trimIndent(),
                            expectedOutput = "Vertically scrollable list rendering only visible items smoothly.",
                            codeExplanation = "items() takes a list and inflates item Composables on-demand during scrolling.",
                            practiceTask = "Write a LazyColumn with an itemsIndexed block showing item numbers 1, 2, 3...",
                            practiceStarterCode = """
@Composable
fun NumberedItems(items: List<String>) {
    // LazyColumn here
}
                            """.trimIndent(),
                            practiceSolution = """
@Composable
fun NumberedItems(items: List<String>) {
    LazyColumn {
        itemsIndexed(items) { index, item ->
            Text("${'$'}{index + 1}. ${'$'}item")
        }
    }
}
                            """.trimIndent(),
                            debuggingSample = """
@Composable
fun LaggyList(items: List<String>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // ERROR: If items has 10,000 entries, Column creates all 10,000 views in memory immediately -> OutOfMemoryError!
        items.forEach { Text(it) }
    }
}
                            """.trimIndent(),
                            debuggingFix = "Replace Column with LazyColumn so items are virtualized and only rendered when visible.",
                            miniProjectIdea = "Create an app feed with pull-to-refresh and infinite pagination.",
                            keyTakeaway = "Always provide unique keys in LazyColumn: items(list, key = { it.id }) to avoid unwanted item recompositions when lists change."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course_android_arch",
            title = "Android Architecture & MVVM",
            category = "Architecture",
            level = "Intermediate to Advanced",
            description = "Clean Architecture, ViewModels, Repository pattern, Use Cases, Unidirectional Data Flow, and StateFlow.",
            durationHours = 20,
            progressPercent = 40,
            iconName = "AccountTree",
            accentColorHex = 0xFF059669,
            modules = listOf(
                CourseModule(
                    id = "mod_arch_layers",
                    title = "1. Clean Architecture Layers",
                    description = "UI Layer, Domain Layer (UseCases), Data Layer (Repositories & Data Sources).",
                    lessons = listOf(
                        Lesson(
                            id = "arch_lesson_1",
                            title = "The Repository Pattern & Single Source of Truth",
                            durationMinutes = 22,
                            isCompleted = true,
                            summary = "Decouple your UI from network and database implementations.",
                            learnTheory = "The Repository abstracts data sources (Local Room DB + Remote Retrofit API). The ViewModel never knows whether data came from disk cache, network, or in-memory state. This pattern creates a Single Source of Truth.",
                            visualConcept = "UI (Compose)\n     ↓ observes StateFlow\n ViewModel\n     ↓ calls\n Repository (Interface)\n  ├── Remote DataSource (Retrofit)\n  └── Local DataSource (Room DB)",
                            sampleCode = """
class UserRepository(
    private val api: UserApi,
    private val dao: UserDao
) {
    fun getUser(id: String): Flow<User> = flow {
        val cached = dao.getUserById(id)
        if (cached != null) emit(cached)
        val fresh = api.fetchUser(id)
        dao.insert(fresh)
        emit(fresh)
    }
}
                            """.trimIndent(),
                            expectedOutput = "Cached data emitted instantly for 0ms cold start; refreshed from network immediately.",
                            codeExplanation = "Offline-first architecture emits local cached data first, fetches latest from API in background, updates database, and emits new state.",
                            practiceTask = "Write a repository method that fetches products, falling back to local database if network fails.",
                            practiceStarterCode = """
class ProductRepository {
    // Write offline-first fetch
}
                            """.trimIndent(),
                            practiceSolution = """
class ProductRepository(private val api: Api, private val dao: Dao) {
    suspend fun getProducts(): List<Product> {
        return try {
            val fresh = api.getProducts()
            dao.save(fresh)
            fresh
        } catch (e: Exception) {
            dao.getAll()
        }
    }
}
                            """.trimIndent(),
                            debuggingSample = """
class ProfileViewModel : ViewModel() {
    fun load() {
        // ERROR: Direct network call inside ViewModel violates Clean Architecture and makes testing impossible!
        val client = OkHttpClient()
        val request = Request.Builder().url("https://api.com").build()
    }
}
                            """.trimIndent(),
                            debuggingFix = "Inject a UserRepository into the ViewModel so network logic is isolated and easily mocked in unit tests.",
                            miniProjectIdea = "Design a news reader app that functions completely offline using Room caching.",
                            keyTakeaway = "ViewModels manage UI state and handle user actions; Repositories manage data. Never put networking code directly inside a ViewModel."
                        )
                    )
                )
            )
        ),
        Course(
            id = "course_databases",
            title = "Room Database & SQLite",
            category = "Databases",
            level = "Intermediate",
            description = "Entities, DAOs, TypeConverters, Relations, SQLite queries, and database migrations.",
            durationHours = 14,
            progressPercent = 55,
            iconName = "Storage",
            accentColorHex = 0xFFD97706,
            modules = listOf()
        ),
        Course(
            id = "course_apis",
            title = "REST APIs & Networking",
            category = "APIs",
            level = "Intermediate",
            description = "HTTP methods, Retrofit, OkHttp interceptors, Moshi/Kotlinx serialization, error handling, and headers.",
            durationHours = 16,
            progressPercent = 50,
            iconName = "Http",
            accentColorHex = 0xFFEA580C,
            modules = listOf()
        ),
        Course(
            id = "course_crossplatform",
            title = "Cross-Platform: Flutter & React Native",
            category = "Cross-Platform",
            level = "All Levels",
            description = "Compare Kotlin Multiplatform, Flutter (Dart), React Native (TS), and Swift/SwiftUI architectures.",
            durationHours = 20,
            progressPercent = 30,
            iconName = "Devices",
            accentColorHex = 0xFF2563EB,
            modules = listOf()
        ),
        Course(
            id = "course_testing",
            title = "Mobile Testing & Debugging Lab",
            category = "Testing",
            level = "Advanced",
            description = "JUnit, Robolectric, MockK, Compose UI testing, Logcat analysis, and crash triage.",
            durationHours = 12,
            progressPercent = 35,
            iconName = "BugReport",
            accentColorHex = 0xFFDC2626,
            modules = listOf()
        ),
        Course(
            id = "course_production",
            title = "Production Engineering & Publishing",
            category = "Production",
            level = "Advanced",
            description = "Play Store guidelines, App Bundles (.aab), Keystores, signing, ProGuard, CI/CD, and release monitoring.",
            durationHours = 10,
            progressPercent = 20,
            iconName = "RocketLaunch",
            accentColorHex = 0xFF4F46E5,
            modules = listOf()
        )
    )

    val playgroundSnippets = listOf(
        CodePlaygroundSnippet(
            id = "snip_hello",
            title = "1. Hello Mobile Developer",
            language = "Kotlin",
            description = "The classic entry point for every mobile developer.",
            code = """
fun main() {
    val platform = "MobileDev Academy"
    val creator = "Awiskar Acharya"
    val tagline = "Learn. Build. Debug. Ship."
    
    println("Welcome to ${'$'}platform!")
    println("Created by: ${'$'}creator")
    println("Core Motto: ${'$'}tagline")
    println("Ready to build production Android apps!")
}
            """.trimIndent(),
            expectedOutput = "Welcome to MobileDev Academy!\nCreated by: Awiskar Acharya\nCore Motto: Learn. Build. Debug. Ship.\nReady to build production Android apps!"
        ),
        CodePlaygroundSnippet(
            id = "snip_compose_state",
            title = "2. Compose State Hoisting Simulation",
            language = "Kotlin",
            description = "Demonstrates unidirectional data flow with events moving up and state flowing down.",
            code = """
data class UserUiState(
    val username: String = "Awiskar",
    val completedLessons: Int = 19,
    val streakDays: Int = 7
)

fun onLessonCompleted(current: UserUiState): UserUiState {
    return current.copy(
        completedLessons = current.completedLessons + 1,
        streakDays = current.streakDays + 1
    )
}

fun main() {
    var state = UserUiState()
    println("Initial State: ${'$'}state")
    
    // User completes a lesson
    state = onLessonCompleted(state)
    println("Updated State: ${'$'}state")
    println("UI recomposed with latest stats!")
}
            """.trimIndent(),
            expectedOutput = "Initial State: UserUiState(username=Awiskar, completedLessons=19, streakDays=7)\nUpdated State: UserUiState(username=Awiskar, completedLessons=20, streakDays=8)\nUI recomposed with latest stats!"
        ),
        CodePlaygroundSnippet(
            id = "snip_coroutines",
            title = "3. Kotlin Flow Pipeline",
            language = "Kotlin",
            description = "Transforming asynchronous data streams with map and filter.",
            code = """
fun main() {
    val lessons = listOf(
        "Variables", "Null Safety", "Compose Basics", 
        "State Management", "Room Database", "Retrofit API"
    )
    
    val advancedLessons = lessons
        .filter { it.contains("Compose") || it.contains("Room") || it.contains("Retrofit") }
        .map { "Advanced Module: ${'$'}it" }
        
    println("Filtered Academy Curriculum:")
    advancedLessons.forEach { println(" - ${'$'}it") }
}
            """.trimIndent(),
            expectedOutput = "Filtered Academy Curriculum:\n - Advanced Module: Compose Basics\n - Advanced Module: Room Database\n - Advanced Module: Retrofit API"
        ),
        CodePlaygroundSnippet(
            id = "snip_flutter_comparison",
            title = "4. Flutter vs Compose Mental Model",
            language = "Dart / Kotlin",
            description = "Comparing Flutter's Widget tree with Compose's Composable functions.",
            code = """
// Jetpack Compose (Kotlin):
// @Composable fun Greeting(name: String) = Text("Hello, ${'$'}name")

// Flutter (Dart):
// class Greeting extends StatelessWidget {
//   final String name;
//   const Greeting({required this.name});
//   @override Widget build(BuildContext context) => Text('Hello, ${'$'}name');
// }

fun main() {
    println("Compose: Functions emitting UI tree via compiler plugin")
    println("Flutter: Object tree of immutable Widgets rendered via Skia/Impeller engine")
    println("Both embrace declarative reactive UI: UI = f(state)")
}
            """.trimIndent(),
            expectedOutput = "Compose: Functions emitting UI tree via compiler plugin\nFlutter: Object tree of immutable Widgets rendered via Skia/Impeller engine\nBoth embrace declarative reactive UI: UI = f(state)"
        )
    )

    val projects = listOf(
        ProjectItem(
            id = "proj_notes",
            title = "Modern Notes & Todo App",
            category = "Beginner",
            difficulty = "★☆☆☆☆",
            duration = "4 Hours",
            summary = "Build a responsive Notes app with CRUD, categories, search, Room persistence, and dark mode.",
            techStack = listOf("Kotlin", "Jetpack Compose", "Room DB", "MVVM", "StateFlow"),
            features = listOf(
                "Create, Read, Update, Delete (CRUD) notes",
                "Real-time category filtering (Work, Personal, Ideas)",
                "Full-text search queries",
                "Color tagging and pinned notes",
                "Local persistence with Room database"
            ),
            prerequisites = listOf("Kotlin Basics", "Compose Layouts", "Room DAOs"),
            milestones = listOf(
                ProjectMilestone("1. Database Schema", "Define NoteEntity and NoteDao with Flow queries", true),
                ProjectMilestone("2. Repository Layer", "Implement NoteRepository wrapping Room DAO", true),
                ProjectMilestone("3. ViewModel & UI State", "Create NoteViewModel exposing NoteUiState", false),
                ProjectMilestone("4. Compose Screens", "Build NoteListScreen and AddEditNoteScreen", false),
                ProjectMilestone("5. Search & Filter", "Add real-time search query filtering", false)
            ),
            requirementsChecklist = listOf(
                "Users can add a new note with title and body",
                "Notes are persisted across app restarts",
                "Search bar filters notes by title or content instantly",
                "Delete action displays an undo Snackbar",
                "Supports both Light and Dark themes"
            ),
            starterTemplate = """
// NoteEntity.kt
@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
            """.trimIndent(),
            solutionCode = """
// NoteViewModel.kt
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val notes: StateFlow<List<NoteEntity>> = repository.getAllNotes()
        .combine(_searchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter { it.title.contains(query, true) || it.content.contains(query, true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchChange(newQuery: String) { _searchQuery.value = newQuery }
    fun saveNote(title: String, content: String) = viewModelScope.launch {
        repository.insert(NoteEntity(title = title, content = content))
    }
}
            """.trimIndent()
        ),
        ProjectItem(
            id = "proj_weather",
            title = "Real-Time Weather App",
            category = "Intermediate",
            difficulty = "★★★☆☆",
            duration = "8 Hours",
            summary = "Fetch live forecasts with Retrofit, handle geolocation permissions, and render animated weather cards.",
            techStack = listOf("Kotlin", "Retrofit", "Compose Animations", "Location API", "Moshi"),
            features = listOf(
                "Live weather data by GPS coordinates or city search",
                "Hourly forecasts & 7-day outlook",
                "Dynamic background gradients based on weather conditions",
                "Offline cache with Room when disconnected",
                "Pull-to-refresh mechanism"
            ),
            prerequisites = listOf("Coroutines", "Retrofit & OkHttp", "Accompanist Permissions"),
            milestones = listOf(
                ProjectMilestone("1. Weather API Client", "Set up Retrofit with Moshi converter and error interceptor", true),
                ProjectMilestone("2. Geolocation Service", "Request ACCESS_FINE_LOCATION and fetch coordinates", false),
                ProjectMilestone("3. Animated Weather UI", "Build dynamic rain, cloud, and sun Compose cards", false),
                ProjectMilestone("4. Error & Offline Handling", "Handle no-network scenarios with cached fallback", false)
            ),
            requirementsChecklist = listOf(
                "Network request state (Loading, Success, Error) handled cleanly",
                "GPS permission requested with user rationale dialog",
                "Temperature unit toggle (Celsius / Fahrenheit)",
                "Graceful error card when offline"
            ),
            starterTemplate = """
interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double
    ): WeatherResponse
}
            """.trimIndent(),
            solutionCode = """
sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val currentTemp: Double, val condition: String) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
            """.trimIndent()
        ),
        ProjectItem(
            id = "proj_ecommerce",
            title = "E-Commerce Shopping App",
            category = "Advanced",
            difficulty = "★★★★☆",
            duration = "14 Hours",
            summary = "Full-featured shopping platform with cart state, payment sheet simulation, reviews, and animations.",
            techStack = listOf("Kotlin", "Clean Architecture", "Compose Navigation", "SharedFlow", "Room"),
            features = listOf(
                "Product catalog with category filtering and sorting",
                "Cart management with persistent local state",
                "Checkout flow simulation with address & payment validation",
                "Favorite items wishlist with instant sync",
                "Shared element transitions on product detail click"
            ),
            prerequisites = listOf("Advanced Compose", "Clean Architecture", "State Management"),
            milestones = listOf(
                ProjectMilestone("1. Clean Architecture Setup", "Core domain models, use cases, and repository interfaces", true),
                ProjectMilestone("2. Shopping Cart Engine", "StateFlow reactive cart state calculation (tax, total, discounts)", true),
                ProjectMilestone("3. Responsive Product Grid", "Adaptive LazyVerticalGrid supporting phones & tablets", false),
                ProjectMilestone("4. Checkout Simulation", "Form validation with field error states", false)
            ),
            requirementsChecklist = listOf(
                "Cart count updates dynamically in top app bar",
                "Product detail screen has animated image pager",
                "Empty cart state displays helpful call-to-action button",
                "Unit tests verify cart subtotal and discount calculations"
            ),
            starterTemplate = """
data class CartItem(
    val product: Product,
    val quantity: Int = 1
)
            """.trimIndent(),
            solutionCode = """
class CartManager {
    private val _items = MutableStateFlow<Map<Long, CartItem>>(emptyMap())
    val items = _items.asStateFlow()

    val subtotal: StateFlow<Double> = _items.map { map ->
        map.values.sumOf { it.product.price * it.quantity }
    }.stateIn(CoroutineScope(Dispatchers.Default), SharingStarted.Eagerly, 0.0)
}
            """.trimIndent()
        ),
        ProjectItem(
            id = "proj_capstone",
            title = "Production Capstone: MobileDev Academy Hub",
            category = "Advanced",
            difficulty = "★★★★★",
            duration = "24 Hours",
            summary = "The ultimate test: an end-to-end production mobile application incorporating all 10 core levels.",
            techStack = listOf("Kotlin", "Jetpack Compose", "Clean Architecture", "Room", "Retrofit", "CI/CD"),
            features = listOf(
                "Authentication flow with token refresh and secure storage",
                "Offline-first sync engine with conflict resolution",
                "Modular feature architecture (feature-auth, feature-courses, core-data)",
                "Full accessibility support (TalkBack, font scaling, 48dp targets)",
                "Production release configuration with ProGuard and baseline profiles"
            ),
            prerequisites = listOf("All 10 Learning Levels"),
            milestones = listOf(
                ProjectMilestone("1. System Design Document", "Draft architecture diagram and API contracts", false),
                ProjectMilestone("2. Data & Sync Engine", "Room database with remote sync queue", false),
                ProjectMilestone("3. Feature Implementation", "Compose UI with design tokens and M3 components", false),
                ProjectMilestone("4. Automated Test Suite", "Unit tests, Robolectric tests, and Compose UI tests", false),
                ProjectMilestone("5. Play Store Release Prep", "Signed AAB bundle, privacy policy, and store assets", false)
            ),
            requirementsChecklist = listOf(
                "Zero hardcoded strings (all in strings.xml)",
                "Clean Architecture with Domain, Data, and UI layers",
                "Graceful offline handling and error boundaries",
                "App meets all Google Play Developer Program policies"
            ),
            starterTemplate = """
// Capstone Architecture Blueprint
// :app
// :core:network
// :core:database
// :core:designsystem
// :feature:dashboard
// :feature:profile
            """.trimIndent(),
            solutionCode = """
// Production-grade repository interface
interface AcademyCapstoneRepository {
    fun syncUserData(): Flow<SyncStatus>
    suspend fun submitCapstoneReview(projectUrl: String): Result<Boolean>
}
            """.trimIndent()
        )
    )

    val debuggingChallenges = listOf(
        DebuggingChallenge(
            id = "debug_1",
            title = "The Vanishing State Counter",
            bugType = "State Issue",
            scenario = "A developer built a counter button in Compose, but clicking it does not update the text on the screen, or resets immediately.",
            brokenCode = """
@Composable
fun CounterBuggy() {
    var count = 0 // <-- BUG!
    Button(onClick = { count++ }) {
        Text("Count: ${'$'}count")
    }
}
            """.trimIndent(),
            logcatError = "W/Compose: Recomposition triggered, local variable re-initialized to 0",
            explanation = "In Jetpack Compose, regular local variables are reset to their initial value every time the Composable function recomposes! State must be preserved using 'remember' and wrapped in 'mutableStateOf'.",
            fixedCode = """
@Composable
fun CounterFixed() {
    var count by remember { mutableStateOf(0) }
    Button(onClick = { count++ }) {
        Text("Count: ${'$'}count")
    }
}
            """.trimIndent()
        ),
        DebuggingChallenge(
            id = "debug_2",
            title = "Crashing Network Call on Main Thread",
            bugType = "Crash",
            scenario = "App crashes instantly when the user opens the profile screen with a fatal exception.",
            brokenCode = """
class ProfileViewModel : ViewModel() {
    fun loadProfile() {
        // BUG: Executing network call on main thread!
        val url = URL("https://api.mobiledev.academy/profile")
        val connection = url.openConnection() as HttpURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        println(data)
    }
}
            """.trimIndent(),
            logcatError = "FATAL EXCEPTION: main\nProcess: com.aistudio.mobiledevacademy, PID: 14820\nandroid.os.NetworkOnMainThreadException\n\tat android.os.StrictMode.onNetwork(StrictMode.java:1605)\n\tat java.net.Inet6AddressImpl.lookupHostByName(Inet6AddressImpl.java:318)",
            explanation = "Android forbids network operations on the Main (UI) thread to avoid freezing the display. All network requests must run on Dispatchers.IO via coroutines.",
            fixedCode = """
class ProfileViewModel : ViewModel() {
    fun loadProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://api.mobiledev.academy/profile")
                val connection = url.openConnection() as HttpURLConnection
                val data = connection.inputStream.bufferedReader().readText()
                // Switch back to Main for UI update if needed
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
            """.trimIndent()
        ),
        DebuggingChallenge(
            id = "debug_3",
            title = "Unsafe Null Pointer Access",
            bugType = "NullPointer",
            scenario = "Crash occurs when rendering a user greeting if the backend returns an empty/null name.",
            brokenCode = """
data class User(val id: String, val name: String?)

fun greet(user: User) {
    // BUG: Forced non-null assertion on nullable property
    val uppercase = user.name!!.uppercase()
    println("HELLO, ${'$'}uppercase")
}
            """.trimIndent(),
            logcatError = "FATAL EXCEPTION: main\njava.lang.NullPointerException: user.name must not be null\n\tat com.example.MainActivityKt.greet(MainActivity.kt:42)",
            explanation = "Using the '!!' (double-bang) operator throws a NullPointerException whenever the value is null. In modern Kotlin, use safe calls (?.) and fallback operators (?:).",
            fixedCode = """
data class User(val id: String, val name: String?)

fun greet(user: User) {
    val uppercase = user.name?.uppercase() ?: "DEVELOPER"
    println("HELLO, ${'$'}uppercase")
}
            """.trimIndent()
        )
    )

    val codeChallenges = listOf(
        CodeChallenge(
            id = "chal_1",
            title = "Count Vowels in Kotlin",
            category = "Programming Fundamentals",
            difficulty = "Easy",
            description = "Write a function that counts the number of vowels (a, e, i, o, u, case-insensitive) in a string.",
            starterCode = """
fun countVowels(input: String): Int {
    // Write your code here
    return 0
}

fun main() {
    println(countVowels("MobileDev Academy"))
}
            """.trimIndent(),
            expectedOutput = "7",
            hint = "Use input.count { it.lowercaseChar() in \"aeiou\" }",
            solutionCode = """
fun countVowels(input: String): Int {
    val vowels = setOf('a', 'e', 'i', 'o', 'u')
    return input.count { it.lowercaseChar() in vowels }
}

fun main() {
    println(countVowels("MobileDev Academy")) // 7
}
            """.trimIndent()
        ),
        CodeChallenge(
            id = "chal_2",
            title = "State Hoisting in Compose",
            category = "Jetpack Compose",
            difficulty = "Medium",
            description = "Refactor an internal state Composable into a stateless Composable accepting value and onValueChange callback.",
            starterCode = """
// Refactor this to be stateless:
@Composable
fun SearchInput(query: String, onQueryChange: (String) -> Unit) {
    // Implement stateless TextField
}
            """.trimIndent(),
            expectedOutput = "Stateless Composable obeying unidirectional data flow",
            hint = "Pass query as value and onQueryChange to onValueChange parameter.",
            solutionCode = """
@Composable
fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search lessons...") },
        modifier = modifier.fillMaxWidth()
    )
}
            """.trimIndent()
        )
    )

    val quizzes = listOf(
        QuizQuestion(
            id = "q1",
            courseCategory = "Kotlin",
            question = "What is the difference between 'val' and 'var' in Kotlin?",
            codeSnippet = "val language = \"Kotlin\"\nvar version = 2.0",
            options = listOf(
                "val is read-only (immutable reference); var is mutable and reassignable",
                "val is for primitive numbers; var is for Strings",
                "val runs on the main thread; var runs on the background thread",
                "There is no difference in modern Kotlin"
            ),
            correctIndex = 0,
            explanation = "val declares an immutable reference that cannot be reassigned once initialized. var declares a mutable variable."
        ),
        QuizQuestion(
            id = "q2",
            courseCategory = "Jetpack Compose",
            question = "What happens during a Composable 'Recomposition'?",
            codeSnippet = null,
            options = listOf(
                "The entire Android Activity is restarted from scratch",
                "Compose re-invokes only the composable functions whose inputs or observed state changed to emit updated UI",
                "All views are destroyed and garbage collected",
                "The APK is recompiled by Gradle"
            ),
            correctIndex = 1,
            explanation = "Recomposition is intelligent and targeted: Compose checks which states changed and only re-runs the specific Composables observing those states."
        ),
        QuizQuestion(
            id = "q3",
            courseCategory = "Architecture",
            question = "Which layer should contain Room Database DAOs and Retrofit network interfaces in Clean Architecture?",
            codeSnippet = null,
            options = listOf(
                "UI Layer (Activity / Composable)",
                "Domain Layer (Use Cases)",
                "Data Layer (Repositories and Data Sources)",
                "Application AndroidManifest"
            ),
            correctIndex = 2,
            explanation = "In Clean Architecture, network APIs, databases, and caches belong to the Data Layer, abstracted behind repository interfaces."
        ),
        QuizQuestion(
            id = "q4",
            courseCategory = "Concurrency",
            question = "Which Coroutine Dispatcher should be used for disk I/O and network operations?",
            codeSnippet = "viewModelScope.launch(???) {\n    val user = api.getUser()\n}",
            options = listOf(
                "Dispatchers.Main",
                "Dispatchers.IO",
                "Dispatchers.Default",
                "Dispatchers.Unconfined"
            ),
            correctIndex = 1,
            explanation = "Dispatchers.IO is optimized for disk and network I/O, utilizing an elastic thread pool to prevent blocking the UI."
        )
    )

    val flashcards = listOf(
        Flashcard(
            id = "fc_1",
            category = "Kotlin",
            front = "What is the Elvis operator (?:) in Kotlin?",
            back = "The Elvis operator returns the expression on the left if it is NOT null; otherwise, it returns the expression on the right.\nExample: val name = input ?: \"Guest\"",
            codeExample = "val name = input ?: \"Default\""
        ),
        Flashcard(
            id = "fc_2",
            category = "Jetpack Compose",
            front = "What is the purpose of rememberSaveable in Jetpack Compose?",
            back = "While 'remember' preserves state across recompositions, 'rememberSaveable' also preserves state across Activity destruction and Configuration Changes (like screen rotation) by saving to the Android SavedState bundle.",
            codeExample = "var text by rememberSaveable { mutableStateOf(\"\") }"
        ),
        Flashcard(
            id = "fc_3",
            category = "Architecture",
            front = "What is the difference between StateFlow and SharedFlow?",
            back = "StateFlow represents state: it is a hot flow that always holds a current value and emits the latest state to new collectors (conflated). SharedFlow is for one-time events (like navigation or Snackbars) without holding sticky state.",
            codeExample = "val uiState: StateFlow<UiState> = _uiState.asStateFlow()"
        ),
        Flashcard(
            id = "fc_4",
            category = "Databases",
            front = "What is a DAO in Room?",
            back = "Data Access Object (DAO) is an interface defining methods for database interactions (SELECT, INSERT, UPDATE, DELETE) using SQL queries and returning reactive Kotlin Flows.",
            codeExample = "@Dao interface UserDao { @Query(\"SELECT * FROM users\") fun getAll(): Flow<List<User>> }"
        )
    )

    val developerTools = listOf(
        DeveloperTool("tool_api", "API Tester / Explorer", "Send live simulated GET/POST requests and inspect JSON response headers & body", "Utility", "Http"),
        DeveloperTool("tool_json", "JSON Viewer & Formatter", "Parse, format, expand/collapse, and validate complex JSON payloads", "Utility", "DataArray"),
        DeveloperTool("tool_sql", "Interactive SQL Playground", "Execute SELECT, WHERE, JOIN, and GROUP BY queries on simulated database tables", "Utility", "Storage"),
        DeveloperTool("tool_regex", "Regex Pattern Playground", "Test regular expressions for emails, phone numbers, and tokens with explanation", "Utility", "Rule"),
        DeveloperTool("tool_color", "Mobile Color & Contrast Tool", "Inspect HEX, RGB, HSL values and verify WCAG AA/AAA contrast ratios", "Utility", "Palette"),
        DeveloperTool("tool_spacing", "M3 Spacing Scale Visualizer", "Explore the 4dp, 8dp, 12dp, 16dp, 24dp, 32dp spacing grid system", "Reference", "GridOn"),
        DeveloperTool("tool_icons", "Material Symbols Catalogue", "Search and copy common Android icon identifiers and code snippets", "Reference", "Search"),
        DeveloperTool("tool_markdown", "Markdown Editor & Preview", "Write technical project documentation with real-time formatted preview", "Utility", "Description"),
        DeveloperTool("tool_http", "HTTP Status Reference Guide", "200, 201, 400, 401, 403, 404, 500 status code explanations & handling", "Reference", "Info"),
        DeveloperTool("tool_errors", "Android Error Dictionary", "Common exceptions (NullPointer, NetworkOnMain, IllegalState) and fixes", "Reference", "Warning"),
        DeveloperTool("tool_arch_vis", "Architecture Visualizer", "Interactive tap-through of Clean Architecture (UI -> ViewModel -> UseCase -> Repo)", "Visualizer", "AccountTree"),
        DeveloperTool("tool_lifecycle_vis", "Activity & Composable Lifecycle", "Visual state machine: ON_CREATE -> ON_START -> ON_RESUME -> ON_PAUSE -> ON_STOP", "Visualizer", "ChangeCircle"),
        DeveloperTool("tool_recomp_vis", "Recomposition Visualizer", "See how state changes trigger selective Composable redraws in Compose", "Visualizer", "Refresh"),
        DeveloperTool("tool_coroutine_vis", "Coroutine Timeline Visualizer", "Simulate Task A, Task B, Task C across Main vs IO Dispatchers", "Visualizer", "Timeline")
    )

    val communityPosts = listOf(
        CommunityPost(
            id = "post_1",
            authorName = "Awiskar Acharya",
            authorBadge = "Academy Creator",
            timeAgo = "2h ago",
            title = "Pro-Tip: Always specify keys in LazyColumn items()",
            content = "If your LazyColumn list items are animating, reordering, or being deleted, omitting 'key = { item.id }' forces Compose to redraw everything rather than moving the existing composable node! It's an instant 30% scroll fps boost.",
            tags = listOf("Compose", "Performance", "BestPractices"),
            likesCount = 142,
            repliesCount = 28,
            isLiked = true,
            isBookmarked = true
        ),
        CommunityPost(
            id = "post_2",
            authorName = "DevElena",
            authorBadge = "Intermediate Learner",
            timeAgo = "5h ago",
            title = "Just shipped my first offline-first Room + Compose App!",
            content = "Huge milestone reached today! Completed the Notes & Todo App milestone in MobileDev Academy. Room + StateFlow felt like magic once the mental model clicked.",
            tags = listOf("Milestone", "Room", "Android"),
            likesCount = 89,
            repliesCount = 14
        ),
        CommunityPost(
            id = "post_3",
            authorName = "Marcus_Codes",
            authorBadge = "Student",
            timeAgo = "1d ago",
            title = "How do you handle one-off navigation events from ViewModel?",
            content = "I was using StateFlow for navigation, but rotating the screen re-triggered the route change. Switched to Channel.receiveAsFlow() as taught in the architecture module and problem solved!",
            tags = listOf("Architecture", "Navigation", "Flow"),
            likesCount = 67,
            repliesCount = 19
        )
    )

    val interviewQuestions = listOf(
        "Explain the difference between remember and rememberSaveable in Jetpack Compose.",
        "What is the Repository Pattern and why is it preferred over direct API calls in ViewModels?",
        "What is a Coroutine Scope, and what happens when a ViewModel is cleared?",
        "How does SQLite indexing improve query performance in Room?",
        "Compare Flutter's rendering architecture with Jetpack Compose's compiler approach."
    )
}
