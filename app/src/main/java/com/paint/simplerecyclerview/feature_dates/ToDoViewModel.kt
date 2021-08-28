package com.paint.simplerecyclerview.feature_dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paint.simplerecyclerview.data.DatesRepository
import com.paint.simplerecyclerview.entity.DateUi
import com.paint.simplerecyclerview.entity.TaskUi
import com.paint.simplerecyclerview.feature_dates.state.*
import com.paint.simplerecyclerview.utils.SingleLiveEvent
import com.paint.simplerecyclerview.utils.getUniqueId
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val DATE_NOT_SELECTED = ""

class ToDoViewModel(
    private val datesRepository: DatesRepository
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
            datesRepository.observeChangesListOfDates()
                .map(::mapListOfDates)
                .catch { emit(DateState.Error(it)) }
                .collect { _dateState.value = it }
        }
    }

    private fun mapListOfDates(dates: List<DateUi>): DateState {
        handleChangedTaskState(dates)

        return when (dates.isEmpty()) {
            true -> DateState.Empty
            false -> DateState.Content(dates)
        }
    }

    private fun handleChangedTaskState(dates: List<DateUi>){
        if (selectedDateId == DATE_NOT_SELECTED) {
            _taskState.value = TaskEmpty
        } else {
            for (i in dates.indices) {
                if (selectedDateId == dates[i].id) {
                    _taskState.value = when (dates[i].tasks.isEmpty()) {
                        true -> TaskEmpty
                        false -> TaskContent(dates[i].tasks)
                    }
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

        datesRepository.getTasksByDateId(selectedDateId, onSuccess = { listOfTasks ->
            _taskState.value = when (listOfTasks.isEmpty()) {
                true -> TaskEmpty
                false -> TaskContent(listOfTasks)
            }
        })
    }

    fun onAddDateClicked() {
        val date = DateUi(id = getUniqueId(), tasks = emptyList())
        datesRepository.addDate(date)
    }

    fun onItemTaskClicked(id: String) {
        val container = when (_taskState.value) {
            is TaskContent -> (_taskState.value as TaskContent).listOfTasks.toMutableList()
            is TaskEmpty -> return
            null -> return
        }

        val position = getTaskPositionById(id) ?: return
        container[position] = when (container[position].isChecked) {
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
        _taskState.value = TaskContent(container)
    }

    fun onDeleteTaskClicked(id: String) {
        datesRepository.deleteTaskById(id)
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
        datesRepository.addTaskByDateId(task, selectedDateId)
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