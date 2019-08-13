package com.teste.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.teste.task.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave(){

    }
}
