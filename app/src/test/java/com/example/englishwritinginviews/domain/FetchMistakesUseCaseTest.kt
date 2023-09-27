package com.example.englishwritinginviews.domain

import com.example.englishwritinginviews.data.MistakesRepository
import com.example.englishwritinginviews.data.WorkResult
import com.example.englishwritinginviews.data.entities.Match
import com.example.englishwritinginviews.data.entities.Mistake
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions

class FetchMistakesUseCaseTest {

    // todo normal mistake class for tests
    @Test
    fun `invoke with non-empty answer should call fetchMistakes and return a Flow of Mistakes`(): Unit =
        runBlocking {
            // Arrange
            val answer = "Sample answer"
            val mistakesRepository = mock(MistakesRepository::class.java)
            val useCase = FetchMistakesUseCase(mistakesRepository)
            val mockMistake = WorkResult.Success(Mistake(listOf(Match(0, "", 0, ""))))
            val mockFlow = flowOf(mockMistake)

            // Mock the behavior of mistakesRepository
            Mockito.`when`(mistakesRepository.fetchMistakes(answer)).thenReturn(mockFlow)

            // Act
            val resultFlow = useCase(answer)

            // Assert
            resultFlow.collect { result ->
                // Assert that the result is the same as the mockMistake
                assertEquals(mockMistake, result)
            }

            // Verify that fetchMistakes was called with the expected answer
            verify(mistakesRepository).fetchMistakes(answer)
        }

    @Test(expected = Exception::class)
    fun `invoke with empty answer should throw an Exception`() = runBlocking {
        // Arrange
        val answer = "" // Empty answer
        val mistakesRepository = mock(MistakesRepository::class.java)
        val useCase = FetchMistakesUseCase(mistakesRepository)

        // Act and Assert
        val actual = useCase(answer)
        assertEquals(Exception("Please write an answer"), actual)

        // Verify that fetchMistakes was not called
        verifyZeroInteractions(mistakesRepository)
    }
}