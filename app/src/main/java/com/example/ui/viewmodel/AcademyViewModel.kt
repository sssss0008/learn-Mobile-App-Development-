package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.AcademyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AppScreen {
    data object Home : AppScreen
    data object Learn : AppScreen
    data object Build : AppScreen
    data object Tools : AppScreen
    data object Profile : AppScreen
    data class CourseDetail(val courseId: String) : AppScreen
    data class LessonDetail(val courseId: String, val lessonId: String) : AppScreen
    data class ProjectDetail(val projectId: String) : AppScreen
    data object QuizList : AppScreen
    data class QuizSession(val quizId: String) : AppScreen
    data object Flashcards : AppScreen
    data class ChallengeDetail(val challengeId: String) : AppScreen
    data class DebuggingDetail(val challengeId: String) : AppScreen
    data class ToolDetail(val toolId: String) : AppScreen
    data object AITutor : AppScreen
    data object CareerCenter : AppScreen
    data object MockInterview : AppScreen
    data object ResumeBuilder : AppScreen
    data object Community : AppScreen
    data object GlobalSearch : AppScreen
    data object CreatorInfo : AppScreen
    data object CertificateView : AppScreen
    data object Onboarding : AppScreen
}

enum class NavigationTab {
    HOME, LEARN, BUILD, TOOLS, PROFILE
}

data class AcademyUiState(
    val currentTab: NavigationTab = NavigationTab.HOME,
    val currentScreen: AppScreen = AppScreen.Home,
    val screenStack: List<AppScreen> = listOf(AppScreen.Home),
    val userProfile: LearnerProfile = LearnerProfile(),
    val courses: List<Course> = AcademyRepository.courses,
    val projects: List<ProjectItem> = AcademyRepository.projects,
    val challenges: List<CodeChallenge> = AcademyRepository.codeChallenges,
    val debuggingChallenges: List<DebuggingChallenge> = AcademyRepository.debuggingChallenges,
    val quizzes: List<QuizQuestion> = AcademyRepository.quizzes,
    val flashcards: List<Flashcard> = AcademyRepository.flashcards,
    val tools: List<DeveloperTool> = AcademyRepository.developerTools,
    val communityPosts: List<CommunityPost> = AcademyRepository.communityPosts,
    val notes: List<NoteItem> = listOf(
        NoteItem("note_1", "The State -> UI Mental Model", "Remember: Compose functions execute repeatedly on recomposition. Never write 'var count = 0' inside Composable without 'remember'.", "Yesterday"),
        NoteItem("note_2", "Room Database & SQLite", "Entities are plain data classes with @Entity. Use StateFlow or Flow in DAO to get live database updates.", "2 days ago")
    ),
    val completedLessonIds: Set<String> = setOf("k_lesson_1", "k_lesson_2", "comp_lesson_1", "arch_lesson_1"),
    val bookmarkedPostIds: Set<String> = setOf("post_1"),
    val searchQuery: String = "",
    val searchResults: List<SearchResultItem> = emptyList(),
    // Code Playground State
    val playgroundCode: String = AcademyRepository.playgroundSnippets.first().code,
    val playgroundLanguage: String = "Kotlin",
    val playgroundOutput: String = "Ready to run code...",
    val isRunningCode: Boolean = false,
    // Interactive Tool States
    val apiMethod: String = "GET",
    val apiUrl: String = "https://api.mobiledev.academy/v1/students/me",
    val apiResponse: String = "{\n  \"status\": 200,\n  \"success\": true,\n  \"data\": {\n    \"name\": \"Developer\",\n    \"role\": \"Android Engineer\",\n    \"completedLessons\": 19,\n    \"streak\": 7\n  }\n}",
    val apiStatusCode: Int = 200,
    val isSendingApi: Boolean = false,
    val sqlQuery: String = "SELECT id, name, category, lessons_count FROM courses WHERE category = 'Kotlin';",
    val sqlResult: List<Map<String, String>> = listOf(
        mapOf("id" to "1", "name" to "Kotlin Language Academy", "category" to "Kotlin", "lessons_count" to "18"),
        mapOf("id" to "2", "name" to "Kotlin Coroutines & Flow", "category" to "Kotlin", "lessons_count" to "12")
    ),
    val regexPattern: String = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
    val regexTestInput: String = "awiskar@mobiledev.academy",
    val isRegexMatch: Boolean = true,
    // AI Tutor Chat State
    val aiMessages: List<ChatMessage> = listOf(
        ChatMessage(
            id = "ai_msg_1",
            isFromAi = true,
            text = "Hello Developer! I'm your AI Mobile Mentor at MobileDev Academy. Ask me to explain any Kotlin/Compose concept, analyze code, or debug errors!",
            codeSnippet = null
        )
    ),
    // AI Explainer State
    val aiExplainInput: String = "val result = flow { emit(1) }.map { it * 2 }.stateIn(viewModelScope)",
    val aiExplainOutput: String = "1. What it does: Creates a reactive Cold Flow emitting 1, multiplies by 2, and converts it to a Hot StateFlow.\n2. Key Concept: StateFlow always holds the latest state and survives UI lifecycle events.\n3. Performance: stateIn ensures that collectors don't re-execute upstream flows redundantly.",
    // AI Debugger State
    val aiDebugError: String = "java.lang.NullPointerException: Attempt to invoke virtual method on a null object reference",
    val aiDebugSolution: String = "Cause: Accessing a nullable property with '!!' without checking for null.\nFix: Replace 'object!!' with 'object?.property ?: defaultValue' using Kotlin's safe call & Elvis operator.",
    // Visualizer active step
    val archVisualizerStep: Int = 0,
    val lifecycleStep: Int = 2 // ON_RESUME
)

