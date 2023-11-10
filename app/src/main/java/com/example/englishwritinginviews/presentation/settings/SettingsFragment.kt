package com.example.englishwritinginviews.presentation.settings

import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.databinding.FragmentSettingsBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun init() {
        val viewModel: SettingsViewModel by viewModels()
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
    }


}
