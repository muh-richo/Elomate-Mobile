package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.KirkpatrickDetailItem
import com.unitedtractors.elomate.databinding.ItemKirkpatrickDetailBinding

class KirkpatrickDetailAdapter(
    private val dataList: List<KirkpatrickDetailItem?>?
) : RecyclerView.Adapter<KirkpatrickDetailAdapter.KirkpatrickDetailViewHolder>() {

    inner class KirkpatrickDetailViewHolder(private val binding: ItemKirkpatrickDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KirkpatrickDetailItem?) {
            if (item == null) return

            binding.tvTitleAssessment.text = item.category ?: "Kategori Tidak Tersedia"

            // Handle Highest Data
            val highestData = item.dataDetail?.highestData
            if (!highestData.isNullOrEmpty()) {
                highestData.getOrNull(0)?.let { firstHighest ->
                    binding.tvHigh1.text = firstHighest.description
                    binding.tvValueSelfHigh1.text = firstHighest.dataLabel?.getOrNull(1)?.averageScore?.toString() ?: "-"
                    binding.tvValuePeerHigh1.text = firstHighest.dataLabel?.getOrNull(0)?.averageScore?.toString() ?: "-"
                }

                highestData.getOrNull(1)?.let { secondHighest ->
                    binding.tvHigh2.text = secondHighest.description
                    binding.tvValueSelfHigh2.text = secondHighest.dataLabel?.getOrNull(1)?.averageScore?.toString() ?: "-"
                    binding.tvValuePeerHigh2.text = secondHighest.dataLabel?.getOrNull(0)?.averageScore?.toString() ?: "-"
                }

                highestData.getOrNull(2)?.let { thirdHighest ->
                    binding.tvHigh3.text = thirdHighest.description
                    binding.tvValueSelfHigh3.text = thirdHighest.dataLabel?.getOrNull(1)?.averageScore?.toString() ?: "-"
                    binding.tvValuePeerHigh3.text = thirdHighest.dataLabel?.getOrNull(0)?.averageScore?.toString() ?: "-"
                }
            }

            // Handle Lowest Data
            val lowestData = item.dataDetail?.lowestData
            if (!lowestData.isNullOrEmpty()) {
                lowestData.getOrNull(0)?.let { firstLowest ->
                    binding.tvLow1.text = firstLowest.description
                    binding.tvValueSelfLow1.text = firstLowest.dataLabel?.getOrNull(1)?.averageScore?.toString() ?: "-"
                    binding.tvValuePeerLow1.text = firstLowest.dataLabel?.getOrNull(0)?.averageScore?.toString() ?: "-"
                }

                lowestData.getOrNull(1)?.let { secondLowest ->
                    binding.tvLow2.text = secondLowest.description
                    binding.tvValueSelfLow2.text = secondLowest.dataLabel?.getOrNull(1)?.averageScore?.toString() ?: "-"
                    binding.tvValuePeerLow2.text = secondLowest.dataLabel?.getOrNull(0)?.averageScore?.toString() ?: "-"
                }

                lowestData.getOrNull(2)?.let { thirdLowest ->
                    binding.tvLow3.text = thirdLowest.description
                    binding.tvValueSelfLow3.text = thirdLowest.dataLabel?.getOrNull(1)?.averageScore?.toString() ?: "-"
                    binding.tvValuePeerLow3.text = thirdLowest.dataLabel?.getOrNull(0)?.averageScore?.toString() ?: "-"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KirkpatrickDetailViewHolder {
        val binding = ItemKirkpatrickDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KirkpatrickDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KirkpatrickDetailViewHolder, position: Int) {
        holder.bind(dataList?.get(position))
    }

    override fun getItemCount(): Int = dataList!!.size
}
