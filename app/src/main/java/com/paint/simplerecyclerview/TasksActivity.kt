package com.paint.simplerecyclerview

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.paint.simplerecyclerview.data.Repository
import com.paint.simplerecyclerview.di.TasksDependencyFactory
import com.paint.simplerecyclerview.entity.DateUiEntity
import com.paint.simplerecyclerview.entity.TaskUi
import java.util.*

class TasksActivity : AppCompatActivity() {

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var datesAdapter: DatesAdapter

    private var listOfDates: List<DateUiEntity> = emptyList()
    private var listOfTasks: List<TaskUi> = emptyList()

    private val repository: Repository by lazy {
        TasksDependencyFactory(applicationContext).provideRepository()
    }

    private var selectedDateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        initListOfDates()
        initListOfTasks()
    }

    private fun initListOfDates() {
        datesAdapter = DatesAdapter { position ->
            selectedDateId = listOfDates[position].id
            repository.getTasksByDateId(selectedDateId, onSuccess = { listOfTasks ->
                this.listOfTasks = listOfTasks
                tasksAdapter.submitList(listOfTasks)
            })
        }
        val dates = findViewById<RecyclerView>(R.id.dates)
        dates.adapter = datesAdapter

        val addDate = findViewById<Button>(R.id.add_date)
        addDate.setOnClickListener { onAddDateClicked() }

        repository.getDates { listOfDates ->
            this.listOfDates = listOfDates
            datesAdapter.submitList(listOfDates)
        }
    }

    private fun initListOfTasks() {
        tasksAdapter = TasksAdapter({ position ->
            onItemClicked(position)
        }, { position ->
            onDeleteClicked(position)
        })

        val tasks = findViewById<RecyclerView>(R.id.tasks)
        tasks.adapter = tasksAdapter
        tasks.edgeEffectFactory = BounceEdgeEffectFactory()

        val addTask = findViewById<Button>(R.id.add_task)
        addTask.setOnClickListener { onAddTaskClicked() }
    }

    private fun onAddDateClicked() {
        val date = DateUiEntity(id = UUID.randomUUID().toString(), tasks = emptyList())
        repository.addDate(date, onSuccess = {
            repository.getDates { listOfDates ->
                this.listOfDates = listOfDates
                datesAdapter.submitList(listOfDates)
            }
        })
    }

    private fun onItemClicked(position: Int) {
        val task = listOfTasks[position]
        val container = listOfTasks.toMutableList()
        container[position] = when (task.isChecked) {
            true -> TaskUi(
                id = task.id,
                viewType = INACTIVE_VIEW_TYPE,
                isChecked = false
            )
            false -> TaskUi(
                id = task.id,
                viewType = INACTIVE_VIEW_TYPE,
                isChecked = true
            )
        }
        listOfTasks = container.toList()
        tasksAdapter.submitList(listOfTasks)
    }

    private fun onDeleteClicked(position: Int) {
        var date: DateUiEntity? = null
        for (i in listOfDates.indices) {
            if (selectedDateId == listOfDates[i].id) {
                date = listOfDates[i]
            }
        }

        val taskId = date!!.tasks[position].id
        repository.deleteTaskByIdAndByDateId(taskId, selectedDateId, onSuccess = {
            repository.getTasksByDateId(selectedDateId, onSuccess = { listOfTasks ->
                this.listOfTasks = listOfTasks
                tasksAdapter.submitList(listOfTasks)
            })
        })
    }

    private fun onAddTaskClicked() {
        if (selectedDateId.isEmpty()) {
            Toast.makeText(applicationContext, "Need select date", Toast.LENGTH_SHORT).show()
            return
        }
        val task = TaskUi(
            id = UUID.randomUUID().toString(),
            viewType = INACTIVE_VIEW_TYPE,
            isChecked = true
        )
        repository.addTaskByDateId(task, selectedDateId, onSuccess = {
            repository.getTasksByDateId(selectedDateId, onSuccess = { listOfTasks ->
                this.listOfTasks = listOfTasks
                tasksAdapter.submitList(listOfTasks)
            })
        })
    }
}

