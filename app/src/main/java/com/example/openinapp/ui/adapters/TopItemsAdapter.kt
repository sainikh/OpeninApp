package com.example.openinapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.R
import com.example.openinapp.ui.data.TopItemsModel


class TopItemsAdapter(private val topThingsList: List<TopItemsModel>,private val context: Context) : RecyclerView.Adapter<TopItemsAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopItemsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.top_things_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: TopItemsAdapter.ViewHolder, position: Int) {
      val item = topThingsList!![position]
        when(item.key){
            "today_clicks" -> {
                holder.key.text =  context.getString(R.string.today_clicks)
                holder.imageCardView.setCardBackgroundColor(R.color.top_select_background)
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.top_clicks))
                holder.imageCardView.radius = 100f
            }
            "top_source" -> {
                holder.key.text = context.getString(R.string.top_source)
                holder.imageCardView.setCardBackgroundColor(R.color.globe_background)
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.globe))
                holder.imageCardView.radius = 100f
            }
            "top_location" -> {
                holder.key.text = context.getString(R.string.top_location)
                holder.imageCardView.setCardBackgroundColor(R.color.pin_background)
                holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.pin))
                holder.imageCardView.radius = 100f
            }
        }
        holder.value.text = item.value
    }

    override fun getItemCount(): Int {
      return topThingsList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val key : TextView = itemView.findViewById(R.id.key)
            val value : TextView = itemView.findViewById(R.id.value)
            val imageView : ImageView = itemView.findViewById(R.id.imageView)
            val imageCardView : CardView = itemView.findViewById(R.id.imageCardView)
    }
}