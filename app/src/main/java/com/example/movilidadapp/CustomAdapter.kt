package com.example.movilidadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val visitors: BooleanArray,val windows: Array<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(visitors[position], windows[position], position+1)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return windows.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(visitor: Boolean, window: String, position: Int ) {
            val positionTv = itemView.findViewById(R.id.position) as TextView
            val visitorTv  = itemView.findViewById(R.id.visitor) as TextView
            val stateTv  = itemView.findViewById(R.id.windows) as TextView
            val visitorNTv  = itemView.findViewById(R.id.visitorNumber) as TextView

            positionTv.text = position.toString()
            visitorTv.text = visitor.toString()
            stateTv.text = window.toString()
            visitorNTv.text = position.toString()
        }
    }
}