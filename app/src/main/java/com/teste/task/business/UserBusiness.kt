package com.teste.task.business

import android.content.Context
import com.teste.task.repository.UserRepository

class UserBusiness(val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)

    private var userId: Int = 0

    fun insert(name: String, email: String, password: String) {

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            userId = mUserRepository.insert(name, email, password)
        }
    }
}
