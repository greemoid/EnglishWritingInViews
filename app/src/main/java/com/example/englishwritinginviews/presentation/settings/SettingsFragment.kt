package com.example.englishwritinginviews.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: SettingsViewModel by viewModels()
        return ComposeView(requireContext()).apply {
            setContent {
                SignOutScreen(
                    modifier = Modifier,
                    viewModel = viewModel,
                    supportFragmentManager = requireActivity().supportFragmentManager
                )
            }
        }
    }
}

@Composable
fun SignOutScreen(
    modifier: Modifier,
    viewModel: SettingsViewModel,
    supportFragmentManager: FragmentManager
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, // Align items to the left by default
            modifier = Modifier
                .fillMaxWidth() // Stretch row to full width, if desired
        ) {
            IconButton(
                onClick = {
                    supportFragmentManager.popBackStack()
                },
                modifier = Modifier.size(40.dp) // Optional sizing for IconButton
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Navigate Back" // More descriptive contentDescription
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Adjust weight for desired spacer size

            Text(
                text = "Settings",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.weight(1f)) // Adjust weight for desired spacer size
        }

        Column(
            modifier = modifier.padding(horizontal = 8.dp)
        ) {
            ButtonItem(
                modifier = modifier,
                buttonIcon = Icons.Default.PlayArrow,
                buttonText = "Settings and other"
            ) {}
            ButtonItem(
                modifier = modifier,
                buttonIcon = Icons.Default.PlayArrow,
                buttonText = "Avatar and other"
            ) {}
            ButtonItem(
                modifier = modifier,
                buttonIcon = Icons.Default.PlayArrow,
                buttonText = "Something like that"
            ) {}
            ButtonItem(
                modifier = modifier,
                buttonIcon = Icons.Default.PlayArrow,
                buttonText = "I like too much"
            ) {}
            ButtonItem(
                modifier = modifier,
                buttonIcon = Icons.Default.PlayArrow,
                buttonText = "Write, write, write"
            ) {}
            ButtonItem(
                modifier = modifier,
                buttonIcon = Icons.Default.PlayArrow,
                buttonText = "Dance, dance, dance"
            ) {}
        }
    }
}

@Composable
fun ButtonItem(
    modifier: Modifier,
    buttonIcon: ImageVector,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Icon(imageVector = buttonIcon, contentDescription = buttonText)
            Text(text = buttonText)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = buttonText
            )
        }
    }
}
