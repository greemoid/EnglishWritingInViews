package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.api.entities.MistakeApiModel
import com.example.englishwritinginviews.data.api.entities.ai.AIResponse
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.domain.WorkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Flow<WorkResult<MistakeApiModel>>.mapMatchesToMistakes(): Flow<WorkResult<List<Mistake>>> {
    return this.map { workResult ->
        when (workResult) {
            is WorkResult.Success -> {
                val mistakesApiModel = workResult.data
                val mistakes = mistakesApiModel?.let { mapMistakesApiModelToMistakes(it) }
                WorkResult.Success(mistakes)
            }

            is WorkResult.Error -> {
                workResult
            }

            is WorkResult.Loading -> {
                WorkResult.Loading()
            }
        }
    } as Flow<WorkResult<List<Mistake>>>
}

fun Flow<WorkResult<AIResponse>>.mapResponseToString(): Flow<WorkResult<String>> {
    return map { workResult ->
        when (workResult) {
            is WorkResult.Success -> {
                val result = workResult.data?.let { it.choices.joinToString { choice -> choice.message.content } }
                WorkResult.Success(result)
            }

            is WorkResult.Error -> workResult
            is WorkResult.Loading -> WorkResult.Loading()
        }
    } as Flow<WorkResult<String>>
}

/* this.map { workResult ->
        when (workResult) {
            is WorkResult.Success -> {
                val mistakesApiModel = workResult.data
                val mistakes = mistakesApiModel?.let { mapMistakesApiModelToMistakes(it) }
                WorkResult.Success(mistakes)
            }

            is WorkResult.Error -> {
                workResult
            }

            is WorkResult.Loading -> {
                WorkResult.Loading()
            }
        }
    } as Flow<WorkResult<List<Mistake>>>*/


fun mapMistakesApiModelToMistakes(mistakesApiModel: MistakeApiModel): List<Mistake> {
    return mistakesApiModel.matches.map { match ->
        Mistake(
            textOfMistake = match.message,
            startOfMistake = match.offset,
            lengthOfMistake = match.length,
        )
    }
}
