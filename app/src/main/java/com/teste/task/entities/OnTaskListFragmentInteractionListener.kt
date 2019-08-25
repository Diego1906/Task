package com.teste.task.entities

interface OnTaskListFragmentInteractionListener {

    fun onListClick(taskId: Int)
    fun onDeleteClick(taskId: Int)
    fun onCompleteClick(taskId: Int)
    fun onUnCompleteClick(taskId: Int)
}