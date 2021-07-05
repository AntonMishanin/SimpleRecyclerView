package com.paint.simplerecyclerview.data.local

import com.paint.simplerecyclerview.ItemUi
import com.paint.simplerecyclerview.data.dto.ItemDto
import com.paint.simplerecyclerview.data.toItemDto
import com.paint.simplerecyclerview.data.toItemUi
import java.util.*
import kotlin.collections.ArrayList

class LocalDataSource {

    private val list: MutableList<ItemDto> = ArrayList()

    fun add(itemUi: ItemUi) {
        val itemDto = itemUi.toItemDto()
        list.add(itemDto)
    }

    fun removeById(id: String) {

    }

    fun getAll(): List<ItemUi> = list.map { itemDto -> itemDto.toItemUi() }
}