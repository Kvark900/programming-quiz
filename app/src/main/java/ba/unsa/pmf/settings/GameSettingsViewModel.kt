package ba.unsa.pmf.settings

import androidx.lifecycle.ViewModel

class GameSettingsViewModel : ViewModel() {
    enum class Level(val id: Int) {
        EASY(1), MEDIUM(2), HARD(3);
    }

    enum class NumberOfQuestions(val number: Int) {
        FIVE(5), TEN(10), FIFTEEN(15);
    }

    data class Settings(var level: Level, var numberOfQuestions: NumberOfQuestions)

    lateinit var settings: Settings

}
