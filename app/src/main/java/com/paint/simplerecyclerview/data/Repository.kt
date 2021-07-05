package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.ItemUi
import com.paint.simplerecyclerview.data.local.LocalDataSource

class Repository(
    private val localDataSource: LocalDataSource
) {

    fun add(itemUi: ItemUi) = localDataSource.add(itemUi)

    fun getAll(): List<ItemUi> = localDataSource.getAll()
}