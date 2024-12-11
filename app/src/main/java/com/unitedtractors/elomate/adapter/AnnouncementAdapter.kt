package com.unitedtractors.elomate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unitedtractors.elomate.data.network.response.DataNotifItem
import com.unitedtractors.elomate.data.network.response.NotificationResponse
import com.unitedtractors.elomate.databinding.CardAnnouncementBinding

class AnnouncementAdapter (
    private var announcements: NotificationResponse
) : RecyclerView.Adapter<AnnouncementAdapter.AnnouncementtViewHolder>() {

    inner class AnnouncementtViewHolder(private val binding: CardAnnouncementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataNotifItem) {
            binding.tvTitleAnnouncement.text = item.title
            binding.tvDescriptionAnnouncement.text = item.notificationType
            binding.tvDeadlineAnnouncement.text = item.deadline
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementtViewHolder
    {
        val binding = CardAnnouncementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnnouncementtViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return announcements.dataNotif!!.size
    }

    override fun onBindViewHolder(holder: AnnouncementtViewHolder, position: Int) {
        announcements.dataNotif?.get(position)?.let { holder.bind(it) }
    }
}