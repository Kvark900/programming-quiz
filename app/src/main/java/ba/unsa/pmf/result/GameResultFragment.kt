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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameResultBinding
import ba.unsa.pmf.game.GameViewModel
import ba.unsa.pmf.settings.GameSettingsViewModel

class GameResultFragment : Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private val gameSettingsViewModel: GameSettingsViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_result, container, false)

        val level = gameSettingsViewModel.settings.level.id
        val numberOfQuestions = gameSettingsViewModel.settings.numberOfQuestions.number
        val numOfCorrectAnswers = gameViewModel.numOfCorrectAnswers
        val percent = numOfCorrectAnswers.toDouble() / numberOfQuestions * 100

        binding.resultMessage.text = "${numOfCorrectAnswers}/${numberOfQuestions} correct answers"
        binding.percentageText.text = "${percent}%"
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameResultFragment_to_gameSettingsFragment)
        }
        return binding.root
    }
}
