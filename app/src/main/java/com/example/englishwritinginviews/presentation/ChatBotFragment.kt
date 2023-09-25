package com.example.englishwritinginviews.presentation

import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.example.englishwritinginviews.databinding.FragmentChatbotBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment


class ChatBotFragment :
    BaseFragment<EmptyViewModel, FragmentChatbotBinding>(FragmentChatbotBinding::inflate) {
    override val viewModel: EmptyViewModel by viewModels()

    override fun init() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("https://beta.character.ai/chat?char=2T3Xhqf5B_b9Wrn8Bg0FeCYR7BPx2LtJQJJCIB4Qe18")
    }

}
