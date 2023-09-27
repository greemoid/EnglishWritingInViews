package com.example.englishwritinginviews.presentation.answer

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.englishwritinginviews.data.WorkResult
import com.example.englishwritinginviews.databinding.FragmentAnswerBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerFragment :
    BaseFragment<AnswerViewModel, FragmentAnswerBinding>(FragmentAnswerBinding::inflate) {
    override val viewModel: AnswerViewModel by viewModels()

    override fun init() {
        val args: AnswerFragmentArgs by navArgs()
        binding.tvAnswer.text = args.answer
        fetchData(args.answer)
    }

    private fun fetchData(answer: String) {
        viewModel.fetchMistakeResponse(answer)
        viewModel.response.observe(this) { response ->
            when (response) {
                is WorkResult.Success -> {
                    for (i in response.data?.matches!!) {
                        highlightText(
                            message = i.message,
                            offset = i.offset,
                            length = i.length,
                        )
                    }

                    binding.progressBar.visibility = View.INVISIBLE
                    binding.tvAnswer.visibility = View.VISIBLE
                }

                is WorkResult.Error -> {
                    binding.tvMistakes.text = response.message.toString()
                    binding.progressBar.visibility = View.INVISIBLE
                }

                is WorkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun highlightText(message: String, offset: Int, length: Int) {

        val spannableString = SpannableString(binding.tvAnswer.text)
        binding.tvAnswer.movementMethod = LinkMovementMethod.getInstance()

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {

                    Snackbar.make(binding.answerConstraintLayout, message, Snackbar.LENGTH_SHORT)
                        .show()

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