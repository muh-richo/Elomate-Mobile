package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R

class AnswerOptionAdapter(
    private val options: List<String?>,
    private val onOptionSelected: (String) -> Unit
) : RecyclerView.Adapter<AnswerOptionAdapter.OptionViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val radioButton: RadioButton = itemView.findViewById(R.id.radio_option)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: String, position: Int) {
            radioButton.text = option
            radioButton.isChecked = position == selectedPosition

            radioButton.setOnClickListener {
                selectedPosition = bindingAdapterPosition
                onOptionSelected(option)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pilihan_ganda, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        options[position]?.let { holder.bind(it, position) }
    }

    override fun getItemCount(): Int = options.size
}