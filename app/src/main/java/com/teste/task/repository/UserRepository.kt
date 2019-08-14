package com.teste.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.teste.task.constants.DataBaseConstants.*

class UserRepository private constructor(context: Context) {

    private val mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    // SINGLETON
    companion object {

        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }
    }

    fun insert(name: String, email: String, password: String): Int {

        val db = mTaskDataBaseHelper.writableDatabase

        val insertValues = ContentValues()
        insertValues.put(USER.COLUMNS.NAME, name)
        insertValues.put(USER.COLUMNS.EMAIL, email)
        insertValues.put(USER.COLUMNS.PASSWORD, password)

        return db.insert(USER.TABLE_NAME, null, insertValues).toInt()
    }

    fun isEmailExistente(email: String): Boolean {

        val retorno: Boolean

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            val projection = arrayOf(USER.COLUMNS.ID)
            val selection = "${USER.COLUMNS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)

            cursor = db.query(
                USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null
            )

            retorno = cursor.count > 0

            cursor.close()

        } catch (e: Exception) {
            throw e
        }

        return retorno
    }
}