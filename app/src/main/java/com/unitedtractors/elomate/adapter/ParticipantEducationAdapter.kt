package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.EducationResponse
import com.unitedtractors.elomate.databinding.CardEducationBinding

class ParticipantEducationAdapter(
    private var education: List<EducationResponse>,
) : RecyclerView.Adapter<ParticipantEducationAdapter.ParticipantEducationViewHolder>() {

    inner class ParticipantEducationViewHolder(private val binding: CardEducationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(education: EducationResponse, isLast: Boolean) {
            if (isLast) {
                binding.line.visibility = View.GONE
            } else {
                binding.line.visibility = View.VISIBLE
            }

            binding.tvNamaUniv.text = education.universitas
            binding.tvJurusan.text = education.jurusan
            binding.tvJenjang.text = education.jenjangStudi
            binding.tvTahunLulus.text = education.tahunLulus.toString()

            binding.ivEditEducation.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantEducationViewHolder {
        val binding = CardEducationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParticipantEducationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return education.size
    }

    override fun onBindViewHolder(holder: ParticipantEducationViewHolder, position: Int) {
        val isLast = position == education.size - 1
        holder.bind(education[position], isLast)
    }

}