package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DeveloperTool
import com.example.data.repository.AcademyRepository
import com.example.ui.theme.*
import com.example.ui.viewmodel.AcademyUiState
import com.example.ui.viewmodel.AcademyViewModel
import com.example.ui.viewmodel.AppScreen

@Composable
fun ToolsScreen(
    uiState: AcademyUiState,
    viewModel: AcademyViewModel,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Utility", "Visualizer", "Reference")

    var selectedToolId by remember { mutableStateOf("tool_api") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat, style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BrandCyan,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        val filteredTools = if (selectedCategory == "All") {
            uiState.tools
        } else {
            uiState.tools.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Developer Utilities & Interactive Simulators",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Hands-on tools built to understand networking, database queries, layout systems, and architecture.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Quick Tool Select Horizontal Carousel
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filteredTools.forEach { tool ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (selectedToolId == tool.id) BrandCyan.copy(alpha = 0.2f)
                            else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(
                                1.dp,
                                if (selectedToolId == tool.id) BrandCyan else MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier.clickable { selectedToolId = tool.id }
                        ) {
                            Text(
                                text = tool.title,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (selectedToolId == tool.id) BrandCyan else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            // Interactive Tool Body
            item {
                when (selectedToolId) {
                    "tool_api" -> ApiTesterTool(uiState, viewModel)
                    "tool_sql" -> SqlPlaygroundTool(uiState, viewModel)
                    "tool_regex" -> RegexPlaygroundTool(uiState, viewModel)
                    "tool_color" -> ColorContrastTool()
                    "tool_spacing" -> SpacingScaleTool()
                    "tool_markdown" -> MarkdownEditorTool()
                    "tool_arch_vis" -> ArchitectureVisualizerTool()
                    "tool_lifecycle_vis" -> LifecycleVisualizerTool()
                    "tool_recomp_vis" -> RecompositionVisualizerTool()
                    "tool_coroutine_vis" -> CoroutineVisualizerTool()
                    "tool_http" -> HttpStatusReferenceTool()
                    "tool_errors" -> ErrorDictionaryTool()
                    else -> ApiTesterTool(uiState, viewModel)
                }
            }
        }
    }
}

@Composable
fun ApiTesterTool(
    uiState: AcademyUiState,
    viewModel: AcademyViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "REST API Explorer",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Simulate HTTP requests and inspect status codes, JSON response bodies, and latency.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Method & URL
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var expanded by remember { mutableStateOf(false) }
                Box {
                    Button(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (uiState.apiMethod) {
                                "GET" -> BrandEmerald
                                "POST" -> BrandIndigo
                                "DELETE" -> BrandRose
                                else -> BrandAmber
                            }
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(uiState.apiMethod, style = MaterialTheme.typography.labelSmall)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("GET", "POST", "DELETE").forEach { method ->
                            DropdownMenuItem(
                                text = { Text(method) },
                                onClick = {
                                    viewModel.updateApiMethod(method)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = uiState.apiUrl,
                    onValueChange = { viewModel.updateApiUrl(it) },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { viewModel.sendApiRequest() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                enabled = !uiState.isSendingApi
            ) {
                if (uiState.isSendingApi) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Fetching Data...")
                } else {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Send Request")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Response Box
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Response Payload",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (uiState.apiStatusCode in 200..299) BrandEmerald.copy(alpha = 0.2f) else BrandRose.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "Status: ${uiState.apiStatusCode} OK",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.apiStatusCode in 200..299) BrandEmerald else BrandRose
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CodeBg,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = uiState.apiResponse,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Color(0xFFA5D6FF)
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun SqlPlaygroundTool(
    uiState: AcademyUiState,
    viewModel: AcademyViewModel
) {
    var queryInput by remember { mutableStateOf(uiState.sqlQuery) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Interactive SQL Playground",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Run SELECT, WHERE, and JOIN queries against in-memory mobile tables.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = queryInput,
                onValueChange = { queryInput = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                label = { Text("SQL Query") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.executeSql(queryInput) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandAmber),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Execute SQL", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Query Result Table (${uiState.sqlResult.size} rows)",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                uiState.sqlResult.forEachIndexed { idx, row ->
                    Text(
                        text = row.entries.joinToString(" | ") { "${it.key}: ${it.value}" },
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    if (idx < uiState.sqlResult.size - 1) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun RegexPlaygroundTool(
    uiState: AcademyUiState,
    viewModel: AcademyViewModel
) {
    var pattern by remember { mutableStateOf(uiState.regexPattern) }
    var testText by remember { mutableStateOf(uiState.regexTestInput) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Regex Pattern Tester",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Test email, phone, token, and string expressions with live regex validation.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = pattern,
                onValueChange = {
                    pattern = it
                    viewModel.updateRegex(pattern, testText)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                label = { Text("Regex Pattern") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = testText,
                onValueChange = {
                    testText = it
                    viewModel.updateRegex(pattern, testText)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                label = { Text("Test String Input") }
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (uiState.isRegexMatch) BrandEmerald.copy(alpha = 0.15f) else BrandRose.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (uiState.isRegexMatch) Icons.Default.CheckCircle else Icons.Default.Cancel,
                        contentDescription = null,
                        tint = if (uiState.isRegexMatch) BrandEmerald else BrandRose,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uiState.isRegexMatch) "Pattern MATCHES the test input!" else "Pattern does NOT match.",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (uiState.isRegexMatch) BrandEmerald else BrandRose
                    )
                }
            }
        }
    }
}

@Composable
fun ColorContrastTool() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Mobile Color & WCAG Contrast Checker",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Verify that text and background color combinations pass accessibility standards (WCAG AA >= 4.5:1).",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            val samplePalettes = listOf(
                Triple("Brand Indigo on Dark Surface", Color(0xFF818CF8), DarkSurface),
                Triple("Brand Cyan on Dark Surface", Color(0xFF38BDF8), DarkSurface),
                Triple("Emerald on White", Color(0xFF047857), Color.White),
                Triple("Rose on Dark Surface", Color(0xFFF43F5E), DarkSurface)
            )

            samplePalettes.forEach { (label, textColor, bgColor) ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = bgColor,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = textColor
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BrandEmerald
                        ) {
                            Text(
                                text = "WCAG AAA PASS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpacingScaleTool() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Material 3 8dp Spacing Grid System",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Always build mobile interfaces along multiples of 4dp and 8dp for clean visual rhythm.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            val dpSizes = listOf(4, 8, 12, 16, 24, 32, 48)
            dpSizes.forEach { dpVal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${dpVal}.dp",
                        style = MaterialTheme.typography.labelMedium.copy(fontFamily = FontFamily.Monospace),
                        modifier = Modifier.width(60.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Box(
                        modifier = Modifier
                            .width(dpVal.dp * 3)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(BrandCyan)
                    )
                }
            }
        }
    }
}

@Composable
fun MarkdownEditorTool() {
    var markdown by remember {
        mutableStateOf(
            "# Project Readme\n\n## Architecture\n- Clean Architecture\n- Jetpack Compose M3\n\n```kotlin\nval academy = \"MobileDev Academy\"\n```"
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Markdown Documentation Editor",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = markdown,
                onValueChange = { markdown = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                minLines = 4
            )
        }
    }
}

@Composable
fun ArchitectureVisualizerTool() {
    var activeLayer by remember { mutableIntStateOf(0) }
    val layers = listOf(
        "1. UI Layer (Compose Screens & Composables)" to "Observes StateFlow from ViewModel. Emits declarative UI. Emits user actions (clicks, swipes) upward.",
        "2. ViewModel (State Holder)" to "Handles UI logic, holds UI state, manages coroutines in viewModelScope. Survives screen rotation.",
        "3. Domain Layer (Use Cases)" to "Encapsulates reusable business logic (e.g., ValidatePasswordUseCase, CalculateCartTotalUseCase). Pure Kotlin.",
        "4. Data Layer (Repository)" to "Single source of truth. Coordinates remote API (Retrofit) and local database cache (Room DB).",
        "5. Data Sources (Retrofit & Room)" to "Low-level drivers: HTTP client queries network; SQLite DAO executes disk read/write."
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Clean Architecture Visualizer",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Tap on any layer to understand its specific responsibility in a production mobile app.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            layers.forEachIndexed { index, (layerTitle, layerDesc) ->
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (activeLayer == index) BrandIndigo.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(
                        1.5.dp,
                        if (activeLayer == index) BrandIndigo else Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { activeLayer = index }
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = layerTitle,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (activeLayer == index) BrandIndigoLight else MaterialTheme.colorScheme.onSurface
                        )
                        if (activeLayer == index) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = layerDesc,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LifecycleVisualizerTool() {
    val states = listOf("onCreate()", "onStart()", "onResume()", "onPause()", "onStop()", "onDestroy()")
    var currentLifecycleIndex by remember { mutableIntStateOf(2) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Android Activity Lifecycle State Machine",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                states.forEachIndexed { index, stateName ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (currentLifecycleIndex == index) BrandEmerald else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.clickable { currentLifecycleIndex = index }
                    ) {
                        Text(
                            text = stateName,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (currentLifecycleIndex == index) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecompositionVisualizerTool() {
    var count by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Compose Recomposition Visualizer",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Tap Increment. Notice how only CounterText recomposes; StaticHeader does NOT recompose.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { count++ },
                colors = ButtonDefaults.buttonColors(containerColor = BrandIndigo)
            ) {
                Text("Increment State ($count)")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = BrandEmerald.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Recomposed Node: Text('Value is $count')",
                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                    color = BrandEmerald,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun CoroutineVisualizerTool() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Coroutine Dispatcher Timeline",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Dispatchers.Main (UI) ───[Render Frame]──────[Update State]──>", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = BrandCyan)
            Text("Dispatchers.IO (Disk) ────────[Read Room DB]──────────────────>", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = BrandAmber)
            Text("Dispatchers.Default (CPU) ─────────[Filter 10k Items]──────────>", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = BrandIndigoLight)
        }
    }
}

@Composable
fun HttpStatusReferenceTool() {
    val statuses = listOf(
        "200 OK" to "The request succeeded. Standard response for successful HTTP requests.",
        "201 Created" to "The request succeeded and a new resource was created (e.g. POST note).",
        "400 Bad Request" to "The server could not understand the request due to invalid syntax/parameters.",
        "401 Unauthorized" to "Authentication required. Missing, invalid, or expired bearer token.",
        "403 Forbidden" to "Authenticated, but the user does not have permission to access the resource.",
        "404 Not Found" to "The requested endpoint or resource does not exist.",
        "429 Too Many Requests" to "Rate limiting triggered. Client must throttle requests.",
        "500 Internal Server Error" to "The backend server crashed while processing the request."
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "HTTP Status Code Reference",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            statuses.forEach { (code, desc) ->
                Text(
                    text = code,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = BrandIndigoLight)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ErrorDictionaryTool() {
    val errors = listOf(
        "NullPointerException" to "Calling methods or accessing members on an object that is null. Fix: Use ?. and ?: operators.",
        "NetworkOnMainThreadException" to "Attempting to execute network I/O on the UI thread. Fix: Switch to withContext(Dispatchers.IO).",
        "IllegalStateException" to "A method was invoked at an illegal or inappropriate time in the lifecycle. Fix: Verify lifecycle observer state.",
        "ActivityNotFoundException" to "An Intent target could not be resolved by the OS. Fix: Check Intent action and package declarations."
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Common Android Exceptions Dictionary",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            errors.forEach { (err, fix) ->
                Text(
                    text = err,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = BrandRose)
                )
                Text(
                    text = fix,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
