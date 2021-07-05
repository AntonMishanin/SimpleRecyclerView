package com.paint.simplerecyclerview.data

import com.paint.simplerecyclerview.ItemUi
import com.paint.simplerecyclerview.data.dto.ItemDto

fun ItemDto.toItemUi() = ItemUi(
    id = this.id
)

fun ItemUi.toItemDto() = ItemDto(
    id = this.id
)