package com.paint.simplerecyclerview.feature_dates

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.paint.simplerecyclerview.databinding.ActivityTasksBinding
import com.paint.simplerecyclerview.di.ToDoDependencyFactory
import com.paint.simplerecyclerview.feature_dates.state.*
import com.paint.simplerecyclerview.utils.BounceEdgeEffectFactory
import com.paint.simplerecyclerview.utils.hide
import com.paint.simplerecyclerview.utils.show
import com.paint.simplerecyclerview.utils.showInToast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToDoActivity : AppCompatActivity() {

    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var datesAdapter: DatesAdapter

    private val viewModel: ToDoViewModel by viewModels {
        ToDoDependencyFactory(applicationContext).provideToDoViewModelFactory()
    }

    private lateinit var binding: ActivityTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        subscribeObservers()
    }

    private fun initView() {
        datesAdapter = DatesAdapter { position -> viewModel.onItemDateClicked(position) }

        binding.dates.adapter = datesAdapter

        binding.addDate.setOnClickListener { viewModel.onAddDateClicked() }

        tasksAdapter = TasksAdapter(onItemClicked = { id ->
            viewModel.onItemTaskClicked(id)
        }, onDeleteClicked = { id ->
            viewModel.onDeleteTaskClicked(id)
        })

        binding.tasks.adapter = tasksAdapter
        binding.tasks.edgeEffectFactory = BounceEdgeEffectFactory()

        binding.addTask.setOnClickListener { viewModel.onAddTaskClicked() }
    }

    private fun subscribeObservers() {
        lifecycleScope.launch {
            viewModel.dateState.collect(::onDateStateChanged)
        }
        viewModel.taskState.observe(this, ::onTaskStateChanged)
        viewModel.visibleInformation.observe(this, ::onInformationChanged)
    }

    private fun onDateStateChanged(state: DateState) {
        when (state) {
            is DateState.Content -> handleStateDateContent(state)
            is DateState.Empty -> handleStateDateEmpty()
            is DateState.Error -> handleStateDateError(state)
            is DateState.Progress -> handleStateDateProgress()
        }
    }

    private fun handleStateDateContent(state: DateState.Content) {
        datesAdapter.submitList(state.listOfDates)
    }

    private fun handleStateDateEmpty() {
//hide list of dates and show layout empty content
    }

    private fun handleStateDateError(state: DateState.Error) {
        state.throwable.printStackTrace()
    }

    private fun handleStateDateProgress(){
        //Show progress bar
    }

    private fun onTaskStateChanged(state: TaskState) {
        when (state) {
            is TaskContent -> handleStateTaskContent(state)
            is TaskEmpty -> handleStateTaskEmpty()
        }
    }

    private fun handleStateTaskContent(state: TaskContent) {
        tasksAdapter.submitList(state.listOfTasks)
        binding.taskEmpty.hide()
        binding.tasks.show()
    }

    private fun handleStateTaskEmpty() {
        binding.tasks.hide()
        binding.taskEmpty.show()
    }

    private fun onInformationChanged(resName: String) = resName.showInToast(applicationContext)
}