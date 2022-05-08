package com.example.todolistappfirstcopy.tasks

data class Task(
    var taskid: String? = null,
    var title: String? = null,
    var description: String? = null,
    var dateTimeInMillis: Long,
    var notification : Boolean? = false,
    var notificationId: Int? = 0,
    var file: String? = null,
    var isFinished: Boolean = false ,
    var isDeleted : Boolean = false,
)
