package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.model.Flashcard
import com.example.data.model.QuizQuestion
import com.example.data.repository.AcademyRepository
import com.example.ui.theme.*
import com.example.ui.viewmodel.AcademyUiState
import com.example.ui.viewmodel.AppScreen

@Composable
fun QuizListScreen(
    uiState: AcademyUiState,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Mobile Engineering Quizzes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Test your mental models across Kotlin, Compose, Clean Architecture, and Coroutines.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(uiState.quizzes) { quiz ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.QuizSession(quiz.id)) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = BrandIndigo.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = quiz.courseCategory,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = quiz.question,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { onNavigate(AppScreen.QuizSession(quiz.id)) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandIndigo),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("Start Quiz", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizSessionScreen(
    quizId: String,
    uiState: AcademyUiState,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val quiz = uiState.quizzes.find { it.id == quizId } ?: AcademyRepository.quizzes.first()
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = BrandIndigo.copy(alpha = 0.15f)
        ) {
            Text(
                text = "${quiz.courseCategory} • Quiz Assessment",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Text(
            text = quiz.question,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
        )

        if (quiz.codeSnippet != null) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CodeBg,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = quiz.codeSnippet,
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color(0xFF79C0FF)),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // Options
        quiz.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOptionIndex == index
            val isCorrect = quiz.correctIndex == index
            val containerColor = when {
                !isSubmitted && isSelected -> BrandIndigo.copy(alpha = 0.2f)
                isSubmitted && isCorrect -> BrandEmerald.copy(alpha = 0.25f)
                isSubmitted && isSelected && !isCorrect -> BrandRose.copy(alpha = 0.25f)
                else -> MaterialTheme.colorScheme.surface
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = containerColor,
                border = BorderStroke(
                    1.dp,
                    if (isSelected) BrandIndigo else MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !isSubmitted) { selectedOptionIndex = index }
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { if (!isSubmitted) selectedOptionIndex = index },
                        colors = RadioButtonDefaults.colors(selectedColor = BrandIndigo)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (isSubmitted) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedOptionIndex == quiz.correctIndex) BrandEmerald.copy(alpha = 0.15f) else BrandRose.copy(alpha = 0.15f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (selectedOptionIndex == quiz.correctIndex) "Correct! +10 XP" else "Incorrect!",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (selectedOptionIndex == quiz.correctIndex) BrandEmerald else BrandRose
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(quiz.explanation, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (!isSubmitted) {
                    isSubmitted = true
                } else {
                    onNavigate(AppScreen.QuizList)
                }
            },
            enabled = selectedOptionIndex != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandIndigo)
        ) {
            Text(if (!isSubmitted) "Submit Answer" else "Next Question")
        }
    }
}

@Composable
fun FlashcardSessionScreen(
    uiState: AcademyUiState,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    val currentCard = uiState.flashcards.getOrNull(currentIndex) ?: AcademyRepository.flashcards.first()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${currentCard.category} Flashcard",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = BrandIndigoLight
            )
            Text(
                text = "Card ${currentIndex + 1} of ${uiState.flashcards.size}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Flippable Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable { isFlipped = !isFlipped },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(1.dp, BrandIndigo.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isFlipped) "EXPLANATION" else "QUESTION (Tap to flip)",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = BrandIndigoLight
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isFlipped) currentCard.back else currentCard.front,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (isFlipped && currentCard.codeExample != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CodeBg,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentCard.codeExample,
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color(0xFF79C0FF)),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Spaced Repetition Rating Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    isFlipped = false
                    currentIndex = (currentIndex + 1) % uiState.flashcards.size
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Again", color = BrandRose)
            }
            OutlinedButton(
                onClick = {
                    isFlipped = false
                    currentIndex = (currentIndex + 1) % uiState.flashcards.size
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Hard", color = BrandAmber)
            }
            Button(
                onClick = {
                    isFlipped = false
                    currentIndex = (currentIndex + 1) % uiState.flashcards.size
                },
                colors = ButtonDefaults.buttonColors(containerColor = BrandEmerald),
                modifier = Modifier.weight(1f)
            ) {
                Text("Mastered")
            }
        }
    }
}
