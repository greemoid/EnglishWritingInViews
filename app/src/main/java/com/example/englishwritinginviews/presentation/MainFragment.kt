package com.example.englishwritinginviews.presentation

import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentMainBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainFragmentViewModel by viewModels()
    override fun init() {
        navigateIfSignedIn()

        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val result = account.getResult(ApiException::class.java)
                    val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                    viewModel.signInGoogle(credentials)
                    navigateIfSignedIn()
                } catch (it: ApiException) {
                    Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnLoginGoogle.setOnClickListener {
            val googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            val intent = GoogleSignIn.getClient(requireActivity(), googleSignInOptions).signInIntent
            launcher.launch(intent)
        }


        binding.btnLoginGuest.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.signInAnonymously()
                    viewModel.signInState.collect {
                        if (it.isLoading) {
                            binding.progressBarLogIn.visibility = View.VISIBLE
                        } else {
                            binding.progressBarLogIn.visibility = View.INVISIBLE
                        }
                        if (it.isSuccess?.isNotEmpty() == true) {
                            findNavController().navigate(R.id.action_mainFragment_to_listOfQuestionsFragment)
                        }
                        if (it.isError.isNotEmpty()) {
                            binding.tvLearEnglish.text = it.isError
                        }

                    }
                }
            }
        }
    }


    private fun navigateIfSignedIn() {
        if (viewModel.currentUser != null)
            findNavController().navigate(R.id.action_mainFragment_to_listOfQuestionsFragment)

    }

}
