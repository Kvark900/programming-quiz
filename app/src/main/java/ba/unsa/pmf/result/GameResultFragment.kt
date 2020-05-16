package ba.unsa.pmf.result

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameResultBinding
import ba.unsa.pmf.game.GameViewModel
import ba.unsa.pmf.settings.GameSettingsViewModel
import kotlin.math.round

class GameResultFragment : Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private val gameSettingsViewModel: GameSettingsViewModel by activityViewModels()
    private lateinit var resultMessageText: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentGameResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_result, container, false)

        setResultsMessages(binding)
        setTittle()
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameResultFragment_to_gameLevelFragment)
            resetGameData()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.results_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.share) return false
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, resultMessageText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
        return true
    }

    private fun setResultsMessages(binding: FragmentGameResultBinding) {
        val numberOfQuestions = gameSettingsViewModel.settings.numberOfQuestions.number
        val numOfCorrectAnswers = gameViewModel.numOfCorrectAnswers
        val percent = (numOfCorrectAnswers.toDouble() / numberOfQuestions * 100).round(2)
        resultMessageText = getString(R.string.num_of_correct_answers, numOfCorrectAnswers, numberOfQuestions)
        binding.resultMessage.text = resultMessageText
        binding.percentageText.text = getString(R.string.percentage, percent)
        if (!gameViewModel.jokerEnabled) binding.jokerMessage.text = getString(R.string.joker_was_used)
    }

    private fun setTittle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_results)
    }

    private fun resetGameData() {
        gameViewModel.numOfCorrectAnswers = 0
        gameViewModel.questionIndex = 0
        gameViewModel.jokerEnabled = true
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}
