package com.example.englishwritinginviews.presentation.answer

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentAnswerBinding
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment :
    BaseFragment<FragmentAnswerBinding>(FragmentAnswerBinding::inflate) {
    private val viewModel: AnswerViewModel by viewModels()
    private var rating: Float = 0.0f

    override fun init() {
        val args: AnswerFragmentArgs by navArgs()
        val question = args.question
        binding.tvQuestionInAnswer.text = question.question
        binding.tvAnswer.text = question.answer
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("question", question)
            findNavController().navigate(R.id.action_answerFragment_to_questionFragment, bundle)
        }

        binding.btnDone.setOnClickListener {
            viewModel.update(question.id, question.answer, rating)
            findNavController().navigate(R.id.action_answerFragment_to_listOfQuestionsFragment)
        }

        binding.btnAi.setOnClickListener {
            openBottomSheetAi(question.answer)
        }

        fetchData(question.answer)
    }

    private fun openBottomSheetAi(answer: String) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_ai_answer, null)
        val tv = view.findViewById<TextView>(R.id.tv_response)

        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        viewModel.fetchAIResponse(answer)

        // todo ai ui-state
        viewModel.aiState.observe(viewLifecycleOwner) { state ->
            tv.setText(state.response)
            if (state.isError) tv.setTextColor(Color.RED)
            if (state.isLoading) tv.setText("Loading...")
        }
    }

    private fun fetchData(answer: String) {
        viewModel.fetchMistakeResponse(answer)

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            for (i in uiState.mistakes) {
                highlightText(
                    message = i.textOfMistake,
                    offset = i.startOfMistake,
                    length = i.lengthOfMistake
                )
            }

            rating = uiState.rating

            binding.ratingBar.rating = rating

            if (uiState.isError) {
                binding.tvAnswer.text = uiState.answer
                binding.tvAnswer.setTextColor(
                    resources.getColor(
                        R.color.red_button,
                        context?.theme
                    )
                )
            }

            if (uiState.isLoading) {
                binding.tvAnswer.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.tvAnswer.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }


    private fun highlightText(message: String, offset: Int, length: Int) {

        val spannableString = SpannableString(binding.tvAnswer.text)
        binding.tvAnswer.movementMethod = LinkMovementMethod.getInstance()

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {

                    val snackBar =
                        Snackbar.make(binding.answerConstraintLayout, message, Snackbar.LENGTH_LONG)
                    with(snackBar) {
                        setActionTextColor(
                            resources.getColor(
                                R.color.red_button,
                                resources.newTheme()
                            )
                        )
                        setTextColor(resources.getColor(R.color.white_text, resources.newTheme()))
                        setBackgroundTint(resources.getColor(R.color.grey_cards, resources.newTheme()))
                        setAction("Close") {
                            dismiss()
                        }
                        show()
                    }
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                    ds.bgColor = Color.argb(100, 255, 255, 0)
                    ds.linkColor = binding.tvAnswer.currentTextColor
                }
            },
            offset,
            offset + length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvAnswer.text = spannableString
    }


}
