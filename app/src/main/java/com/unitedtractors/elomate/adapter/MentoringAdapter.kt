package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.databinding.CardMentoringBinding

class MentoringAdapter(
    private val mentoringList: MutableList<MentoringResponse>,
    private val onUpcomingClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<MentoringAdapter.UpcomingViewHolder>() {

    inner class UpcomingViewHolder(private val binding: CardMentoringBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(upcoming: MentoringResponse) {
            binding.tvDate.text = upcoming.tanggalMentoring
            binding.tvTime.text = "${upcoming.jamMulai} - ${upcoming.jamSelesai}"
            binding.tvCourse.text = upcoming.namaCourse
            binding.tvMentor.text = upcoming.namaFasilitator
            binding.tvTopic.text = upcoming.namaTopik
            binding.tvMethod.text = upcoming.metodeMentoring
            binding.tvType.text = upcoming.tipeMentoring
            binding.tvStatus.text = upcoming.status

            if (upcoming.status == "Upcoming") {
                binding.icDelete.visibility = View.VISIBLE
                binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.blue_50)
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_500))
                binding.ivStatus.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.blue_500))
            } else if (upcoming.status == "Need Revision" || upcoming.status == "Missed") {
                binding.icDelete.visibility = View.GONE
                binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.error_50)
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.error_500))
                binding.ivStatus.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.error_500))
            } else if (upcoming.status == "Approve") {
                binding.icDelete.visibility = View.GONE
                binding.status.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.success_50)
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_900))
                binding.ivStatus.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.success_900))
            }

            binding.btnFeedback.setOnClickListener {
                upcoming.mentoringId?.let { it1 -> onUpcomingClick(it1) }
            }

            binding.icDelete.setOnClickListener {
                upcoming.mentoringId?.let { it1 -> onDeleteClick(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val binding = CardMentoringBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpcomingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mentoringList.size
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(mentoringList[position])
    }
}