package com.paint.simplerecyclerview.di

import android.content.Context
import com.paint.simplerecyclerview.data.Repository
import com.paint.simplerecyclerview.data.realm.TasksLocalDataSource
import io.realm.Realm
import io.realm.RealmConfiguration


class TasksDependencyFactory(context: Context) {

    init {
        Realm.init(context)
    }

    fun provideRepository(): Repository {
        val realm = provideRealm()
        val localDataSource = TasksLocalDataSource(realm)
        return Repository(localDataSource = localDataSource)
    }

    private fun provideRealm() = try {
        Realm.getDefaultInstance()
    } catch (e: Exception) {
        e.printStackTrace()
        val config = RealmConfiguration.Builder().build()
        Realm.getInstance(config)
    }
}