package com.teste.task.repository

import android.content.Context

class UserRepository private constructor(context: Context) {

    private val mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }
    }
}