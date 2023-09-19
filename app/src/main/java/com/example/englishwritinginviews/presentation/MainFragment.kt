package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.databinding.FragmentMainBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment

class MainFragment :
    BaseFragment<EmptyViewModel, FragmentMainBinding>(FragmentMainBinding::inflate) {

    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {

    }


}