package com.teste.task.business

import android.content.Context
import com.teste.task.R
import com.teste.task.constants.TaskConstants
import com.teste.task.entities.UserEntity
import com.teste.task.repository.UserRepository
import com.teste.task.util.SecurityPreferences
import com.teste.task.util.ValidationException

class UserBusiness(val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean {

        var retorno = false

        val user: UserEntity? = mUserRepository.getLogin(email, password)

        user?.let {
            // Salvar dados do usuário no sharedPreferences
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, it.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.NAME, it.nome)
            mSecurityPreferences.storeString(TaskConstants.KEY.EMAIL, it.email)

            retorno = true
        }

        return retorno
    }

    fun insert(name: String, email: String, password: String) {

        try {
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                throw ValidationException(context.getString(R.string.informe_todos_campos))
            }

            if (mUserRepository.isEmailExistente(email)) {
                throw ValidationException(context.getString(R.string.email_em_uso))
            }

            val userId = mUserRepository.insert(name, email, password)

            // Salvar dados do usuário no sharedPreferences
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.EMAIL, email)

        } catch (e: Exception) {
            throw e
        }
    }
}
