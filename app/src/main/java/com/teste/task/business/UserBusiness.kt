package com.teste.task.business

import android.content.Context
import com.teste.task.repository.UserRepository
import com.teste.task.util.ValidationException

class UserBusiness(context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)

    private var userId: Int = 0

    fun insert(name: String, email: String, password: String) {

        try {

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                throw ValidationException("É necessário preencher todos os campos.")
            }

            userId = mUserRepository.insert(name, email, password)

        } catch (e: Exception) {
            throw e
        }
    }
}
