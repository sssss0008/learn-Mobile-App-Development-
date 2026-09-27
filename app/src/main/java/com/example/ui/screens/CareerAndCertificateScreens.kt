package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.AcademyRepository
import com.example.ui.theme.*
import com.example.ui.viewmodel.AcademyUiState
import com.example.ui.viewmodel.AppScreen

@Composable
fun CareerCenterScreen(
    uiState: AcademyUiState,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Mobile Developer Career Center",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Prepare for technical interviews, build your engineering portfolio, and polish your mobile resume.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.MockInterview) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, BrandIndigo.copy(alpha = 0.4f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(BrandIndigo.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = BrandIndigoLight, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Mock Technical Interview", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Interactive simulator asking real Android & architecture questions with instant feedback.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        item {
            Text(
                text = "Top Technical Interview Questions",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(AcademyRepository.interviewQuestions) { question ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.HelpOutline, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(question, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

@Composable
fun MockInterviewScreen(
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var userResponse by remember { mutableStateOf("") }
    var isAnswerSubmitted by remember { mutableStateOf(false) }

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
                text = "Lead Android Interviewer Simulation",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Question 1 of 5:",
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Explain the difference between remember and rememberSaveable in Jetpack Compose, and provide a scenario where omitting rememberSaveable causes a bug.",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        OutlinedTextField(
            value = userResponse,
            onValueChange = { userResponse = it },
            placeholder = { Text("Type your architectural explanation...") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            minLines = 4
        )

        if (isAnswerSubmitted) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BrandEmerald.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Interviewer Evaluation Feedback:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = BrandEmerald))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "• Key concepts covered: Configuration changes, Activity recreation, SavedState bundle.\n• Missing nuance: rememberSaveable supports custom Saver objects for non-serializable classes.\n• Score: 8.5/10 (Strong pass)",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Button(
            onClick = { isAnswerSubmitted = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandIndigo),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(if (!isAnswerSubmitted) "Submit Answer for Review" else "Next Interview Question")
        }
    }
}

@Composable
fun ResumeBuilderScreen(
    uiState: AcademyUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Mobile Developer Resume Blueprint",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Industry-standard resume outline showcasing your Academy projects, Kotlin skills, and architecture mastery.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(uiState.userProfile.name, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                    Text("Android & Mobile Engineer • ${uiState.userProfile.selectedPath}", style = MaterialTheme.typography.bodySmall, color = BrandCyan)

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("TECHNICAL SKILLS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight))
                    Text("Kotlin, Jetpack Compose, Coroutines, Flow, Room DB, Retrofit, MVVM, Clean Architecture, JUnit, Robolectric, Git, CI/CD", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("FEATURED ACADEMY PROJECTS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight))
                    Text("• Modern Notes & Todo: Offline-first Room persistence, StateFlow reactive search\n• Real-Time Weather App: Geolocation APIs, Retrofit Moshi serialization\n• Capstone Production Hub: Modular clean architecture, 100% Compose UI", style = MaterialTheme.typography.bodySmall)

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("EDUCATION & CERTIFICATIONS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight))
                    Text("MobileDev Academy • Verified Kotlin Android Certification\nInstructor: Awiskar Acharya", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun CertificateScreen(
    uiState: AcademyUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(2.dp, Brush.linearGradient(listOf(BrandIndigo, BrandCyan, BrandEmerald))),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(BrandIndigo, BrandCyan))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "MOBILEDEV ACADEMY",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = BrandCyan
                    )
                )
                Text(
                    text = "Learn. Build. Debug. Ship.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "CERTIFICATE OF COMPLETION",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "This is officially awarded to",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = uiState.userProfile.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrandIndigoLight
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "for successfully completing the comprehensive curriculum in\nKotlin Android Application Engineering & Architecture",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("Awiskar Acharya", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        Text("Lead Educator & Creator", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("ID: MDA-2026-9812", style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace))
                        Text("Verified Credential", style = MaterialTheme.typography.labelSmall, color = BrandEmerald)
                    }
                }
            }
        }
    }
}
