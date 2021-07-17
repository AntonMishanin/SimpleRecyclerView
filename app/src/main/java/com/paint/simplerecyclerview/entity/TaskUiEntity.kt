package com.paint.simplerecyclerview.entity
/*
sealed class TaskEntity(
   open val id: String
)

data class InactiveTaskEntity(
   override val id: String
): TaskEntity(id)

data class ActiveTaskEntity(
    override val id: String
): TaskEntity(id)
 */

data class TaskUi(
    val id: String,
    val viewType: Int,
    val isChecked: Boolean
)