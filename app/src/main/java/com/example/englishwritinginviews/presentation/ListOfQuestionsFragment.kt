package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentListOfQuestionsBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment


class ListOfQuestionsFragment :
    BaseFragment<EmptyViewModel, FragmentListOfQuestionsBinding>(FragmentListOfQuestionsBinding::inflate) {

    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {

    }

    fun createQuestionList(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "Describe your favorite holiday destination and why you like it.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 2,
                text = "Write about a memorable childhood experience and its impact on your life.",
                difficulty = Difficulty.YELLOW,
                isChecked = true
            ),
            Question(
                id = 3,
                text = "Discuss the advantages and disadvantages of living in a big city.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 4,
                text = "Explain the process of preparing your favorite dish.",
                difficulty = Difficulty.GREEN,
                isChecked = true
            ),
            Question(
                id = 5,
                text = "Describe a person who has had a significant influence on your life.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 6,
                text = "Discuss the importance of education in today's world.",
                difficulty = Difficulty.RED,
                isChecked = true
            ),
            Question(
                id = 7,
                text = "Write about a book or movie that has inspired you and explain why.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 8,
                text = "Describe your dream job and what skills you would need for it.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 9,
                text = "Discuss the benefits and drawbacks of social media.",
                difficulty = Difficulty.RED,
                isChecked = true
            ),
            Question(
                id = 10,
                text = "Write about a historical event that you find interesting and its significance.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 11,
                text = "Describe the impact of technology on our daily lives.",
                difficulty = Difficulty.YELLOW,
                isChecked = true
            ),
            Question(
                id = 12,
                text = "Discuss the importance of preserving the environment.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 13,
                text = "Write about a hobby or activity that you enjoy and why it brings you happiness.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 14,
                text = "Describe your ideal vacation and what activities you would do.",
                difficulty = Difficulty.YELLOW,
                isChecked = true
            ),
            Question(
                id = 15,
                text = "Discuss the role of sports in society.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 16,
                text = "Write about a challenging experience you have faced and what you learned from it.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 17,
                text = "Describe a person you admire and explain why they are inspirational.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 18,
                text = "Discuss the impact of globalization on culture and society.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 19,
                text = "Write about a time when you had to make a difficult decision and how you approached it.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 20,
                text = "Describe a historical figure you would like to meet and why.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 21,
                text = "Discuss the pros and cons of online shopping.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 22,
                text = "Write about a time when you had to overcome a fear and how you did it.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 23,
                text = "Describe the impact of music on your life.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 24,
                text = "Discuss the benefits of learning a second language.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 25,
                text = "Write about a place you would like to visit in the future and why.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 26,
                text = "Describe the qualities of a good friend and why they are important.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 27,
                text = "Discuss the role of media in shaping public opinion.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 28,
                text = "Write about a social issue that you are passionate about and propose solutions.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 29,
                text = "Describe the impact of climate change on the environment.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 30,
                text = "Discuss the advantages and disadvantages of studying abroad.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 31,
                text = "Write about a recent news event that caught your attention and its implications.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 32,
                text = "Describe a cultural tradition or festival that is important to you.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 33,
                text = "Discuss the benefits of volunteering and giving back to the community.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 34,
                text = "Write about a time when you had to work as part of a team and what you learned from the experience.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 35,
                text = "Describe your favorite piece of art and what it means to you.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 36,
                text = "Discuss the influence of celebrities on young people.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 37,
                text = "Write about a place from your childhood that holds special memories for you.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 38,
                text = "Describe the impact of social media on interpersonal relationships.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 39,
                text = "Discuss the pros and cons of renewable energy sources.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 40,
                text = "Write about a time when you faced a language barrier and how you overcame it.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 41,
                text = "Describe the importance of time management in achieving success.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 42,
                text = "Discuss the impact of technology on the job market.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 43,
                text = "Write about a person you look up to and why they inspire you.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 44,
                text = "Describe the benefits of regular exercise and a healthy lifestyle.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 45,
                text = "Discuss the role of government in addressing poverty and inequality.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 46,
                text = "Write about a significant scientific discovery and its implications.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 47,
                text = "Describe a time when you had to adapt to a new culture or environment.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            ),
            Question(
                id = 48,
                text = "Discuss the benefits of travel and experiencing different cultures.",
                difficulty = Difficulty.RED,
                isChecked = false
            ),
            Question(
                id = 49,
                text = "Write about a personal goal you have and how you plan to achieve it.",
                difficulty = Difficulty.GREEN,
                isChecked = false
            ),
            Question(
                id = 50,
                text = "Describe the impact of social media on mental health.",
                difficulty = Difficulty.YELLOW,
                isChecked = false
            )
        )
    }

    data class Question(
        val id: Int,
        val text: String?,
        val difficulty: Difficulty,
        val isChecked: Boolean
    )

    enum class Difficulty(val color: Int, val label: String) {
        RED(R.color.red_button, "Hard"),
        YELLOW(R.color.yellow, "Medium"),
        GREEN(R.color.grey_cards, "Easy")
    }


}