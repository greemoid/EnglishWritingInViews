package com.example.englishwritinginviews.presentation.account

import com.example.englishwritinginviews.domain.FetchAnsweredQuestionsUseCase
import com.example.englishwritinginviews.domain.QuestionDomain
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.util.Locale
import org.junit.Assert.assertEquals

class AccountViewModelTest {

    private lateinit var viewModel: AccountViewModel
    private lateinit var useCase: FetchAnsweredQuestionsUseCase

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = spyk(AccountViewModel(useCase))
    }

    /*@Test
    fun `getSetOfDates returns set successfully`() {
        val list = listOf(
            QuestionDomain(1, "", "", "", "", false, 18487L, 1.1),
            QuestionDomain(1, "", "", "", "", false, 18489L, 1.1),
            QuestionDomain(1, "", "", "", "", false, 18488L, 1.1)
        )

        val set = setOf(LocalDate.of(2021, 10, 13), LocalDate.of(2021, 10, 15), LocalDate.of(2021, 10, 14))

        every { viewModel["getSetOfDates"](list) } returns set

        val result = viewModel.foo()

        assertEquals(set, result)
    }*/

    /*@Test
    fun `toLocalDate returns LocalDate successfully`() {
        every { viewModel["toLocalDate"](18487L) } returns LocalDate.of(2021, 10, 13)

        val expected = LocalDate.of(2021, 10, 13)
        val result = viewModel.foo()

        assertEquals(expected, result)
    }*/

}