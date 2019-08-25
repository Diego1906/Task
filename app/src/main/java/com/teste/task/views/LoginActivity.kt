package com.teste.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.teste.task.R
import com.teste.task.business.UserBusiness
import com.teste.task.constants.TaskConstants
import com.teste.task.util.SecurityPreferences
import com.teste.task.util.extensions.startNewActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()

        verifyLoggedUser()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                handleLogin()
            }
        }
    }

    private fun setListeners() {
        buttonLogin.setOnClickListener(this)
    }

    private fun verifyLoggedUser() {
        val id = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val nome = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        val email = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)

        // Usuário Logado
        if (id.isNotEmpty() && nome.isNotEmpty() && email.isNotEmpty()) {
            this.startNewActivity(MainActivity())
        }
    }

    private fun handleLogin() {
        val email = editEmailLogin.text.toString()
        val password = editPasswordLogin.text.toString()

        if (!mUserBusiness.login(email, password)) {
            Snackbar.make(loginLayout, getString(R.string.usuario_senha_incorretos), Snackbar.LENGTH_LONG).show()
            return
        }

        this.startNewActivity(MainActivity())
    }
}
