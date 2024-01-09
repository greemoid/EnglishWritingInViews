package com.example.englishwritinginviews.data.db

import com.example.englishwritinginviews.data.BaseLivesHandler
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BaseLivesHandlerTest {


    private lateinit var livesSharedPreferences: LivesSharedPreferences
    private lateinit var baseLivesHandler: BaseLivesHandler

    @Before
    fun setup() {
        livesSharedPreferences = mockk()
        baseLivesHandler = BaseLivesHandler(livesSharedPreferences)
    }

    @Test
    fun `getTheSmallestTimeDiff returns the correct time`() {
        every { livesSharedPreferences.getTheSmallestTime() } returns 1698240186239
        val expected = "1 hours 2 minutes"

        val result = baseLivesHandler.getTheSmallestTimeDiff()

        assertEquals(expected, result)
    }

    @Test
    fun `getAvailableLives returns correct size of set`() {
        every { livesSharedPreferences.getSavedLives() } returns setOf(
            "7100000",
            "7300000",
        )

        val result = baseLivesHandler.getAvailableLives()
        val expected = 2

        assertEquals(expected, result)
    }

    @Test
    fun `isLifeAvailable returns true`() {
        every { baseLivesHandler.getAvailableLives() } returns 2

        val result = baseLivesHandler.isLifeAvailable()
        assertTrue(result)
    }

    @Test
    fun `isLifeAvailable returns false`() {
        every { baseLivesHandler.getAvailableLives() } returns 0

        val result = baseLivesHandler.isLifeAvailable()
        assertFalse(result)
    }


}
