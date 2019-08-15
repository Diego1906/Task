package com.teste.task.repository

import android.content.Context
import android.database.Cursor
import com.teste.task.constants.DataBaseConstants.*
import com.teste.task.entities.TaskEntity

class TaskRepository private constructor(context: Context) {

    private val mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    // SINGLETON
    companion object {

        private var INSTANCE: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
            return INSTANCE as TaskRepository
        }
    }

    fun getList(userId: Int): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery(
                "SELECT * FROM ${TASK.TABLE_NAME} WHERE ${TASK.COLUMNS.USER_ID} = $userId", null
            )

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.ID))
                    val userId = cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.USER_ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.PRIORITYID))
                    val description = cursor.getString(cursor.getColumnIndex(TASK.COLUMNS.DESCRIPTION))
                    val complete = (cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.COMPLETE)) == 1)
                    val duedate = cursor.getString(cursor.getColumnIndex(TASK.COLUMNS.DUEDATE))

                    list.add(TaskEntity(id, userId, priorityId, description, complete, duedate))
                }
                cursor.close()
            }
        } catch (e: Exception) {
            return list
        }

        return list
    }
}