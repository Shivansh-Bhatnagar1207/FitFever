package com.example.vitalizeme.adapter


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalizeme.R
import com.example.vitalizeme.constants.PrefConstants
import com.example.vitalizeme.model.WT

class WTAdapter(
    var WTList: MutableList<WT>,
    val marksetComplete: (WT,Int) -> Unit,
    val onCompleteSet: (WT, Int) -> Unit
) : RecyclerView.Adapter<WTAdapter.WTViewHolder>() {



    inner class WTViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val img = itemview.findViewById<ImageView>(R.id.wtImage)
        val title = itemview.findViewById<TextView>(R.id.wtTitle)
        val setCompleted = itemview.findViewById<TextView>(R.id.setCompleted)
        val calBurned = itemview.findViewById<TextView>(R.id.calBurned)
        val calBurnedPerSet = itemview.findViewById<TextView>(R.id.calBurnedPerSet)
        val time = itemview.findViewById<TextView>(R.id.totalTime)
        val MarkSet = itemview.findViewById<ImageButton>(R.id.MarkComplete)
        val recordSet = itemview.findViewById<ImageView>(R.id.RecordSet)
    }

    override fun getItemCount(): Int {
        return WTList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WTViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.wt_card, parent, false)

        return WTViewHolder(view)
    }


    override fun onBindViewHolder(holder: WTViewHolder, position: Int) {
        val item = WTList[position]

        item.let {
            holder.img.setImageResource(item.img)
            holder.title.text = item.title
            holder.calBurnedPerSet.text = "${item.calPerSet} Kcal"
            holder.time.text="${item.timeSpent/60} min"
            holder.calBurned.text="${item.totalCal}"
            holder.setCompleted.text = "${item.set}"
        }

        holder.MarkSet.setOnClickListener {
            marksetComplete(item,position)
        }

        holder.recordSet.setOnClickListener {
            onCompleteSet(item,position)
            holder.calBurned.text = "0"
            holder.time.text = "0"
            holder.setCompleted.text="0"
        }

    }


}