package com.paint.simplerecyclerview.feature_dates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.R
import com.paint.simplerecyclerview.entity.TaskUi
import java.lang.IllegalArgumentException

const val INACTIVE_VIEW_TYPE = 0
const val ACTIVE_VIEW_TYPE = 1

class TasksAdapter(
    private val onItemClicked: (id: String) -> Unit,
    private val onDeleteClicked: (id: String) -> Unit
) : ListAdapter<TaskUi, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            INACTIVE_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_task_inactive, parent, false)
                InactiveViewHolder(view)
            }
            ACTIVE_VIEW_TYPE -> {
                val view = inflater.inflate(R.layout.item_task_active, parent, false)
                ActiveViewHolder(view)
            }
            else -> throw IllegalArgumentException("View type not found $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).viewType) {
            INACTIVE_VIEW_TYPE -> {
                val inactiveViewHolder = holder as InactiveViewHolder
                inactiveViewHolder.onBind(getItem(position), onItemClicked, onDeleteClicked)
            }
            ACTIVE_VIEW_TYPE -> {
                val activeViewHolder = holder as ActiveViewHolder
                activeViewHolder.onBind(getItem(position), onItemClicked)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val inactiveViewHolder = holder as InactiveViewHolder
            inactiveViewHolder.onBind(payloads)
        }
    }

    class InactiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val delete: ImageView = view.findViewById(R.id.delete)
        private val checkBox = view.findViewById<CheckBox>(R.id.task_checked)

        fun onBind(
            taskEntity: TaskUi,
            onItemClicked: (id: String) -> Unit,
            onDeleteClicked: (id: String) -> Unit
        ) {
            checkBox.isChecked = taskEntity.isChecked

            itemView.setOnClickListener {
                onItemClicked(taskEntity.id)
            }

            delete.setOnClickListener {
                onDeleteClicked(taskEntity.id)
            }
        }

        fun onBind(payloads: List<Any>) {
            val isChecked = payloads.last() as Boolean
            checkBox.isChecked = isChecked
        }
    }

    class ActiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val checkBox = view.findViewById<CheckBox>(R.id.task_checked)

        fun onBind(taskEntity: TaskUi, onItemClicked: (id: String) -> Unit) {
            checkBox.isChecked = taskEntity.isChecked

            itemView.setOnClickListener {
                onItemClicked(taskEntity.id)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<TaskUi>() {
    override fun areItemsTheSame(
        oldTaskEntity: TaskUi,
        newTaskEntity: TaskUi
    ): Boolean {
        return oldTaskEntity.id == newTaskEntity.id
    }

    override fun areContentsTheSame(
        oldTaskEntity: TaskUi,
        newTaskEntity: TaskUi
    ): Boolean {
        return oldTaskEntity == newTaskEntity
    }

    override fun getChangePayload(oldItem: TaskUi, newItem: TaskUi): Any? {
        if (oldItem.isChecked != newItem.isChecked) return newItem.isChecked
        return super.getChangePayload(oldItem, newItem)
    }
}