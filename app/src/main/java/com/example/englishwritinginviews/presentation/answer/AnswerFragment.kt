package com.example.englishwritinginviews.presentation.answer

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentAnswerBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment :
    BaseFragment<FragmentAnswerBinding>(FragmentAnswerBinding::inflate) {
    private val viewModel: AnswerViewModel by viewModels()

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
            findNavController().navigate(R.id.action_answerFragment_to_listOfQuestionsFragment)
        }

        fetchData(question.answer)
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

            binding.ratingBar.rating = uiState.rating

            // todo handle errors and give to it texts
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