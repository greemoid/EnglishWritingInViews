package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentMainBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment

class MainFragment :
    BaseFragment<EmptyViewModel, FragmentMainBinding>(FragmentMainBinding::inflate) {

    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listOfQuestionsFragment)
        }
    }

}