package com.teste.task.business

import android.content.Context
import com.teste.task.R
import com.teste.task.constants.DataBaseConstants
import com.teste.task.entities.TaskEntity
import com.teste.task.repository.TaskRepository
import com.teste.task.util.SecurityPreferences
import com.teste.task.util.ValidationException

class TaskBusiness(val context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id: Int) = mTaskRepository.get(id)

    fun getList(taskFilter: Int): MutableList<TaskEntity> {
        val userId =
            mSecurityPreferences.getStoredString(DataBaseConstants.TASK.COLUMNS.USER_ID).toInt()

        return mTaskRepository.getList(userId, taskFilter)
    }

    fun insert(taskEntity: TaskEntity) {
        validateFields(taskEntity)

        mTaskRepository.insert(taskEntity)
    }

    fun update(taskEntity: TaskEntity) {
        validateFields(taskEntity)

        mTaskRepository.update(taskEntity)
    }

    private fun validateFields(taskEntity: TaskEntity) {
        if (taskEntity.description.isEmpty() || taskEntity.duedate.isEmpty()) {
            throw ValidationException(context.getString(R.string.necessario_preencher_todos_campos))
        }
    }

    fun delete(taskId: Int) = mTaskRepository.delete(taskId)

    fun complete(taskId: Int, complete: Boolean) = mTaskRepository.complete(taskId, complete)
}