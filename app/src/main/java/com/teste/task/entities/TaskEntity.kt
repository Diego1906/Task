package com.teste.task.entities

data class TaskEntity(
    val id: Int,
    val userId: Int,
    val priorityId: Int,
    val description: String,
    val complete: Boolean,
    val duedate: String
)
