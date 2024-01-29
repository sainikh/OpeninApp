package com.example.openinapp.ui.adapters

import android.os.Build
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.R
import com.example.openinapp.data.model.RecentLink
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class LinksAdapter(private val links: List<RecentLink>) :
    RecyclerView.Adapter<LinksAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.link_row, parent, false)
        return LinksAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinksAdapter.ViewHolder, position: Int) {
        val link = links!!.get(position)
        holder.linkName.text = link.title
        holder.linkDate.text = getDate(link.created_at)
        holder.actualLinkUrl.text = link.web_link
        holder.noOfClicks.text = link.total_clicks.toString()
    }

    override fun getItemCount(): Int {
        return links!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val linkName: TextView = itemView.findViewById(R.id.linkNameTextView)
        val linkDate: TextView = itemView.findViewById(R.id.linkDate)
        val noOfClicks: TextView = itemView.findViewById(R.id.noOfClicks)
        val actualLinkUrl: TextView = itemView.findViewById(R.id.actualLinkUrl)
    }

    private fun getDate(time: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val zonedDateTime = ZonedDateTime.parse(
                time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                    .withZone(ZoneOffset.UTC)
            )
            return zonedDateTime.toLocalDate().toString()
        } else {
            return time
        }
    }

}
