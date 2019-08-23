package com.teste.task.repository

import android.content.ContentValues
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

    fun get(id: Int): TaskEntity? {

        var taskEntity: TaskEntity? = null

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase
            val columns = arrayOf(
                TASK.COLUMNS.ID,
                TASK.COLUMNS.USER_ID,
                TASK.COLUMNS.PRIORITYID,
                TASK.COLUMNS.DESCRIPTION,
                TASK.COLUMNS.DUEDATE,
                TASK.COLUMNS.COMPLETE
            )
            val filters = "${TASK.COLUMNS.ID} = ?"
            val valueFilters = arrayOf(id.toString())

            cursor = db.query(TASK.TABLE_NAME, columns, filters, valueFilters, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val taskId = cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.USER_ID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(TASK.COLUMNS.DESCRIPTION))
                val duedate = cursor.getString(cursor.getColumnIndex(TASK.COLUMNS.DUEDATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(TASK.COLUMNS.COMPLETE)) == 1)

                taskEntity = TaskEntity(taskId, userId, priorityId, description, complete, duedate)
            }

            cursor.close()

        } catch (e: Exception) {
            return taskEntity
        }

        return taskEntity
    }

    fun getList(userId: Int, taskFilter: Int): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery(
                """SELECT *
                        FROM ${TASK.TABLE_NAME}
                        WHERE ${TASK.COLUMNS.USER_ID} = $userId
                        AND ${TASK.COLUMNS.COMPLETE} = $taskFilter""",
                null
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

    fun insert(taskEntity: TaskEntity) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase
            val complete = if (taskEntity.complete) 1 else 0

            val insertValues = ContentValues()
            insertValues.put(TASK.COLUMNS.USER_ID, taskEntity.userId)
            insertValues.put(TASK.COLUMNS.PRIORITYID, taskEntity.priorityId)
            insertValues.put(TASK.COLUMNS.DESCRIPTION, taskEntity.description)
            insertValues.put(TASK.COLUMNS.DUEDATE, taskEntity.duedate)
            insertValues.put(TASK.COLUMNS.COMPLETE, complete)

            db.insert(TASK.TABLE_NAME, null, insertValues)

        } catch (e: Exception) {
            throw e
        }
    }

    fun update(taskEntity: TaskEntity) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase
            val complete = if (taskEntity.complete) 1 else 0

            val updateValues = ContentValues()
            updateValues.put(TASK.COLUMNS.USER_ID, taskEntity.userId)
            updateValues.put(TASK.COLUMNS.PRIORITYID, taskEntity.priorityId)
            updateValues.put(TASK.COLUMNS.DESCRIPTION, taskEntity.description)
            updateValues.put(TASK.COLUMNS.DUEDATE, taskEntity.duedate)
            updateValues.put(TASK.COLUMNS.COMPLETE, complete)

            val where = "${TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(taskEntity.id.toString())

            db.update(TASK.TABLE_NAME, updateValues, where, whereArgs)

        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(taskId: Int) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val where = "${TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(taskId.toString())

            db.delete(TASK.TABLE_NAME, where, whereArgs)

        } catch (e: Exception) {
            throw e
        }
    }
}