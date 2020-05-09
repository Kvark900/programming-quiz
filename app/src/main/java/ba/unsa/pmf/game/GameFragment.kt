/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ba.unsa.pmf.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameBinding
import ba.unsa.pmf.settings.GameSettingsViewModel


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val gameViewModel: GameViewModel by activityViewModels()
    private val gameSettingViewModel: GameSettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.game = gameViewModel
        randomizeQuestions()
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            if (answeredCorrectly()) gameViewModel.numOfCorrectAnswers++
            gameViewModel.questionIndex++
            if (gameViewModel.questionIndex < gameSettingViewModel.settings.numberOfQuestions.number) {
                setQuestion()
                binding.invalidateAll()
            } else {
                view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
            }
        }
        return binding.root
    }

    private fun answeredCorrectly(): Boolean {
        return getSelectedAnswers().containsAll(gameViewModel.getCorrectOptions())
    }

    private fun getSelectedAnswers(): HashSet<String> {
        val selectedAnswers = HashSet<String>()
        if (binding.firstAnswerCheckbox.isChecked)
            selectedAnswers.add(binding.firstAnswerCheckbox.text.toString())
        if (binding.secondAnswerCheckbox.isChecked)
            selectedAnswers.add(binding.secondAnswerCheckbox.text.toString())
        if (binding.thirdAnswerCheckbox.isChecked)
            selectedAnswers.add(binding.thirdAnswerCheckbox.text.toString())
        if (binding.fourthAnswerCheckbox.isChecked)
            selectedAnswers.add(binding.fourthAnswerCheckbox.text.toString())
        return selectedAnswers
    }

    private fun randomizeQuestions() {
        gameViewModel.questions.shuffle()
        gameViewModel.questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        gameViewModel.currentQuestion = gameViewModel.questions[gameViewModel.questionIndex]
        gameViewModel.answers = gameViewModel.currentQuestion.answers.keys.toMutableList()
        gameViewModel.answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.title_android_trivia_question,
                        gameViewModel.questionIndex + 1,
                        gameSettingViewModel.settings.numberOfQuestions.number)
    }
}
