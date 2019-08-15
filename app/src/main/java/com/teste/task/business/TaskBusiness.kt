package com.teste.task.business

import android.content.Context
import com.teste.task.entities.TaskEntity
import com.teste.task.repository.TaskRepository

class TaskBusiness(val context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int): MutableList<TaskEntity> = mTaskRepository.getList(userId)
}