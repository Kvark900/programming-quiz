

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

class GameResultFragment : Fragment() {
    private val gameViewModel: GameViewModel by activityViewModels()
    private val gameSettingsViewModel: GameSettingsViewModel by activityViewModels()
    private lateinit var resultMessageText: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentGameResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_result, container, false)

        val numberOfQuestions = gameSettingsViewModel.settings.numberOfQuestions.number
        val numOfCorrectAnswers = gameViewModel.numOfCorrectAnswers
        val percent = numOfCorrectAnswers.toDouble() / numberOfQuestions * 100
        resultMessageText = "${numOfCorrectAnswers}/${numberOfQuestions} correct answers"
        setTittle()

        binding.resultMessage.text = resultMessageText
        binding.percentageText.text = "${percent}%"
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameResultFragment_to_gameSettingsFragment)
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
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, resultMessageText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
        return true
    }

    private fun setTittle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_results)
    }

    private fun resetGameData() {
        gameViewModel.numOfCorrectAnswers = 0
        gameViewModel.questionIndex = 0
        gameViewModel.jokerEnabled = true
    }
}
