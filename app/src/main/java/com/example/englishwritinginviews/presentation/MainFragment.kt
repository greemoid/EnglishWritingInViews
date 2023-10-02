package com.example.englishwritinginviews.presentation

import androidx.navigation.fragment.findNavController
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentMainBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    override fun init() {
        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_listOfQuestionsFragment)
        }
    }
}