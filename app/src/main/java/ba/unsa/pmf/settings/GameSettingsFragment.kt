package ba.unsa.pmf.settings

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameSettingsBinding

/**
 * A simple [Fragment] subclass.
 */
class GameSettingsFragment : Fragment() {
    private lateinit var binding: FragmentGameSettingsBinding
    private val gameSettingViewModel: GameSettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_settings, container, false)
        binding.playButton.setOnClickListener{ view: View ->
            initSettings()
            view.findNavController().navigate(R.id.action_gameSettingsFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun initSettings() {
       gameSettingViewModel.settings = GameSettingsViewModel.Settings(GameSettingsViewModel.Level.EASY, GameSettingsViewModel.NumberOfQuestions.FIVE)

        when (binding.levelRadioGroup.checkedRadioButtonId) {
            R.id.easyLevelRadioButton -> gameSettingViewModel.settings.level = GameSettingsViewModel.Level.EASY
            R.id.mediumLevelRadioButton -> gameSettingViewModel.settings.level = GameSettingsViewModel.Level.MEDIUM
            R.id.hardLevelRadioButton -> gameSettingViewModel.settings.level = GameSettingsViewModel.Level.HARD
        }
        when (binding.numOfQuestionsRadioGroup.checkedRadioButtonId) {
            R.id.fiveQuestionsRadioButton -> gameSettingViewModel.settings.numberOfQuestions = GameSettingsViewModel.NumberOfQuestions.FIVE
            R.id.tenQuestionsRadioButton -> gameSettingViewModel.settings.numberOfQuestions = GameSettingsViewModel.NumberOfQuestions.TEN
            R.id.fifteenQuestionsRadioButton -> gameSettingViewModel.settings.numberOfQuestions = GameSettingsViewModel.NumberOfQuestions.FIFTEEN
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
                requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }
}
