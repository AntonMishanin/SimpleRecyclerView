package com.paint.simplerecyclerview.feature_dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paint.simplerecyclerview.domain.entity.DateDomain
import com.paint.simplerecyclerview.domain.entity.TaskDomain
import com.paint.simplerecyclerview.domain.usecase.*
import com.paint.simplerecyclerview.feature_dates.entity.*
import com.paint.simplerecyclerview.feature_dates.state.*
import com.paint.simplerecyclerview.utils.SingleLiveEvent
import com.paint.simplerecyclerview.utils.getUniqueId
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val DATE_NOT_SELECTED = ""

class ToDoViewModel(
    private val addDateUseCase: AddDateUseCase,
    private val addTaskByDateIdUseCase: AddTaskByDateIdUseCase,
    private val deleteTaskByDateIdUseCase: DeleteTaskByIdUseCase,
    private val getDateByIdUseCase: GetDateByIdUseCase,
    private val getDatesUseCase: GetDatesUseCase,
    private val getTasksByDateIdUseCase: GetTasksByDateIdUseCase,
    private val observeChangesListOfDatesUseCase: ObserveChangesListOfDatesUseCase
) : ViewModel() {

    private val _dateState = MutableStateFlow<DateState>(DateState.Progress)
    val dateState: StateFlow<DateState> = _dateState

    private val _taskState = MutableLiveData<TaskState>()
    val taskState: LiveData<TaskState> = _taskState

    private val _visibleInformation = SingleLiveEvent<String>()
    val visibleInformation: LiveData<String> = _visibleInformation

    private var selectedDateId = DATE_NOT_SELECTED

    init {
        viewModelScope.launch {
            observeChangesListOfDatesUseCase()
                .map(::mapListOfDates)
                .catch { emit(DateState.Error(it)) }
                .collect { _dateState.value = it }
        }
    }

    private fun mapListOfDates(datesDomain: List<DateDomain>): DateState {
        val datesUi = datesDomain.map { dateDomain -> dateDomain.toDateUi() }

        handleChangedTaskState(datesUi)

        return when (datesDomain.isEmpty()) {
            true -> DateState.Empty
            false -> DateState.Content(datesUi)
        }
    }

    private fun handleChangedTaskState(dates: List<DateUi>) {
        if (selectedDateId == DATE_NOT_SELECTED) {
            _taskState.value = TaskEmpty
        } else {
            updateTaskState(dates)
        }
    }

    private fun updateTaskState(dates: List<DateUi>) {
        for (i in dates.indices) {
            if (selectedDateId == dates[i].id) {
                _taskState.value = when (dates[i].tasks.isEmpty()) {
                    true -> TaskEmpty
                    false -> TaskContent(dates[i].tasks)
                }
            }
        }
    }

    fun onItemDateClicked(position: Int) {
        selectedDateId = when (_dateState.value) {
            is DateState.Content -> (_dateState.value as DateState.Content).listOfDates[position].id
            is DateState.Empty -> return
            is DateState.Error -> return
            is DateState.Progress -> return
        }

        getTasksByDateIdUseCase(selectedDateId, ::mapListOfTasksToState)
    }

    private fun mapListOfTasksToState(listOfTasks: List<TaskDomain>) {
        _taskState.value = when (listOfTasks.isEmpty()) {
            true -> TaskEmpty
            false -> TaskContent(listOfTasks.map { taskDomain -> taskDomain.toTaskUi() })
        }
    }

    fun onAddDateClicked() {
        val date = DateUi(id = getUniqueId(), tasks = emptyList())
        addDateUseCase(date.toDateDomain())
    }

    fun onItemTaskClicked(id: String) {
        val container = when (_taskState.value) {
            is TaskContent -> (_taskState.value as TaskContent).listOfTasks.toMutableList()
            is TaskEmpty -> return
            null -> return
        }

        val position = getTaskPositionById(id) ?: return
        container[position] = toggleTaskCheckedField(id, container[position])

        _taskState.value = TaskContent(container)
    }

    private fun toggleTaskCheckedField(id: String, taskUi: TaskUi): TaskUi{
       return when (taskUi.isChecked) {
            true -> TaskUi(
                id = id,
                viewType = INACTIVE_VIEW_TYPE,
                isChecked = false
            )
            false -> TaskUi(
                id = id,
                viewType = INACTIVE_VIEW_TYPE,
                isChecked = true
            )
        }
    }

    fun onDeleteTaskClicked(id: String) {
        deleteTaskByDateIdUseCase(id)
    }

    fun onAddTaskClicked() {
        if (selectedDateId == DATE_NOT_SELECTED) {
            _visibleInformation.value = "need_select_date"
            return
        }
        val task = TaskUi(
            id = getUniqueId(),
            viewType = INACTIVE_VIEW_TYPE,
            isChecked = true
        )
        addTaskByDateIdUseCase(task.toTaskDomain(), selectedDateId)
    }

    private fun getTaskPositionById(id: String): Int? {
        val container = when (_taskState.value) {
            is TaskContent -> (_taskState.value as TaskContent).listOfTasks
            is TaskEmpty -> return null
            null -> return null
        }

        for (i in container.indices) {
            if (container[i].id == id) return i
        }
        return null
    }
}