data class SearchResultItem(
    val title: String,
    val category: String, // Course, Lesson, Project, Tool, Challenge
    val description: String,
    val targetScreen: AppScreen
)

data class ChatMessage(
    val id: String,
    val isFromAi: Boolean,
    val text: String,
    val codeSnippet: String? = null
)

class AcademyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AcademyUiState())
    val uiState: StateFlow<AcademyUiState> = _uiState.asStateFlow()

    fun navigateToTab(tab: NavigationTab) {
        val targetScreen = when (tab) {
            NavigationTab.HOME -> AppScreen.Home
            NavigationTab.LEARN -> AppScreen.Learn
            NavigationTab.BUILD -> AppScreen.Build
            NavigationTab.TOOLS -> AppScreen.Tools
            NavigationTab.PROFILE -> AppScreen.Profile
        }
        _uiState.update {
            it.copy(
                currentTab = tab,
                currentScreen = targetScreen,
                screenStack = listOf(targetScreen)
            )
        }
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.update {
            it.copy(
                currentScreen = screen,
                screenStack = it.screenStack + screen
            )
        }
    }

    fun navigateBack(): Boolean {
        val stack = _uiState.value.screenStack
        if (stack.size > 1) {
            val updatedStack = stack.dropLast(1)
            val previousScreen = updatedStack.last()
            val associatedTab = when (previousScreen) {
                is AppScreen.Home -> NavigationTab.HOME
                is AppScreen.Learn -> NavigationTab.LEARN
                is AppScreen.Build -> NavigationTab.BUILD
                is AppScreen.Tools -> NavigationTab.TOOLS
                is AppScreen.Profile -> NavigationTab.PROFILE
                else -> _uiState.value.currentTab
            }
            _uiState.update {
                it.copy(
                    currentScreen = previousScreen,
                    screenStack = updatedStack,
                    currentTab = associatedTab
                )
            }
            return true
        }
        return false
    }

    fun markLessonCompleted(lessonId: String) {
        _uiState.update { state ->
            val updatedCompleted = state.completedLessonIds + lessonId
            val newDailyGoal = (state.userProfile.dailyGoalCompleted + 1).coerceAtMost(state.userProfile.dailyGoalTarget)
            val updatedProfile = state.userProfile.copy(
                completedLessonsCount = updatedCompleted.size,
                dailyGoalCompleted = newDailyGoal,
                overallProgressPercent = ((updatedCompleted.size.toFloat() / state.userProfile.totalLessonsCount) * 100).toInt()
            )
            state.copy(
                completedLessonIds = updatedCompleted,
                userProfile = updatedProfile
            )
        }
    }

    fun toggleBookmark(postId: String) {
        _uiState.update { state ->
            val updated = if (state.bookmarkedPostIds.contains(postId)) {
                state.bookmarkedPostIds - postId
            } else {
                state.bookmarkedPostIds + postId
            }
            state.copy(bookmarkedPostIds = updated)
        }
    }

    fun addNote(lessonTitle: String, content: String) {
        val newNote = NoteItem(
            id = "note_${System.currentTimeMillis()}",
            lessonTitle = lessonTitle,
            content = content,
            date = "Just now"
        )
        _uiState.update { it.copy(notes = listOf(newNote) + it.notes) }
    }

    // Code Playground Execution Simulation
    fun setPlaygroundCode(code: String) {
        _uiState.update { it.copy(playgroundCode = code) }
    }

    fun runPlaygroundCode() {
        _uiState.update { it.copy(isRunningCode = true, playgroundOutput = "Compiling & executing...") }
        viewModelScope.launch {
            delay(600)
            val code = _uiState.value.playgroundCode
            val output = when {
                code.contains("val cannot be reassigned", true) || (code.contains("val score") && code.contains("score =")) ->
                    "[COMPILATION ERROR] line 3: Val cannot be reassigned.\nHint: Use 'var' for mutable variables."
                code.contains("println") -> {
                    // Extract strings in println
                    val regex = Regex("println\\((.*)\\)")
                    val matches = regex.findAll(code).map { it.groupValues[1].replace("\"", "").replace("\${developer}", "Awiskar Acharya").replace("\${platform}", "MobileDev Academy").replace("\${tagline}", "Learn. Build. Debug. Ship.") }.joinToString("\n")
                    if (matches.isNotBlank()) matches else "Process finished with exit code 0\nOutput:\nHello Mobile Developer!"
                }
                else -> "Program executed successfully with 0 warnings.\nReturn code: 0 (OK)"
            }
            _uiState.update { it.copy(isRunningCode = false, playgroundOutput = output) }
        }
    }

    fun resetPlaygroundCode() {
        val defaultSnip = AcademyRepository.playgroundSnippets.first()
        _uiState.update { it.copy(playgroundCode = defaultSnip.code, playgroundOutput = defaultSnip.expectedOutput) }
    }

    // Interactive API Tester
    fun updateApiMethod(method: String) { _uiState.update { it.copy(apiMethod = method) } }
    fun updateApiUrl(url: String) { _uiState.update { it.copy(apiUrl = url) } }
    fun sendApiRequest() {
        _uiState.update { it.copy(isSendingApi = true) }
        viewModelScope.launch {
            delay(500)
            val response = when (_uiState.value.apiMethod) {
                "GET" -> "{\n  \"status\": 200,\n  \"endpoint\": \"/v1/students/me\",\n  \"data\": {\n    \"id\": \"student_4091\",\n    \"name\": \"Developer\",\n    \"learningPath\": \"Android (Kotlin + Compose)\",\n    \"modulesCompleted\": 19\n  }\n}"
                "POST" -> "{\n  \"status\": 201,\n  \"message\": \"Resource created successfully\",\n  \"id\": \"lesson_eval_9982\"\n}"
                "DELETE" -> "{\n  \"status\": 204,\n  \"message\": \"Resource deleted\"\n}"
                else -> "{\n  \"status\": 200,\n  \"ok\": true\n}"
            }
            _uiState.update {
                it.copy(
                    isSendingApi = false,
                    apiResponse = response,
                    apiStatusCode = if (_uiState.value.apiMethod == "POST") 201 else 200
                )
            }
        }
    }

    // SQL Playground Execution
    fun executeSql(query: String) {
        _uiState.update { state ->
            val result = if (query.contains("courses", true)) {
                listOf(
                    mapOf("id" to "1", "name" to "Kotlin Masterclass", "category" to "Kotlin", "lessons_count" to "18"),
                    mapOf("id" to "2", "name" to "Jetpack Compose", "category" to "Compose", "lessons_count" to "24"),
                    mapOf("id" to "3", "name" to "Android Architecture", "category" to "Architecture", "lessons_count" to "20")
                )
            } else {
                listOf(
                    mapOf("id" to "101", "title" to "Notes App", "difficulty" to "Beginner"),
                    mapOf("id" to "102", "title" to "Weather App", "difficulty" to "Intermediate")
                )
            }
            state.copy(sqlQuery = query, sqlResult = result)
        }
    }

    // Regex Playground Tester
    fun updateRegex(pattern: String, testInput: String) {
        val matches = try {
            val regex = Regex(pattern)
            regex.matches(testInput)
        } catch (e: Exception) {
            false
        }
        _uiState.update { it.copy(regexPattern = pattern, regexTestInput = testInput, isRegexMatch = matches) }
    }

    // AI Coding Tutor
    fun sendAiPrompt(prompt: String) {
        if (prompt.isBlank()) return
        val userMsg = ChatMessage(id = "user_${System.currentTimeMillis()}", isFromAi = false, text = prompt)
        _uiState.update { it.copy(aiMessages = it.aiMessages + userMsg) }

        viewModelScope.launch {
            delay(700)
            val aiResponse = when {
                prompt.contains("compose", true) && prompt.contains("not updating", true) ->
                    "In Jetpack Compose, UI updates when state changes. If your UI isn't updating, check: 1) Did you wrap your variable in 'remember { mutableStateOf(...) }'? 2) If using a ViewModel, are you observing a StateFlow using 'collectAsStateWithLifecycle()'? Mutating a plain variable does NOT trigger recomposition!"
                prompt.contains("viewmodel", true) ->
                    "A ViewModel is a lifecycle-aware state holder designed to preserve UI state across configuration changes (e.g., screen rotation). It should expose immutable StateFlow to the Compose UI and use viewModelScope for coroutines."
                prompt.contains("retrofit", true) ->
                    "Retrofit turns your HTTP API into a Kotlin interface using annotations like @GET, @POST, and @Query. It handles serialization (via Moshi or Kotlinx.serialization) and works seamlessly with suspend functions!"
                prompt.contains("error", true) || prompt.contains("nullpointer", true) ->
                    "NullPointerException occurs when calling methods or accessing properties on an object that is null. Fix it using Kotlin's safe call operator (?.) or provide a default fallback with the Elvis operator (?:)."
                else ->
                    "Great question! In mobile development, mastering the foundational mental models (unidirectional data flow, immutability, thread dispatching) separates senior engineers from beginners. Would you like a code example or an architectural breakdown?"
            }
            val aiMsg = ChatMessage(id = "ai_${System.currentTimeMillis()}", isFromAi = true, text = aiResponse)
            _uiState.update { it.copy(aiMessages = it.aiMessages + aiMsg) }
        }
    }

    // Global Search
    fun search(query: String) {
        _uiState.update { state ->
            val results = mutableListOf<SearchResultItem>()
            if (query.isNotBlank()) {
                state.courses.filter { it.title.contains(query, true) || it.category.contains(query, true) }.forEach {
                    results.add(SearchResultItem(it.title, "Course", it.description, AppScreen.CourseDetail(it.id)))
                }
                state.projects.filter { it.title.contains(query, true) || it.techStack.any { t -> t.contains(query, true) } }.forEach {
                    results.add(SearchResultItem(it.title, "Project", it.summary, AppScreen.ProjectDetail(it.id)))
                }
                state.tools.filter { it.title.contains(query, true) || it.subtitle.contains(query, true) }.forEach {
                    results.add(SearchResultItem(it.title, "Tool", it.subtitle, AppScreen.ToolDetail(it.id)))
                }
                state.challenges.filter { it.title.contains(query, true) }.forEach {
                    results.add(SearchResultItem(it.title, "Challenge", it.description, AppScreen.ChallengeDetail(it.id)))
                }
            }
            state.copy(searchQuery = query, searchResults = results)
        }
    }

    fun completeOnboarding(path: String, experience: String, goal: String) {
        _uiState.update { state ->
            state.copy(
                userProfile = state.userProfile.copy(
                    selectedPath = path,
                    experienceLevel = experience,
                    primaryGoal = goal
                ),
                currentScreen = AppScreen.Home
            )
        }
    }
}
