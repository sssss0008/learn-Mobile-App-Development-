package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Lesson
import com.example.data.repository.AcademyRepository
import com.example.ui.components.InteractiveCodeEditor
import com.example.ui.theme.*
import com.example.ui.viewmodel.AcademyUiState
import com.example.ui.viewmodel.AcademyViewModel
import com.example.ui.viewmodel.AppScreen

@Composable
fun LessonDetailScreen(
    courseId: String,
    lessonId: String,
    uiState: AcademyUiState,
    viewModel: AcademyViewModel,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val course = uiState.courses.find { it.id == courseId } ?: AcademyRepository.courses.first()
    val lesson: Lesson = course.modules.flatMap { it.lessons }.find { it.id == lessonId }
        ?: course.modules.firstOrNull()?.lessons?.firstOrNull()
        ?: AcademyRepository.courses.first().modules.first().lessons.first()

    var isLessonCompleted by remember { mutableStateOf(uiState.completedLessonIds.contains(lesson.id)) }
    var userNoteText by remember { mutableStateOf("") }
    var showNoteDialog by remember { mutableStateOf(false) }

    // Interactive practice code state inside lesson
    var practiceCode by remember { mutableStateOf(lesson.sampleCode) }
    var practiceOutput by remember { mutableStateOf(lesson.expectedOutput) }
    var isRunningPractice by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Info & Progress
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = course.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandIndigoLight
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${lesson.durationMinutes} mins", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = lesson.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = lesson.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = { showNoteDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.AddComment, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Note", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        // STEP 1: LEARN (Theory)
        item {
            LessonSectionCard(stepNumber = "1", stepTitle = "LEARN: Conceptual Core") {
                Text(
                    text = lesson.learnTheory,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )
            }
        }

        // STEP 2: SEE (Visual Mental Model)
        item {
            LessonSectionCard(stepNumber = "2", stepTitle = "SEE: Visual Architecture Model") {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = CodeBg,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = lesson.visualConcept,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Color(0xFF79C0FF)
                        ),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // STEP 3 & 4: CODE & RUN (Interactive Code Runner)
        item {
            LessonSectionCard(stepNumber = "3 & 4", stepTitle = "CODE & RUN: Interactive Execution") {
                Text(
                    text = lesson.codeExplanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))
                InteractiveCodeEditor(
                    code = practiceCode,
                    onCodeChange = { practiceCode = it },
                    isRunning = isRunningPractice,
                    output = practiceOutput,
                    onRun = {
                        isRunningPractice = true
                        practiceOutput = "Executing in mobile environment..."
                        // In-memory execution simulation
                        practiceOutput = lesson.expectedOutput
                        isRunningPractice = false
                    },
                    onReset = {
                        practiceCode = lesson.sampleCode
                        practiceOutput = lesson.expectedOutput
                    },
                    onExplain = { onNavigate(AppScreen.AITutor) }
                )
            }
        }

        // STEP 5: PRACTICE
        item {
            LessonSectionCard(stepNumber = "5", stepTitle = "PRACTICE: Try It Yourself") {
                Text(
                    text = lesson.practiceTask,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Practice Starter Code:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = BrandCyan
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = lesson.practiceStarterCode,
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // STEP 6: DEBUG (Common Pitfalls & Fix)
        item {
            LessonSectionCard(stepNumber = "6", stepTitle = "DEBUG: Identify & Fix The Bug") {
                Text(
                    text = "Common Beginner Mistake:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = BrandRose
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1E0A0E),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = lesson.debuggingSample,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Color(0xFFFFA198)
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Solution & Fix:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = BrandEmerald
                )
                Text(
                    text = lesson.debuggingFix,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // STEP 7 & 8: BUILD & REVIEW
        item {
            LessonSectionCard(stepNumber = "7 & 8", stepTitle = "BUILD & REVIEW: Key Takeaways") {
                Text(
                    text = "Mini Project Exercise: ${lesson.miniProjectIdea}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = BrandIndigoLight
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = BrandEmerald.copy(alpha = 0.12f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Takeaway: ${lesson.keyTakeaway}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = BrandEmerald,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        // STEP 9: COMPLETE LESSON BUTTON
        item {
            Button(
                onClick = {
                    viewModel.markLessonCompleted(lesson.id)
                    isLessonCompleted = true
                    viewModel.navigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLessonCompleted) BrandEmerald else BrandIndigo
                )
            ) {
                Icon(
                    imageVector = if (isLessonCompleted) Icons.Default.CheckCircle else Icons.Default.DoneAll,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isLessonCompleted) "Lesson Completed ✓" else "Mark Complete & Continue",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showNoteDialog) {
        AlertDialog(
            onDismissRequest = { showNoteDialog = false },
            title = { Text("Add Personal Lesson Note") },
            text = {
                OutlinedTextField(
                    value = userNoteText,
                    onValueChange = { userNoteText = it },
                    label = { Text("What did you learn?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (userNoteText.isNotBlank()) {
                        viewModel.addNote(lesson.title, userNoteText)
                    }
                    showNoteDialog = false
                    userNoteText = ""
                }) {
                    Text("Save Note")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNoteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun LessonSectionCard(
    stepNumber: String,
    stepTitle: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = BrandIndigo.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "STEP $stepNumber",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandIndigoLight
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stepTitle,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}
