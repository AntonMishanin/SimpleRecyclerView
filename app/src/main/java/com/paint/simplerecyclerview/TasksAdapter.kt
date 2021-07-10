package com.paint.simplerecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.entity.ActiveTaskEntity
import com.paint.simplerecyclerview.entity.InactiveTaskEntity
import com.paint.simplerecyclerview.entity.TaskEntity

private const val INACTIVE_VIEW_TYPE = 0
private const val ACTIVE_VIEW_TYPE = 1

class TasksAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    private val onDeleteClicked: (position: Int) -> Unit
) : ListAdapter<TaskEntity, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is InactiveTaskEntity -> INACTIVE_VIEW_TYPE
        is ActiveTaskEntity -> ACTIVE_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            INACTIVE_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_task_inactive, parent, false)
                InactiveViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_task_active, parent, false)
                ActiveViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position)) {
            is InactiveTaskEntity -> {
                val inactiveViewHolder = holder as InactiveViewHolder
                inactiveViewHolder.bind(getItem(position) as InactiveTaskEntity)
                inactiveViewHolder.itemView.setOnClickListener { onItemClicked(position) }
                inactiveViewHolder.delete.setOnClickListener { onDeleteClicked(position) }
            }
            is ActiveTaskEntity -> {
                val activeViewHolder = holder as ActiveViewHolder
                activeViewHolder.bind(getItem(position) as ActiveTaskEntity)
                activeViewHolder.itemView.setOnClickListener { onItemClicked(position) }
            }
        }
    }

    class InactiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val delete: ImageView = view.findViewById(R.id.delete)

        fun bind(taskEntity: InactiveTaskEntity) = with(itemView) {

        }
    }

    class ActiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(taskEntity: ActiveTaskEntity) = with(itemView) {

        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
    override fun areItemsTheSame(
        oldTaskEntity: TaskEntity,
        newTaskEntity: TaskEntity
    ): Boolean {
        return oldTaskEntity.id == newTaskEntity.id
    }

    override fun areContentsTheSame(
        oldTaskEntity: TaskEntity,
        newTaskEntity: TaskEntity
    ): Boolean {
        return oldTaskEntity == newTaskEntity
    }
}