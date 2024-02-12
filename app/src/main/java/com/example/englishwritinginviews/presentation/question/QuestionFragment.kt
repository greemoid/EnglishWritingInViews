package com.example.englishwritinginviews.presentation.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.domain.QuestionDomain
import com.kizitonwose.calendar.compose.HorizontalCalendar

class QuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args by navArgs<QuestionFragmentArgs>()
        val question = args.question
        return ComposeView(requireContext()).apply {
            setContent {
                QuestionScreen(question = question.question, onSubmit = { answer ->
                    val bundle = Bundle().apply {
                        val questionDomain = QuestionDomain(
                            id = args.question.id,
                            question = question.question,
                            answer = answer,
                            difficulty = question.difficulty,
                            color = question.color,
                            isAnswered = question.isAnswered,
                            answeredAt = question.answeredAt,
                            rating = question.rating
                        )
                        putSerializable("question", questionDomain)
                    }
                    findNavController().navigate(
                        R.id.action_questionFragment_to_answerFragment,
                        bundle
                    )
                })

            }
        }
    }

    @Composable
    fun QuestionScreen(question: String, onSubmit: (String) -> Unit) {
        val answerText = remember { mutableStateOf("") }
        val wordCount =
            remember { mutableStateOf(0) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.rules),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))



            TextField(
                value = answerText.value,
                onValueChange = {
                    answerText.value = it
                    wordCount.value = answerText.value.trim().split(Regex("\\s+")).size
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(324.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.hint_writing),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )

            Text(
                text = stringResource(id = R.string.counter, wordCount.value),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSubmit(answerText.value)
                },
                enabled = wordCount.value >= 5,
                modifier = Modifier.fillMaxWidth(),
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = if (wordCount.value >= 5) Color.Red else Color.Gray
                )*/
            ) {
                Text(text = stringResource(id = R.string.submit))
            }
        }
    }


    /*override fun init() {
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

    }*/
}
