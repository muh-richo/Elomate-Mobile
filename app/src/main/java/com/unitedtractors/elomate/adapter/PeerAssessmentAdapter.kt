package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.R
import com.unitedtractors.elomate.data.network.response.DataAssessment
import com.unitedtractors.elomate.databinding.CardPeerAssessmentBinding

class PeerAssessmentAdapter(
    private var peerAssessmentList: List<DataAssessment?>?,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<PeerAssessmentAdapter.PeerAssessmentViewHolder>() {

    inner class PeerAssessmentViewHolder(private val binding: CardPeerAssessmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: DataAssessment) {
            binding.tvNamaLengkap.text = user.namaLengkap
            binding.tvStatus.text = user.statusPeerAssessment

            if (user.statusPeerAssessment == "Incomplete") {
                binding.ivStatus.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.error_500)
                )
                binding.tvStatus.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.error_500)
                )
            }

            binding.root.setOnClickListener {
                user.userId?.let { it1 -> onItemClicked(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeerAssessmentViewHolder {
        val binding = CardPeerAssessmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PeerAssessmentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return peerAssessmentList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PeerAssessmentViewHolder, position: Int) {
        peerAssessmentList?.get(position)?.let { user ->
            holder.bind(user)
        }
    }
}