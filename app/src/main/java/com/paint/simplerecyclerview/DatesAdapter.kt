package com.paint.simplerecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.entity.DateUiEntity

class DatesAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : ListAdapter<DateUiEntity, DatesAdapter.ItemViewHolder>(DatesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_date, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DatesAdapter.ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { onItemClicked(position) }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(date: DateUiEntity) = with(itemView) {
            // TODO: Bind the data with View

            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}

class DatesDiffCallback : DiffUtil.ItemCallback<DateUiEntity>() {
    override fun areItemsTheSame(
        oldTaskEntity: DateUiEntity,
        newTaskEntity: DateUiEntity
    ): Boolean {
        return oldTaskEntity.id == newTaskEntity.id
    }

    override fun areContentsTheSame(
        oldTaskEntity: DateUiEntity,
        newTaskEntity: DateUiEntity
    ): Boolean {
        return oldTaskEntity == newTaskEntity
    }
}