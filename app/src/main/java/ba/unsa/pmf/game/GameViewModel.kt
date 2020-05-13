package ba.unsa.pmf.game

import androidx.lifecycle.ViewModel
import ba.unsa.pmf.settings.GameSettingsViewModel

class GameViewModel : ViewModel() {
    data class Question(
            val text: String,
//            val level: GameSettingsViewModel.Level,
            val answers: Map<String, Boolean>)

    var jokerEnabled: Boolean = true
    var numOfCorrectAnswers = 0
    var questionIndex = 0
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    val questions: MutableList<Question> = mutableListOf(
            Question(text = "Which of these is not a core data type?",
                    answers = mapOf("Lists" to false, "Dictionary" to false, "Tuples" to false, "Class" to true)),
            Question(text = "What data type is the object below ?\nL = [1, 23, ‘hello’, 1]",
                    answers = mapOf("List" to true, "Dictionary" to false, "Tuple" to false, "Array" to false)),
            Question(text = "Which of the following function convert a string to a float in python?",
                    answers = mapOf("int(x, [base])" to false, "long(x, [base])" to false, "float(x)" to true, "str(x)" to false)),
            Question(text = "Which operator is overloaded by the or() function?",
                    answers = mapOf("||" to false, "|" to true, "//" to false, "/" to false)),
            Question(text = "Python distinguishes between these number types:",
                    answers = mapOf("integer" to true, "float" to true, "complex" to true, "bytes" to false)),
            Question(text = "The following types are immutable sequences:",
                    answers = mapOf("Strings" to true, "Tuples" to true, "Bytes" to true, "Lists" to false)),
            Question(text = "There are currently two intrinsic mutable sequence types:",
                    answers = mapOf("Lists" to true, "Byte Arrays" to true, "Tuples" to false, "Strings" to false)),
            Question(text = "What are sets?",
                    answers = mapOf("These represent unordered, finite sets of unique, immutable objects" to true,
                            "These represent ordered, finite sets of unique, immutable objects" to false,
                            "These represent ordered, infinite sets of unique, immutable objects" to false,
                            "These represent unordered, finite sets of unique, mutable objects" to false)),
            Question(text = "There are currently two intrinsic set types:",
                    answers = mapOf("Sets" to true, "Frozen sets" to false, "List" to false, "Dictionaries" to false)),
            Question(text = "What are mappings?",
                    answers = mapOf("These represent finite sets of objects indexed by arbitrary index sets" to true,
                            "These represent infinite sets of objects indexed by arbitrary index sets" to false,
                            "These represent finite lists of objects indexed by arbitrary index lists" to false,
                            "These represent finite tuples of objects indexed by arbitrary index tuples" to false)),
            Question(text = "Which of these are mapping types",
                    answers = mapOf("Dictionaries" to true,
                            "Sets" to false,
                            "Lists" to false,
                            "Tuples" to false)),
            Question(text = "What are callable types",
                    answers = mapOf("These are the types to which the function call operation can be applied" to true,
                            "These are the types to which the function call operation can not be applied" to false,
                            "These are the types to which the database call operation can be applied" to false,
                            "These are the types to which the network call operation can be applied" to false)),
            Question(text = "What are coroutine functions",
                    answers = mapOf("A function or method which is defined using async def is called a coroutine function." to true,
                            "A function or method which is defined using inheritance is called a coroutine function." to false,
                            "A function or method which is defined using database call is called a coroutine function." to false,
                            "A function or method which is defined using network call is called a coroutine function." to false)),
            Question(text = "What is 'pass' statement",
                    answers = mapOf("pass is a null operation — when it is executed, nothing happens" to true,
                            "pass is recursive function call" to false,
                            "pass terminates function execution" to false,
                            "pass terminates loop" to false)),
            Question(text = "What does 'continue' statement",
                    answers = mapOf("It continues with the next cycle of the nearest enclosing loop" to true,
                            "It continues with the next cycle of the nearest function" to false,
                            "It continues function recursion" to false,
                            "It breaks the next cycle of the nearest enclosing loop" to false)),
            Question(text = "What does 'break' statement",
                    answers = mapOf("It terminates the nearest enclosing loop" to true,
                            "It continues the nearest enclosing loop" to false,
                            "It terminates the function recursion" to false,
                            "It terminates the database call" to false))

    )

    fun getCorrectOptions(): Set<String> {
       return currentQuestion.answers.filter {entry -> entry.value }.keys
    }


}
