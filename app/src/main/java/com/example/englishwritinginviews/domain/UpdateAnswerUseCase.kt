package com.example.englishwritinginviews.domain


class UpdateAnswerUseCase(private val repository: MistakesRepository) {
    operator fun invoke(id: Int, answer: String, answeredAt: Long): QuestionDomain =
        repository.updateAnswer(id, answer, answeredAt)
}
