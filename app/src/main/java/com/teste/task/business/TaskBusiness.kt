package com.teste.task.business

import android.content.Context
import com.teste.task.constants.DataBaseConstants
import com.teste.task.entities.TaskEntity
import com.teste.task.repository.TaskRepository
import com.teste.task.util.SecurityPreferences

class TaskBusiness(val context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id: Int) = mTaskRepository.get(id)

    fun getList(taskFilter: Int): MutableList<TaskEntity> {
        val userId =
            mSecurityPreferences.getStoredString(DataBaseConstants.TASK.COLUMNS.USER_ID).toInt()

        return mTaskRepository.getList(userId, taskFilter)
    }

    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)
}