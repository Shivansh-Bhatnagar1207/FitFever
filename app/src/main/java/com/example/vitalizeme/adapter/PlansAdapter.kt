package com.example.vitalizeme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalizeme.R
import com.example.vitalizeme.model.Plans

class PlansAdapter(var plans : List<Plans>, private val onclick: (Plans)->Unit) : RecyclerView.Adapter<PlansAdapter.PlansViewHolder>() {
    inner class PlansViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val images = itemview.findViewById<ImageView>(R.id.PlanImage)
        val title = itemview.findViewById<TextView>(R.id.PlanTitle)
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlansViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_item,parent,false)
        return PlansViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        val item = plans[position]

        holder.images.setImageResource(item.planImage)
        holder.title.text =  item.planTitle


        holder.itemView.setOnClickListener {
            onclick(item)
        }
    }



}