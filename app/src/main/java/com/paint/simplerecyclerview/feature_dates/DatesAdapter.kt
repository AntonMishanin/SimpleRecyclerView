package com.paint.simplerecyclerview.feature_dates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.R
import com.paint.simplerecyclerview.entity.DateUi

class DatesAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : ListAdapter<DateUi, DatesAdapter.ItemViewHolder>(DatesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_date, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClicked(position) }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(date: DateUi) {
        }
    }
}

class DatesDiffCallback : DiffUtil.ItemCallback<DateUi>() {
    override fun areItemsTheSame(
        oldTask: DateUi,
        newTask: DateUi
    ): Boolean {
        return oldTask.id == newTask.id
    }

    override fun areContentsTheSame(
        oldTask: DateUi,
        newTask: DateUi
    ): Boolean {
        return oldTask == newTask
    }
}