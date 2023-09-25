package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.databinding.FragmentStrikeBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment

class StrikeFragment :
    BaseFragment<EmptyViewModel, FragmentStrikeBinding>(FragmentStrikeBinding::inflate) {
    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {

    }

}