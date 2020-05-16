package ba.unsa.pmf.game

import android.app.Application
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import ba.unsa.pmf.R
import ba.unsa.pmf.settings.GameSettingsViewModel.Level

class GameViewModel(application: Application) : AndroidViewModel(application) {
    data class Question(
            val text: Spanned,
            val type: QuestionType,
            val difficult: Level,
            val answers: Map<String, Boolean>)

    enum class QuestionType {
        MULTIPLE_CHOICE,
        OPEN
    }

    var jokerEnabled: Boolean = true
    var numOfCorrectAnswers = 0
    var questionIndex = 0
    lateinit var currentQuestion: Question

    lateinit var questions: MutableList<Question>
    lateinit var answers: MutableList<String>

    private val easyQuestions = mutableListOf<Question>()
    private val mediumQuestions = mutableListOf<Question>()
    private val hardQuestions = mutableListOf<Question>()

    private val questionsRepository = mutableListOf(
            Question(text = getSpanned(R.string.question1),
                    difficult = Level.EASY,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q1a1) to false,
                            getString(R.string.q1a2) to false,
                            getString(R.string.q1a3) to false,
                            getString(R.string.q1a4) to true)),
            Question(text = getSpanned(R.string.question2),
                    difficult = Level.EASY,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q2a1) to true,
                            getString(R.string.q2a2) to false,
                            getString(R.string.q2a3) to false,
                            getString(R.string.q2a4) to false)),
            Question(text = getSpanned(R.string.question3),
                    difficult = Level.EASY,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q3a1) to true,
                            getString(R.string.q3a2) to false,
                            getString(R.string.q3a3) to false,
                            getString(R.string.q3a4) to false)),
            Question(text = getSpanned(R.string.question4),
                    difficult = Level.EASY,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q4a1) to true,
                            getString(R.string.q4a2) to false,
                            getString(R.string.q4a3) to false,
                            getString(R.string.q4a4) to false)),
            Question(text = getSpanned(R.string.question5),
                    difficult = Level.EASY,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q5a1) to true,
                            getString(R.string.q5a2) to false,
                            getString(R.string.q5a3) to false,
                            getString(R.string.q5a4) to false)),
            Question(text = getSpanned(R.string.question6),
                    difficult = Level.EASY,
                    type = QuestionType.OPEN,
                    answers = mapOf(getString(R.string.q6a1) to true)),
            Question(text = getSpanned(R.string.question7),
                    difficult = Level.EASY,
                    type = QuestionType.OPEN,
                    answers = mapOf(getString(R.string.q7a1) to true)),
            Question(text = getSpanned(R.string.question8),
                    difficult = Level.EASY,
                    type = QuestionType.OPEN,
                    answers = mapOf(getString(R.string.q8a1) to true)),
            Question(text = getSpanned(R.string.question9),
                    difficult = Level.MEDIUM,
                    type = QuestionType.OPEN,
                    answers = mapOf(getString(R.string.q9a1) to true,
                            getString(R.string.q9a2) to true)),
            Question(text = getSpanned(R.string.question10),
                    difficult = Level.MEDIUM,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q10a1) to false,
                            getString(R.string.q10a2) to false,
                            getString(R.string.q10a3) to true,
                            getString(R.string.q10a4) to false)),
            Question(text = getSpanned(R.string.question11),
                    difficult = Level.MEDIUM,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q11a1) to true,
                            getString(R.string.q11a2) to true,
                            getString(R.string.q11a3) to true,
                            getString(R.string.q11a4) to false)),
            Question(text = getSpanned(R.string.question12),
                    difficult = Level.MEDIUM,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q12a1) to true,
                            getString(R.string.q12a2) to false,
                            getString(R.string.q12a3) to false,
                            getString(R.string.q12a4) to false)),
            Question(text = getSpanned(R.string.question13),
                    difficult = Level.MEDIUM,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q13a1) to true,
                            getString(R.string.q13a2) to false,
                            getString(R.string.q13a3) to false,
                            getString(R.string.q13a4) to false)),
            Question(text = getSpanned(R.string.question14),
                    difficult = Level.MEDIUM,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q14a1) to true,
                            getString(R.string.q14a2) to false,
                            getString(R.string.q14a3) to false,
                            getString(R.string.q14a4) to false)),
            Question(text = getSpanned(R.string.question15),
                    difficult = Level.HARD,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q15a1) to false,
                            getString(R.string.q15a2) to true,
                            getString(R.string.q15a3) to false,
                            getString(R.string.q15a4) to false)),
            Question(text = getSpanned(R.string.question16),
                    difficult = Level.HARD,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q16a1) to true,
                            getString(R.string.q16a2) to true,
                            getString(R.string.q16a3) to true,
                            getString(R.string.q16a4) to false)),
            Question(text = getSpanned(R.string.question17),
                    difficult = Level.HARD,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q17a1) to true,
                            getString(R.string.q17a2) to true,
                            getString(R.string.q17a3) to false,
                            getString(R.string.q17a4) to false)),
            Question(text = getSpanned(R.string.question18),
                    difficult = Level.HARD,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q18a1) to true,
                            getString(R.string.q18a2) to false,
                            getString(R.string.q18a3) to false,
                            getString(R.string.q18a4) to false)),
            Question(text = getSpanned(R.string.question19),
                    difficult = Level.HARD,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q19a1) to true,
                            getString(R.string.q19a2) to false,
                            getString(R.string.q19a3) to false,
                            getString(R.string.q19a4) to false)),
            Question(text = getSpanned(R.string.question20),
                    difficult = Level.HARD,
                    type = QuestionType.MULTIPLE_CHOICE,
                    answers = mapOf(getString(R.string.q20a1) to true,
                            getString(R.string.q20a2) to false,
                            getString(R.string.q20a3) to false,
                            getString(R.string.q20a4) to false))

    )

    private fun getString(id: Int) = getApplication<Application>().resources.getString(id)

    private fun getSpanned(id: Int) = Html.fromHtml(getString(id), FROM_HTML_MODE_LEGACY)

    private val questionDistribution =
            mapOf(Level.EASY to mapOf(Level.EASY to .50, Level.MEDIUM to .25, Level.HARD to .25),
                    Level.MEDIUM to mapOf(Level.MEDIUM to .50, Level.EASY to .25, Level.HARD to .25),
                    Level.HARD to mapOf(Level.HARD to .50, Level.MEDIUM to .25, Level.EASY to .25)
            )

    fun initQuestions(level: Level, numOfQ: Int) {
        groupQuestions()
        shuffleQuestions()
        distributeQuestions(level, numOfQ)
        questions.shuffle()
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.keys.toMutableList()
        answers.shuffle()
    }

    private fun distributeQuestions(level: Level, numOfQ: Int) {
        val numOfEasy = dist(level, Level.EASY)?.times(numOfQ)?.toInt()?.plus(1)
        val numOfMedium = dist(level, Level.MEDIUM)?.times(numOfQ)?.toInt()?.plus(1)
        val numOfHard = dist(level, Level.HARD)?.times(numOfQ)?.toInt()?.plus(1)

        questions = (easyQuestions.subList(0, numOfEasy!!) +
                    mediumQuestions.subList(0, numOfMedium!!) +
                    hardQuestions.subList(0, numOfHard!!))
                    .toMutableList()
    }

    private fun dist(level: Level, questionLevel: Level): Double? {
        return questionDistribution[level]?.get(questionLevel)
    }

    fun getCorrectOptions(): Set<String> {
        return currentQuestion.answers.filter { entry -> entry.value }.keys
    }

    private fun groupQuestions() {
        questionsRepository.forEach { question: Question ->
            when (question.difficult) {
                Level.EASY -> easyQuestions.add(question)
                Level.MEDIUM -> mediumQuestions.add(question)
                Level.HARD -> hardQuestions.add(question)
            }
        }
    }

    private fun shuffleQuestions() {
        easyQuestions.shuffle()
        mediumQuestions.shuffle()
        hardQuestions.shuffle()
    }
}
