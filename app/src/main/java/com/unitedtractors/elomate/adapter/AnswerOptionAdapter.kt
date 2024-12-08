package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.databinding.ItemPilihanGandaBinding

class AnswerOptionAdapter(
    private val options: List<String?>,
    private val questionId: Int,
    private val selectedAnswers: MutableMap<Int, String>, // Untuk menyimpan status jawaban
    private val onOptionSelected: (String) -> Unit
) : RecyclerView.Adapter<AnswerOptionAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(private val binding: ItemPilihanGandaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: String) {
            binding.radioOption.text = option

            // Cek apakah opsi ini sudah dipilih sebelumnya
            binding.radioOption.isChecked = selectedAnswers[questionId] == option

            binding.radioOption.setOnClickListener {
                selectedAnswers[questionId] = option // Simpan pilihan ke dalam selectedAnswers
                onOptionSelected(option)
                notifyDataSetChanged() // Perbarui tampilan
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemPilihanGandaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        options[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = options.size
}
