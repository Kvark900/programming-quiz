package ba.unsa.pmf.settings

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameLevelBinding
import ba.unsa.pmf.databinding.FragmentGameSettingsBinding

class GameLevelFragment : Fragment() {
    private lateinit var binding: FragmentGameLevelBinding
    private val gameSettingViewModel: GameSettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_level, container, false)
        setTitle()

        binding.levelNextButton.setOnClickListener { view: View ->
            initSettings()
            view.findNavController().navigate(R.id.action_gameLevelFragment_to_numberOfQuestionsFragment)
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

    private fun setTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_settings)
    }
}
