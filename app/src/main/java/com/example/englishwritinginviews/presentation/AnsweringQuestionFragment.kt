package com.example.englishwritinginviews.presentation

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
            findNavController().navigate(R.id.action_answeringQuestionFragment_to_answerFragment)
        }

        binding.etAnswer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val text = p0.toString()
                val wordCount = text.trim().split(Regex("\\s+")).size
                binding.tvCounter.text = resources.getString(R.string.counter, wordCount)
            }

        })
    }
}