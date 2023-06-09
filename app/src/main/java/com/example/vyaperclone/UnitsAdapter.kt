package com.example.vyaperclone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UnitsAdapter : RecyclerView.Adapter<UnitsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_items_units_layout, parent, false)
        return UnitsViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnitsViewHolder, position: Int) {

        holder.setData()
    }

    override fun getItemCount(): Int {
        return 1
    }
}