package com.paint.simplerecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.data.Repository
import com.paint.simplerecyclerview.data.local.LocalDataSourceImpl
import com.paint.simplerecyclerview.di.TasksDependencyFactory
import com.paint.simplerecyclerview.entity.*
import java.util.*

class TasksActivity : AppCompatActivity() {

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var datesAdapter: DatesAdapter

    private val repository: Repository by lazy {
        TasksDependencyFactory(applicationContext).provideRepository()
    }

    private var selectedDateId = ""

    private var listOfTasks: List<TaskEntity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        initListOfDates()
        initListOfTasks()
    }

    private fun initListOfDates() {
        datesAdapter = DatesAdapter { position ->
            selectedDateId = repository.getDates()[position].id
            listOfTasks = repository.getTasksByDateId(selectedDateId)
            tasksAdapter.submitList(listOfTasks)
        }
        val dates = findViewById<RecyclerView>(R.id.dates)
        dates.adapter = datesAdapter

        val addDate = findViewById<Button>(R.id.add_date)
        addDate.setOnClickListener { onAddDateClicked() }
    }

    private fun initListOfTasks() {
        tasksAdapter = TasksAdapter({ position ->
            onItemClicked(position)
        }, { position ->
            onDeleteClicked(position)
        })

        val tasks = findViewById<RecyclerView>(R.id.tasks)
        tasks.adapter = tasksAdapter

        val addTask = findViewById<Button>(R.id.add_task)
        addTask.setOnClickListener { onAddTaskClicked() }
    }

    private fun onAddDateClicked() {
        val date = DateUiEntity(id = UUID.randomUUID().toString(), tasks = emptyList())
        repository.addDate(date)
        datesAdapter.submitList(repository.getDates())
    }

    private fun onItemClicked(position: Int) {
        val task = listOfTasks[position]
        val container = listOfTasks.toMutableList()
        container[position] = ActiveTaskEntity(id = task.id)
        listOfTasks = container.toList()
        tasksAdapter.submitList(listOfTasks)
    }

    private fun onDeleteClicked(position: Int) {
        val date = repository.getDateById(selectedDateId)
        if (position >= date.tasks.size) {
            listOfTasks = repository.getTasksByDateId(selectedDateId)
            tasksAdapter.submitList(listOfTasks)
            return
        }

        val taskId = date.tasks[position].id
        repository.deleteTaskByIdAndByDateId(taskId, selectedDateId)
        listOfTasks = repository.getTasksByDateId(selectedDateId)
        tasksAdapter.submitList(listOfTasks)
    }

    private fun onAddTaskClicked() {
        if (selectedDateId.isEmpty()) {
            Toast.makeText(applicationContext, "Need select date", Toast.LENGTH_SHORT).show()
            return
        }
        val task = InactiveTaskEntity(
            id = UUID.randomUUID().toString()
        )
        repository.addTaskByDateId(task, selectedDateId)
        listOfTasks = repository.getTasksByDateId(selectedDateId)
        tasksAdapter.submitList(listOfTasks)
    }
}

