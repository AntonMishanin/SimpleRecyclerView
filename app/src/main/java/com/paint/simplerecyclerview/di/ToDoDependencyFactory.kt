package com.paint.simplerecyclerview.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paint.simplerecyclerview.feature_dates.ToDoViewModel
import com.paint.simplerecyclerview.data.DatesRepositoryImpl
import com.paint.simplerecyclerview.data.local.LocalDataSourceImpl
import com.paint.simplerecyclerview.domain.repository.DatesRepository
import com.paint.simplerecyclerview.domain.usecase.*
import io.realm.Realm
import io.realm.RealmConfiguration


class ToDoDependencyFactory(context: Context) {

    init {
        Realm.init(context)
    }

    fun provideToDoViewModelFactory(): ToDoViewModelFactory {
        val repository = provideRepository()

        val addDateUseCase = AddDateUseCase(repository)
        val addTaskByDateIdUseCase = AddTaskByDateIdUseCase(repository)
        val deleteTaskByDateIdUseCase = DeleteTaskByIdUseCase(repository)
        val getDateByIdUseCase = GetDateByIdUseCase(repository)
        val getDatesUseCase = GetDatesUseCase(repository)
        val getTasksByDateIdUseCase = GetTasksByDateIdUseCase(repository)
        val observeChangesListOfDatesUseCase = ObserveChangesListOfDatesUseCase(repository)

        return ToDoViewModelFactory(
            addDateUseCase,
            addTaskByDateIdUseCase,
            deleteTaskByDateIdUseCase,
            getDateByIdUseCase,
            getDatesUseCase,
            getTasksByDateIdUseCase,
            observeChangesListOfDatesUseCase
        )
    }

    private fun provideRepository(): DatesRepository {
        val realm = provideRealm()
        val localDataSource = LocalDataSourceImpl(realm)
        return DatesRepositoryImpl(localDataSource = localDataSource)
    }

    private fun provideRealm() = try {
        Realm.getDefaultInstance()
    } catch (e: Exception) {
        e.printStackTrace()
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.getInstance(config)
    }
}

class ToDoViewModelFactory(
    private val addDateUseCase: AddDateUseCase,
    private val addTaskByDateIdUseCase: AddTaskByDateIdUseCase,
    private val deleteTaskByDateIdUseCase: DeleteTaskByIdUseCase,
    private val getDateByIdUseCase: GetDateByIdUseCase,
    private val getDatesUseCase: GetDatesUseCase,
    private val getTasksByDateIdUseCase: GetTasksByDateIdUseCase,
    private val observeChangesListOfDatesUseCase: ObserveChangesListOfDatesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(
                addDateUseCase,
                addTaskByDateIdUseCase,
                deleteTaskByDateIdUseCase,
                getDateByIdUseCase,
                getDatesUseCase,
                getTasksByDateIdUseCase,
                observeChangesListOfDatesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}