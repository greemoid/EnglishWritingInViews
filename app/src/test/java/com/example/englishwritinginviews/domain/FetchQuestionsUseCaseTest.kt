package com.example.englishwritinginviews.domain

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchQuestionsUseCaseTest {

    private lateinit var mistakesRepository: MistakesRepository
    private lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase

    @Before
    fun setup() {
        mistakesRepository = mockk<MistakesRepository>()
        fetchQuestionsUseCase = FetchQuestionsUseCase(mistakesRepository)
    }


    @Test
    fun `getQuestions - success`() = runBlocking {
        val filterList = setOf("filter1", "filter2")
        val expectedQuestions = listOf(
            QuestionDomain(
                1, "Question 1", "ewrwe r", "werwerw", "sdfsdfs", true, 2423423L, 4.4
            ), QuestionDomain(2, "Question 2", "ewrwe r", "werwerw", "sdfsdfs", true, 2423423L, 4.4)
        )

        // Mock the repository's behavior
        every { mistakesRepository.getQuestions(filterList) } returns flow {
            emit(expectedQuestions)
        }

        // Invoke the use case
        val resultFlow = fetchQuestionsUseCase(filterList)

        // Collect and verify the result
        resultFlow.collect { result ->
            assertEquals(expectedQuestions, result)
        }
    }

    @Test
    fun `getQuestions - empty list`() = runBlocking {
        val filterList = setOf("filter1", "filter2")
        val expectedQuestions = emptyList<QuestionDomain>()

        // Mock the repository's behavior
        every { mistakesRepository.getQuestions(filterList) } returns flow {
            emit(expectedQuestions)
        }

        // Invoke the use case
        val resultFlow = fetchQuestionsUseCase(filterList)

        // Collect and verify the result
        resultFlow.collect { result ->
            assertEquals(expectedQuestions, result)
        }
    }


}
