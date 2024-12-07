package com.unitedtractors.elomate.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.request.AnswerSelfAssessmentRequest
import com.unitedtractors.elomate.data.network.response.QuestionItem
import com.unitedtractors.elomate.databinding.ItemQuestionAssessmentBinding

class QuestionAssessmentAdapter(
    private val questions: List<QuestionItem?>?
) : RecyclerView.Adapter<QuestionAssessmentAdapter.QuestionViewHolder>() {

    private val answers: MutableMap<Int, Int> = mutableMapOf()

    fun getAnswers(): List<AnswerSelfAssessmentRequest>? {
        val allAnswered = questions?.all { question ->
            question?.questionId?.let { answers.containsKey(it) } ?: false
        } ?: false

        return if (allAnswered) {
            answers.map { entry ->
                AnswerSelfAssessmentRequest(
                    questionId = entry.key,
                    answerLikert = entry.value.toString()
                )
            }
        } else {
            null
        }
    }

    inner class QuestionViewHolder(private val binding: ItemQuestionAssessmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: QuestionItem) {
            binding.tvQuestion.text = question.questionText

            // Listener jika ingin menambahkan action pada pilihan (RadioGroup)
            binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                val answer = when (checkedId) {
                    binding.radio1.id -> 1
                    binding.radio2.id -> 2
                    binding.radio3.id -> 3
                    binding.radio4.id -> 4
                    binding.radio5.id -> 5
                    else -> 0
                }

                if (answer != 0) {
                    // Simpan jawaban ke map
                    question.questionId?.let { questionId ->
                        answers[questionId] = answer
                        Log.d("QuestionAnswer", "Question ID: $questionId, Answer: $answer")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionAssessmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        questions?.get(position)?.let { question ->
            holder.bind(question)
        }
    }

    override fun getItemCount(): Int = questions?.size ?: 0
}
