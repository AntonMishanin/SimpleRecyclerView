package com.paint.simplerecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.entity.TaskUi
import java.lang.IllegalArgumentException

const val INACTIVE_VIEW_TYPE = 0
const val ACTIVE_VIEW_TYPE = 1

class TasksAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    private val onDeleteClicked: (position: Int) -> Unit
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
                inactiveViewHolder.onBind(getItem(position))
                inactiveViewHolder.itemView.setOnClickListener { onItemClicked(position) }
                inactiveViewHolder.delete.setOnClickListener { onDeleteClicked(position) }
            }
            ACTIVE_VIEW_TYPE -> {
                val activeViewHolder = holder as ActiveViewHolder
                activeViewHolder.onBind(getItem(position))
                activeViewHolder.itemView.setOnClickListener { onItemClicked(position) }
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
            inactiveViewHolder.onBind(getItem(position), payloads)
        }
    }

    class InactiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val delete: ImageView = view.findViewById(R.id.delete)
        private val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        fun onBind(taskEntity: TaskUi) = with(itemView) {
            //checkBox.post {
                checkBox.isChecked = taskEntity.isChecked
                //checkBox.isSelected = true
            //}
        }

        fun onBind(taskEntity: TaskUi, payloads: List<Any>) = with(itemView) {
            Log.d("WW", "PAYLOADSIZE = ${payloads.size}")
            //checkBox.post {
                val isChecked = payloads.last() as Boolean
                checkBox.isChecked = isChecked
                //checkBox.isSelected = true
            //}
        }
    }

    class ActiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        fun onBind(taskEntity: TaskUi) = with(itemView) {
            //checkBox.post {
                checkBox.isChecked = taskEntity.isChecked
                //checkBox.isSelected = false
           // }
            //checkBox.jumpDrawablesToCurrentState()
        }

        fun onBind(taskEntity: TaskUi, payloads: MutableList<Any>) = with(itemView) {
           // checkBox.post {
                val isChecked = payloads.last() as Boolean
                checkBox.isChecked = isChecked
                //checkBox.isSelected = false
            //}
            //checkBox.jumpDrawablesToCurrentState()
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
        Log.d("WW", "getChangePayload = ${oldItem.isChecked != newItem.isChecked}")
        if (oldItem.isChecked != newItem.isChecked) return newItem.isChecked
        return super.getChangePayload(oldItem, newItem)
    }
}