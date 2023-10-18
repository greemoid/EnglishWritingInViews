package com.example.englishwritinginviews.presentation.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.englishwritinginviews.databinding.WrittenTextsItemLayoutBinding
import com.example.englishwritinginviews.domain.QuestionDomain
import com.example.englishwritinginviews.presentation.core.setDifficultyColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WrittenTextsAdapter : Adapter<WrittenTextsAdapter.WrittenTextsViewHolder>() {
    inner class WrittenTextsViewHolder(private val binding: WrittenTextsItemLayoutBinding) :
        ViewHolder(binding.root) {
        fun bind(question: QuestionDomain) {
            with(binding) {
                tvDayOfWriting.text = formatTimeMillisToDateString(question.answeredAt)
                tvQuestionInInsights.text = question.question
                tvDifficultyInInsights.text = question.difficulty
                tvDifficultyInInsights.setDifficultyColor(question.color)
                tvTextInInsights.text = question.answer
                ratingBarInInsights.rating = question.rating.toFloat()
            }

            itemView.setOnClickListener {
                onItemClickListener?.let { it(question) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrittenTextsViewHolder {
        val binding = WrittenTextsItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WrittenTextsViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WrittenTextsViewHolder, position: Int) {
        differ.currentList.sortedByDescending { it.answeredAt }
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

    private fun formatTimeMillisToDateString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis

        val currentDate = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())

        if (isSameDay(calendar, currentDate)) {
            return "Today"
        }

        currentDate.add(Calendar.DAY_OF_YEAR, -1)
        if (isSameDay(calendar, currentDate)) {
            return "Yesterday"
        }

        return dateFormat.format(Date(timeMillis))
    }

    private fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }
}