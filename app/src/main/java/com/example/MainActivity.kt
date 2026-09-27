package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AcademyHeader
import com.example.ui.screens.*
import com.example.ui.theme.BrandIndigoLight
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AcademyViewModel
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.NavigationTab

class MainActivity : ComponentActivity() {

    private val viewModel: AcademyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: AcademyViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isRootTab = uiState.currentScreen is AppScreen.Home ||
            uiState.currentScreen is AppScreen.Learn ||
            uiState.currentScreen is AppScreen.Build ||
            uiState.currentScreen is AppScreen.Tools ||
            uiState.currentScreen is AppScreen.Profile

    BackHandler(enabled = !isRootTab) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (uiState.currentScreen !is AppScreen.Onboarding) {
                AcademyHeader(
                    title = "MobileDev Academy",
                    subtitle = "Learn. Build. Debug. Ship.",
                    canNavigateBack = !isRootTab,
                    onBackClick = { viewModel.navigateBack() },
                    onSearchClick = { viewModel.navigateTo(AppScreen.GlobalSearch) },
                    onAiClick = { viewModel.navigateTo(AppScreen.AITutor) },
                    onCreatorClick = { viewModel.navigateTo(AppScreen.CreatorInfo) }
                )
            }
        },
        bottomBar = {
            if (isRootTab && uiState.currentScreen !is AppScreen.Onboarding) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = BrandIndigoLight
                ) {
                    val tabs = listOf(
                        Triple(NavigationTab.HOME, "Home", Icons.Default.Home),
                        Triple(NavigationTab.LEARN, "Learn", Icons.Default.MenuBook),
                        Triple(NavigationTab.BUILD, "Build", Icons.Default.Code),
                        Triple(NavigationTab.TOOLS, "Tools", Icons.Default.Build),
                        Triple(NavigationTab.PROFILE, "Profile", Icons.Default.Person)
                    )

                    tabs.forEach { (tab, label, icon) ->
                        NavigationBarItem(
                            selected = uiState.currentTab == tab,
                            onClick = { viewModel.navigateToTab(tab) },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)

        when (val screen = uiState.currentScreen) {
            is AppScreen.Home -> HomeScreen(uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.Learn -> LearnScreen(uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.Build -> BuildScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.Tools -> ToolsScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.Profile -> ProfileScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.CourseDetail -> CourseDetailScreen(screen.courseId, uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.LessonDetail -> LessonDetailScreen(screen.courseId, screen.lessonId, uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.ProjectDetail -> ProjectDetailScreen(screen.projectId, uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.QuizList -> QuizListScreen(uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.QuizSession -> QuizSessionScreen(screen.quizId, uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.Flashcards -> FlashcardSessionScreen(uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.ChallengeDetail -> ChallengeDetailScreen(screen.challengeId, uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.DebuggingDetail -> DebuggingDetailScreen(screen.challengeId, uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.ToolDetail -> ToolsScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.AITutor -> AITutorScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.CareerCenter -> CareerCenterScreen(uiState, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.MockInterview -> MockInterviewScreen({ viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.ResumeBuilder -> ResumeBuilderScreen(uiState, screenModifier)
            is AppScreen.CertificateView -> CertificateScreen(uiState, screenModifier)
            is AppScreen.CreatorInfo -> CreatorDetailScreen(screenModifier)
            is AppScreen.Community -> CommunityScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.GlobalSearch -> GlobalSearchScreen(uiState, viewModel, { viewModel.navigateTo(it) }, screenModifier)
            is AppScreen.Onboarding -> OnboardingScreen(viewModel, screenModifier)
        }
    }
}
