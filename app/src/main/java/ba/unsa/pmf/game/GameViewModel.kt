package ba.unsa.pmf.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    data class Question(
            val text: String,
            val answers: Map<String, Boolean>)

    var numOfCorrectAnswers = 0
    var questionIndex = 0
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    val questions: MutableList<Question> = mutableListOf(
            Question(text = "What is Android Jetpack?",
                    answers = mapOf("All of these" to true, "Tools" to false, "Documentation" to false, "Libraries" to false)),
            Question(text = "What is the base class for layouts?",
                    answers = mapOf("ViewGroup" to true, "ViewSet" to false, "ViewCollection" to false, "ViewRoot" to false)),
            Question(text = "What layout do you use for complex screens?",
                    answers = mapOf("ConstraintLayout" to true, "GridLayout" to false, "LinearLayout" to false, "FrameLayout" to false)),
            Question(text = "What do you use to push structured data into a layout?",
                    answers = mapOf("Data binding" to true, "Data pushing" to false, "Set text" to false, "An OnClick method" to false)),
            Question(text = "What method do you use to inflate layouts in fragments?",
                    answers = mapOf("onCreateView()" to true, "onActivityCreated()" to false, "onCreateLayout()" to false, "onInflateLayout()" to false)),
            Question(text = "What's the build system for Android?",
                    answers = mapOf("Gradle" to true, "Graddle" to false, "Grodle" to false, "Groyle" to false)),
            Question(text = "Which class do you use to create a vector drawable?",
                    answers = mapOf("VectorDrawable" to true, "AndroidVectorDrawable" to false, "DrawableVector" to false, "AndroidVector" to false)),
            Question(text = "Which one of these is an Android navigation component?",
                    answers = mapOf("NavController" to true, "NavCentral" to false, "NavMaster" to false, "NavSwitcher" to false)),
            Question(text = "Which XML element lets you register an activity with the launcher activity?",
                    answers = mapOf("intent-filter" to true, "app-registry" to false, "launcher-registry" to false, "app-launcher" to false)),
            Question(text = "What do you use to mark a layout for data binding?",
                    answers = mapOf("<layout>" to true, "<binding>" to false, "<data-binding>" to false, "<dbinding>" to false))
    )

    fun getCorrectOptions(): Set<String> {
       return currentQuestion.answers.filter {entry -> entry.value }.keys
    }


}
