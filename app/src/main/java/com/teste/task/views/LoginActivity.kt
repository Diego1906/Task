package com.teste.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.teste.task.R
import com.teste.task.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)

        setListeners()
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

    private fun handleLogin() {
        val email = editEmailLogin.text.toString()
        val password = editPasswordLogin.text.toString()

        if (!mUserBusiness.login(email, password)) {
            Snackbar.make(loginLayout, getString(R.string.usuario_senha_incorretos), Snackbar.LENGTH_LONG).show()
            return
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
