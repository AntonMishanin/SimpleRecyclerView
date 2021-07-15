package com.paint.simplerecyclerview.data.realm

import io.realm.RealmList
import io.realm.RealmObject

open class DateDto(
    var id: String = "",
    var tasks: RealmList<TaskDto> = RealmList()
): RealmObject()

open class TaskDto(
    var id: String = ""
): RealmObject()