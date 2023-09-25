package com.example.englishwritinginviews.presentation.listOfQuestions

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.QuestionItemLayoutBinding
import com.example.englishwritinginviews.presentation.core.Question

class QuestionListAdapter :
    RecyclerView.Adapter<QuestionListAdapter.QuestionListViewHolder>() {
    inner class QuestionListViewHolder(private val binding: QuestionItemLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(question: Question) {
            with(binding) {
                questionTextView.text = question.text
                difficultyTextView.text = question.difficulty.label
                val color = Color.parseColor(question.difficulty.color)
                difficultyTextView.setTextColor(color)
                if (question.isChecked) {
                    questionImageView.setImageResource(R.drawable.ic_checked)
                } else {
                    questionImageView.setImageResource(R.drawable.ic_unchecked)
                }

            }
            itemView.setOnClickListener {
                onItemClickListener?.let { it(question) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionListViewHolder {
        val binding =
            QuestionItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionListViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: QuestionListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private val differCallback = object : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun setOnItemClickListener(listener: (Question) -> Unit) {
        onItemClickListener = listener
    }

    private var onItemClickListener: ((Question) -> Unit)? = null
}