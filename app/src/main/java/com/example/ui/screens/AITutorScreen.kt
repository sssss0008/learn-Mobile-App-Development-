package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AcademyUiState
import com.example.ui.viewmodel.AcademyViewModel
import com.example.ui.viewmodel.AppScreen

@Composable
fun AITutorScreen(
    uiState: AcademyUiState,
    viewModel: AcademyViewModel,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedAiMode by remember { mutableIntStateOf(0) } // 0: Tutor Chat, 1: Code Explainer, 2: AI Debugger, 3: Project Generator, 4: Code Review
    val modes = listOf("AI Tutor", "Code Explainer", "Debugger", "Project Gen", "Code Review")

    var promptInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedAiMode,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = BrandIndigoLight,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedAiMode]),
                    color = BrandIndigoLight
                )
            }
        ) {
            modes.forEachIndexed { index, title ->
                Tab(
                    selected = selectedAiMode == index,
                    onClick = { selectedAiMode = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                )
            }
        }

        when (selectedAiMode) {
            0 -> {
                // AI Tutor Chat
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Suggested Prompts Chips
                    val suggestions = listOf(
                        "Explain ViewModel",
                        "Why is my Compose UI not updating?",
                        "Explain Coroutine Dispatchers",
                        "How does Retrofit work?"
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        suggestions.forEach { sugg ->
                            SuggestionChip(
                                onClick = {
                                    promptInput = sugg
                                    viewModel.sendAiPrompt(sugg)
                                },
                                label = { Text(sugg, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.aiMessages) { msg ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (msg.isFromAi) Arrangement.Start else Arrangement.End
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (msg.isFromAi) MaterialTheme.colorScheme.surfaceVariant else BrandIndigo,
                                    border = BorderStroke(
                                        1.dp,
                                        if (msg.isFromAi) MaterialTheme.colorScheme.outlineVariant else BrandIndigo
                                    ),
                                    modifier = Modifier.widthIn(max = 320.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = if (msg.isFromAi) "MobileDev AI Mentor" else "You",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = if (msg.isFromAi) BrandCyan else Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = msg.text,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (msg.isFromAi) MaterialTheme.colorScheme.onSurface else Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = promptInput,
                            onValueChange = { promptInput = it },
                            placeholder = { Text("Ask your mobile development tutor...") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = {
                                if (promptInput.isNotBlank()) {
                                    viewModel.sendAiPrompt(promptInput)
                                    promptInput = ""
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(BrandIndigo)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                        }
                    }
                }
            }

            1 -> {
                // AI Code Explainer
                var codeToExplain by remember { mutableStateOf(uiState.aiExplainInput) }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "AI Code Explainer",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Paste any Kotlin, Compose, or Flutter code. The AI will deconstruct it into mental models, mechanics, and best practices.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = codeToExplain,
                            onValueChange = { codeToExplain = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            label = { Text("Source Code") },
                            minLines = 3
                        )
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Educational Breakdown:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(uiState.aiExplainOutput, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            2 -> {
                // AI Debugger
                var errorTrace by remember { mutableStateOf(uiState.aiDebugError) }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "AI Error & Stack Trace Debugger",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Paste your crash stack trace or Logcat error line to get actionable root causes and verified fixes.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = errorTrace,
                            onValueChange = { errorTrace = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            label = { Text("Logcat / Stack Trace") },
                            minLines = 3
                        )
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Root Cause & Investigation Fix:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BrandRose))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(uiState.aiDebugSolution, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            3 -> {
                // AI Project Generator
                var projectPrompt by remember { mutableStateOf("I want to build a fitness workout tracker app with SQLite history.") }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "AI Project Architecture Generator",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Describe your app idea. Receive a structured engineering blueprint with feature milestones and database schema.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = projectPrompt,
                            onValueChange = { projectPrompt = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("App Idea Description") }
                        )
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Generated Engineering Blueprint:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BrandCyan))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("• Target: Android Kotlin + Jetpack Compose\n• Recommended Architecture: MVVM + Clean Architecture\n• Data Layer: Room Database (WorkoutEntity, ExerciseDao)\n• UI Layer: DashboardScreen, ActiveWorkoutScreen, HistoryScreen\n• Estimated Development Time: 12 Hours", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }

            4 -> {
                // AI Code Reviewer
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "AI Code Reviewer",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Automated senior engineer review checking for architecture violations, memory leaks, and performance bottlenecks.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Review Score: 92/100 (Pass)", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BrandEmerald))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("✓ Readability: Clean naming, idiomatic Kotlin\n✓ Immutability: 'val' used correctly throughout\n⚠ Optimization: Consider adding 'key' parameter to LazyColumn items to prevent unnecessary recompositions on list reorder\n✓ Security: No hardcoded secrets or API tokens detected", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
