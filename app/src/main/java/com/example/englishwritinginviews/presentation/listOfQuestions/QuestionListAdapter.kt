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
import com.example.englishwritinginviews.domain.QuestionDomain

class QuestionListAdapter :
    RecyclerView.Adapter<QuestionListAdapter.QuestionListViewHolder>() {
    inner class QuestionListViewHolder(private val binding: QuestionItemLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(question: QuestionDomain) {
            with(binding) {
                questionTextView.text = question.question
                difficultyTextView.text = question.difficulty
                /*val color = Color.parseColor(question.color)
                difficultyTextView.setTextColor(color)*/

                //todo think about colors in DB
                val color = when (question.color) {
                    "RED" -> "#FF0000"
                    "YELLOW" -> "#ffff00"
                    "GREEN" -> "#00ff00"
                    else -> "#FFFFFF"
                }
                difficultyTextView.setTextColor(Color.parseColor(color))
                if (question.isAnswered) {
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

    private val differCallback = object : DiffUtil.ItemCallback<QuestionDomain>() {
        override fun areItemsTheSame(oldItem: QuestionDomain, newItem: QuestionDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuestionDomain, newItem: QuestionDomain): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun setOnItemClickListener(listener: (QuestionDomain) -> Unit) {
        onItemClickListener = listener
    }

    private var onItemClickListener: ((QuestionDomain) -> Unit)? = null
}