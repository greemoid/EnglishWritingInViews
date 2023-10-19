package com.example.englishwritinginviews.presentation

import android.app.Activity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentSettingsBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class SettingsFragment :
    BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

        private lateinit var mAuth: FirebaseAuth

    override fun init() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
            mAuth = FirebaseAuth.getInstance()
//            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }


    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    account.idToken?.let { firebaseAuthWithGoogle(it) }
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    binding.button.text = "error"
                }
            }


        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, /*accessToken=*/ null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    Toast.makeText(requireActivity(), "Authentication Success.", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(requireActivity(), "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}