package com.example.vitalizeme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalizeme.R
import com.example.vitalizeme.model.Banner

class BannerAdapter(var bannerItems: List<Banner>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    inner class BannerViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val title = itemview.findViewById<TextView>(R.id.ImageText)
    }

    override fun getItemCount(): Int {
        return bannerItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = bannerItems[position]
        holder.title.text = item.Title
    }

}