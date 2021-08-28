package com.paint.simplerecyclerview.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paint.simplerecyclerview.feature_dates.ToDoViewModel
import com.paint.simplerecyclerview.data.DatesRepository
import com.paint.simplerecyclerview.data.local.LocalDataSourceImpl
import io.realm.Realm
import io.realm.RealmConfiguration


class ToDoDependencyFactory(context: Context) {

    init {
        Realm.init(context)
    }

    fun provideToDoViewModelFactory(): ToDoViewModelFactory {
        val repository = provideRepository()
        return ToDoViewModelFactory(repository)
    }

    private fun provideRepository(): DatesRepository {
        val realm = provideRealm()
        val localDataSource = LocalDataSourceImpl(realm)
        return DatesRepository(localDataSource = localDataSource)
    }

    private fun provideRealm() = try {
        Realm.getDefaultInstance()
    } catch (e: Exception) {
        e.printStackTrace()
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.getInstance(config)
    }
}

class ToDoViewModelFactory(private val datesRepository: DatesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoViewModel(datesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}