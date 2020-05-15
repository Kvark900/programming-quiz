package ba.unsa.pmf.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import ba.unsa.pmf.R
import ba.unsa.pmf.databinding.FragmentGameBinding
import ba.unsa.pmf.settings.GameSettingsViewModel
import kotlinx.android.synthetic.main.fragment_game.view.*


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var checkBoxes: List<CheckBox>
    private val gameVM: GameViewModel by activityViewModels()
    private val gameSettingVM: GameSettingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.game = gameVM
        setTitle()
        initCheckBoxes()
        gameVM.initQuestions(gameSettingVM.settings.level, gameSettingVM.settings.numberOfQuestions.number)
        renderQuestion()
        binding.submitButton.setOnClickListener(submitButtonAction)
        setHasOptionsMenu(true)
        return binding.root
    }

    private val submitButtonAction = { view: View ->
        if (answeredCorrectly()) gameVM.numOfCorrectAnswers++
        gameVM.questionIndex++
        if (gameVM.questionIndex < gameSettingVM.settings.numberOfQuestions.number) {
            resetAnswers()
            setQuestion()
            renderQuestion()
            setTitle()
            binding.invalidateAll()
            view?.hideKeyboard()
        } else {
            view.findNavController().navigate(R.id.action_gameFragment_to_gameResultFragment)
            view?.hideKeyboard()
        }
    }

    private fun answeredCorrectly(): Boolean {
        return when (gameVM.currentQuestion.type) {
            GameViewModel.QuestionType.OPEN -> wroteCorrectly()
            else -> chosenCorretly()
        }
    }

    private fun wroteCorrectly(): Boolean {
        val currQuestion = gameVM.currentQuestion
        if (currQuestion.type != GameViewModel.QuestionType.OPEN) return false
        val answer: String? = binding.textAnswer?.text?.toString()?.trim()
        return currQuestion.answers.keys.find { s -> s.trim().equals(answer, true) } != null
    }

    private fun chosenCorretly(): Boolean {
        return (getSelectedAnswers().size == gameVM.getCorrectOptions().size &&
                (getSelectedAnswers() - gameVM.getCorrectOptions()).isEmpty())
    }

    private fun initCheckBoxes() {
        checkBoxes = listOf(
                binding.firstAnswerCheckbox,
                binding.secondAnswerCheckbox,
                binding.thirdAnswerCheckbox,
                binding.fourthAnswerCheckbox
        )
    }

    private fun renderQuestion() {
        val isOpen = gameVM.currentQuestion.type == GameViewModel.QuestionType.OPEN
        binding.checkboxGroup.visibility  = if (isOpen) GONE else VISIBLE
        binding.textAnswer?.visibility = if (isOpen) VISIBLE else GONE
        val params = binding.submitButton.layoutParams as ConstraintLayout.LayoutParams
        params.topToBottom = if (isOpen) binding.textAnswer?.id!! else binding.checkboxGroup.id
    }

    private fun getSelectedAnswers(): HashSet<String> {
        return checkBoxes.filter { checkBox -> checkBox.isChecked }.map { checkBox ->
            checkBox.text.toString()
        }.toHashSet()
    }

    private fun resetAnswers() {
        checkBoxes.forEach { checkBox: CheckBox -> checkBox.isChecked = false }
        binding.textAnswer?.text?.clear()
    }

    private fun setQuestion() {
        gameVM.currentQuestion = gameVM.questions[gameVM.questionIndex]
        gameVM.answers = gameVM.currentQuestion.answers.keys.toMutableList()
        gameVM.answers.shuffle()
        setTitle()
    }

    private fun setTitle() {
        (activity as AppCompatActivity).supportActionBar?.title =
                getString(R.string.title_programming_question,
                        gameVM.questionIndex + 1,
                        gameSettingVM.settings.numberOfQuestions.number)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (gameVM.jokerEnabled) enableJoker(menu.findItem(R.id.jokerButton))
        else disableJoker(menu.findItem(R.id.jokerButton))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.jokerButton) return false
        if (!gameVM.jokerEnabled) return false
        val intent = Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI)
        startActivity(intent)
        disableJoker(item)
        return true
    }

    private fun disableJoker(item: MenuItem) {
        if (item.itemId != R.id.jokerButton) return
        gameVM.jokerEnabled = false
        item.icon.alpha = 130
        item.isEnabled = false
    }

    private fun enableJoker(item: MenuItem) {
        if (item.itemId != R.id.jokerButton) return
        gameVM.jokerEnabled = true
        item.icon.alpha = 255
        item.isEnabled = true
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
