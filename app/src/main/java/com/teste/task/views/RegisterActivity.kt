package com.teste.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.teste.task.R
import com.teste.task.business.UserBusiness
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Eventos
        setListeners()

        // Instanciar variáveis da classe
        mUserBusiness = UserBusiness(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave() {

        val name = editName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        mUserBusiness.insert(name, email, password)
    }
}
