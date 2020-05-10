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

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameBinding
import ba.unsa.pmf.settings.GameSettingsViewModel


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var checkBoxes: List<CheckBox>
    private val gameViewModel: GameViewModel by activityViewModels()
    private val gameSettingViewModel: GameSettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.game = gameViewModel
        initCheckBoxes()
        randomizeQuestions()
        binding.submitButton.setOnClickListener(submitButtonAction)
        setHasOptionsMenu(true)
        return binding.root
    }

    private val submitButtonAction = { view: View ->
        if (answeredCorrectly()) gameViewModel.numOfCorrectAnswers++
        gameViewModel.questionIndex++
        if (gameViewModel.questionIndex < gameSettingViewModel.settings.numberOfQuestions.number) {
            resetCheckboxes()
            setQuestion()
            binding.invalidateAll()
        } else {
            view.findNavController().navigate(R.id.action_gameFragment_to_gameResultFragment)
        }
    }

    private fun initCheckBoxes() {
        checkBoxes = listOf(
                binding.firstAnswerCheckbox,
                binding.secondAnswerCheckbox,
                binding.thirdAnswerCheckbox,
                binding.fourthAnswerCheckbox
        )
    }

    private fun answeredCorrectly(): Boolean {
        return getSelectedAnswers().containsAll(gameViewModel.getCorrectOptions())
    }

    private fun getSelectedAnswers(): HashSet<String> {
        return checkBoxes.filter { checkBox -> checkBox.isChecked }.map { checkBox ->
            checkBox.text.toString()
        }.toHashSet()
    }

    private fun randomizeQuestions() {
        gameViewModel.questions.shuffle()
        gameViewModel.questionIndex = 0
        setQuestion()
    }

    private fun resetCheckboxes() {
        checkBoxes.forEach { checkBox: CheckBox -> checkBox.isChecked = false }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (gameViewModel.jokerEnabled) enableJoker(menu.getItem(0))
        else disableJoker(menu.getItem(0))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!gameViewModel.jokerEnabled) return true
        val intent = Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI)
        startActivity(intent)
        disableJoker(item)
        return true
    }

    private fun disableJoker(item: MenuItem) {
        gameViewModel.jokerEnabled = false
        item.icon.alpha = 130
        item.isEnabled = false
    }

    private fun enableJoker(item: MenuItem) {
        gameViewModel.jokerEnabled = true
        item.icon.alpha = 255
        item.isEnabled = true
    }
}
