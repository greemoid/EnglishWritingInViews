package com.example.englishwritinginviews.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment()
/*BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate)*/ {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column {
                    Text(text = "String")
                    Button(onClick = {}) {
                        Text(text = "Button")
                    }
                }
            }
        }
    }
    /*
        override fun init() {
            val viewModel: SettingsViewModel by viewModels()
            binding.btnSignOut.setOnClickListener {
                viewModel.signOut()
            }
    
        }*/


}
