package com.unitedtractors.elomate.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.network.response.MentoringResponse
import com.unitedtractors.elomate.databinding.CardMentoringBinding

class UpcomingAdapter(
    private val upcomingList: List<MentoringResponse>,
    private val onUpcomingClick: (Int) -> Unit
) : RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {

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

                if (upcoming.status == "Processing") {
//                    binding.status.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.teal_200))
                    binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_1))
                } else if (upcoming.status == "Need Revision") {
//                    binding.status.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.error_50))
                    binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.error_500))
                } else if (upcoming.status == "Approve") {
//                    binding.status.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.success_50))
                    binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.success_900))
                }

                binding.root.setOnClickListener {
                    upcoming.courseId?.let { it1 -> onUpcomingClick(it1) }
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
        return upcomingList.size
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(upcomingList[position])
    }
}