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
import ba.unsa.pmf.databinding.FragmentNumberOfQuestionsBinding

/**
 * A simple [Fragment] subclass.
 */
class NumberOfQuestionsFragment : Fragment() {
    private lateinit var binding: FragmentNumberOfQuestionsBinding
    private val gameSettingViewModel: GameSettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_number_of_questions, container, false)
        setTitle()
        binding.levelNextButton.setOnClickListener{ view: View ->
            initSettings()
            view.findNavController().navigate(R.id.action_numberOfQuestionsFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
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

    private fun initSettings() {
        when (binding.numOfQuestionsRadioGroup?.checkedRadioButtonId) {
            R.id.fiveQuestionsRadioButton -> gameSettingViewModel.settings.numberOfQuestions = GameSettingsViewModel.NumberOfQuestions.FIVE
            R.id.tenQuestionsRadioButton -> gameSettingViewModel.settings.numberOfQuestions = GameSettingsViewModel.NumberOfQuestions.TEN
            R.id.fifteenQuestionsRadioButton -> gameSettingViewModel.settings.numberOfQuestions = GameSettingsViewModel.NumberOfQuestions.FIFTEEN
        }
    }

    private fun setTitle() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_settings)
    }

}
