package com.paint.simplerecyclerview.data.realm

import io.realm.RealmList
import io.realm.RealmObject

data class DateDto(
    val id: String,
    val tasks: RealmList<TaskDto>
): RealmObject()

data class TaskDto(
    var id: String = ""
): RealmObject()