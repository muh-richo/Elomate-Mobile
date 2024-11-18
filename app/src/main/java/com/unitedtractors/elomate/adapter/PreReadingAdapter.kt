package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.PreActivityResponse
import com.unitedtractors.elomate.data.network.response.PreReadingResponse
import com.unitedtractors.elomate.databinding.CardAssignmentBinding
import com.unitedtractors.elomate.databinding.CardMateriBinding

class PreReadingAdapter(
    private val preReadingList: List<PreReadingResponse>,
    private val onPreReadingClick: (Int) -> Unit
) : RecyclerView.Adapter<PreReadingAdapter.PreReadingViewHolder>() {

    inner class PreReadingViewHolder(private val binding: CardMateriBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(assignment: PreReadingResponse) {
            binding.tvTitleMateri.text = assignment.titleMateri

            // Set click listener
            binding.root.setOnClickListener {
                assignment.materiId?.let { it1 -> onPreReadingClick(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreReadingViewHolder {
        val binding: CardMateriBinding = CardMateriBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PreReadingViewHolder(binding)
    }

    override fun getItemCount(): Int = preReadingList.size

    override fun onBindViewHolder(holder: PreReadingViewHolder, position: Int) {
        holder.bind(preReadingList[position])
    }
}