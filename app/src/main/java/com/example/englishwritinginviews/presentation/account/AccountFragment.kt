package com.example.englishwritinginviews.presentation.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentAccountBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class AccountFragment :
    BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    companion object {
        private const val SELECT_IMAGE_REQUEST_CODE = -1
    }


    override fun init() {
        val adapter = WrittenTextsAdapter()
        val rv = binding.rvActivity
        rv.adapter = adapter

        binding.ivAvatar.setOnClickListener {
            selectImageFromGallery()
        }

        setAvatarFromCache()
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun saveImageToCacheDirectory(uri: Uri) {
        try {
            val inputStream = context?.contentResolver?.openInputStream(uri)
            val outputStream = FileOutputStream(File(context?.cacheDir, "avatar_image.jpg"))

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setAvatarFromCache() {
        val avatarImageView = binding.ivAvatar
        val avatarImage = File(context?.cacheDir, "avatar_image.jpg")

        if (avatarImage.exists()) {
            val avatarDrawable = Drawable.createFromPath(avatarImage.absolutePath)
            avatarImageView.setImageDrawable(avatarDrawable)
        } else {
            avatarImageView.setImageResource(R.drawable.ic_account)
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == SELECT_IMAGE_REQUEST_CODE && result.resultCode == RESULT_OK) {

                val imageUri = result.data?.data
                if (imageUri != null) {
                    saveImageToCacheDirectory(imageUri)
                    setAvatarFromCache()
                }
            }
        }


}