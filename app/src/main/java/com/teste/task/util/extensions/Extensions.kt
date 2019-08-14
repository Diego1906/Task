package com.teste.task.util.extensions

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

fun Activity.startNewActivity(newActivity: AppCompatActivity) {
    ContextCompat.startActivity(this, Intent(this, newActivity::class.java), null)
    this.finish()
}