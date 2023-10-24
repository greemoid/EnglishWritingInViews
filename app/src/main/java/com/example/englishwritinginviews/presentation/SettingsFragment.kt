package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentSettingsBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {


    override fun init() {
        val viewModel: SettingsViewModel by viewModels()
        binding.button.setOnClickListener {
            viewModel.signOut()
            findNavController().navigate(R.id.action_settingsFragment_to_mainFragment)
        }
    }
}