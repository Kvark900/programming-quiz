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

package ba.unsa.pmf.result

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameResultBinding
import ba.unsa.pmf.game.GameViewModel
import ba.unsa.pmf.settings.GameSettingsViewModel
import kotlin.math.log

class GameResultFragment : Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private val gameSettingsViewModel: GameSettingsViewModel by activityViewModels()
    private lateinit var resultMessageText: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_result, container, false)

        val level = gameSettingsViewModel.settings.level.id
        val numberOfQuestions = gameSettingsViewModel.settings.numberOfQuestions.number
        val numOfCorrectAnswers = gameViewModel.numOfCorrectAnswers
        val percent = numOfCorrectAnswers.toDouble() / numberOfQuestions * 100
        resultMessageText = "${numOfCorrectAnswers}/${numberOfQuestions} correct answers"

        binding.resultMessage.text = resultMessageText
        binding.percentageText.text = "${percent}%"
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameResultFragment_to_gameSettingsFragment)
            gameViewModel.numOfCorrectAnswers = 0
            gameViewModel.questionIndex = 0
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, resultMessageText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
        return true
    }


}
