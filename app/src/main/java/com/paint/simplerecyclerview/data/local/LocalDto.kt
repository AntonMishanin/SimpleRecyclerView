package com.paint.simplerecyclerview.data.local

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DateDto(
    @PrimaryKey
    var id: String = "",
    var tasks: RealmList<TaskDto> = RealmList()
): RealmObject()

open class TaskDto(
    @PrimaryKey
    var id: String = ""
): RealmObject()