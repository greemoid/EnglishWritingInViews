package com.example.englishwritinginviews.data.db

import com.example.englishwritinginviews.data.db.entities.QuestionDbModel
import com.example.englishwritinginviews.data.db.entities.toDomainModel
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.testUtils.createListOfQuestionDB
import com.example.englishwritinginviews.testUtils.createListOfQuestionDomain
import com.example.englishwritinginviews.testUtils.createQuestionDB
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var mockDao: QuestionDao

    @Before
    fun setup() {
        mockDao = mockk()
        localDataSource = LocalDataSource(mockDao)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `getQuestions should return all questions when filterList is empty`() = runBlocking {
        // Arrange
        val filterList = emptySet<String>()
        val mockDbModels = createListOfQuestionDB(true, 1)
        val expectedDomainModels = mockDbModels.map { it.toDomainModel() }
        coEvery { mockDao.getAllQuestions() } returns flow { emit(mockDbModels) }

        // Act
        val result = mutableListOf<QuestionDomain>()
        localDataSource.getQuestions(filterList).collect {
            it.map { model ->
                result.add(model)
            }
        }

        // Assert
        assertEquals(expectedDomainModels, result)
    }

    @Test
    fun `getQuestions should return filtered questions when filterList is not empty`() =
        runBlocking {
            // Arrange
            val filterList = setOf("category1", "category2")
            val mockDbModels = createListOfQuestionDB(true, 1)
            val expectedDomainModels = mockDbModels.map { it.toDomainModel() }
            coEvery { mockDao.getFilteredQuestions(filterList) } returns flow { emit(mockDbModels) }

            // Act
            val result = mutableListOf<QuestionDomain>()
            localDataSource.getQuestions(filterList).collect {
                it.map { model ->
                    result.add(model)
                }
            }

            // Assert
            assertEquals(expectedDomainModels, result)
        }

    @Test
    fun `getAnsweredQuestions should return answered questions`() = runBlocking {
        // Arrange
        val mockDbModels = createListOfQuestionDB(true, 1)
        val expectedDomainModels = mockDbModels.map { it.toDomainModel() }
        coEvery { mockDao.getAnsweredQuestions() } returns flow { emit(mockDbModels) }

        // Act
        val result = mutableListOf<QuestionDomain>()

        localDataSource.getAnsweredQuestions().collect {
            it.map { model ->
                result.add(model)
            }
        }

        // Assert
        assertEquals(expectedDomainModels, result)
    }

    @Test
    fun `updateAnswer should update and return the question with the new values`() = runBlocking {
        // Arrange
        val id = 1
        val answer = "Updated answer"
        val answeredAt = System.currentTimeMillis()
        val rating = 4.5f
        val updatedQuestionDbModel = createQuestionDB(true)
        val expectedDomainModel = updatedQuestionDbModel.toDomainModel()
        coEvery {
            mockDao.updateAndGetAnswer(id, answer, true, answeredAt, rating)
        } returns updatedQuestionDbModel

        // Act
        val result = localDataSource.updateAnswer(id, answer, answeredAt, rating)

        // Assert
        assertEquals(expectedDomainModel, result)
    }

    @Test
    fun `updateAnswer should throw Exception`() {
        val id = 1
        val answer = "Updated answer"
        val answeredAt = System.currentTimeMillis()
        val rating = 4.5f
        val expectedException = Exception()

        coEvery {
            mockDao.updateAnswer(
                id,
                answer,
                true,
                answeredAt,
                rating
            )
        } throws expectedException

        val exception = assertThrows(Exception::class.java) {
            localDataSource.updateAnswer(id, answer, answeredAt, rating)
        }

        assertEquals("Error: Cannot update your answer", exception.message)
    }

    @Test
    fun `safeFlowCall should return value generated by block`() = runBlocking {
        val block = createMockBlock()

        every { block() } returns flowOf(createListOfQuestionDB(true, 1))

        val result = localDataSource.safeFlowCall { block() }
        val list = mutableListOf<QuestionDomain>()
        result.collect {
            it.map { model ->
                list.add(model)
            }
        }
        val expected = createListOfQuestionDomain(true, 1)

        assertEquals(expected, list)
    }


    /*@Test
    fun `safeFlowCall should throw exception`() {
        val block: () -> Flow<List<QuestionDbModel>> = { throw CustomDatabaseException("Error: Cannot make safe call to database") }

        var exception: CustomDatabaseException? = null

        try {
            localDataSource.safeFlowCall(block)
        } catch (e: CustomDatabaseException) {
            exception = e
        }

        assertNotNull(exception)
        assertEquals("Error: Cannot make safe call to database", exception!!.message)
    }*/

    private fun createMockBlock(): () -> Flow<List<QuestionDbModel>> {
        try {
            return mockk()
        } catch (e: Exception) {
            throw Exception("Error: Cannot make safe call to database")
        }
    }
}
