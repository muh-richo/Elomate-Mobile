package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.KirkpatrickDetail
import com.unitedtractors.elomate.databinding.ItemKirkpatrickDetailBinding

class KirkpatrickDetailAdapter(
    private val details: List<KirkpatrickDetail>
) : RecyclerView.Adapter<KirkpatrickDetailAdapter.KirkpatrickViewHolder>() {

    inner class KirkpatrickViewHolder(val binding: ItemKirkpatrickDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: KirkpatrickDetail) {
            binding.apply {
                tvTitleAssessment.text = detail.category

                val highestItems = detail.highestData
                tvHigh1.text = highestItems.getOrNull(0)?.first ?: ""
                tvValueSelfHigh1.text = highestItems.getOrNull(0)?.second ?: ""
                tvValuePeerHigh1.text = highestItems.getOrNull(0)?.third ?: ""
                tvHigh2.text = highestItems.getOrNull(1)?.first ?: ""
                tvValueSelfHigh2.text = highestItems.getOrNull(1)?.second ?: ""
                tvValuePeerHigh2.text = highestItems.getOrNull(1)?.third ?: ""
                tvHigh3.text = highestItems.getOrNull(2)?.first ?: ""
                tvValueSelfHigh3.text = highestItems.getOrNull(2)?.second ?: ""
                tvValuePeerHigh3.text = highestItems.getOrNull(2)?.third ?: ""

                val lowestItems = detail.lowestData
                tvLow1.text = lowestItems.getOrNull(0)?.first ?: ""
                tvValueSelfLow1.text = lowestItems.getOrNull(0)?.second ?: ""
                tvValuePeerLow1.text = highestItems.getOrNull(0)?.third ?: ""
                tvLow2.text = lowestItems.getOrNull(1)?.first ?: ""
                tvValueSelfLow2.text = lowestItems.getOrNull(1)?.second ?: ""
                tvValuePeerLow2.text = highestItems.getOrNull(1)?.third ?: ""
                tvLow3.text = lowestItems.getOrNull(2)?.first ?: ""
                tvValueSelfLow3.text = lowestItems.getOrNull(2)?.second ?: ""
                tvValuePeerLow3.text = highestItems.getOrNull(2)?.third ?: ""
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KirkpatrickViewHolder {
        val binding = ItemKirkpatrickDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return KirkpatrickViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KirkpatrickViewHolder, position: Int) {
        holder.bind(details[position])
    }

    override fun getItemCount(): Int = details.size
}
