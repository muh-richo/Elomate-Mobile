package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.UserResponse
import com.unitedtractors.elomate.databinding.CardParticipantBinding

class ParticipantAdapter(
    private var participant: List<UserResponse>
) : RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    inner class ParticipantViewHolder(private val binding: CardParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserResponse) {
            binding.tvNamaLengkap.text = user.namaLengkap
            binding.tvPosisi.text = user.posisi
            binding.tvRole.text = user.roleName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val binding = CardParticipantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ParticipantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return participant.size
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(participant[position])
    }
}