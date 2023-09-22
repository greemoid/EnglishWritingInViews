package com.example.englishwritinginviews.presentation

import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.databinding.FragmentSettingsBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment


class SettingsFragment :
    BaseFragment<EmptyViewModel, FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {

    }

}