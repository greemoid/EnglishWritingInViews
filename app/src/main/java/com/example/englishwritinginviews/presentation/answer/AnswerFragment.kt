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
        binding.tvAnswer.text = args.answer
        binding.tvQuestionInAnswer.text = args.question
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("answer", binding.tvAnswer.text.toString())
            bundle.putString("question", args.question)
            findNavController().navigate(R.id.action_answerFragment_to_questionFragment, bundle)
        }
        fetchData(args.answer)
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