package com.example.englishwritinginviews.presentation.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwritinginviews.databinding.WrittenTextsItemLayoutBinding

class WrittenTextsAdapter : Adapter<WrittenTextsAdapter.WrittenTextsViewHolder>() {
    inner class WrittenTextsViewHolder(binding: WrittenTextsItemLayoutBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrittenTextsViewHolder {
        val binding = WrittenTextsItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WrittenTextsViewHolder(binding)
    }

    override fun getItemCount(): Int = 50

    override fun onBindViewHolder(holder: WrittenTextsViewHolder, position: Int) {

    }
}