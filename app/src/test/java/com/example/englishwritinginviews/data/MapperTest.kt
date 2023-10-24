package com.example.englishwritinginviews.data

import com.example.englishwritinginviews.data.api.entities.Match
import com.example.englishwritinginviews.data.api.entities.MistakeApiModel
import com.example.englishwritinginviews.domain.Mistake
import com.example.englishwritinginviews.domain.WorkResult
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MapperTest {

    @Test
    fun `mapMatchesToMistakes should map WorkResult correctly`() = runBlocking {
        // Create some sample WorkResults
        val successResult = WorkResult.Success(MistakeApiModel(/* populate with data */))
        val errorResult = WorkResult.Error<MistakeApiModel>("Something went wrong")
        val loadingResult = WorkResult.Loading<MistakeApiModel>()

        // Create a flow with the sample WorkResults
        val inputFlow = flowOf(successResult, errorResult, loadingResult)

        // Apply the mapper to the input flow
        val outputFlow = inputFlow.mapMatchesToMistakes()

        // Collect the results
        val resultList = outputFlow.toList()

        // Assert the mapping results
        assertEquals(3, resultList.size)

        // Verify the first item is a WorkResult.Success with a list of Mistakes
        val firstResult = resultList[0]
        assertTrue(firstResult is WorkResult.Success<List<Mistake>>)
        // You should perform specific assertions on the content of firstResult.data

        // Verify the second item is a WorkResult.Error
        val secondResult = resultList[1]
        assertTrue(secondResult is WorkResult.Error<List<Mistake>>)

        // You can assert the error message or other error-specific properties

        // Verify the third item is a WorkResult.Loading
        val thirdResult = resultList[2]
        assertTrue(thirdResult is WorkResult.Loading<List<Mistake>>)
        // No specific assertions are needed for loading results
    }

    @Test
    fun `mapMistakesApiModelToMistakes should map correctly`() {
        val input = listOf(Match(0, "", 0, ""), Match(0, "", 0, ""), Match(0, "", 0, ""))

        val result = mapMistakesApiModelToMistakes(MistakeApiModel(input))

        val expected = listOf(Mistake("", 0, 0), Mistake("", 0, 0), Mistake("", 0, 0))

        assertEquals(expected, result)
    }
}