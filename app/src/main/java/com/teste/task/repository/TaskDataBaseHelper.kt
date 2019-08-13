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
    // Tipos suportados: INTERGER, REAL, TEXT e BLOB
    private val mCreateTableUser =
        """CREATE TABLE ${USER.TABLE_NAME} ( 
                ${USER.COLUMNS.ID} INTERGER PRIMARY KEY AUTOINCREMENT,
                ${USER.COLUMNS.NAME} TEXT,
                ${USER.COLUMNS.EMAIL} TEXT,
                ${USER.COLUMNS.PASSWORD} TEXT 
            );"""

    private val mDeleteTableUser = "DROP TABLE IF EXISTS ${USER.TABLE_NAME}"

    // EVENTO EXECUTADO NA PRIMEIRA VEZ QUE O BANCO DE DADOS É CRIADO
    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(mCreateTableUser)
    }

    // EVENTO EXECUTADO SEMPRE QUE O BANCO DE DADOS JÁ EXISTIR E O MESMO FOR RECEBER ATUALIZAÇÃO NA ESTRUTURA
    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqlLite.execSQL(mDeleteTableUser)
        sqlLite.execSQL(mCreateTableUser)
    }
}