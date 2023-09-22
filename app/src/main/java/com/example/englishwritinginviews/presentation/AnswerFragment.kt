package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.databinding.FragmentAnswerBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment


class AnswerFragment :
    BaseFragment<EmptyViewModel, FragmentAnswerBinding>(FragmentAnswerBinding::inflate) {
    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {

    }

}