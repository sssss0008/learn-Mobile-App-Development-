package com.example.data.model

data class Course(
    val id: String,
    val title: String,
    val category: String, // e.g. "Kotlin", "Android", "Compose", "Flutter", "Cross-Platform"
    val level: String, // "Beginner", "Intermediate", "Advanced"
    val description: String,
    val durationHours: Int,
    val progressPercent: Int,
    val iconName: String,
    val accentColorHex: Long,
    val modules: List<CourseModule> = emptyList()
)

data class CourseModule(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val title: String,
    val durationMinutes: Int,
    val isCompleted: Boolean = false,
    val isLocked: Boolean = false,
    val summary: String,
    // Complete 9-step learning loop: Learn -> See -> Code -> Run -> Practice -> Debug -> Build -> Review -> Apply
    val learnTheory: String,
    val visualConcept: String,
    val sampleCode: String,
    val expectedOutput: String,
    val codeExplanation: String,
    val practiceTask: String,
    val practiceStarterCode: String,
    val practiceSolution: String,
    val debuggingSample: String,
    val debuggingFix: String,
    val miniProjectIdea: String,
    val keyTakeaway: String
)

data class CodePlaygroundSnippet(
    val id: String,
    val title: String,
    val language: String, // Kotlin, Compose, Dart, Swift, TypeScript
    val description: String,
    val code: String,
    val expectedOutput: String
)

data class ProjectItem(
    val id: String,
    val title: String,
    val category: String, // "Beginner", "Intermediate", "Advanced"
    val difficulty: String, // "★☆☆☆☆", "★★★☆☆", "★★★★★"
    val duration: String,
    val summary: String,
    val techStack: List<String>,
    val features: List<String>,
    val prerequisites: List<String>,
    val milestones: List<ProjectMilestone>,
    val requirementsChecklist: List<String>,
    val starterTemplate: String,
    val solutionCode: String,
    val isCompleted: Boolean = false
)

data class ProjectMilestone(
    val title: String,
    val description: String,
    val completed: Boolean = false
)

data class CodeChallenge(
    val id: String,
    val title: String,
    val category: String,
    val difficulty: String, // Easy, Medium, Hard
    val description: String,
    val starterCode: String,
    val expectedOutput: String,
    val hint: String,
    val solutionCode: String,
    val isSolved: Boolean = false
)

data class DebuggingChallenge(
    val id: String,
    val title: String,
    val bugType: String, // "Crash", "State Issue", "NullPointer", "Lifecycle", "Coroutine"
    val scenario: String,
    val brokenCode: String,
    val logcatError: String,
    val explanation: String,
    val fixedCode: String,
    val isFixed: Boolean = false
)

data class QuizQuestion(
    val id: String,
    val courseCategory: String,
    val question: String,
    val codeSnippet: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val type: String = "Multiple Choice" // "Code Output", "Debugging", "Architecture"
)

data class Flashcard(
    val id: String,
    val category: String,
    val front: String,
    val back: String,
    val codeExample: String? = null,
    val mastery: MasteryLevel = MasteryLevel.NEW
)

enum class MasteryLevel {
    NEW, LEARNING, REVIEW, MASTERED
}

data class DeveloperTool(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String, // "Utility", "Visualizer", "Reference"
    val iconKey: String
)

data class CreatorProfile(
    val name: String = "Awiskar Acharya",
    val title: String = "Mobile App Developer | Educator | Creator",
    val tagline: String = "Learn. Build. Debug. Ship.",
    val bio: String = "Dedicated to transforming enthusiastic beginners into industry-ready mobile engineers. With extensive experience in Kotlin, Jetpack Compose, Cross-Platform Architecture, and Clean Production Systems, I teach not merely syntax, but the architectural mental models behind scalable applications.",
    val teachingPhilosophy: String = "Real developers don't just copy code; they build mental models. The loop: Learn -> See -> Code -> Run -> Debug -> Practice -> Build -> Test -> Ship is how engineering mastery is achieved.",
    val linkedInUrl: String = "https://www.linkedin.com/in/awiskaracharya/",
    val totalStudents: String = "48,500+",
    val completedProjectsCount: Int = 120,
    val featuredArticles: List<CreatorArticle> = emptyList()
)

data class CreatorArticle(
    val title: String,
    val readTime: String,
    val category: String,
    val summary: String
)

data class LearnerProfile(
    val name: String = "Developer",
    val roleTitle: String = "Aspiring Mobile Architect",
    val selectedPath: String = "Android Developer (Kotlin + Compose)",
    val experienceLevel: String = "Beginner to Intermediate",
    val primaryGoal: String = "Build Production Mobile Apps",
    val streakDays: Int = 7,
    val isStreakPaused: Boolean = false,
    val overallProgressPercent: Int = 42,
    val completedLessonsCount: Int = 19,
    val totalLessonsCount: Int = 45,
    val completedProjectsCount: Int = 3,
    val solvedChallengesCount: Int = 14,
    val quizAccuracyPercent: Int = 91,
    val dailyGoalCompleted: Int = 1,
    val dailyGoalTarget: Int = 2,
    val skillBreakdown: Map<String, Int> = mapOf(
        "Programming" to 85,
        "Kotlin" to 80,
        "Android" to 70,
        "UI/UX" to 65,
        "Databases" to 55,
        "APIs" to 50,
        "Testing" to 35,
        "Architecture" to 40
    )
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val authorBadge: String,
    val timeAgo: String,
    val title: String,
    val content: String,
    val tags: List<String>,
    val likesCount: Int,
    val repliesCount: Int,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false
)

data class NoteItem(
    val id: String,
    val lessonTitle: String,
    val content: String,
    val date: String
)

data class RoadmapNode(
    val id: String,
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean,
    val isCurrent: Boolean,
    val prerequisites: List<String> = emptyList()
)

data class Certificate(
    val id: String,
    val title: String,
    val learnerName: String,
    val completionPercent: Int = 100,
    val issueDate: String,
    val credentialId: String,
    val instructor: String = "Awiskar Acharya"
)
