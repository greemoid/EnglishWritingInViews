package com.example.englishwritinginviews.presentation.question

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentQuestionBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment

class QuestionFragment :
    BaseFragment<FragmentQuestionBinding>(FragmentQuestionBinding::inflate) {

    override fun init() {
        val args: QuestionFragmentArgs by navArgs()
        val question = args.question
        binding.tvQuestion.text = question.question
        binding.etAnswer.setText(question.answer)

        binding.tvCounter.text = resources.getString(R.string.counter, 0)



        binding.btnSubmit.setOnClickListener {
            val bundle = Bundle()
            val answer = binding.etAnswer.text.toString()
            question.answer = answer
            bundle.putSerializable("question", question)

            findNavController().navigate(
                R.id.action_questionFragment_to_answerFragment,
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