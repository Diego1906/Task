package com.teste.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.teste.task.constants.DataBaseConstants.*

class TaskDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "task.db"
    }

    // SQLite
    // Tipos suportados: INTEGER, REAL, TEXT e BLOB

    private val mCreateTableUser =
        """CREATE TABLE ${USER.TABLE_NAME} ( 
                ${USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${USER.COLUMNS.NAME} TEXT,
                ${USER.COLUMNS.EMAIL} TEXT,
                ${USER.COLUMNS.PASSWORD} TEXT 
            );"""

    private val mCreateTablePriority =
        """CREATE TABLE ${PRIORITY.TABLE_NAME} ( 
                ${PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY,
                ${PRIORITY.COLUMNS.DESCRIPTION} TEXT
            );"""

    private val mCreateTableTask =
        """CREATE TABLE ${TASK.TABLE_NAME} ( 
                ${TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${TASK.COLUMNS.USER_ID} INTEGER,
                ${TASK.COLUMNS.PRIORITY_ID} INTEGER,
                ${TASK.COLUMNS.DESCRIPTION} TEXT,
                ${TASK.COLUMNS.COMPLETE} INTEGER,
                ${TASK.COLUMNS.DUEDATE} TEXT
            );"""

    private val mInsertPriorities =
        """INSERT INTO ${PRIORITY.TABLE_NAME} VALUES(1, 'Baixa'), (2, 'Média'), (3, 'Alta'), (4, 'Crítica')"""

    private val mDeleteTableUser = "DROP TABLE IF EXISTS ${USER.TABLE_NAME}"
    private val mDeleteTablePriority = "DROP TABLE IF EXISTS ${PRIORITY.TABLE_NAME}"
    private val mDeleteTableTask = "DROP TABLE IF EXISTS ${TASK.TABLE_NAME}"

    // EVENTO EXECUTADO NA PRIMEIRA VEZ QUE O BANCO DE DADOS É CRIADO
    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(mCreateTableUser)
        sqlLite.execSQL(mCreateTablePriority)
        sqlLite.execSQL(mCreateTableTask)
        sqlLite.execSQL(mInsertPriorities)
    }

    // EVENTO EXECUTADO SEMPRE QUE O BANCO DE DADOS JÁ EXISTIR E O MESMO FOR RECEBER ATUALIZAÇÃO NA ESTRUTURA
    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Delete tables
        sqlLite.execSQL(mDeleteTableUser)
        sqlLite.execSQL(mDeleteTablePriority)
        sqlLite.execSQL(mDeleteTableTask)

        // Create tables
        sqlLite.execSQL(mCreateTableUser)
        sqlLite.execSQL(mCreateTablePriority)
        sqlLite.execSQL(mCreateTableTask)

        sqlLite.execSQL(mInsertPriorities)
    }
}