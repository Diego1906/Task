package com.teste.task.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

//fun Activity.startNewActivity(newActivity: AppCompatActivity) {
//    ContextCompat.startActivity(this, Intent(this, newActivity::class.java), null)
//    this.finish()
//}

fun Fragment.starNewActivity(newActivity: AppCompatActivity) {
    startActivity(Intent(context, newActivity::class.java))
}

fun Activity.startNewActivity(newActivity: AppCompatActivity) {
    startActivity(Intent(this, newActivity::class.java))
    this.finish()
}