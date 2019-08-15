package com.teste.task.repository

import android.content.Context
import android.database.Cursor
import com.teste.task.constants.DataBaseConstants.*
import com.teste.task.entities.PriorityEntity

class PriorityRepository private constructor(context: Context) {

    private val mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    // SINGLETON
    companion object {

        private var INSTANCE: PriorityRepository? = null

        fun getInstance(context: Context): PriorityRepository {
            if (INSTANCE == null) {
                INSTANCE = PriorityRepository(context)
            }
            return INSTANCE as PriorityRepository
        }
    }

    fun getList(): MutableList<PriorityEntity> {

        val list = mutableListOf<PriorityEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${PRIORITY.TABLE_NAME}", null)

            if (cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(PRIORITY.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(PRIORITY.COLUMNS.DESCRIPTION))

                    list.add(PriorityEntity(id, description))
                }
                cursor.close()
            }
        } catch (e: Exception) {
            return list
        }

        return list
    }
}