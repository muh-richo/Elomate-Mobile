package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.QuestionResponse
import com.unitedtractors.elomate.databinding.ItemQuestionEssayBinding

class QuestionEssayAdapter(
    private var questions: List<QuestionResponse>
) : RecyclerView.Adapter<QuestionEssayAdapter.QuestionEssayAdapterViewHolder>() {

    inner class QuestionEssayAdapterViewHolder(private val binding: ItemQuestionEssayBinding) :
        RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(question: QuestionResponse) {
                binding.tvQuestionNumber.text = (position + 1).toString()
                binding.tvQuestionText.text = question.questionText
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionEssayAdapterViewHolder {
        val binding = ItemQuestionEssayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionEssayAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionEssayAdapterViewHolder, position: Int) {
        holder.bind(questions[position])
    }
}