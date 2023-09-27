package com.example.englishwritinginviews.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentAnsweringQuestionBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment


class AnsweringQuestionFragment :
    BaseFragment<EmptyViewModel, FragmentAnsweringQuestionBinding>(FragmentAnsweringQuestionBinding::inflate) {
    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {
        val args: AnsweringQuestionFragmentArgs by navArgs()
        binding.tvQuestion.text = args.question.text
        binding.tvCounter.text = resources.getString(R.string.counter, 0)

        binding.btnSubmit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("answer", binding.etAnswer.text.toString())
            findNavController().navigate(
                R.id.action_answeringQuestionFragment_to_answerFragment,
                bundle
            )
        }


        binding.etAnswer.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(editableText: Editable?) {
                    val wordCount = editableText.toString().trim().split(Regex("\\s+")).size
                    with(binding.btnSubmit) {
                        if (wordCount >= 5) {
                            isEnabled = true
                            setBackgroundColor(
                                resources.getColor(
                                    R.color.red_button,
                                    resources.newTheme()
                                )
                            )
                        } else {
                            isEnabled = false
                            setBackgroundColor(
                                resources.getColor(
                                    R.color.grey_cards,
                                    resources.newTheme()
                                )
                            )
                        }
                    }
                    binding.tvCounter.text = resources.getString(R.string.counter, wordCount)
                }
            })

    }
}